package mybatis.log.to.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wjq
 * @since 2022/11/16
 */
public class Service {
    public static final Pattern NULL_PATTERN = Pattern.compile("null((\\, )|\\z)");
    public static final Pattern TYPE_PATTERN = Pattern.compile("\\((\\p{Upper}\\p{Alpha}*)\\)((\\, )|\\z)");

    public static final Param NULL_PARAM = new Param("null", "null");

    String[] logs;

    public String convert(String sourceLog) {
        logs = sourceLog.split("\n");
        if (logs.length >= 2) {
            String preparing = null;
            String parameters = null;
            for (String log : logs) {
                if (preparing == null) {
                    preparing = indexOf(log, "==>  Preparing: ");
                } else {
                    parameters = indexOf(log, "==> Parameters: ");
                    if (parameters != null) break;
                }
            }
            if (preparing != null && parameters != null) {
                List<Param> params = parseParam(parameters);
                Iterator<Param> iterator = params.iterator();
                StringBuilder builder = new StringBuilder();
                int start = 0;
                int end = preparing.length();
                while (start < end) {
                    char c = preparing.charAt(start);
                    if (c == '?') {
                        if (iterator.hasNext()) {
                            Param param = iterator.next();
                            if ("Integer".equals(param.type) || "Long".equals(param.type)) {
                                builder.append(param.value);
                            } else {
                                builder.append('\'');
                                builder.append(param.value);
                                builder.append('\'');
                            }
                        } else {
                            return "参数个数不匹配";
                        }
                    } else {
                        builder.append(c);
                    }
                    start++;
                }
                return builder.toString();
            }
        }
        return "日志格式不正确";
    }


    String indexOf(String str, String pattern) {
        int parIndex = str.indexOf(pattern);
        if (parIndex != -1) {
            return str.substring(parIndex + pattern.length());
        }
        return null;
    }

    List<Param> parseParam(String log) {
        List<Param> result = new ArrayList<>();
        Matcher nullMatcher = NULL_PATTERN.matcher(log);
        Matcher typeMatcher = TYPE_PATTERN.matcher(log);
        int start = 0;
        int end = log.length();
        while (start < end) {
            nullMatcher.region(start, end);
            if (nullMatcher.lookingAt()) {
                result.add(NULL_PARAM);
            } else if (typeMatcher.find(start)) {
                result.add(new Param(log.substring(start, typeMatcher.start()), typeMatcher.group(1)));
                start = typeMatcher.end();
            } else {
                start++;
            }
        }
        return result;
    }

    static class Param {

        final String type;
        final String value;

        public Param(String value, String type) {
            this.type = type;
            this.value = value;
        }
    }
}
