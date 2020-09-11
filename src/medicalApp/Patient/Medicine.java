package medicalApp.Patient;

public class Medicine {

    private int id, paId;
    private String medicineName, consumedDate = "0";
    private String hour, min;



    public Medicine(int paId, String medicineName, String hour, String min) {
        this.paId = paId;
        this.medicineName = medicineName;
        this.hour = hour;
        this.min = min;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }


    public String getConsumedDate() {
        return consumedDate;
    }

    public void setConsumedDate(String consumedDate) {
        this.consumedDate = consumedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaId() {
        return paId;
    }

    public void setPaId(int paId) {
        this.paId = paId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }




}
