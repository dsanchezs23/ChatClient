package app.controller;

import app.model.RightBarModel;
import app.services.Service;
import app.view.RightBarView;

/**
 *
 * @author Daniel Sánchez S.
 * @version 2.0
 */
public class RightBarController {

    public RightBarController(RightBarModel model, RightBarView view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public RightBarController() {
        this(new RightBarModel(), new RightBarView());
    }
    
    public RightBarController(PrincipalController principalController){
        this.principalController = principalController;
        this.model = new RightBarModel();
        this.view =  new RightBarView();
        view.setController(this);
        view.setModel(model);
    }

    public RightBarModel getModel() {
        return model;
    }

    public void setModel(RightBarModel model) {
        this.model = model;
    }

    public RightBarView getView() {
        return view;
    }

    public void setView(RightBarView view) {
        this.view = view;
    }

    public void setPrincipalModel(PrincipalController principalController) {
        model.setPrincipalmodel(principalController.getModel());
    }

    public void addMessagePanel(String id) {
        model.addMessagePanel(id);
    }

    public void settingAllId() {
        model.setAllId(Service.getInstance().getAllId());
        model.commit();
    }

    public void fillAllId() {
        view.contactsComboBox.removeAllItems();
        for (String id : model.getAllId()) {
            view.contactsComboBox.addItem(id);
        }
    }

    public PrincipalController getPrincipalController() {
        return principalController;
    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }
    
    PrincipalController principalController;
    RightBarModel model;
    RightBarView view;
}
