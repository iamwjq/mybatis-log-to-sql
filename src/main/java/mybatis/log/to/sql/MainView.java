package mybatis.log.to.sql;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;

import static mybatis.log.to.sql.SwingUtil.invokeLater;

/**
 * @author wjq
 * @since 2022/11/16
 */
public class MainView {
    private JScrollPane logScrollPane;
    private JScrollPane sqlScrollPane;
    JFrame jFrame;

    public MainView(Service service) {
        invokeLater(() -> {
            jFrame = new JFrame("mybatis log to sql");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setResizable(false);

            JPanel panel = new JPanel();
            jFrame.setContentPane(panel);
            panel.setLayout(null);

            JTextArea logTextArea = new JTextArea();
            logTextArea.setLineWrap(true);
            logScrollPane = new JScrollPane(logTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add(logScrollPane);


            JTextArea sqlTextArea = new JTextArea();
            sqlTextArea.setLineWrap(true);
            sqlTextArea.setEditable(false);
            sqlScrollPane = new JScrollPane(sqlTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add(sqlScrollPane);

            logTextArea.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    try {
                        sqlTextArea.getDocument().remove(0, sqlTextArea.getDocument().getLength());
                        String log = e.getDocument().getText(0, e.getLength());
                        String sql = service.convert(log);
                        sqlTextArea.getDocument().insertString(0, sql, null);
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                }

                public void removeUpdate(DocumentEvent e) {

                }

                public void changedUpdate(DocumentEvent e) {
                }
            });

        });
    }

    public void show(Point location, Dimension size) {
        invokeLater(() -> {
            jFrame.setLocation(location);
            jFrame.setSize(size);
            jFrame.setVisible(true);

            Insets insets = jFrame.getInsets();
            logScrollPane.setBounds(insets.left, 0, jFrame.getWidth() - insets.left * 4, (jFrame.getHeight() - insets.top - insets.bottom) / 2);
            sqlScrollPane.setBounds(insets.left, (jFrame.getHeight() - insets.top - insets.bottom) / 2, jFrame.getWidth() - insets.left * 4, (jFrame.getHeight() - insets.top - insets.bottom) / 2);
        });
    }
}
