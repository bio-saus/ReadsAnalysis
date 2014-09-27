import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.event.*;

/**
 * Created by matthijs on 24-9-14.
 */
public class SelectableCirlcePane extends JPanel {

    private ArchedCirlce baseCircle;
    private ArchedCirlce seletedCircle;
    boolean switchStart;
    boolean switchEnd;

    public SelectableCirlcePane() {
        baseCircle = new ArchedCirlce(this, 50, 0, 1);
        seletedCircle = new ArchedCirlce(this, 40, 0, 0);
        switchStart = false;
        switchEnd = false;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (baseCircle.hasEdgeContact(e.getPoint(), 10)) {
                    double degree = baseCircle.getDegree(e.getPoint());
                    double percentage = baseCircle.getPercentage(degree);
                    double deltaStart = Math.abs(seletedCircle.getStart() - percentage);
                    double deltaEnd = Math.abs(seletedCircle.getEnd() - percentage);
                    if (deltaStart < 0.1) {
                        switchStart = true;
                    } else if (deltaEnd < 0.1) {
                        switchEnd = true;
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                switchStart = false;
                switchEnd = false;

            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent e) {

            }

            public void mouseDragged(MouseEvent e) {
                if (switchStart || switchEnd) {

                    double degree = baseCircle.getDegree(e.getPoint());
                    double percentage = baseCircle.getPercentage(degree);
                    double deltaStart = Math.abs(seletedCircle.getStart() - percentage);
                    double deltaEnd = Math.abs(seletedCircle.getEnd() - percentage);
                    if (switchEnd) {
                        seletedCircle.setEnd(percentage);
                    } else {
                        seletedCircle.setStart(percentage);
                    }
                    repaint();
                }

            }

        });
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.RED);
        g2.draw(baseCircle.getCircle());
        g2.setPaint(Color.BLUE);
        g2.draw(seletedCircle.getCircle());
        baseCircle.getRadius();
    }

}
