package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Daniel Sánchez S.
 * @version 2.0
 */
public class RightBarModel extends Observable {

    public RightBarModel() {
        users = new ArrayList<>();
    }

    //change the methods
    public void commit() {
        setChanged();
        notifyObservers();
    }

    public List<String> getUsuarios() {
        return users;
    }

    public void addMessagePanel(String id) {
        if (users.isEmpty()) {
            users.add(id);
            commit();
            notifyPrincipal();
        } else {
            //adding the position
            if (!checkUserInUsers(id)) {
                users.add(id);
                commit();
                notifyPrincipal();
            }
        }
    }

    private boolean checkUserInUsers(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void setUsuarios(List<String> usuarios) {
        this.users = usuarios;
    }

    public PrincipalModel getPrincipalmodel() {
        return principalmodel;
    }

    public void setPrincipalmodel(PrincipalModel principalmodel) {
        this.principalmodel = principalmodel;
    }

    public void notifyPrincipal() {
        principalmodel.commit();
    }

    public List<String> getAllId() {
        return allId;
    }

    public void setAllId(List<String> allId) {
        this.allId = allId;
    }

    private List<String> users;
    private PrincipalModel principalmodel;
    private List<String> allId;
}
