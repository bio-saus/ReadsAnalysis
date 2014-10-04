import java.awt.*;
import java.awt.geom.Arc2D;
import javax.swing.JComponent;

/**
 * Created by matthijs on 25-9-14.
 */
public class ArchedCirlce {

    private double A = 0;
    private double B = 0;
    private double padding;
    private int gap;
    private JComponent parent;
    private Arc2D.Double arch;

    public ArchedCirlce(JComponent container, double px_from_edge, double x1, double x2) {
        padding = px_from_edge;
        setA(x1);
        setB(x2);
        parent = container;
        gap = 20;
    }

    public void setA(double x) {
        if (x >= 0 && x <= 1) {
            A = x;
        } else if (x < 0) {
            A = 0;
        } else if (x > 1) {
            A = 1;
        }
    }

    public double getLeftBound() {
        return Math.min(A, B);
    }

    public void setB(double x) {
        if (x >= 0 && x <= 1) {
            B = x;
        } else if (x < 0) {
            B = 0;
        } else if (x > 1) {
            B = 1;
        }
    }

    public double getRightBound() {
        return Math.max(A, B);
    }

    public double getDelta() {
        return Math.abs(A - B);
    }

    public double getRadius() {
        return arch.getWidth() / 2.0;
    }

    public Point getCenter() {
        return new Point(parent.getWidth() / 2, parent.getHeight() / 2);
    }

    public double getPercentage(double degree) {
        return (degree - gap) / (360 - gap * 2);
    }

    public double getDegreeFromPercentage(double percentage) {
        return percentage * (360 - gap * 2) + gap;
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

        double start = getLeftBound() * (360 - gap * 2) + gap;
        double delta = getDelta() * (360 - gap * 2);

        // Accumulate offset
        start = (start + 90) % 360;

        arch = new Arc2D.Double(x, y, w, h, start, delta, Arc2D.OPEN);
        return arch;
    }

    public Point getPoint(double value) {
        Point center = getCenter();
        double range = getRadius();
        double angle = (getDegreeFromPercentage(value) + 90) % 360;
        double x = center.getX() + (range * Math.cos(Math.toRadians(angle)));
        double y = center.getY() + (-range * Math.sin(Math.toRadians(angle)));
        return new Point((int) x, (int) y);
    }
}
