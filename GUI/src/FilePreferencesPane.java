import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Matthijs on 15-09-14.
 */
public class FilePreferencesPane extends JPanel {

    private Font FONT_DEFAULT;
    private Color FONT_DEFAULT_COLOR;
    public SelectFilePane selectFile;

    public FilePreferencesPane(Font font) {
        FONT_DEFAULT = font;
        FONT_DEFAULT_COLOR = new Color(200, 200, 200);

        setBackground(new Color(50, 50, 50));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(150, 0));

        selectFile = new SelectFilePane(FONT_DEFAULT);

        add(selectFile, BorderLayout.PAGE_START);
    }

    public void addFileButtonListener(ActionListener listener) {
        selectFile.addFileButtonListener(listener);
    }

    public void setInfo(String message) {
        selectFile.setInfo(message);
    }
}
