package rouefortune;

public class MessageJoueur {

    private String message;
    private String contenu;

    public MessageJoueur(){}

    public MessageJoueur(String message, String contenu){
        this.message = message;
        this.contenu = contenu;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }


}