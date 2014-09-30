import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.ArrayList;


/**
 * Created by matthijs on 24-9-14.
 */
public class SelectableCirlcePane extends JPanel {

    private ArchedCirlce baseCircle;
    private ArchedCirlce seletedCircle;
    private boolean switchStart;
    private boolean switchEnd;
    private List<ActionListener> listenerList;

    public SelectableCirlcePane() {

        setMinimumSize(new Dimension(200, 200));

        baseCircle = new ArchedCirlce(this, 40, 0, 1);
        seletedCircle = new ArchedCirlce(this, 30, 0, 0);
        switchStart = false;
        switchEnd = false;
        listenerList = new ArrayList<ActionListener>();

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (seletedCircle.hasEdgeContact(e.getPoint(), 10)) {
                    double degree = baseCircle.getDegree(e.getPoint());
                    double percentage = baseCircle.getPercentage(degree);
                    if (seletedCircle.getStart() == seletedCircle.getEnd()) {
                        switchEnd = true;
                        seletedCircle.setStart(percentage);
                    } else if (Math.abs(seletedCircle.getStart() - percentage) < 0.1) {
                        switchStart = true;
                    } else if (Math.abs(seletedCircle.getEnd() - percentage) < 0.1) {
                        switchEnd = true;
                    }

                }
            }

            public void mouseReleased(MouseEvent e) {
                if (switchStart || switchEnd) {
                    switchStart = false;
                    switchEnd = false;
                    notifyListeners();
                }
            }

        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent e) {
                if (seletedCircle.hasEdgeContact(e.getPoint(), 10)) {
                    double degree = baseCircle.getDegree(e.getPoint());
                    double percentage = baseCircle.getPercentage(degree);
                    double deltaStart = Math.abs(seletedCircle.getStart() - percentage);
                    double deltaEnd = Math.abs(seletedCircle.getEnd() - percentage);
                    if (deltaStart < 0.1) {
//                        System.out.println("Start Contact");
                    }
                    if (deltaEnd < 0.1) {
//                        System.out.println("End Contact");
                    }
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (switchStart || switchEnd) {

                    double degree = baseCircle.getDegree(e.getPoint());
                    double percentage = baseCircle.getPercentage(degree);

                    if (switchEnd) {
                        seletedCircle.setEnd(percentage);
                    } else {
                        seletedCircle.setStart(percentage);
                    }
                    repaint();
                    notifyListeners();
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
        Point s = seletedCircle.getPoint(seletedCircle.getStart());
        Point e = seletedCircle.getPoint(seletedCircle.getEnd());
        int d = 16;
        Ellipse2D.Double circleS = new Ellipse2D.Double(s.x - (d / 2) + 1, s.y - (d / 2) + 1, d, d);
        Ellipse2D.Double circleE = new Ellipse2D.Double(e.x - (d / 2) + 1, e.y - (d / 2) + 1, d, d);
        g2.draw(circleS);
        g2.draw(circleE);
        baseCircle.getRadius();
    }

    public void addActionListener(ActionListener al) {
        listenerList.add(al);
    }

    public void removeActionListener(ActionListener al) {
        listenerList.remove(al);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Button Clicked!");
    }

    private void notifyListeners() {

        ActionEvent event = new ActionEvent(
                this, ActionEvent.ACTION_PERFORMED, "Shit just changed motherf*cka!"
        );
        for (ActionListener action : listenerList) {
            action.actionPerformed(event);
        }
    }

//    public abstract class ValueActionListener extends AbstractAction {
//
//        public void actionPerformed(ActionEvent e){
//
//        }
//
//        public double getStart() {
//            return getStartPercentage();
//        }
//
//        public double getEnd() {
//            return getEndPercentage();
//        }
//    }

    public double getStartPercentage() {
        return seletedCircle.getStart();
    }

    public double getEndPercentage() {
        return seletedCircle.getEnd();
    }
}
