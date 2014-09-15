import javax.swing.*;
import java.awt.*;

/**
 * Created by Matthijs on 15-09-14.
 */
public class BioToolsInterface extends JFrame {

    private FilePreferencesPane preferences;
    private SequenceViewerPane seqViewer;
    private Font FONT_NORMAL;

    public BioToolsInterface(Dimension FRAMESIZE) {

        FONT_NORMAL = new Font("Verdana", Font.BOLD, 12);

        setSize(FRAMESIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        preferences = new FilePreferencesPane(FONT_NORMAL);
        seqViewer = new SequenceViewerPane();

        add(preferences, BorderLayout.LINE_START);
        add(seqViewer, BorderLayout.CENTER);
    }
}
