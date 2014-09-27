import java.awt.*;
import java.awt.geom.Arc2D;
import javax.swing.JComponent;

/**
 * Created by matthijs on 25-9-14.
 */
public class ArchedCirlce {

    private double start = 0;
    private double end = 0;
    private double padding;
    private JComponent parent;
    private Arc2D.Double arch;

    public ArchedCirlce(JComponent container, double px_from_edge, double x1, double x2) {
        padding = px_from_edge;
        setStart(x1);
        setEnd(x2);
        parent = container;
    }

    public void setStart(double x) {
        if (x >= 0 && x <= 1) {
            start = x;
        } else if (x < 0) {
            start = 0;
        } else if (x > 1) {
            start = 1;
        }
    }

    public double getStart() {
        return start;
    }

    public void setEnd(double x) {
        if (x >= 0 && x <= 1) {
            end = x;
        } else if (x < 0) {
            end = 0;
        } else if (x > 1) {
            end = 1;
        }
    }

    public double getEnd() {
        return end;
    }

    public double getRadius() {
        return arch.getWidth() / 2.0;
    }

    public Point getCenter() {
        return new Point(parent.getWidth() / 2, parent.getHeight() / 2);
    }

    public double getPercentage(double degree) {
        return (degree - 5) / 350;
    }

    public boolean isVisible(double degree) {
        return (degree >= 5 && degree <= 355);
    }

    public double getRadian(Point p) {
        Point center = getCenter();
        Point top = new Point(center.x, center.y - (int) getRadius());
        double A = top.distance(p);
        double B = center.distance(top);
        double C = center.distance(p);
        return (Math.pow(B, 2) + Math.pow(C, 2) - Math.pow(A, 2)) / (2 * B * C);
    }

    public double getDegree(Point p) {
        double degree = Math.acos(getRadian(p)) * 180 / Math.PI;
        if (getCenter().getX() > p.getX())
            return degree;
        else
            return 360 - degree;
    }

    public boolean hasEdgeContact(Point p, double threshold) {
        return Math.abs(p.distance(getCenter()) - getRadius()) < threshold;
    }

    public Arc2D.Double getCircle() {

        double x, y, w, h;
        x = y = padding;
        if (parent.getWidth() < parent.getHeight()) {
            w = h = parent.getWidth() - x * 2;
            y = (parent.getHeight() - h) / 2;
        } else {
            w = h = parent.getHeight() - y * 2;
            x = (parent.getWidth() - w) / 2;
        }
        double endDegree = (end - start) * 350;
        double startDegree = start * 350 + 95;

        arch = new Arc2D.Double(x, y, w, h, startDegree, endDegree, Arc2D.OPEN);
        return arch;
    }
}
