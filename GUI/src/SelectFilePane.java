import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Matthijs on 15-09-14.
 */
public class SelectFilePane extends JPanel {

    private JButton selectFileButton;
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

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int exitValue = fc.showOpenDialog(SelectFilePane.this);
                if (exitValue == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    selectedFileField.setText(file.getName());
                }
                System.out.println(SelectFilePane.this.getSize());
            }
        });

        add(selectFileButton, Component.LEFT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(selectedFileField, Component.LEFT_ALIGNMENT);

    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        SelectFilePane.file = file;
    }

}
