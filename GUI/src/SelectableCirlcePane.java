import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.ArrayList;


/**
 * Created by matthijs on 24-9-14.
 */
public class SelectableCirlcePane extends JPanel {

    private ArchedCirlce baseCircle;
    private ArchedCirlce selectedCircle;
    private int selectedEdge;
    private List<ActionListener> listenerList;

    public SelectableCirlcePane() {

        baseCircle = new ArchedCirlce(this, 10, 0, 1);
        selectedCircle = new ArchedCirlce(this, 10, 0, 0);
        selectedEdge = 0;
        listenerList = new ArrayList<ActionListener>();

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (selectedCircle.hasEdgeContact(e.getPoint(), 10)) {
                    double degree = baseCircle.getDegree(e.getPoint());
                    double percentage = baseCircle.getPercentage(degree);
                    if (selectedCircle.getLeftBound() == selectedCircle.getRightBound()) {
                        selectedEdge = 2;
                        selectedCircle.setA(percentage);
                    } else if (Math.abs(selectedCircle.getLeftBound() - percentage) < 0.1) {
                        selectedEdge = 1;
                    } else if (Math.abs(selectedCircle.getRightBound() - percentage) < 0.1) {
                        selectedEdge = 2;
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (selectedEdge != 0) {
                    selectedEdge = 0;
                    notifyListeners();
                }
            }

        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent e) {
//                if (selectedCircle.hasEdgeContact(e.getPoint(), 10)) {
//                    double degree = baseCircle.getDegree(e.getPoint());
//                    double percentage = baseCircle.getPercentage(degree);
//                    double deltaStart = Math.abs(selectedCircle.getLeftBound() - percentage);
//                    double deltaEnd = Math.abs(selectedCircle.getRightBound() - percentage);
//                    if (deltaStart < 0.1) {
////                        System.out.println("Start Contact");
//                    }
//                    if (deltaEnd < 0.1) {
////                        System.out.println("End Contact");
//                    }
//                }
            }

            public void mouseDragged(MouseEvent e) {
                if (selectedEdge != 0) {

                    double degree = baseCircle.getDegree(e.getPoint());
                    double percentage = baseCircle.getPercentage(degree);

                    if (percentage < selectedCircle.getLeftBound()) {
                        selectedEdge = 1;
                    } else if (percentage > selectedCircle.getRightBound()) {
                        selectedEdge = 2;
                    }

                    if (selectedEdge == 1) {
                        selectedCircle.setA(percentage);
                    } else if (selectedEdge == 2) {
                        selectedCircle.setB(percentage);
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

        float dash1[] = {5.0f};

        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.RED);
        g2.setStroke(new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dash1, 0.0f));
        g2.draw(baseCircle.getCircle());

        g2.setPaint(Color.BLUE);
        g2.setStroke(new BasicStroke(2.5f));
        g2.draw(selectedCircle.getCircle());

        g2.setStroke(new BasicStroke(1.0f));
        Point s = selectedCircle.getPoint(selectedCircle.getLeftBound());
        Point e = selectedCircle.getPoint(selectedCircle.getRightBound());
        int d = 10;
        Ellipse2D.Double circleS = new Ellipse2D.Double(s.x - (d / 2) + 1, s.y - (d / 2) + 1, d, d);
        Ellipse2D.Double circleE = new Ellipse2D.Double(e.x - (d / 2) + 1, e.y - (d / 2) + 1, d, d);
        g2.fill(circleS);
        g2.fill(circleE);
        baseCircle.getRadius();
    }

    public void addActionListener(ActionListener al) {
        listenerList.add(al);
    }

    public void removeActionListener(ActionListener al) {
        listenerList.remove(al);
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
        return selectedCircle.getLeftBound();
    }

    public double getEndPercentage() {
        return selectedCircle.getRightBound();
    }

    public void setA(double a) {
        selectedCircle.setA(a);
    }

    public void setB(double b) {
        selectedCircle.setB(b);
    }
}
