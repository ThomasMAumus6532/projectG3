import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String username;
    private int port;
    private Socket socket;
    private Controller c;

    public Client(String username, int port){
        this.setUsername(username);
        this.setPort(port);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Controller getC() {
        return c;
    }

    public void setC(Controller c) {
        this.c = c;
    }

    public boolean connect() throws IOException{
        this.socket = new Socket("localhost", this.port);
        new DataOutputStream(this.socket.getOutputStream()).writeUTF(this.username);
        ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
        try {
            Message message = (Message) objectInputStream.readObject();
            if(message.getMessageType().equals("connection_denied")){
                socket.close();
                return false;
            }
            else if(message.getMessageType().equals("new_user")) {
                this.c = (Controller) message.getMessage();
            }
            return true;
        } catch (ClassNotFoundException e){
            System.out.println(e);
            return false;
        }

    }
}
