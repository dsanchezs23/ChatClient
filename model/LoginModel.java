package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JPanel;

/**
 *
 * @author Daniel Daniel Sánchez S.
 * @version 2.0
 */
public class LoginModel extends Observable{

    public LoginModel() {
        users = new ArrayList<>();
    }
    
    //change the methods

    public void commit() {
        setChanged();
        notifyObservers();
    }

    public List<JPanel> getUsuarios() {
        return users;
    }

    public void add(JPanel usuario) {
        users.add(usuario);
        commit();
    }

    public void setUsuarios(List<JPanel> usuarios) {
        this.users = usuarios;
    }

    private List<JPanel> users;

}
