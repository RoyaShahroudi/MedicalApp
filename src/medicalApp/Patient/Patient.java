package medicalApp.Patient;

public class Patient {
    private String name, familyName, email, password, birthDate, gender, weight;
    private byte[] img;
    private int id, birthD, birthM, birthY;

    public Patient(String name, String familyName, String email, String password,
                   int birthD, int birthM, int birthY, String gender, String weight,
                   byte[] img) {
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        this.password = password;
        this.birthD = birthD;
        this.birthM = birthM;
        this.birthY = birthY;
        this.gender = gender;
        this.weight = weight;
        this.img = img;
    }

    public Patient() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBirthD() {
        return birthD;
    }

    public void setBirthD(int birthD) {
        this.birthD = birthD;
    }

    public int getBirthM() {
        return birthM;
    }

    public void setBirthM(int birthM) {
        this.birthM = birthM;
    }

    public int getBirthY() {
        return birthY;
    }

    public void setBirthY(int birthY) {
        this.birthY = birthY;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
