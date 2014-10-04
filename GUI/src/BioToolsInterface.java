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
    private JScrollPane contentScrollPane;
    private Font FONT_NORMAL;
    private StatusBar statusbar;

    public BioToolsInterface() {

        FONT_NORMAL = new Font("Verdana", Font.BOLD, 12);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(new Dimension(400, 400));
        setLayout(new BorderLayout());

        preferences = new FilePreferencesPane(FONT_NORMAL);
        preferences.addFileButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setSelectedFile(new File("/home/matthijs/Dropbox/Hogeschool/05) Protein Modeling/Sequentie Analyse/3e jaar/query.fa"));
                int exitValue = fc.showOpenDialog(BioToolsInterface.this);
                if (exitValue == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        setGeneViewer(file);
                        preferences.setInfo(file.getName());
                    } catch (IOException error) {
                        System.out.println(error.toString());
                    }
                }
            }
        });

        contentScrollPane = new JScrollPane(null,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentScrollPane.setViewport(new JViewport() {
            @Override
            public void scrollRectToVisible(Rectangle rect) {
            }
        });

        statusbar = new StatusBar();

        add(preferences, BorderLayout.LINE_START);
        add(contentScrollPane, BorderLayout.CENTER);
        add(statusbar, BorderLayout.PAGE_END);
    }

    private void setGeneViewer(File file) throws IOException {
        statusbar.setMessage("loading " + file.getCanonicalPath());
        seqViewer = new SequenceSelectionPane(file.getAbsolutePath());
        seqViewer.setSize(contentScrollPane.getViewport().getSize());
        contentScrollPane.setViewportView(seqViewer);
        seqViewer.setCircleSize();
        seqViewer.setPreferredSizeToCircleWidth();
        contentScrollPane.addComponentListener(new ComponentListener() {
            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                int width = contentScrollPane.getViewport().getWidth();
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
        statusbar.setMessage(file.getName() + " opened");
    }
}
