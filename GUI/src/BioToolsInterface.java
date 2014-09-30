import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by Matthijs on 15-09-14.
 */
public class BioToolsInterface extends JFrame {

    private FilePreferencesPane preferences;
    private SequenceSelectionPane seqViewer;
    private Font FONT_NORMAL;

    public BioToolsInterface() {

        FONT_NORMAL = new Font("Verdana", Font.BOLD, 12);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        preferences = new FilePreferencesPane(FONT_NORMAL);
        seqViewer = new SequenceSelectionPane();

        final JScrollPane seqScrollPane = new JScrollPane(
                seqViewer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        seqScrollPane.setBorder(BorderFactory.createEmptyBorder());
        seqScrollPane.setViewport(new JViewport() {
            @Override
            public void scrollRectToVisible(Rectangle rect) {
            }
        });
        seqScrollPane.setViewportView(seqViewer);
        seqScrollPane.addComponentListener(new ComponentListener() {

            public void componentMoved(ComponentEvent e) {

            }

            public void componentResized(ComponentEvent e) {

                int width = seqScrollPane.getViewport().getWidth();
                if (seqViewer.getWidth() != width) {
                    seqViewer.setPreferredSize(new Dimension(width, 99999));
                    Dimension d = new Dimension(width, seqViewer.getHeight());
                    seqViewer.setPreferredSize(d);
                }
            }

            public void componentHidden(ComponentEvent e) {

            }

            public void componentShown(ComponentEvent e) {

            }

        });

        add(preferences, BorderLayout.LINE_START);
        add(seqScrollPane, BorderLayout.CENTER);

    }
}
