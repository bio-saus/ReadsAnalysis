import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by matthijs on 27-9-14.
 */
public class SequenceSelectionPane extends JPanel {

    private SelectableCirlcePane circle;
    private GenomeViewer sequence;

    public SequenceSelectionPane() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        circle = new SelectableCirlcePane();
        sequence = new GenomeViewer();

        sequence.setOffset(439);
        sequence.setSequence("ATCGGTGCGTACCACACATGTCGCGCGCTCGGCTCACTCTAAATATTCTCGCACTATCGGTGCGTACCACACACAAAACACATGTGTCGCGCGCTCGGCTCACTCTAAATATTCTCGCACTATCGGTGCGTACCACACACAAAACACATGTGTCGCGCGCTCGGCTCACTCTAAATATTCTCGCACTATCGGTGCGTACCACACACAAAACACATGTGTCGCGCGCTCGGCTCACTCTAAATATTCTCGCACTATCGGTGCGTACCACACACAAAACACATGTGTCGCGCGCTCGGCTCACTCTAAATATTCTCGCACT");

        sequence.setBackground(getBackground());

        addComponentListener(new ComponentListener() {
            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                int width = circle.getWidth();
                int max = 400;
                if (circle.getHeight() != width && circle.getHeight() != 400) {
                    circle.setPreferredSize(new Dimension(width, Math.min(max, width)));
                }
            }

            public void componentHidden(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }
        });

        add(circle);
        add(sequence);

    }

    public int getPreferredHeight() {
        return (int) (circle.getPreferredSize().getHeight() +
                sequence.getPreferredSize().getHeight()
        );
    }

}
