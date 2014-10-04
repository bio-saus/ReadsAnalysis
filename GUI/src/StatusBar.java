import javax.swing.*;
import java.awt.*;

/**
 * Created by matthijs on 1-10-14.
 */
public class StatusBar extends JLabel {

    /**
     * Creates a new instance of StatusBar
     */
    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        setMessage("Ready");
    }

    public void setMessage(String message) {
        setText(" " + message);
    }
}
