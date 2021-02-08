package app.controller;

import app.model.LoginModel;
import app.model.PrincipalModel;
import app.services.Proxy;
import app.view.LoginView;
import app.view.PrincipalView;
import app.view.SignInView;
import app.view.SignUpView;
import java.util.Arrays;
import javax.swing.JPanel;
import protocolchat.Client;

/**
 *
 * @author Daniel Sánchez S.
 * @version 2.0
 */
public class LoginController {

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public LoginModel getModel() {
        return model;
    }

    public void setModel(LoginModel model) {
        this.model = model;
    }

    public LoginView getView() {
        return view;
    }

    public void setView(LoginView view) {
        this.view = view;
    }

    public void changePanel(javax.swing.JPanel panel) {
        if (currentPanel != panel) {
            view.bodyLogInPanel.remove(currentPanel);
            currentPanel = panel;
            view.bodyLogInPanel.add(currentPanel);
            view.validate();
            view.repaint();
        }
        model.commit();
    }

    public void add(JPanel usuario) {
        model.add(usuario);
    }

    public SignInView getSignIn() {
        return signIn;
    }

    public void setSignIn(SignInView signIn) {
        this.signIn = signIn;
        signIn.setController(this);
        signIn.setModel(model);
    }

    public SignUpView getSignUp() {
        return signUp;
    }

    public void setSignUp(SignUpView signUp) {
        this.signUp = signUp;
        signUp.setController(this);
        signUp.setModel(model);
    }

    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanel(JPanel currentPanel) {
        this.currentPanel = currentPanel;
    }

    public Client login() throws Exception {
        //check if the login is correct  
        if (!"".equals(signIn.idTextField.getText()) && !"".equals(Arrays.toString(signIn.passwordField.getPassword()))) {
            if (!"Identification".equals(signIn.idTextField.getText())) {
                Client client = new Client(signIn.idTextField.getText(), "", signIn.passwordField.getText());
                return Proxy.instance().login(client);
            }
        }
        return null;
    }

    public boolean signUp() throws Exception {
        if (!"".equals(signUp.nameTextField.getText()) && !"".equals(signUp.idTextField.getText()) && !"".equals(Arrays.toString(signUp.passwordField.getPassword()))) {
            if (!"Identification".equals(signUp.idTextField.getText())) {
                Client client = new Client(signUp.idTextField.getText(), signUp.nameTextField.getText(), signUp.passwordField.getText());
                Proxy.instance().signUp(client);
                //model.commit();
                signUp.nameTextField.setText("Name");
                signUp.idTextField.setText("Identification");
                signUp.passwordField.setText("             ");
                return true;
            }
        }
        return false;
    }

    public void initiatePrincipal(Client user) throws Exception {
        try {
            if (user != null) {
                PrincipalModel modelPrincipal = new PrincipalModel();
                modelPrincipal.setCurrentUser(user);
                PrincipalView viewPrincipal = new PrincipalView();
                PrincipalController controllerPrincipal = new PrincipalController(modelPrincipal, viewPrincipal);
                viewPrincipal.setrightController(new RightBarController(controllerPrincipal));
                Proxy.setController(controllerPrincipal);
                controllerPrincipal.setName();
                viewPrincipal.setVisible(true);
                view.setVisible(false);
                view.dispose();
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            System.out.println(ex.getCause());
        }
    }

    private SignInView signIn = new SignInView();
    private SignUpView signUp = new SignUpView();
    private JPanel currentPanel;
    LoginModel model;
    LoginView view;
}
