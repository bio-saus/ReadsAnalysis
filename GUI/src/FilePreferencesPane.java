import javax.swing.*;
import java.awt.*;

/**
 * Created by Matthijs on 15-09-14.
 */
public class FilePreferencesPane extends JPanel {

    private Font FONT_DEFAULT;
    private Color FONT_DEFAULT_COLOR;

    public FilePreferencesPane(Font font) {

        FONT_DEFAULT = font;
        FONT_DEFAULT_COLOR = new Color(200, 200, 200);

        setBackground(new Color(50, 50, 50));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel open = new JLabel("Open file...");
        JLabel shiz = new JLabel("Analyse all dem shiz!");

        open.setForeground(FONT_DEFAULT_COLOR);
        shiz.setForeground(FONT_DEFAULT_COLOR);

        open.setFont(FONT_DEFAULT);
        shiz.setFont(FONT_DEFAULT);

        add(open);
        add(shiz);

    }
}
