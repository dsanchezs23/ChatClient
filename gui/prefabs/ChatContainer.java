package app.gui.prefabs;

import app.controller.PrincipalController;
import java.awt.Dimension;

/**
 *
 * @author Sánchez S.
 * @version 2.0
 */
public class ChatContainer extends javax.swing.JPanel {

    /**
     * Creates new form ChatContainer
     */
    public ChatContainer() {
        initComponents();
    }

    public ChatContainer(String id) {
        initComponents();
        nameContactLabel.setText(id);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(16, 0), new java.awt.Dimension(32, 0), new java.awt.Dimension(16, 32767));
        nameContactLabel = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        newMessageButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(16, 0), new java.awt.Dimension(32, 0), new java.awt.Dimension(16, 32767));

        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                formAncestorResized(evt);
            }
        });
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        add(filler3);

        nameContactLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        nameContactLabel.setForeground(new java.awt.Color(255, 255, 255));
        nameContactLabel.setText("Name");
        add(nameContactLabel);
        add(filler1);

        newMessageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app.gui.icons/icons8-messaging-40.png"))); // NOI18N
        newMessageButton.setBorder(null);
        newMessageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                newMessageButtonMouseReleased(evt);
            }
        });
        add(newMessageButton);
        add(filler2);
    }// </editor-fold>//GEN-END:initComponents

    private void formAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formAncestorResized
        this.setPreferredSize(new Dimension(getParent().getPreferredSize().width - 30, DEFAULT_HEIGHT));
    }//GEN-LAST:event_formAncestorResized

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        this.setPreferredSize(new Dimension(getParent().getPreferredSize().width - 30, DEFAULT_HEIGHT));
    }//GEN-LAST:event_formAncestorAdded

    private void newMessageButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newMessageButtonMouseReleased
        if (principalController.isContact(nameContactLabel.getText())){
            principalController.putChatView(nameContactLabel.getText());
        }
        else{
            principalController.sendFirstMessage(nameContactLabel.getText());
            principalController.putChatView(nameContactLabel.getText());
        }
    }//GEN-LAST:event_newMessageButtonMouseReleased
    
    public void setPrincipalController(PrincipalController principalController){
        this.principalController = principalController;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    public javax.swing.JLabel nameContactLabel;
    private javax.swing.JButton newMessageButton;
    // End of variables declaration//GEN-END:variables

    public static int DEFAULT_HEIGHT = 80;
    PrincipalController principalController;
}
