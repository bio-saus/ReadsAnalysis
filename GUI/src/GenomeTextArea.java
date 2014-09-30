import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by matthijs on 27-9-14.
 */
public class GenomeTextArea extends JTextArea {

    private Font font;
    private int gapInterval;
    private int sequenceLength;
    private int numberOfColums;
    private int borderSize;

    public GenomeTextArea() {

        borderSize = 10;
        font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        sequenceLength = 0;
        numberOfColums = 0;
        setGapInterval(5);

        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(null);
        setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
        setEditable(false);
        setFont(font);

        addComponentListener(new ComponentListener() {

            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                setColums();
            }

            public void componentHidden(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }

        });

    }

    public void setGapInterval(int gap) {
        gapInterval = gap;
    }

    public int getWrappedLines() {
        double h = getPreferredSize().getHeight() - (borderSize * 2);
        return (int) (h / getLineHeight());
    }

    public void setGappedText(String text) {
        setText("");
        sequenceLength = text.length();
        if (gapInterval > 0) {
            for (int i = 0; i < sequenceLength; i += gapInterval) {
                append(text.substring(i, Math.min(i + gapInterval, sequenceLength)));
                if (i < sequenceLength - gapInterval) append(" ");
            }
        } else {
            setText(text);
        }
    }

    public int getLength() {
        return sequenceLength;
    }

    public int getWidthInBases() {
        if (gapInterval > 0) {
            return getColums() * gapInterval;
        } else {
            FontMetrics metrics = getFontMetrics(font);
            try {
                char c = getText(0, 1).charAt(0);
                int w = metrics.charWidth(c);
                double n = (getWidth() - 12) / w;
                return (int) n;
            } catch (BadLocationException e) {
                return 0;
            }
        }
    }

    public int getColums() {
        return numberOfColums;
    }

    private void setColums() {
        FontMetrics metrics = getFontMetrics(font);
        int length = getText().length();
        int widthCount = 0;
        int widthArea = getWidth() - 12;
        int columCount = 0;
        try {
            for (int blockI = 0; blockI < length && widthCount < widthArea; blockI += gapInterval + 1, ++columCount) {
                for (int charI = blockI; charI < blockI + gapInterval + 1; ++charI) {
                    char c = getText(charI, 1).charAt(0);
                    int w = metrics.charWidth(c);
                    widthCount += w;
                }
            }
        } catch (BadLocationException e) {

        }
        --columCount;
        numberOfColums = columCount;
    }

    public int getLineHeight() {
        FontMetrics metrics = getFontMetrics(font);
        return metrics.getHeight();
    }

}
