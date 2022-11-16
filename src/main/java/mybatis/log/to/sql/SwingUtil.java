package mybatis.log.to.sql;

import javax.swing.*;

/**
 * @author wjq
 * @since 2022/11/16
 */
public class SwingUtil {
    public static void invokeLater(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable);
        }
    }
}
