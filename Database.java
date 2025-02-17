package System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class Database {
    static Connection conn;
    static final String URL = "jdbc:mysql://localhost:3306/EmployeeDB";  
    static final String USER = "root"; 
    static final String PASSWORD = "Aaru@6122003"; 
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void createLoginTable() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();

            String query = "CREATE TABLE IF NOT EXISTS Login (" +
                           "username VARCHAR(50) PRIMARY KEY, " +
                           "password VARCHAR(50) NOT NULL" +
                           ");";

            stmt.executeUpdate(query);

            // Insert default admin user (only if table is empty)
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Login;");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO Login (username, password) VALUES ('admin', 'admin123');");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbInit() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();

            String query = "CREATE TABLE IF NOT EXISTS EmployeeData ("
                    + "Employee_id VARCHAR(50) PRIMARY KEY, "
                    + "Employee_name VARCHAR(100), "
                    + "department VARCHAR(100), "
                    + "Employee_joinDate DATE, "
                    + "Employee_gender VARCHAR(10), "
                    + "Employee_contact VARCHAR(15), "
                    + "Employee_email VARCHAR(100), "
                    + "salary DECIMAL(10,2), "
                    + "Employee_address TEXT"
                    + ");";

            stmt.executeUpdate(query);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void insertEmployeeData(String id, String name, String department, String joinDate,
            String gender, String contact, String salary, String email, String address) throws SQLException {

    	String query = "INSERT INTO EmployeeData(Employee_id, Employee_name, department, Employee_joinDate, " +
    	"Employee_gender, Employee_contact, salary, Employee_email, Employee_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    			PreparedStatement pstmt = conn.prepareStatement(query)) {

    		pstmt.setString(1, id);
    		pstmt.setString(2, name);
    		pstmt.setString(3, department);
    		pstmt.setString(4, joinDate);
			pstmt.setString(5, gender);
			pstmt.setString(6, contact);
			pstmt.setDouble(7, Double.parseDouble(salary));  // Ensure salary is numeric
			pstmt.setString(8, email);
			pstmt.setString(9, address);

			pstmt.executeUpdate();
			} catch (SQLException e) {
			e.printStackTrace();
			}
		}


    public static void updateEmployeeData(String id, String name, String department, String contact, String joinDate,
                                          String gender, String email, String salary, String address) throws SQLException {
        String query = "UPDATE EmployeeData SET Employee_name='" + name + "', department='" + department + "', " +
                "Employee_contact='" + contact + "', Employee_joinDate='" + joinDate + "', Employee_gender='" + gender + "', " +
                "salary='" + salary + "', Employee_email='" + email + "', Employee_address='" + address + "' WHERE Employee_id='" + id + "';";

        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        conn.close();
    }

    public static void deleteEmployeeData(String id) throws SQLException {
        String query = "DELETE FROM EmployeeData WHERE Employee_id='" + id + "';";

        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        conn.close();
    }

    public static void searchEmployeeData(DefaultTableModel model, String searchTerm, String column) throws SQLException {
        model.setRowCount(0);
        String query = "SELECT * FROM EmployeeData WHERE " + column + " LIKE '%" + searchTerm + "%';";

        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            model.addRow(new Object[]{
                    rs.getString("Employee_id"),
                    rs.getString("Employee_name"),
                    rs.getString("department"),
                    rs.getString("Employee_joinDate"),
                    rs.getString("Employee_gender"),
                    rs.getString("Employee_contact"),
                    rs.getString("salary"),
                    rs.getString("Employee_email"),
                    rs.getString("Employee_address")
            });
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    public static void loadData(DefaultTableModel model) throws SQLException {
        model.setRowCount(0);
        String query = "SELECT * FROM EmployeeData;";

        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            model.addRow(new Object[]{
                    rs.getString("Employee_id"),
                    rs.getString("Employee_name"),
                    rs.getString("department"),
                    rs.getString("Employee_joinDate"),
                    rs.getString("Employee_gender"),
                    rs.getString("Employee_contact"),
                    rs.getString("salary"),
                    rs.getString("Employee_email"),
                    rs.getString("Employee_address")
            });
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
