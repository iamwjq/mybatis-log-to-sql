package mybatis.log.to.sql;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * 视图
 */
public class App {

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int w = (screenSize.width > Constants.DEFAULT_WIDTH) ? Constants.DEFAULT_WIDTH : screenSize.width;
        int h = (screenSize.height > Constants.DEFAULT_HEIGHT) ? Constants.DEFAULT_HEIGHT : screenSize.height;
        int x = (screenSize.width - w) / 2;
        int y = (screenSize.height - h) / 2;

        Configuration config = new Configuration();
        config.setMainWindowLocation(new Point(x, y));
        config.setMainWindowSize(new Dimension(500, 400));

        MainController mainController = new MainController(config);
        mainController.show();
    }
}
