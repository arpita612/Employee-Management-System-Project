package System;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.JRadioButton;
import java.awt.Font;

public class EmployeeManagement {

	private JFrame frmStduentManagementSystem;
	private JTextField nameTextField;
	private JTextField departmentTextField;
	private JTextField contactTextField;
	private JTextField emailTextField;
	private JTextField idTextField;
	private JTextField joinDateTextField;
	private JTable table;
	private JTextField salaryTextField;
	private JTextField deletetextfield;
	private JTextField searchTextField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
	        try {
	            Database.dbInit();
	            Database.createLoginTable();
	            LoginForm.main(null); // Start with login form
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}

	public EmployeeManagement() {
		initialize();
	}
	public JFrame getFrmStduentManagementSystem() {
	    return frmStduentManagementSystem;
	}

	private void initialize() {
		frmStduentManagementSystem = new JFrame();
		frmStduentManagementSystem.setTitle("Employee Management System");
		frmStduentManagementSystem.setBounds(100, 100, 1100, 600);
		frmStduentManagementSystem.setResizable(false);
		frmStduentManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStduentManagementSystem.getContentPane();

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Id");
		model.addColumn("Name");
		model.addColumn("Department");
		model.addColumn("Joining Date");
		model.addColumn("Gender");
		model.addColumn("Contact");
		model.addColumn("Salary");
		model.addColumn("E-mail");
		model.addColumn("Address");
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(0, 0, 0)));
		inputPanel.setBounds(0, 0, 412, 565);
		inputPanel.setBackground(new Color(222, 221, 218));
		inputPanel.setLayout(null);
		
		JLabel idLabel = new JLabel("Employee ID");
		idLabel.setBounds(22, 197, 196, 27);
		inputPanel.add(idLabel);
		
		idTextField = new JTextField();
		idTextField.setBounds(206, 197, 196, 27);
		idTextField.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(idTextField);
		
		JLabel namelabel = new JLabel("Employee Name");
		namelabel.setBounds(22, 231, 196, 27);
		inputPanel.add(namelabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(206, 231, 196, 27);
		nameTextField.setToolTipText("");
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(nameTextField);
		
		JLabel departmentLabel = new JLabel("Department");
		departmentLabel.setBounds(22, 265, 196, 27);
		inputPanel.add(departmentLabel);
		
		departmentTextField = new JTextField();
		departmentTextField.setBounds(206, 265, 196, 27);
		departmentTextField.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(departmentTextField);
		
		JLabel joinDateLabel = new JLabel("Joining Date");
		joinDateLabel.setBounds(22, 299, 196, 27);
		inputPanel.add(joinDateLabel);
		
		joinDateTextField = new JTextField();
		joinDateTextField.setBounds(206, 299, 196, 27);
		joinDateTextField.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(joinDateTextField);
		
		JLabel genderLabel = new JLabel("Gender");
		genderLabel.setBounds(22, 333, 196, 27);
		inputPanel.add(genderLabel);
		
		JComboBox<String> genderComboBox = new JComboBox<String>();
		genderComboBox.setBounds(206, 333, 196, 27);
		genderComboBox.setEditable(false);
		genderComboBox.setMaximumRowCount(3);
		genderComboBox.addItem("Male");
		genderComboBox.addItem("Female");
		genderComboBox.addItem("Other");
		frmStduentManagementSystem.getContentPane().setLayout(null);
		inputPanel.add(genderComboBox);
		
		JLabel contactLabel = new JLabel("Contact");
		contactLabel.setBounds(22, 367, 196, 27);
		inputPanel.add(contactLabel);
		
		contactTextField = new JTextField();
		contactTextField.setBounds(206, 367, 196, 27);
		contactTextField.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(contactTextField);
		
		JLabel emailLabel = new JLabel("E-mail");
		emailLabel.setBounds(22, 401, 196, 27);
		inputPanel.add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(206, 401, 196, 27);
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(emailTextField);
		
		JLabel salaryLabel = new JLabel("Salary");
		salaryLabel.setBounds(22, 435, 196, 27);
		inputPanel.add(salaryLabel);
		
		salaryTextField = new JTextField();
		salaryTextField.setBounds(206, 435, 196, 27);
		salaryTextField.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(salaryTextField);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setBounds(22, 469, 196, 27);
		inputPanel.add(addressLabel);
		
		JTextArea addressTextArea = new JTextArea();
		addressTextArea.setBounds(206, 469, 196, 55);
		inputPanel.add(addressTextArea);
		frmStduentManagementSystem.getContentPane().add(inputPanel);

		JButton insertButton = new JButton("Insert");
		insertButton.setBounds(12, 531, 196, 27);
		inputPanel.add(insertButton);
		insertButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Database.insertEmployeeData(idTextField.getText(), 
										   nameTextField.getText(), 
										   departmentTextField.getText(),  
										   joinDateTextField.getText(),
										   genderComboBox.getSelectedItem().toString(),
										   contactTextField.getText(),
										   salaryTextField.getText(), 
										   emailTextField.getText(),
										   addressTextArea.getText());
					Database.loadData(model);
					JOptionPane.showMessageDialog(inputPanel,"Employee successfully inserted","Inserted", JOptionPane.INFORMATION_MESSAGE);

					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(inputPanel,"Employee ID Already exists","ERROR", JOptionPane.ERROR_MESSAGE);
					e2.printStackTrace();
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.setBounds(206, 531, 196, 27);
		inputPanel.add(updateButton);
		
		JTextArea titleText = new JTextArea();
		titleText.setEditable(false);
		titleText.setLineWrap(true);
		titleText.setWrapStyleWord(true);
		titleText.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 35));
		titleText.setBackground(new Color(222, 221, 218));
		titleText.setText("Employee Management System");
		titleText.setBounds(12, 12, 388, 178);
		titleText.setWrapStyleWord(true);
		titleText.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(4, 226, 201)));
		inputPanel.add(titleText);
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					Database.updateEmployeeData(idTextField.getText(), 
							   nameTextField.getText(), 
							   departmentTextField.getText(),  
							   joinDateTextField.getText(),
							   genderComboBox.getSelectedItem().toString(),
							   contactTextField.getText(),
							   emailTextField.getText(),
							   salaryTextField.getText(), addressTextArea.getText());
					Database.loadData(model);
					JOptionPane.showMessageDialog(inputPanel,"Employee successfully Updated","Updated", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e2) {
					
					e2.printStackTrace();
				}
			}
		});
		
		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(0, 0, 0)));
		outputPanel.setBounds(413, 0, 687, 565);
		outputPanel.setBackground(new Color(222, 221, 218));
		frmStduentManagementSystem.getContentPane().add(outputPanel);
		
		table = new JTable(model);
		table.setBackground(new Color(222, 221, 218));
		table.setVisible(true);

		outputPanel.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 143, 663, 410);
		scrollPane.setViewportBorder(null);
		outputPanel.add(scrollPane);
		
		JButton loadButton = new JButton("Load Data");
		loadButton.setBounds(351, 12, 117, 25);
		outputPanel.add(loadButton);
		loadButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						try {
							Database.loadData(model);
						} catch (Exception e2) {
							
							e2.printStackTrace();
						}
					}
				});

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(12, 12, 117, 25);
		outputPanel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Database.deleteEmployeeData(deletetextfield.getText());
					Database.loadData(model);
					JOptionPane.showMessageDialog(outputPanel,"Employee successfully deleted","Deleted", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e2) {
					
					e2.printStackTrace();
				}
			}
		});
		
		deletetextfield = new JTextField();
		deletetextfield.setBounds(154, 12, 185, 25);
		outputPanel.add(deletetextfield);
		deletetextfield.setColumns(10);
		
		ButtonGroup bg = new ButtonGroup();
		
		JRadioButton nameRadio = new JRadioButton("Name");
		nameRadio.setBackground(new Color(222, 221, 218));
		nameRadio.setBounds(115, 100, 75, 23);
		outputPanel.add(nameRadio);
		
		JRadioButton idRadio = new JRadioButton("ID");
		idRadio.setBackground(new Color(222, 221, 218)); 
		idRadio.setBounds(305, 100, 75, 23);
		outputPanel.add(idRadio);
		
		JRadioButton emailRadio = new JRadioButton("E-mail");
		emailRadio.setBackground(new Color(222, 221, 218));
		emailRadio.setBounds(495, 100, 75, 23);
		outputPanel.add(emailRadio);
		
		bg.add(nameRadio);
		bg.add(idRadio);
		bg.add(emailRadio);

		JButton searchButton = new JButton("Search");
		searchButton.setBounds(12, 62, 117, 25);
		outputPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					try {
						
						if (nameRadio.isSelected()) {
							
							Database.searchEmployeeData(model,searchTextField.getText(),"Employee_name" );
							
						}else if (idRadio.isSelected()) {
							
							Database.searchEmployeeData(model,searchTextField.getText(),"Employee_id" );
							
						}else if(emailRadio.isSelected()) {
							Database.searchEmployeeData(model,searchTextField.getText(),"Employee_email" );
						}else {
							JOptionPane.showMessageDialog(outputPanel,"Please Select a field to search","ERROR", JOptionPane.ERROR_MESSAGE);

						}
						
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(outputPanel,"NOT Found","ERROR", JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
					}
			}
		});
		
		searchTextField = new JTextField();
		searchTextField.setColumns(10);
		searchTextField.setBounds(154, 62, 185, 25);
		outputPanel.add(searchTextField);
		
		JLabel searchBy = new JLabel("Search By:");
		searchBy.setBounds(12, 99, 98, 25);
		outputPanel.add(searchBy);
	
	}
}
