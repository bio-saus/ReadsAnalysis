import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

/**
 * Created by matthijs on 27-9-14.
 */
public class SequenceSelectionPane extends JPanel {

    private SelectableCirlcePane circle;
    private GenomeViewer sequence;
    private FastaReader file;

    public SequenceSelectionPane(String filepath) throws IOException {

        setLayout(new BorderLayout());

        circle = new SelectableCirlcePane();
        sequence = new GenomeViewer();
        file = new FastaReader(filepath);

        sequence.setBackground(getBackground());

        circle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    long len = file.getSequenceLength();
                    long start = (long) (len * circle.getStartPercentage());
                    long end = (long) (len * circle.getEndPercentage());
                    String seq = file.getSequence(start, end);
                    sequence.setOffset((int) start);
                    sequence.setSequence(seq);
                    setPreferredSizeToCircleWidth();
                } catch (IOException error) {
                }
            }
        });

        addComponentListener(new ComponentListener() {
            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                int width = circle.getWidth();
                int max = 400;
                if (circle.getHeight() != width && circle.getHeight() != 400) {
                    circle.setPreferredSize(new Dimension(width, Math.min(max, width)));
                    setPreferredSizeToCircleWidth();
                }
            }

            public void componentHidden(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }
        });

        add(circle, BorderLayout.PAGE_START);
        add(sequence, BorderLayout.CENTER);

    }

    public int getPreferredHeight() {
        return (int) (circle.getPreferredSize().getHeight() +
                sequence.getPreferredSize().getHeight()
        );
    }

    public void setPreferredSizeToCircleWidth() {
        setPreferredSize(new Dimension(circle.getWidth(), getPreferredHeight()));
    }

}
