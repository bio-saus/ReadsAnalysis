import javax.swing.*;
import javax.swing.border.Border;
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
    private QuickSelectionPane control;
    private SelectionControl page_start;

    public SequenceSelectionPane(String filepath) throws IOException {

        setLayout(new BorderLayout());

        control = new QuickSelectionPane();
        circle = new SelectableCirlcePane();
        sequence = new GenomeViewer();
        file = new FastaReader(filepath);
        page_start = new SelectionControl();

        sequence.setBackground(getBackground());

        circle.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        refreshSequence();
                    }
                }
        );

        addComponentListener(new ComponentListener() {
            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                setCircleSize();
                setPreferredSizeToCircleWidth();
            }

            public void componentHidden(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }
        });

        control.addAllActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetRange(0, 1);
            }
        });

        control.addNoneActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetRange(0, 0);
            }
        });

        add(page_start, BorderLayout.PAGE_START);
        add(sequence, BorderLayout.CENTER);
    }

    public void SetRange(double A, double B) {
        circle.setA(A);
        circle.setB(B);
        circle.repaint();
        refreshSequence();
    }

    private void refreshSequence() {
        try {
            long len = file.getSequenceLength();
            long start = (long) (len * circle.getStartPercentage());
            String seq = getSequence();
            sequence.setOffset((int) start);
            sequence.setSequence(seq);
            setPreferredSizeToCircleWidth();
        } catch (IOException error) {
        }
    }

    private class SelectionControl extends JPanel {

        public SelectionControl() {

            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add(control);
            add(circle);

        }

    }

    private int getPreferredHeight() {
        return (int) (
                control.getPreferredSize().getHeight() +
                        circle.getPreferredSize().getHeight() +
                        sequence.getPreferredSize().getHeight()
        );
    }

    public void setPreferredSizeToCircleWidth() {
        setPreferredSize(new Dimension(circle.getWidth(), getPreferredHeight()));
    }

    public void setCircleSize() {
        int width = getWidth();
        int max = 400;
        if (circle.getHeight() != width && circle.getHeight() != 400) {
            Dimension d = new Dimension(width, Math.min(max, width));
            circle.setPreferredSize(d);
            circle.setSize(d);
        }
    }

    public String getSequence() throws IOException {
        long len = file.getSequenceLength();
        long start = (long) (len * circle.getStartPercentage());
        long end = (long) (len * circle.getEndPercentage());
        return file.getSequence(start, end);
    }

}
