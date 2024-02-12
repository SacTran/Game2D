import javax.swing.*;
import java.awt.*;

public class qlThucdon extends JDialog {
    private JPanel qltd;
    private JLabel ImageLogo;

    public qlThucdon(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(qltd);
        setMinimumSize(new Dimension(500, 800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        qlThucdon qltd1 = new qlThucdon(null);
        qltd1.ImageLogo = new JLabel(new ImageIcon("user.png"));
        qltd1.add(qltd1.ImageLogo);
        qltd1.pack();
    }

    private void createUIComponents() {
        ImageLogo = new JLabel(new ImageIcon("Image/user.png"));
        // TODO: place custom component creation code here
    }
}
