package medicalApp.common;

public class Visit {

    private int paId, drId, visitId;
    private String description, unread = "true";
    private byte[] file;

    public Visit(int paId, int drId, String description, byte[] file) {
        this.paId = paId;
        this.drId = drId;
        this.description = description;
        this.file = file;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public int getPaId() {
        return paId;
    }

    public void setPaId(int paId) {
        this.paId = paId;
    }

    public int getDrId() {
        return drId;
    }

    public void setDrId(int drId) {
        this.drId = drId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
