import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server{

    private static Controller c;
    private static Map<String, Socket> mapSocket;

    public static void main (String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(15);
        Socket socket = null;
        Server.c = new Controller();
        Server.mapSocket = new HashMap<>();
        try{
            while(true) {
                try {
                    socket = serverSocket.accept();
                } catch(IOException e) {
                    System.out.println(e);
                }
                ServerThread serverThread = new ServerThread(Server.c, socket);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally{
        socket.close();
        }
    }

    public static Controller getC() {
        return c;
    }

    public static void setC(Controller c) {
        Server.c = c;
    }

    public static Map<String, Socket> getMapSocket() {
        return mapSocket;
    }

    public static void setMapSocket(Map<String, Socket> mapSocket) {
        Server.mapSocket = mapSocket;
    }
}
