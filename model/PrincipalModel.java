package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import protocolchat.Client;

/**
 *
 * @author Daniel Daniel Sánchez S.
 * @version 2.0
 */
public class PrincipalModel extends Observable {

    public PrincipalModel() {
        users = new ArrayList<>();
    }

    public void setCurrentUser(Client user){
        this.user = user;
        commit();
    }
    
    public Client getCurrentUser(){
        return user;
    }
    
    public void commit() {
        setChanged();
        notifyObservers();
    }

    public List<Integer> getUsuarios() {
        return users;
    }
    
    public void addMessagePanel() {
        if (users.isEmpty()) {
            users.add(0);
        } else {
            //adding the position
            users.add((users.size() - 1) + 1);
        }
        commit();
    }

    public void setUsuarios(List<Integer> usuarios) {
        this.users = usuarios;
    }
    
    private Client user;
    private List<Integer> users;
}
