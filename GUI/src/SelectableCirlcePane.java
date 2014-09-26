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

    public SelectableCirlcePane() {
        baseCircle = new ArchedCirlce(this, 50, 0, 1);
        seletedCircle = new ArchedCirlce(this, 40, 0, 0);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent arg0) {
                System.out.println("mouseReleased");
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent e) {

            }

            public void mouseDragged(MouseEvent e) {
                if (baseCircle.hasEdgeContact(e.getPoint(), 10) || seletedCircle.hasEdgeContact(e.getPoint(), 10)) {
                    double degree = baseCircle.getDegree(e.getPoint());
                    if (baseCircle.isVisible(degree)) {
                        double percentage = baseCircle.getPercentage(degree);
                        double deltaStart = Math.abs(seletedCircle.getStart() - percentage);
                        double deltaEnd = Math.abs(seletedCircle.getEnd() - percentage);
                        if (deltaStart > deltaEnd && deltaEnd < 0.1) {
                            seletedCircle.setEnd(percentage);
                        } else if (deltaStart < 0.1) {
                            seletedCircle.setStart(percentage);
                        }
                        repaint();
                    }
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
