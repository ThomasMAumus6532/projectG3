import javax.swing.*;
import java.awt.*;

public class Deconnection extends JFrame{
    private JPanel panel;
    private JLabel text;

    public Deconnection(){
        super("Deconnection");
    }

    public void initComponents(){
        panel = new JPanel();
        text = new JLabel("Vous avez bien été déconnecté");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBorder(BorderFactory.createEmptyBorder(100,100,50,100));
        panel.setLayout(new GridLayout(1, 1, 10, 10));
        panel.add(text, BorderLayout.CENTER);
        this.add(panel, BorderLayout.NORTH);
    }
}
