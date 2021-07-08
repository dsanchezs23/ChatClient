package app.controller;

import app.model.PrincipalModel;
import app.services.Proxy;
import app.view.ChatView;
import app.view.LoginView;
import app.view.PrincipalView;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolchat.Contact;
import protocolchat.Message;

/**
 *
 * @author Daniel Sánchez S.
 * @version 2.0
 */
public class PrincipalController {

    public PrincipalController(PrincipalModel model, PrincipalView view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public PrincipalModel getModel() {
        return model;
    }

    public void setModel(PrincipalModel model) {
        this.model = model;
    }

    public PrincipalView getView() {
        return view;
    }

    public void setView(PrincipalView view) {
        this.view = view;
    }

    public void addMessagePanel() {
        model.addMessagePanel();
    }

    public void setName() {
        view.nameLabel.setText(model.getCurrentUser().getName());
    }

    public void putChatView(String contactId) {
        try {
            view.bodyPanel.removeAll();
            chatView = new ChatView(contactId, this);
            chatView.setBackground(new Color(47, 49, 54));
            setMessagesInTheChat(chatView, contactId);
            view.bodyPanel.add(chatView);
            view.validate();
            view.repaint();
        } catch (Exception ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setMessagesInTheChat(ChatView chatView, String contactId) {
        List<Message> messages = new ArrayList<>();
        gettingMessageForTheChat(contactId, messages);
        String text = "";
        for (Message message : messages) {
            if (message.getEmitter().equals(contactId)) {
                text += contactId + ": " + message.getText() + "\n";
            } else {
                text += "Me: " + message.getText() + "\n";
            }
        }
        chatView.messagesTextArea.setText(text);
    }

    private void gettingMessageForTheChat(String contactId, List<Message> messages) {
        for (Message message : model.getCurrentUser().getMessages().values()) {
            if ((message.getEmitter().equals(model.getCurrentUser().getId()) && message.getReceiver().equals(contactId)
                    || message.getEmitter().equals(contactId) && message.getReceiver().equals(model.getCurrentUser().getId()))) {

                messages.add(message);
            }
        }
    }

    public String getLasTime(String id) throws Exception {
        Contact contactClient = getContact(id);
        LocalDate localDate = LocalDate.now();
        if (localDate.toString().equals(contactClient.getLastTime().format(DateTimeFormatter.ISO_DATE))) {
            return "Today at "
                    + contactClient.getLastTime().format(DateTimeFormatter.ISO_TIME);
        } else if ((localDate.getDayOfMonth() - contactClient.getLastTime().getDayOfMonth()) == 1) {
            return "Yesterday at "
                    + contactClient.getLastTime().format(DateTimeFormatter.ISO_TIME);
        } else {
            return contactClient.getLastTime().format(DateTimeFormatter.ISO_DATE)
                    + " at " + contactClient.getLastTime().format(DateTimeFormatter.ISO_TIME);
        }
    }

    private Contact getContact(String id) {
        for (Contact contact : model.getCurrentUser().getContacts().values()) {
            if (contact.getId().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    public boolean isContact(String id) {
        return getContact(id) != null;
    }

    public void sendMessage() {
        try {
            Message message = new Message(0, model.getCurrentUser().getId(), chatView.contactIdLabel.getText(), LocalDateTime.now(), chatView.messageTextArea.getText());
            Proxy.instance().post(message);
            chatView.messageTextArea.setText("");
            model.commit();
        } catch (Exception ex) {
            System.out.println("Error sending the message");
        }
    }

    public void sendFirstMessage(String id) {
        try {
            Message message = new Message(0, model.getCurrentUser().getId(), id, LocalDateTime.now(), "Contact Added");
            Proxy.instance().post(message);
            model.getCurrentUser().getContacts().put(id, new Contact(id));
            model.commit();
        } catch (Exception ex) {
            System.out.println("Error sending the message");
        }
    }

    public void deliver(Message message) {
        model.getCurrentUser().getMessages().put(message.getSequence(), message);
        if (message.getEmitter().equals(chatView.contactIdLabel) || message.getReceiver().equals(chatView.contactIdLabel)) {
            String text = chatView.messagesTextArea.getText();
            if (message.getEmitter().equals(model.getCurrentUser().getId())) {
                text += "Me: " + message.getText() + "\n";
            } else {
                text += message.getEmitter() + ": " + message.getText() + "\n";
            }
            chatView.messagesTextArea.setText(text);
        }
    }
    

    public void signOut() {
        try {
            Proxy.instance().logout(model.getCurrentUser());
        } catch (Exception ex) {
        }
        model.setCurrentUser(null);
        model.commit();
        startLoginView();
    }

    private void startLoginView() {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        view.setVisible(false);
        view.dispose();
    }
    PrincipalModel model;
    PrincipalView view;
    ChatView chatView;
}
