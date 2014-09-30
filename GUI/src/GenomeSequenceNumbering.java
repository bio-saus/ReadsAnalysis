import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.*;

/**
 * Created by matthijs on 28-9-14.
 */
public class GenomeSequenceNumbering extends JPanel {

    private Font font;
    private java.util.List<JLabel> labels;
    private int lHeight;

    public GenomeSequenceNumbering(int lineHeight) {

        lHeight = lineHeight;
        labels = new ArrayList<JLabel>();
        font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        setBorder(null);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    }

    public int getRows() {
        return labels.size();
    }

    public void setNumbering(int start, int rows, int interval) {
        delLabels();
        for (int i = 0; i < rows; ++i) {
            JLabel index = new JLabel("" + start);
            index.setFont(font);
            index.setSize(new Dimension(50, lHeight));
            labels.add(index);
            add(index);

            start += interval;
        }
        doLayout();
    }

    private void delLabels() {
        for (int i = 0; i < labels.size(); ++i) {
            remove(labels.get(i));
        }
        labels.clear();
        repaint();
    }
}
