package mybatis.log.to.sql;

import java.awt.*;

/**
 * @author wjq
 * @since 2022/11/16
 */
public class Configuration {
    private Point mainWindowLocation;
    private Dimension mainWindowSize;

    public Point getMainWindowLocation() {
        return mainWindowLocation;
    }

    public void setMainWindowLocation(Point mainWindowLocation) {
        this.mainWindowLocation = mainWindowLocation;
    }

    public Dimension getMainWindowSize() {
        return mainWindowSize;
    }

    public void setMainWindowSize(Dimension mainWindowSize) {
        this.mainWindowSize = mainWindowSize;
    }
}
