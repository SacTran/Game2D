import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class qlBanHang extends JDialog {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel loginPanel;

    public User user;
    public qlBanHang(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(300,300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String users = txtUsername.getText();
                String password = String.valueOf(txtPassword.getPassword());
                user = getAuthenticateUser(users,password);
                if (user!=null) {
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(qlBanHang.this,
                            "User or Password Invalid", "Try Again", JOptionPane.ERROR_MESSAGE);
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
    private User  getAuthenticateUser(String users, String password){
        User user = null;
        final String url = "jdbc:mysql://localhost:3306/qlbh";
        final String username = "root";
        final String passwords = "12345678";
        try(Connection conn = DriverManager.getConnection(url,username,passwords)){
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Users WHERE Username=? AND Password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,users);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.userID = resultSet.getInt("UserID");
                user.username = resultSet.getString("Username");
                user.password = resultSet.getString("Password");
                user.email = resultSet.getString("Email");

                stm.close();
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public static void main(String[] args) {
        qlBanHang qlbh = new qlBanHang(null);
        User user = qlbh.user;
        if (user!=null){
            System.out.println("Login Success");
            System.out.println("Username:" + user.username);
            System.out.println("Email:" + user.email);
            qlThucdon qltd = new qlThucdon(null);

        }
        else {
            System.out.println("Authentication cancel");
            qlbh.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
}
