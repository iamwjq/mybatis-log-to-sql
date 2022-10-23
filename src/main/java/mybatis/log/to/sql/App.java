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
public class App extends JFrame implements Observer {

    private final JTextArea sqlTextArea;

    public App(final Model model) throws HeadlessException {
        super("mybatis log to sql");
        setSize(400, 400);
        setResizable(false);
        setLocation(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        JTextArea logTextArea = new JTextArea();
        logTextArea.setLineWrap(true);
        JScrollPane logScrollPane = new JScrollPane(logTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(logScrollPane);


        sqlTextArea = new JTextArea();
        sqlTextArea.setLineWrap(true);
        sqlTextArea.setEditable(false);
        JScrollPane sqlScrollPane = new JScrollPane(sqlTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(sqlScrollPane);

        logTextArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                try {
                    sqlTextArea.getDocument().remove(0, sqlTextArea.getDocument().getLength());
                    String log = e.getDocument().getText(0, e.getLength());
                    model.convertTo(log.split("\n"));
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }

            public void removeUpdate(DocumentEvent e) {

            }

            public void changedUpdate(DocumentEvent e) {
            }
        });

        setVisible(true);

        Insets insets = getInsets();
        logScrollPane.setBounds(insets.left, 0, getWidth() - insets.left * 4, (getHeight() - insets.top - insets.bottom) / 2);
        sqlScrollPane.setBounds(insets.left, (getHeight() - insets.top - insets.bottom) / 2, getWidth() - insets.left * 4, (getHeight() - insets.top - insets.bottom) / 2);

    }

    public void update(Observable o, Object arg) {
        if (o instanceof Model) {
            Model model = (Model) o;
            String sql = model.convert();
            try {
                sqlTextArea.getDocument().insertString(0, sql, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Model model = new Model();
        App view = new App(model);
        model.addObserver(view);
    }
}
