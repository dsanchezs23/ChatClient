package app.services;

import app.controller.PrincipalController;
import app.controller.RightBarController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import protocolchat.Client;
import protocolchat.Contact;
import protocolchat.IServiceProtocol;
import protocolchat.Message;
import protocolchat.Protocol;

/**
 *
 * @author Daniel Sánchez S.
 * @version 2.0
 */
public class Proxy implements IServiceProtocol {

    public static IServiceProtocol instance() {
        if (theInstance == null) {
            theInstance = new Proxy();
        }
        return theInstance;
    }

    public static void setRightController(RightBarController rightBarController) {
        Proxy.rightBarController = rightBarController;
    }

    public Proxy() {
    }

    public static void setController(PrincipalController principalController) {
        Proxy.principalController = principalController;
    }

    private void connect() throws Exception {
        skt = new Socket(Protocol.SERVER, Protocol.PORT);
        out = new ObjectOutputStream(skt.getOutputStream());
        in = new ObjectInputStream(skt.getInputStream());
    }

    private void disconnect() throws Exception {
        skt.shutdownOutput();
        skt.close();
    }

    @Override
    public Client login(Client client) throws Exception {
        connect();
        try {
            out.writeInt(Protocol.LOGIN);
            out.writeObject(client);
            out.flush();
            int response = in.readInt();
            if (response == Protocol.NO_ERROR) {
                Client clientlogged = (Client) in.readObject();
                List<String> allid = (List<String>) in.readObject();
                Service.getInstance().setAllId(allid);
                this.start();
                return clientlogged;
            } else {
                disconnect();
                throw new Exception("No remote user");
            }
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    @Override
    public boolean signUp(Client client) throws Exception {
        connect();
        try {
            out.writeInt(Protocol.SIGNUP);
            out.writeObject(client);
            out.flush();
            int response = in.readInt();
            if (response == Protocol.NO_ERROR) {
                return true;
            } else {
                disconnect();
                return false;
            }
        } catch (IOException ex) {
            throw new Exception("This user already exists");
        }
    }

    @Override
    public void logout(Client client) throws Exception {
        out.writeInt(Protocol.LOGOUT);
        out.writeObject(client);
        out.flush();
        this.stop();
        this.disconnect();
    }

    @Override
    public Client getClient(Client client) throws Exception {
        connect();
        Client clientAux = null;
        try {
            out.writeInt(Protocol.SEARCHING);
            out.writeObject(client);
            out.flush();
            int response = in.readInt();
            if (response == Protocol.NO_ERROR) {
                clientAux = (Client) in.readObject();
            } else {
                disconnect();
                return null;
            }
        } catch (IOException ex) {
            throw new Exception("The user doesn't exist");
        }
        return clientAux;
    }

    @Override
    public void post(Message message) {
        try {
            out.writeInt(Protocol.POST);
            out.writeObject(message);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void start() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                listen();
            }
        });
        flag = true;
        t.start();
    }

    public void stop() {
        flag = false;
    }

    public void listen() {
        int method;
        while (flag) {
            try {
                method = in.readInt();
                switch (method) {
                    case Protocol.DELIVER:
                    try {
                        Message message = (Message) in.readObject();
                        deliver(message);
                    } catch (ClassNotFoundException ex) {
                    }
                    break;
                    case Protocol.UPDATE:
                        Client client = (Client) in.readObject();
                        updateStatus(client);
                        break;
                    case Protocol.UPDATEALLID:
                        String newId = in.readUTF();
                        //updateAllId(newId);
                        break;
                    case Protocol.UPDATECONTACT:
                        Contact contact = (Contact) in.readObject();
                        updateContact(contact);
                }
                out.flush();
            } catch (IOException ex) {
                flag = false;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deliver(final Message message) {
        try{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                principalController.deliver(message);
            }
        }
        );
        }catch(Exception ex){
            System.out.println("No deliver");
            System.out.println(ex.getMessage());
        }
            
    }

    public void updateStatus(Client client) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                principalController.getModel().commit();
            }
        }
        );
    }

    @Override
    public Client getClient(String id) throws Exception {
        return getClient(new Client(id));
    }

    public void updateAllId(String newId) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*principalController.getModel().getCurrentUser().getAllId().add(newId);
                control.model.commit();*/
            }
        }
        );
    }

    public void updateContact(Contact contact) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                principalController.getModel().getCurrentUser().getContacts().put(contact.getId(), contact);
                principalController.getModel().commit();
            }
        }
        );
    }

    @Override
    public void updateClient(Client client) throws Exception {
        connect();
        try {
            out.writeInt(Protocol.UPDATE);
            out.writeObject(client);
            out.flush();
            int response = in.readInt();
            if (response == Protocol.NO_ERROR) {
            } else {
                disconnect();
            }
        } catch (IOException ex) {
            throw new Exception("This user already exists");
        }
    }

    //@Override
    public List<String> getAllId() {
        return allId;
    }

    private static IServiceProtocol theInstance;
    Socket skt;
    ObjectInputStream in;
    ObjectOutputStream out;
    static PrincipalController principalController;
    static RightBarController rightBarController;
    private boolean flag = true;
    private List<String> allId;

}
