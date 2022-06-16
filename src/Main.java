import DB.DBConnection;
import Windows.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        DBConnection conn = new DBConnection();

        if (conn.getConnection()) {
            MainWindow mainWd = new MainWindow();
            mainWd.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "请打开数据库！", "警告",
                    JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }

    }
}
