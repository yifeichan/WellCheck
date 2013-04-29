/*
 * Add comment
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wellcheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.PreparedStatement;
import java.util.*;

public class Database {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private String url;
    private String user;
    private String password;
    private PreparedStatement statement;
    int temp;

    public Database() {
        con = null;
        st = null;
        rs = null;
        rsmd = null;
        statement = null;

        url = "jdbc:mysql://205.178.146.105/1_0362c2e_3";
        user = "1_0362c2e_3";
        password = "3ggM4KkyFD";
    }

    public void Connect() {
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
        return false;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return false;
	}
}
public String getUserType(String username,String password){
       try {
    int count = 0;
    statement = (PreparedStatement) con.prepareStatement("SELECT usertype FROM users WHERE username = \""+username+"\" AND password=\""+password+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
    return rs.getString(1);
    }
        return null;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;
	} 
}
public boolean patientTable(){
    try {
    ArrayList<String> patient = new ArrayList();
    ArrayList<Integer> doctor = new ArrayList();
    statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName, Patient.Doctor FROM users JOIN Patient ON (users.userid = Patient.userid)");
    rs = statement.executeQuery();
    while(rs.next()){
        patient.add(rs.getString(1)+" "+rs.getString(2));
        doctor.add(Integer.parseInt(rs.getString(3)));
        
    }
    int j = 0;
    for(int i=0;i<doctor.size();i++){
    statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName FROM users JOIN Patient ON (users.userid = Patient.Doctor) WHERE Patient.Doctor = \""+doctor.get(i)+"\"");
    rs = statement.executeQuery();
    if(rs.next()){
        DoctorWindowController.addPatient(patient.get(i), rs.getString(1)+" "+rs.getString(2));
        DoctorWindowController.patientComboBox(patient.get(i));
        }
    }
        return false;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return false;
	}
}
public boolean patientScreenTable(String firstname,String lastname){
     try {
    ArrayList<String> patient = new ArrayList();
    ArrayList<Integer> doctor = new ArrayList();
    String userid = "";
    statement = (PreparedStatement) con.prepareStatement("SELECT userid FROM users Where FirstName= \""+firstname+"\" AND LastName=\""+lastname+"\"");
    rs = statement.executeQuery();
    if(rs.next()){
    userid = rs.getString(1);
    }
    statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName FROM users JOIN Patient ON (users.userid = Patient.userid) WHERE Patient.DependantTo =\""+userid+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
            patient.add(rs.getString(1)+" "+rs.getString(2));
      
                     
    }
    statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName, Patient.Doctor FROM users JOIN Patient ON (users.userid = Patient.userid)");
    rs = statement.executeQuery();
    while(rs.next()){
        if(rs.getString(1).equalsIgnoreCase(firstname) && rs.getString(2).equalsIgnoreCase(lastname)){
            patient.add(rs.getString(1)+" "+rs.getString(2));
            doctor.add(Integer.parseInt(rs.getString(3)));
        }else if(patient.contains((String)rs.getString(1)+" "+rs.getString(2))){
            doctor.add(Integer.parseInt(rs.getString(3)));
        }          
    }
    int j = 0;
    for(int i=0;i<doctor.size();i++){
    statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName FROM users JOIN Patient ON (users.userid = Patient.Doctor) WHERE Patient.Doctor = \""+doctor.get(i)+"\"");
    rs = statement.executeQuery();
    if(rs.next()){
        DoctorWindowController.addPatient(patient.get(i), rs.getString(1)+" "+rs.getString(2));
        DoctorWindowController.patientComboBox(patient.get(i));
        }
    }
        return false;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return false;
	}
}
public ArrayList<dataTable> dataTable(String fName,String lName){
    try {
    ArrayList<dataTable> data = new ArrayList();
    int count = 0;
    String id="";
    statement = (PreparedStatement) con.prepareStatement("SELECT userid FROM users Where FirstName= \""+fName+"\" AND LastName=\""+lName+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
    id = rs.getString(1);
    }
    statement = (PreparedStatement) con.prepareStatement("SELECT BloodPressure, SugarLevel,Weight,Date,Comments FROM Records Where PatientId=\""+id+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
    data.add(new dataTable(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
    }
        return data;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;
	}
}
public String getSecretQuestion(String fName, String lName){
    try {
    int count = 0;
    statement = (PreparedStatement) con.prepareStatement("SELECT SecretQuestion FROM users WHERE FirstName = \""+fName+"\" AND LastName=\""+lName+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
    return rs.getString(1);
    }
        return null;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;
	}
}
public String getSecretAnswer(String fName, String lName){
     try {
    int count = 0;
    statement = (PreparedStatement) con.prepareStatement("SELECT SecretAnswer FROM users WHERE FirstName = \""+fName+"\" AND LastName=\""+lName+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
    return rs.getString(1);
    }
        return null;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;
	}
}
public String getPassword(String fName, String lName){
     try {
    int count = 0;
    statement = (PreparedStatement) con.prepareStatement("SELECT Password FROM users WHERE FirstName = \""+fName+"\" AND LastName=\""+lName+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
    return rs.getString(1);
    }
        return null;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;
	}
}
public boolean deleteUser(String fName, String lName){
     try {
    String userid="";
    statement = (PreparedStatement) con.prepareStatement("Select userid FROM users WHERE FirstName = \""+fName+"\"AND LastName=\""+lName+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
    userid = rs.getString(1);
        }
    statement = (PreparedStatement) con.prepareStatement("DELETE FROM users WHERE userid ="+userid+"");
    statement.execute();
    statement = (PreparedStatement) con.prepareStatement("DELETE FROM Patient WHERE userid ="+userid+"");
    statement.execute();
    statement = (PreparedStatement) con.prepareStatement("DELETE FROM Records WHERE PatientId ="+userid+"");
    statement.executeQuery();
    return true;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return false;
	}
}
public String getUserName(String fName, String lName){
     try {
    int count = 0;
    statement = (PreparedStatement) con.prepareStatement("SELECT username FROM users WHERE FirstName = \""+fName+"\" AND LastName=\""+lName+"\"");
    rs = statement.executeQuery();
    while(rs.next()){
        System.out.println(rs.getString(1));
    return rs.getString(1);
    }
        return null;
    } catch (SQLException ex) {
		Logger lgr = Logger.getLogger(Database.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;
	}
}
public void initializePatient(String firstName, String lastName, String dateOfBirth, String address1,
        String address2, String city, String state, String zip, String phoneNumber,
        String insuranceProvider, String memberId, String groupNumber, String assignedDoctor, String userId) {
    String empty = "empty";
    try {
    statement = (PreparedStatement) con.prepareStatement("SELECT userid FROM users");
    rs = statement.executeQuery();
    while(rs.next()){
        userId = rs.getString(1);
    }
    if(userId.equals(" ")){
        userId = "1";
    }else{
    int temp = Integer.parseInt(userId);
    temp++;
    userId = Integer.toString(temp);
    }
    statement = (PreparedStatement) con.prepareStatement(
            "INSERT INTO `1_0362c2e_3`.`Patient` (`userid`, `Address1`, `Address2`, `City`, `State`, "
            + "`ZipCode`, `PhoneNumber`, `InsuranceProvider`, `memberid`, `groupid`, `DependantTo`, `Doctor`) "
            + "VALUES ('" + userId + "', '" + address1 + "', '" + address2 + "', '" + city + "', '" + state + "', '" + zip + "', '" + phoneNumber 
            + "', '" + insuranceProvider + "', '" + memberId + "', '" + groupNumber + "', '0', '" + assignedDoctor + "');");
    statement.execute();
    statement = (PreparedStatement) con.prepareStatement("INSERT INTO `1_0362c2e_3`.`users`(`userid` ,`username` ,`password` ,`usertype` ,"
+"`FirstName` ,`LastName` ,`DOB` ,`SecretQuestion` ,`SecretAnswer`)"
+"VALUES ('" + userId + "', '" + empty + "', '" + empty + "', " + " 'Patient', " + " '" + firstName + "', '" + lastName + "', '" + dateOfBirth 
            + "', '" + empty + "', '" + empty + "');");
    
    statement.execute();

    public String getUserType(String username, String password) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT usertype FROM users WHERE username = \"" + username + "\" AND password=\"" + password + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public boolean patientTable() {
        try {
            ArrayList<String> patient = new ArrayList();
            ArrayList<Integer> doctor = new ArrayList();
            statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName, Patient.Doctor FROM users JOIN Patient ON (users.userid = Patient.userid)");
            rs = statement.executeQuery();
            while (rs.next()) {
                patient.add(rs.getString(1) + " " + rs.getString(2));
                doctor.add(Integer.parseInt(rs.getString(3)));

            }
            int j = 0;
            for (int i = 0; i < doctor.size(); i++) {
                statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName FROM users JOIN Patient ON (users.userid = Patient.Doctor) WHERE Patient.Doctor = \"" + doctor.get(i) + "\"");
                rs = statement.executeQuery();
                if (rs.next()) {
                    DoctorWindowController.addPatient(patient.get(i), rs.getString(1) + " " + rs.getString(2));
                    DoctorWindowController.patientComboBox(patient.get(i));
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public boolean patientScreenTable(String firstname, String lastname) {
        try {
            ArrayList<String> patient = new ArrayList();
            ArrayList<Integer> doctor = new ArrayList();
            String userid = "";
            statement = (PreparedStatement) con.prepareStatement("SELECT userid FROM users Where FirstName= \"" + firstname + "\" AND LastName=\"" + lastname + "\"");
            rs = statement.executeQuery();
            if (rs.next()) {
                userid = rs.getString(1);
            }
            statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName FROM users JOIN Patient ON (users.userid = Patient.userid) WHERE Patient.DependantTo =\"" + userid + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                patient.add(rs.getString(1) + " " + rs.getString(2));


            }
            statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName, Patient.Doctor FROM users JOIN Patient ON (users.userid = Patient.userid)");
            rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase(firstname) && rs.getString(2).equalsIgnoreCase(lastname)) {
                    patient.add(rs.getString(1) + " " + rs.getString(2));
                    doctor.add(Integer.parseInt(rs.getString(3)));
                } else if (patient.contains((String) rs.getString(1) + " " + rs.getString(2))) {
                    doctor.add(Integer.parseInt(rs.getString(3)));
                }
            }
            int j = 0;
            for (int i = 0; i < doctor.size(); i++) {
                statement = (PreparedStatement) con.prepareStatement("SELECT FirstName, LastName FROM users JOIN Patient ON (users.userid = Patient.Doctor) WHERE Patient.Doctor = \"" + doctor.get(i) + "\"");
                rs = statement.executeQuery();
                if (rs.next()) {
                    DoctorWindowController.addPatient(patient.get(i), rs.getString(1) + " " + rs.getString(2));
                    DoctorWindowController.patientComboBox(patient.get(i));
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public ArrayList<dataTable> dataTable(String fName, String lName) {
        try {
            ArrayList<dataTable> data = new ArrayList();
            int count = 0;
            String id = "";
            statement = (PreparedStatement) con.prepareStatement("SELECT userid FROM users Where FirstName= \"" + fName + "\" AND LastName=\"" + lName + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getString(1);
            }
            statement = (PreparedStatement) con.prepareStatement("SELECT BloodPressure, SugarLevel,Weight,Date,Comments FROM Records Where PatientId=\"" + id + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                data.add(new dataTable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            return data;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public String getSecretQuestion(String fName, String lName) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT SecretQuestion FROM users WHERE FirstName = \"" + fName + "\" AND LastName=\"" + lName + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public String getSecretAnswer(String fName, String lName) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT SecretAnswer FROM users WHERE FirstName = \"" + fName + "\" AND LastName=\"" + lName + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public String getPassword(String fName, String lName) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT Password FROM users WHERE FirstName = \"" + fName + "\" AND LastName=\"" + lName + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public boolean deleteUser(String fName, String lName) {
        try {
            String userid = "";
            statement = (PreparedStatement) con.prepareStatement("Select userid FROM users WHERE FirstName = \"" + fName + "\"AND LastName=\"" + lName + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                userid = rs.getString(1);
            }
            statement = (PreparedStatement) con.prepareStatement("DELETE FROM users WHERE userid =" + userid + "");
            statement.execute();
            statement = (PreparedStatement) con.prepareStatement("DELETE FROM Patient WHERE userid =" + userid + "");
            statement.execute();
            statement = (PreparedStatement) con.prepareStatement("DELETE FROM Records WHERE PatientId =" + userid + "");
            statement.executeQuery();
            return true;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public String getUserName(String fName, String lName) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT username FROM users WHERE FirstName = \"" + fName + "\" AND LastName=\"" + lName + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public void initializePatient(String firstName, String lastName, String dateOfBirth, String address1,
            String address2, String city, String state, String zip, String phoneNumber,
            String insuranceProvider, String memberId, String groupNumber, String assignedDoctor, String userId) {
        String empty = "empty";
        try {
            statement = (PreparedStatement) con.prepareStatement(
                    "INSERT INTO `1_0362c2e_3`.`Patient` (`userid`, `Address1`, `Address2`, `City`, `State`, "
                    + "`ZipCode`, `PhoneNumber`, `InsuranceProvider`, `memberid`, `groupid`, `DependantTo`, `Doctor`) "
                    + "VALUES ('" + userId + "', '" + address1 + "', '" + address2 + "', '" + city + "', '" + state + "', '" + zip + "', '" + phoneNumber
                    + "', '" + insuranceProvider + "', '" + memberId + "', '" + groupNumber + "', '0', '" + assignedDoctor + "');");
            statement.execute();
            statement = (PreparedStatement) con.prepareStatement("INSERT INTO `1_0362c2e_3`.`users`(`userid` ,`username` ,`password` ,`usertype` ,"
                    + "`FirstName` ,`LastName` ,`DOB` ,`SecretQuestion` ,`SecretAnswer`)"
                    + "VALUES ('" + userId + "', '" + empty + "', '" + empty + "', " + " 'Patient', " + " '" + firstName + "', '" + lastName + "', '" + dateOfBirth
                    + "', '" + empty + "', '" + empty + "');");

            statement.execute();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public boolean userExist(String string1) {
        try {
            statement = (PreparedStatement) con.prepareStatement("SELECT username FROM users WHERE username =\"" + string1 + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equals(string1)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return false;
    }

    public boolean checkPassword(String user, String password) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("Select password FROM users WHERE username = \"" + user + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public String getId(String name) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT userid FROM users WHERE FirstName = \"" + name + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public String[] getInfo(String id) {
        int count = 0;
        String[] array = new String[20];
        try {
            statement = (PreparedStatement) con.prepareStatement("SELECT Date, BloodPressure, SugarLevel, Weight FROM Records WHERE PatientId = \"" + id + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                array[count] = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                count++;
            }
            array[count] = null;
            return array;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public String getDoctorFirst(String id) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT FirstName FROM users WHERE userid = \"" + id + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public String getDoctorLast(String id) {
        try {
            int count = 0;
            statement = (PreparedStatement) con.prepareStatement("SELECT LastName FROM users WHERE userid = \"" + id + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public void addEntry(String first, String last, String BloodPressure, String SugarLevel, String Weight, String Date, String Comments) {

        try {
            String PatientId = "";
            statement = (PreparedStatement) con.prepareStatement("SELECT userid FROM users WHERE FirstName = \"" + first + "\" AND LastName=\"" + last + "\"");
            rs = statement.executeQuery();
            while (rs.next()) {
                PatientId = rs.getString(1);
            }
            System.out.println("this is a ID " + PatientId);
            statement = (PreparedStatement) con.prepareStatement(
                    "INSERT INTO `1_0362c2e_3`.`Records` (`id`, `PatientId`, `BloodPressure`, `SugarLevel`, `Weight`, "
                    + "`Date`, `Comments`) "
                    + "VALUES ('" + 1 + "', '" + PatientId + "', '" + BloodPressure + "', '" + SugarLevel + "', '" + Weight + "', '" + Date + "', '" + Comments + "');");
            statement.execute();


        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /*Kent's method
     * This method queries the database using an arbitrary string and returns
     * a List of Lists. Proper processing of the results is done in the calling
     * method.
     */
    public List dbQuery(String qstring) {
        try {
            statement = (PreparedStatement) con.prepareStatement(qstring);
            rs = statement.executeQuery();
            rsmd = rs.getMetaData();

            List<Object> objlist;
            List<List> returnlist = new ArrayList();

            while (rs.next()) {
                objlist = new ArrayList();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    objlist.add(rs.getObject(i));
                }
                returnlist.add(objlist);
            }
            return returnlist;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    /*Kent's Method
     * This method modifies a database entry using an arbitrary string.
     * The calling method must supply the correct string.
     */
    public void updateDB(String qstring) {
        try {
            statement = (PreparedStatement) con.prepareStatement(qstring);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}
