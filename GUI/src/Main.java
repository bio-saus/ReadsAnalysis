import java.awt.*;

/**
 * Created by Matthijs on 15-09-14.
 */
public class Main {

    private static BioToolsInterface gui;

    public static void main(String[] args) {
        gui = new BioToolsInterface();
        gui.setSize(new Dimension(500, 500));
        gui.setVisible(true);
    }
}
