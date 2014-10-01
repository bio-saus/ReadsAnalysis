import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Matthijs on 15-09-14.
 */
public class SelectFilePane extends JPanel {

    public JButton selectFileButton;
    private JTextField selectedFileField;
    private static File file;

    public SelectFilePane(Font font) {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(new Color(0, 0, 0, 0));

        selectFileButton = new JButton("Open file");
        selectedFileField = new JTextField();

//        selectFileButton.setBackground(new Color(0, 0, 0, 0));
//        selectedFileField .setBackground(new Color(0, 0, 0, 0));

        selectFileButton.setFont(font);
        selectedFileField.setFont(font);

        add(selectFileButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(selectedFileField);

    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        SelectFilePane.file = file;
    }

    public void addFileButtonListener(ActionListener listener) {
        selectFileButton.addActionListener(listener);
    }

    public void setInfo(String message) {
        selectedFileField.setText(message);
    }

}
