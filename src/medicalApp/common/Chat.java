package medicalApp.common;

public class Chat {
    private int messageId, drId, paID;
    private String sender, message;

    public Chat(int drId, int paID,String sender, String message) {
        this.drId = drId;
        this.paID = paID;
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getDrId() {
        return drId;
    }

    public void setDrId(int drId) {
        this.drId = drId;
    }

    public int getPaID() {
        return paID;
    }

    public void setPaID(int paID) {
        this.paID = paID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
