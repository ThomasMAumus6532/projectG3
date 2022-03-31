public class Test {

    public static void main(String[] args){
        Connection connexion = new Connection(Server.getC());
        connexion.initComponents();
        connexion.pack();
        connexion.setVisible(true);
    }
}
