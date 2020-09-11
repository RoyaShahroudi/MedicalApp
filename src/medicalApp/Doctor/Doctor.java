package medicalApp.Doctor;

import java.sql.Date;

public class Doctor {
    private String name, familyName, email, password, speciality, msNo, description;
    private byte[] msImg, img;
    private int id, birthD, birthM, birthY;

    public Doctor(String name, String familyName, String email, String password,
                  int birthD, int birthM, int birthY, String speciality, String msNo,
                  byte[] msImg, byte[] img) {
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        this.password = password;
        this.birthD = birthD;
        this.birthM = birthM;
        this.birthY = birthY;
        this.speciality = speciality;
        this.msNo = msNo;
        this.msImg = msImg;
        this.img = img;
    }

    public Doctor() {

    }
    //used for doctor name comboBox in visitRequest page
    public Doctor(String name, String familyName) {
        this.name = name;
        this.familyName = familyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getMsNo() {
        return msNo;
    }

    public void setMsNo(String msNo) {
        this.msNo = msNo;
    }

    public byte[] getMsImg() {
        return msImg;
    }

    public void setMsImg(byte[] msImg) {
        this.msImg = msImg;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
