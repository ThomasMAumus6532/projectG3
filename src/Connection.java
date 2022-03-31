import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

public class Connection extends JFrame implements ActionListener{
    private JButton button;
    private JLabel mess;
    private JTextArea username;
    private Controller c;

    public Connection(Controller controller){
        super("Connexion");
        this.c = controller;
    }

    public void actionPerformed(ActionEvent a){
        Client user = new Client(username.getText().toString(), 15);
        boolean test = true;
        try{
            test = user.connect();
        }catch (IOException e){
            System.out.println(e);
        }
        if (test){
            Menu menu = new Menu(user);
            this.setVisible(false);
        }
        else{
            JOptionPane.showMessageDialog(null, "The username is already taken, choose an other please.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void initComponents(){
        JPanel panel = new JPanel();
        button = new JButton("Send");
        button.addActionListener(this);
        username = new JTextArea();
        mess = new JLabel();
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.add(username, BorderLayout.CENTER);
        panel.add(button, BorderLayout.CENTER);
        panel.add(mess, BorderLayout.CENTER);
        this.add(panel, BorderLayout.NORTH);
    }

}