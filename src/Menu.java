import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Menu extends JFrame implements ActionListener {

    private JList<String> users;
    private JButton deconnection;
    private JTextField message;
    private JTextArea history;
    private JButton send;
    private JPanel panel;
    private JLabel labelUsername;
    private Client client;

    public Menu(Client client) {
        super("Menu");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        this.client = client;

        send.setActionCommand("send");
        send.addActionListener(this);
        deconnection.setActionCommand("disconnection");
        deconnection.addActionListener(this);
        labelUsername.setText(this.client.getUsername());
        DefaultListModel<String> usernames = new DefaultListModel<String>();
        for(String username : client.getC().getList()) {
            usernames.addElement(username);
        }
        users.setModel(usernames);
        panel.updateUI();

        Menu menu = this;
        this.addWindowListener(new WindowAdapter() {
            public void WindowClosing(WindowEvent e) {
                menu.disconnection();
            }
        });

        MenuThread thread = new MenuThread(client, this);
        thread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action == "send"){
            String txt = message.getText();
            String destinataire = users.getSelectedValue().toString();
            String expediteur = this.client.getUsername();
            try{
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.client.getSocket().getOutputStream());
                Message message = new Message(expediteur,destinataire, txt, "message");
                objectOutputStream.writeObject(message);
            } catch (IOException ee){
                System.out.println(ee);
            }
        }
        else if(action == "disconnection"){
            String expediteur = this.client.getUsername();
            try{
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.client.getSocket().getOutputStream());
                Message message = new Message(expediteur,"", "", "disconnection");
                objectOutputStream.writeObject(message);
            } catch (IOException ee){
                System.out.println(ee);
            }
            this.disconnection();
        }
    }

    public void setUsers(JList<String> users) {
        this.users = users;
    }

    public JList<String> getUsers() {
        return users;
    }

    public JButton getDeconnection() {
        return deconnection;
    }

    public void setDeconnection(JButton deconnection) {
        this.deconnection = deconnection;
    }

    public JTextField getMessage() {
        return message;
    }

    public void setMessage(JTextField message) {
        this.message = message;
    }

    public JTextArea getHistory() {
        return history;
    }

    public void setHistory(JTextArea history) {
        this.history = history;
    }

    public JButton getSend() {
        return send;
    }

    public void setSend(JButton send) {
        this.send = send;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void disconnection(){
        Deconnection deconnection = new Deconnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deconnection.initComponents();
        deconnection.pack();
        deconnection.setVisible(true);
    }
}
