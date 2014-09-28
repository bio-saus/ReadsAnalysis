import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by matthijs on 28-9-14.
 */
public class GenomeViewer extends JPanel {

    private GenomeTextArea sequence;
    private GenomeSequenceNumbering index;
    private int offset;

    public GenomeViewer() {

        offset = 0;

        setLayout(new BorderLayout());

        sequence = new GenomeTextArea();
        index = new GenomeSequenceNumbering(sequence.getLineHeight());

        index.setBackground(getBackground());
        sequence.setBackground(getBackground());

        sequence.addComponentListener(new ComponentListener() {
            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                index.setNumbering(offset, sequence.getWrappedLines(), sequence.getWidthInBases());
            }

            public void componentHidden(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }
        });

        add(index, BorderLayout.LINE_START);
        add(sequence, BorderLayout.CENTER);
    }

    public void setSequence(String seq) {
        sequence.setGappedText(seq);
    }

    public void setOffset(int num) {
        offset = num;
    }
}
