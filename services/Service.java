package app.services;

import java.util.List;
import javax.swing.SwingUtilities;
import protocolchat.Client;
import protocolchat.Contact;

/**
 *
 * @author Daniel Sánchez S.
 * @version 2.0
 */
public class Service {

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public void updateStatus(Client client) {
        //I have to use a hash table

        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                controller.model.getContactList().stream().filter(ct -> (ct.getId().equals(client.getId()))).forEachOrdered(ct -> {
                    ct.setStatus(client.isStatus());
                    controller.model.commit();
                });
            }
        }
      / );*/
    }

    public void updateAllId(String newId) {
        //I have to use a hash table

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                allId.add(newId);
            }
        }
        );
    }

    public void updateContact(Contact contact) {
        //I have to use a hash table
        /* SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                control.model.client.getContacts().add(contact);
                control.model.commit();
            }
        }
        );*/
    }

    public void setAllId(List<String> allId) {
        this.allId = allId;
    }
    
    public List<String> getAllId() {
        return allId;
    }
    
    private static Service instance = null;
    private List<String> allId;
}
