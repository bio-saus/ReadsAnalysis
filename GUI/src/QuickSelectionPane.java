import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by matthijs on 3-10-14.
 */
public class QuickSelectionPane extends JPanel {

    private JButton selectAll;
    private JButton selectNone;

    public QuickSelectionPane() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        selectAll = new JButton("all");
        selectNone = new JButton("none");

        add(selectAll);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(selectNone);

    }

    public void addAllActionListener(ActionListener listener) {
        selectAll.addActionListener(listener);
    }

    public void addNoneActionListener(ActionListener listener) {
        selectNone.addActionListener(listener);
    }

}
