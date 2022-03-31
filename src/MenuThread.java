import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MenuThread extends Thread{

    private Client client;
    private Menu m;

    public MenuThread(Client client, Menu m){
        this.setClient(client);
        this.setM(m);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Menu getM() {
        return m;
    }

    public void setM(Menu m) {
        this.m = m;
    }

    public void run(){
        try {
            while(true) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
                    Message message = (Message) objectInputStream.readObject();
                    if (message.getMessageType().equals("message")){
                        String msg = "[" + message.getExpediteur() + "] : " + (String) message.getMessage();
                        m.getHistory().setText(m.getHistory().getText() + "\n" + msg);
                    }
                    else if(message.getMessageType().equals("new_user")){
                        this.client.setC((Controller) message.getMessage());
                        DefaultListModel<String> usernames = new DefaultListModel<String>();
                        this.client.getC().getList().forEach(user -> usernames.addElement(user.toString()));
                        m.getUsers().setModel(usernames);
                        String msg = message.getExpediteur() + " is connected";
                        m.getHistory().setText(m.getHistory().getText() + "\n" + msg );
                    }
                    else if(message.getMessageType().equals("disconnection")){
                        this.client.setC((Controller) message.getMessage());
                        DefaultListModel<String> usernames = new DefaultListModel<String>();
                        this.client.getC().getList().forEach(user -> usernames.addElement(user.toString()));
                        m.getUsers().setModel(usernames);
                        String msg = message.getExpediteur() + " disconnected";
                        m.getHistory().setText(m.getHistory().getText() + "\n" + msg );
                    }

                } catch (ClassNotFoundException | IOException e){
                    System.out.println(e);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
