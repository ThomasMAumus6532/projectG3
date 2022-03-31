import java.io.Serializable;

public class Message implements Serializable {

    private String expediteur;
    private String destinataire;
    private Object message;
    private String messageType;

    public Message(String expediteur, String destinataire, Object message, String messageType) {
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.message = message;
        this.messageType = messageType;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
