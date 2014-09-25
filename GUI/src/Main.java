import java.awt.*;

/**
 * Created by Matthijs on 15-09-14.
 */
public class Main {

    private static BioToolsInterface gui;

    public static void main(String[] args) {
        gui = new BioToolsInterface(new Dimension(600, 300));
        gui.setVisible(true);
    }
}
