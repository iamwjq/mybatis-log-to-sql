package mybatis.log.to.sql;

import java.awt.*;

/**
 * 视图
 */
public class App {

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int w = Math.min(screenSize.width, Constants.DEFAULT_WIDTH);
        int h = Math.min(screenSize.height, Constants.DEFAULT_HEIGHT);
        int x = (screenSize.width - w) / 2;
        int y = (screenSize.height - h) / 2;

        Configuration config = new Configuration();
        config.setMainWindowLocation(new Point(x, y));
        config.setMainWindowSize(new Dimension(500, 400));

        MainController mainController = new MainController(config);
        mainController.show();
    }
}
