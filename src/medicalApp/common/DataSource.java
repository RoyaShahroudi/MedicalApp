package medicalApp.common;

import medicalApp.Doctor.Doctor;
import medicalApp.Patient.Medicine;
import medicalApp.Patient.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "Medical App.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:F:\\Java\\JavaFinalProject\\" + DB_NAME;

    public static final String TABLE_DOCTOR = "doctor";
    public static final String DOCTOR_ID = "id";
    public static final String DOCTOR_NAME = "name";
    public static final String DOCTOR_LAST_N = "lastN";
    public static final String DOCTOR_EMAIL = "email";
    public static final String DOCTOR_PASSWORD = "password";
    public static final String DOCTOR_BIRTH_D = "birthD";
    public static final String DOCTOR_BIRTH_M = "birthM";
    public static final String DOCTOR_BIRTH_Y = "birthY";
    public static final String DOCTOR_SPECIALITY = "speciality";
    public static final String DOCTOR_MS_NO = "MSNo";
    public static final String DOCTOR_MS_IMG = "MSImg";
    public static final String DOCTOR_IMG = "img";
    public static final String DOCTOR_DESCRIPTION = "description";

    public final String INSERT_DOCTOR = "INSERT INTO " + TABLE_DOCTOR + " (" +
            DOCTOR_NAME + ", " + DOCTOR_LAST_N + ", " + DOCTOR_EMAIL + ", " + DOCTOR_PASSWORD + ", " +
            DOCTOR_BIRTH_D + ", " + DOCTOR_BIRTH_M + ", " + DOCTOR_BIRTH_Y + ", " + DOCTOR_SPECIALITY + ", " +
            DOCTOR_MS_NO + ", " + DOCTOR_MS_IMG + ", " + DOCTOR_IMG +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    public static final String TABLE_PATIENT = "patient";
    public static final String PATIENT_ID = "id";
    public static final String PATIENT_NAME = "name";
    public static final String PATIENT_LAST_N = "lastN";
    public static final String PATIENT_EMAIL = "email";
    public static final String PATIENT_PASSWORD = "password";
    public static final String PATIENT_BIRTH_D = "birthD";
    public static final String PATIENT_BIRTH_M = "birthM";
    public static final String PATIENT_BIRTH_Y = "birthY";
    public static final String PATIENT_GENDER = "gender";
    public static final String PATIENT_WEIGHT = "weight";
    public static final String PATIENT_IMG = "img";

    public final String INSERT_PATIENT = "INSERT INTO " + TABLE_PATIENT + " (" +
            PATIENT_NAME + ", " + PATIENT_LAST_N + ", " + PATIENT_EMAIL + ", " + PATIENT_PASSWORD + ", " +
            PATIENT_BIRTH_D + ", " + PATIENT_BIRTH_M + ", " + PATIENT_BIRTH_Y + ", " + PATIENT_GENDER + ", " +
            PATIENT_WEIGHT + ", " + PATIENT_IMG + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String TABLE_VISIT = "visit";
    private static final String VISIT_ID = "visit_id";
    private static final String VISIT_UNREAD = "unread";
    private static final String VISIT_DR_ID = "Dr_id";
    private static final String VISIT_PAID = "patient_id";
    private static final String VISIT_DESCRIPTION = "description";
    private static final String VISIT_FILE = "file";

    public final String INSERT_VISIT = "INSERT INTO " + TABLE_VISIT + " (" + VISIT_UNREAD + ", " +
            VISIT_DR_ID + ", " + VISIT_PAID + ", " + VISIT_DESCRIPTION + ", " + VISIT_FILE +
            ") VALUES (?, ?, ?, ?, ?)";

    private final String TABLE_CHAT = "chat";
    private final String CHAT_ID = "message_id";
    private final String CHAT_DR_ID = "Dr_id";
    private final String CHAT_PAID = "patient_id";
    private final String CHAT_SENDER = "sender";
    private final String CHAT_MESSAGE = "message";

    public final String INSERT_CHAT = "INSERT INTO " + TABLE_CHAT + " (" + CHAT_DR_ID + ", " +
            CHAT_PAID + ", " + CHAT_SENDER + ", " + CHAT_MESSAGE + ") VALUES (?, ?, ?, ?)";

    private final String TABLE_MEDICINE = "medicine";
    private final String MEDICINE_ID = "id";
    private final String MEDICINE_PAID = "patient_id";
    private final String MEDICINE_NAME = "name";
    private final String MEDICINE_CONSUMED_DATE = "consumed_Date";
    private final String MEDICINE_HOUR = "hour";
    private final String MEDICINE_MIN = "min";


    public final String INSERT_MEDICINE = "INSERT INTO " + TABLE_MEDICINE + " (" + MEDICINE_PAID + ", " +
            MEDICINE_NAME + ", " + MEDICINE_CONSUMED_DATE + ", "+ MEDICINE_HOUR + ", " +
            MEDICINE_MIN + ") VALUES (?, ?, ?, ?, ?)";

    private PreparedStatement insertDr;
    private PreparedStatement insertPa;
    private PreparedStatement insertVisit;
    private PreparedStatement insertChat;
    private PreparedStatement insertMedicine;

    private Connection conn;


    private static DataSource instance = new DataSource();

    private DataSource() {

    }

    public static DataSource getInstance() {
        return instance;
    }


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertDr = conn.prepareStatement(INSERT_DOCTOR);
            insertPa = conn.prepareStatement(INSERT_PATIENT);
            insertVisit = conn.prepareStatement(INSERT_VISIT);
            insertChat = conn.prepareStatement(INSERT_CHAT);
            insertMedicine = conn.prepareStatement(INSERT_MEDICINE);
            System.out.println("DataSource open");
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null)
                insertDr.close();
                insertPa.close();
                insertVisit.close();
                insertChat.close();
                insertMedicine.close();
                conn.close();
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }

    }

    public void createTable() {
        try {
            System.out.println("DataSource create table");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_DOCTOR + " (" +
                    DOCTOR_ID + " INTEGER PRIMARY KEY, " +
                    DOCTOR_NAME + " TEXT, " +
                    DOCTOR_LAST_N + " TEXT, " +
                    DOCTOR_EMAIL + " TEXT, " +
                    DOCTOR_PASSWORD + " TEXT, " +
                    DOCTOR_BIRTH_D + " INTEGER, " +
                    DOCTOR_BIRTH_M + " INTEGER, " +
                    DOCTOR_BIRTH_Y + " INTEGER, " +
                    DOCTOR_SPECIALITY + " TEXT, " +
                    DOCTOR_MS_NO + " TEXT, " +
                    DOCTOR_MS_IMG + " BLOB, " +
                    DOCTOR_IMG + " BLOB, " +
                    DOCTOR_DESCRIPTION + " TEXT)");

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_PATIENT + " (" +
                    PATIENT_ID + " INTEGER PRIMARY KEY, " +
                    PATIENT_NAME + " TEXT, " +
                    PATIENT_LAST_N + " TEXT, " +
                    PATIENT_EMAIL + " TEXT, " +
                    PATIENT_PASSWORD + " TEXT, " +
                    PATIENT_BIRTH_D + " INTEGER, " +
                    PATIENT_BIRTH_M + " INTEGER, " +
                    PATIENT_BIRTH_Y + " INTEGER, " +
                    PATIENT_GENDER + " TEXT, " +
                    PATIENT_WEIGHT + " TEXT, " +
                    PATIENT_IMG + " BLOB)");

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_VISIT + " (" +
                    VISIT_ID + " INTEGER PRIMARY KEY, " +
                    VISIT_UNREAD + " TEXT, " +
                    VISIT_DR_ID + " INTEGER, " +
                    VISIT_PAID + " INTEGER, " +
                    VISIT_DESCRIPTION + " TEXT, " +
                    VISIT_FILE + " BLOB)");

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CHAT + " (" +
                    CHAT_ID + " INTEGER PRIMARY KEY, " +
                    CHAT_DR_ID + " INTEGER, " +
                    CHAT_PAID + " INTEGER, " +
                    CHAT_SENDER + " TEXT, " +
                    CHAT_MESSAGE + " TEXT)");

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_MEDICINE + " (" +
                    MEDICINE_ID + " INTEGER PRIMARY KEY, " +
                    MEDICINE_PAID + " INTEGER, " +
                    MEDICINE_NAME + " TEXT, " +
                    MEDICINE_CONSUMED_DATE + " TEXT, " +
                    MEDICINE_HOUR + " TEXT, " +
                    MEDICINE_MIN + " TEXT)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDoctorInfo(Doctor doctor) {

        try {
            insertDr.setString(1, doctor.getName());
            insertDr.setString(2, doctor.getFamilyName());
            insertDr.setString(3, doctor.getEmail());
            insertDr.setString(4, doctor.getPassword());
            insertDr.setInt(5, doctor.getBirthD());
            insertDr.setInt(6, doctor.getBirthM());
            insertDr.setInt(7, doctor.getBirthY());
            insertDr.setString(8, doctor.getSpeciality());
            insertDr.setString(9, doctor.getMsNo());
            insertDr.setBytes(10, doctor.getMsImg());
            insertDr.setBytes(11, doctor.getImg());
            insertDr.executeUpdate();

        } catch (SQLException e) {
            System.out.println("InsertDoctorInfo failed: " + e.getMessage());
        }

    }

    public void insertPatientInfo(Patient patient) {

        try {
            insertPa.setString(1, patient.getName());
            insertPa.setString(2, patient.getFamilyName());
            insertPa.setString(3, patient.getEmail());
            insertPa.setString(4, patient.getPassword());
            insertPa.setInt(5, patient.getBirthD());
            insertPa.setInt(6, patient.getBirthM());
            insertPa.setInt(7, patient.getBirthY());
            insertPa.setString(8, patient.getGender());
            insertPa.setString(9, patient.getWeight());
            insertPa.setBytes(10, patient.getImg());
            insertPa.executeUpdate();

        } catch (SQLException e) {
            System.out.println("insertPatientInfo failed: " + e.getMessage());
        }
    }

    public void insertVisit(Visit visit) {
        try {
            insertVisit.setString(1, visit.getUnread());
            insertVisit.setInt(2, visit.getDrId());
            insertVisit.setInt(3, visit.getPaId());
            insertVisit.setString(4, visit.getDescription());
            insertVisit.setBytes(5, visit.getFile());
            insertVisit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertMessage(Chat chat) {
        try {
            insertChat.setInt(1, chat.getDrId());
            insertChat.setInt(2, chat.getPaID());
            insertChat.setString(3, chat.getSender());
            insertChat.setString(4, chat.getMessage());
            insertChat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMedicine(Medicine medicine) {
        try {
            insertMedicine.setInt(1, medicine.getPaId());
            insertMedicine.setString(2, medicine.getMedicineName());
            insertMedicine.setString(3, medicine.getConsumedDate());
            insertMedicine.setString(4, medicine.getHour());
            insertMedicine.setString(5, medicine.getMin());

            insertMedicine.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> getChatFromDB(int drId, int paId) throws SQLException {
        ArrayList<Chat> chatList = new ArrayList<>();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + TABLE_CHAT + " WHERE " +
                CHAT_DR_ID + " = ? AND " + CHAT_PAID + "= ?");

        statement.setInt(1, drId);
        statement.setInt(2, paId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet == null) return null;
        while (resultSet.next()) {
            Chat chat = new Chat(resultSet.getInt(CHAT_DR_ID), resultSet.getInt(CHAT_PAID),
                    resultSet.getString(CHAT_SENDER), resultSet.getString(CHAT_MESSAGE));
            chat.setMessageId(resultSet.getInt(CHAT_ID));
            chatList.add(chat);
        }

        return chatList;
    }
    public ArrayList<Chat> getDrChatFromDB(int drId) throws SQLException {
        ArrayList<Chat> chatList = new ArrayList<>();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + TABLE_CHAT + " WHERE " +
                CHAT_DR_ID + " = ?");

        statement.setInt(1, drId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet == null) return null;
        while (resultSet.next()) {
            Chat chat = new Chat(resultSet.getInt(CHAT_DR_ID), resultSet.getInt(CHAT_PAID),
                    resultSet.getString(CHAT_SENDER), resultSet.getString(CHAT_MESSAGE));
            chat.setMessageId(resultSet.getInt(CHAT_ID));
            chatList.add(chat);
        }

        return chatList;
    }
    public ArrayList<Chat> getPaChatFromDB(int paId) throws SQLException {
        ArrayList<Chat> chatList = new ArrayList<>();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + TABLE_CHAT + " WHERE " +
                CHAT_PAID + "= ?");

        statement.setInt(1, paId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet == null) return null;
        while (resultSet.next()) {
            Chat chat = new Chat(resultSet.getInt(CHAT_DR_ID), resultSet.getInt(CHAT_PAID),
                    resultSet.getString(CHAT_SENDER), resultSet.getString(CHAT_MESSAGE));
            chat.setMessageId(resultSet.getInt(CHAT_ID));
            chatList.add(chat);
        }

        return chatList;
    }

    public Doctor getDrFromDB(String email, String password) {
        try {
            Statement statement = conn.createStatement();
            statement.execute("SELECT * FROM " + TABLE_DOCTOR + " WHERE " + DOCTOR_EMAIL + "=\'" + email +
                    "\' AND " + DOCTOR_PASSWORD + "=\'" + password + "\'");

            ResultSet resultSet = statement.getResultSet();
//            convert byte to ByteArrayOutputStream
            if (resultSet == null) {
                return null;
            } else {
                byte[] byteMSImg = resultSet.getBytes(DOCTOR_MS_IMG);
                byte[] byteImg = resultSet.getBytes(DOCTOR_IMG);

                Doctor doctor = new Doctor(resultSet.getString(DOCTOR_NAME), resultSet.getString(DOCTOR_LAST_N),
                        resultSet.getString(DOCTOR_EMAIL), resultSet.getString(DOCTOR_PASSWORD),
                        resultSet.getInt(DOCTOR_BIRTH_D), resultSet.getInt(DOCTOR_BIRTH_M),
                        resultSet.getInt(DOCTOR_BIRTH_Y), resultSet.getString(DOCTOR_SPECIALITY),
                        resultSet.getString(DOCTOR_MS_NO), byteMSImg, byteImg);
                doctor.setId(resultSet.getInt(DOCTOR_ID));
                doctor.setDescription(resultSet.getString(DOCTOR_DESCRIPTION));
                return doctor;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Patient getPaFromDB(String email, String password) {
        try {
            Statement statement = conn.createStatement();
            statement.execute("SELECT * FROM " + TABLE_PATIENT + " WHERE " + PATIENT_EMAIL + "=\'" + email +
                    "\' AND " + PATIENT_PASSWORD + "=\'" + password + "\'");


            ResultSet resultSet = statement.getResultSet();
//            convert byte to ByteArrayOutputStream
            if (resultSet == null) {
                return null;
            } else {
                byte[] byteImg = resultSet.getBytes(PATIENT_IMG);

                Patient patient = new Patient(resultSet.getString(PATIENT_NAME), resultSet.getString(PATIENT_LAST_N),
                        resultSet.getString(PATIENT_EMAIL), resultSet.getString(PATIENT_PASSWORD),
                        resultSet.getInt(PATIENT_BIRTH_D), resultSet.getInt(PATIENT_BIRTH_M),
                        resultSet.getInt(PATIENT_BIRTH_Y), resultSet.getString(PATIENT_GENDER),
                        resultSet.getString(PATIENT_WEIGHT), byteImg);
                patient.setId(resultSet.getInt(DOCTOR_ID));
                return patient;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> setDrNameCombo(String speciality) throws SQLException {

        List<String> nameList = new ArrayList<String>();

        try (
                PreparedStatement statement = conn.prepareStatement("SELECT " + DOCTOR_NAME + ", " +
                        DOCTOR_LAST_N + " FROM " + TABLE_DOCTOR + " WHERE " + DOCTOR_SPECIALITY + " = ?");
        ) {
            statement.setString(1, speciality);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String fn = resultSet.getString(DOCTOR_NAME);
                String ln = resultSet.getString(DOCTOR_LAST_N);
                //use two space because of names like Amir Hossein  Mohammadi
                nameList.add(fn + "  " + ln);
            }
        }
        return nameList;

    }

    public int findDrId(String drName, String drLastN) throws SQLException {
        int id = 0;
        try (PreparedStatement statement = conn.prepareStatement("SELECT " + DOCTOR_ID + " FROM " +
                TABLE_DOCTOR + " WHERE " + DOCTOR_NAME + "= ? AND " + DOCTOR_LAST_N + "= ?");
        ) {
            statement.setString(1, drName);
            statement.setString(2, drLastN);
            ResultSet resultSet = statement.executeQuery();
            id = resultSet.getInt(DOCTOR_ID);
        }
        return id;

    }
    public Doctor getDrFromDBbyName(String name, String lastN) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + TABLE_DOCTOR +
                " WHERE " + DOCTOR_NAME + " = ? AND " + DOCTOR_LAST_N + "= ?");
        statement.setString(1, name);
        statement.setString(2, lastN);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet == null) {
            return null;
        } else {
            byte[] byteMSImg = resultSet.getBytes(DOCTOR_MS_IMG);
            byte[] byteImg = resultSet.getBytes(DOCTOR_IMG);

            Doctor doctor = new Doctor(resultSet.getString(DOCTOR_NAME), resultSet.getString(DOCTOR_LAST_N),
                    resultSet.getString(DOCTOR_EMAIL), resultSet.getString(DOCTOR_PASSWORD),
                    resultSet.getInt(DOCTOR_BIRTH_D), resultSet.getInt(DOCTOR_BIRTH_M),
                    resultSet.getInt(DOCTOR_BIRTH_Y), resultSet.getString(DOCTOR_SPECIALITY),
                    resultSet.getString(DOCTOR_MS_NO), byteMSImg, byteImg);
            doctor.setId(resultSet.getInt(DOCTOR_ID));
            doctor.setDescription(resultSet.getString(DOCTOR_DESCRIPTION));
            return doctor;
        }

    }
    public Patient getPaFromDBbyId(int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                TABLE_PATIENT + " WHERE " + PATIENT_ID + "= ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet == null) return null;

            Patient patient = new Patient(resultSet.getString(PATIENT_NAME), resultSet.getString(PATIENT_LAST_N),
            resultSet.getString(PATIENT_EMAIL), resultSet.getString(PATIENT_PASSWORD),
            resultSet.getInt(PATIENT_BIRTH_D), resultSet.getInt(PATIENT_BIRTH_M),
            resultSet.getInt(PATIENT_BIRTH_Y), resultSet.getString(PATIENT_GENDER),
            resultSet.getString(PATIENT_WEIGHT), resultSet.getBytes(PATIENT_IMG));
        patient.setId(resultSet.getInt(PATIENT_ID));
        return patient;
    }

    public Doctor getDrFromDBbyId(int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +
                TABLE_DOCTOR + " WHERE " + DOCTOR_ID + "= ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet == null) return null;

        byte[] byteMSImg = resultSet.getBytes(DOCTOR_MS_IMG);
        byte[] byteImg = resultSet.getBytes(DOCTOR_IMG);

        Doctor doctor = new Doctor(resultSet.getString(DOCTOR_NAME), resultSet.getString(DOCTOR_LAST_N),
                resultSet.getString(DOCTOR_EMAIL), resultSet.getString(DOCTOR_PASSWORD),
                resultSet.getInt(DOCTOR_BIRTH_D), resultSet.getInt(DOCTOR_BIRTH_M),
                resultSet.getInt(DOCTOR_BIRTH_Y), resultSet.getString(DOCTOR_SPECIALITY),
                resultSet.getString(DOCTOR_MS_NO), byteMSImg, byteImg);
        doctor.setId(resultSet.getInt(DOCTOR_ID));
        return doctor;
    }
    public void updatePaName(String name, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                    " SET " + PATIENT_NAME + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, name);
        statement.setInt(2, id);
        statement.executeUpdate();


    }
    public void updatePaLastN(String lastN, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_LAST_N + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, lastN);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaEmail(String email, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_EMAIL + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, email);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaPassword(String password, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_PASSWORD + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, password);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaBirthD(String birthD, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_BIRTH_D + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, birthD);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaBirthM(String birthM, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_BIRTH_M + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, birthM);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaBirthY(String birthY, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_BIRTH_Y + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, birthY);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaGender(String gender, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_GENDER + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, gender);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaWeight(String weight, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_WEIGHT + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setString(1, weight);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    public void updatePaImg(byte[] img, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_PATIENT +
                " SET " + PATIENT_IMG + "= ? WHERE " + PATIENT_ID + "= ?");

        statement.setBytes(1, img);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public void updateDrDescription(String description, int id) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_DOCTOR +
                " SET " + DOCTOR_DESCRIPTION + "= ? WHERE " + DOCTOR_ID + "= ?");

        statement.setString(1, description);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public ArrayList<Visit> getVisitsFromDB(int drId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + TABLE_VISIT +
                " WHERE " + VISIT_DR_ID + "= ? AND " + VISIT_UNREAD + "= ?");
        statement.setInt(1, drId);
        statement.setString(2, "true");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet == null) return null;

        ArrayList<Visit> visitList = new ArrayList<>();
        while (resultSet.next()) {
            Visit visit = new Visit(resultSet.getInt(VISIT_PAID),resultSet.getInt(VISIT_DR_ID),
                     resultSet.getString(VISIT_DESCRIPTION), resultSet.getBytes(VISIT_FILE));
            visit.setUnread(resultSet.getString(VISIT_UNREAD));
            visit.setVisitId(resultSet.getInt(VISIT_ID));
            visitList.add(visit);
        }
        return visitList;
    }

    public void setVisitAsRead(int visitId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_VISIT +
                " SET " + VISIT_UNREAD + "= ? WHERE " + VISIT_ID + "= ?");

        statement.setString(1, "false");
        statement.setInt(2, visitId);
        statement.executeUpdate();
    }
    public void setMedicineConsumed(int medicineId, String lastConsumed) throws SQLException {
       PreparedStatement statement = conn.prepareStatement("UPDATE " + TABLE_MEDICINE +
               " SET " + MEDICINE_CONSUMED_DATE + "= ? WHERE " + MEDICINE_ID + "= ?");
       statement.setString(1, lastConsumed);
       statement.setInt(2, medicineId);
       statement.executeUpdate();
    }

    public ArrayList<Medicine> getMedicine(int patientId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + TABLE_MEDICINE +
                " WHERE " + MEDICINE_PAID + "= ?");
        statement.setInt(1, patientId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet == null) return null;

        ArrayList<Medicine> medicineList = new ArrayList<>();
        while (resultSet.next()) {
            Medicine medicine = new Medicine(resultSet.getInt(MEDICINE_PAID), resultSet.getString(MEDICINE_NAME),
                    resultSet.getString(MEDICINE_HOUR), resultSet.getString(MEDICINE_MIN));
            medicine.setId(resultSet.getInt(MEDICINE_ID));
            medicine.setConsumedDate(resultSet.getString(MEDICINE_CONSUMED_DATE));
           medicineList.add(medicine);
        }
        return medicineList;
    }


}
