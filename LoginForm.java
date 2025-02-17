package System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class LoginForm {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(() -> {
            try {
                Database.dbInit();
                Database.createLoginTable();
                LoginForm window = new LoginForm();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginForm() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Login");
        frame.setBounds(100, 100, 350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 50, 80, 25);
        frame.getContentPane().add(lblUsername);
        
        usernameField = new JTextField();
        usernameField.setBounds(120, 50, 180, 25);
        frame.getContentPane().add(usernameField);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 90, 80, 25);
        frame.getContentPane().add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(120, 90, 180, 25);
        frame.getContentPane().add(passwordField);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(120, 130, 90, 25);
        frame.getContentPane().add(btnLogin);
        
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setBounds(120, 160, 90, 25);
        frame.getContentPane().add(btnSignUp);
        
        // Login Button Action
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    frame.dispose();
                    // Open the Employee Management System
                    EmployeeManagement employeeManagement = new EmployeeManagement();
                    employeeManagement.getFrmStduentManagementSystem().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Sign Up Button Action
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Username and Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (signUpUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Sign Up Successful! Please login.");
                    usernameField.setText("");
                    passwordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Sign Up Failed! Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Authenticate User Method (Login)
    private boolean authenticateUser(String username, String password) {
        boolean isValidUser = false;
        try {
            Connection conn = DriverManager.getConnection(Database.URL, Database.USER, Database.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Login WHERE username='" + username + "' AND password='" + password + "';";
            ResultSet rs = stmt.executeQuery(query);
            isValidUser = rs.next();
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValidUser;
    }

    // Sign Up User Method (Insert new user into database)
    private boolean signUpUser(String username, String password) {
        boolean isSuccess = false;
        try {
            Connection conn = DriverManager.getConnection(Database.URL, Database.USER, Database.PASSWORD);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Login WHERE username='" + username + "';";
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                // Username already exists
                JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                rs.close();
                stmt.close();
                conn.close();
                return isSuccess;
            }

            String insertQuery = "INSERT INTO Login (username, password) VALUES ('" + username + "', '" + password + "');";
            int rowsAffected = stmt.executeUpdate(insertQuery);
            
            if (rowsAffected > 0) {
                isSuccess = true;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
