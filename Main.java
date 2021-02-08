package app;

import app.view.LoginView;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Daniel Sánchez S.
 * @version 2.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            //LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            //LoginController controller = new LoginController(model, view);
            view.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
