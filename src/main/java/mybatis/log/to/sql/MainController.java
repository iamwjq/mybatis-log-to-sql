package mybatis.log.to.sql;

/**
 * @author wjq
 * @since 2022/11/16
 */
public class MainController {
    Configuration configuration;
    MainView mainView;

    public MainController(Configuration configuration) {
        this.configuration = configuration;
        this.mainView = new MainView(new Service());
    }

    public void show() {
        mainView.show(configuration.getMainWindowLocation(), configuration.getMainWindowSize());
    }
}
