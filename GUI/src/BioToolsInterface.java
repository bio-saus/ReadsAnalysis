import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Matthijs on 15-09-14.
 */
public class BioToolsInterface extends JFrame {

    private FilePreferencesPane preferences;
    private SequenceSelectionPane seqViewer;
    private JScrollPane seqScroller;
    private Font FONT_NORMAL;

    public BioToolsInterface() {

        FONT_NORMAL = new Font("Verdana", Font.BOLD, 12);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        preferences = new FilePreferencesPane(FONT_NORMAL);
        preferences.addFileButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
//                fc.setSelectedFile(new File("/home/matthijs/Dropbox/Hogeschool/05) Protein Modeling/Sequentie Analyse/3e jaar/query.fa"));
                int exitValue = fc.showOpenDialog(BioToolsInterface.this);
                if (exitValue == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        setGeneViewer(file.getAbsolutePath());
                        preferences.setInfo(file.getName());
                    } catch (IOException error) {
                        System.out.println(error.toString());
                    }
                }
            }
        });

        seqScroller = new JScrollPane(null,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        seqScroller.setBorder(BorderFactory.createEmptyBorder());
        seqScroller.setViewport(new JViewport() {
            @Override
            public void scrollRectToVisible(Rectangle rect) {
            }
        });

        add(preferences, BorderLayout.LINE_START);
        add(seqScroller, BorderLayout.CENTER);

    }

    private void setGeneViewer(String file) throws IOException {
        seqViewer = new SequenceSelectionPane(file);
        seqScroller.setViewportView(seqViewer);
        seqScroller.addComponentListener(new ComponentListener() {
            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                int width = seqScroller.getViewport().getWidth();
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
    }
}
