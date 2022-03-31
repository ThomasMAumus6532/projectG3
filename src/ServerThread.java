import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private Controller c;
    private Socket socket;
    private boolean isRunning;

    public ServerThread(Controller c, Socket socket) throws IOException{
        this.c = c;
        this.socket = socket;
        String clientMessage = new DataInputStream(this.socket.getInputStream()).readUTF();
        boolean test = false;
        for(String u : this.c.getList()){
            if(u.equals(clientMessage)){
                test = true;
            }
        }
        Message message = null;
        if(test){
            message = new Message("", "", null, "connection_denied");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(message);
        }
        else{
            this.c.getList().add(clientMessage);
            Server.getMapSocket().put(clientMessage, this.socket);
            for(Socket s : Server.getMapSocket().values()) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
                message = new Message(clientMessage,"",this.c, "new_user");
                objectOutputStream.writeObject(message);

            }
        }
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

    public void run(){
        while(true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                if (message.getMessageType().equals("message")) {
                    Socket socket = Server.getMapSocket().get(message.getDestinataire());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                }
                else if (message.getMessageType().equals("disconnection")){
                    String username = message.getExpediteur();
                    Server.getMapSocket().remove(username);
                    Server.getC().getList().remove(username);
                    for(Socket s : Server.getMapSocket().values()) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
                        message = new Message(username,"",this.c, "disconnection");
                        objectOutputStream.writeObject(message);
                    }
                }
                Thread.sleep(1000);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

