package com.example.school.view.swing;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.example.school.controller.SchoolController;
import com.example.school.model.Student;
import com.example.school.view.StudentView;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class StudentSwingView extends JFrame implements StudentView {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtName;
	private JButton btnAdd;
	private JList<Student> listStudents;
	private JScrollPane scrollPane;
	private JButton btnDeleteSelected;
	private JLabel lblErrorMessage;
	
	private SchoolController schoolController;
	
	private DefaultListModel<Student> listStudentsModel;

	/**
	 * Create the frame.
	 */
	public StudentSwingView() {
		setTitle("Student View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblId = new JLabel("id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		contentPane.add(lblId, gbc_lblId);
		
		
		final KeyAdapter btnAddEnabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnAdd.setEnabled(
					!txtId.getText().trim().isEmpty() &&
					!txtName.getText().trim().isEmpty()
				);
			}
		};
		
		txtId = new JTextField();
		txtId.addKeyListener(btnAddEnabler);
		txtId.setName("idTextBox");
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.insets = new Insets(0, 0, 5, 0);
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtId.gridx = 1;
		gbc_txtId.gridy = 0;
		contentPane.add(txtId, gbc_txtId);
		txtId.setColumns(10);
		
		JLabel lblName = new JLabel("name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		contentPane.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		txtName.addKeyListener(btnAddEnabler);
		txtName.setName("nameTextBox");
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 1;
		contentPane.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		btnAdd.addActionListener(
				e -> schoolController.newStudent(new Student(txtId.getText(), txtName.getText())));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 2;
		contentPane.add(btnAdd, gbc_btnAdd);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		
		listStudentsModel = new DefaultListModel<>();
		listStudents = new JList<>(listStudentsModel);
		
		listStudents.addListSelectionListener(e -> btnDeleteSelected.
				setEnabled(listStudents.getSelectedIndex() != -1));
		
		scrollPane.setViewportView(listStudents);
		listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listStudents.setName("studentList");
		
		btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.setEnabled(false);
		btnDeleteSelected.addActionListener(
				e -> schoolController.deleteStudent(listStudents.getSelectedValue()));
		GridBagConstraints gbc_btnDeleteSelected = new GridBagConstraints();
		gbc_btnDeleteSelected.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteSelected.gridwidth = 2;
		gbc_btnDeleteSelected.gridx = 0;
		gbc_btnDeleteSelected.gridy = 4;
		contentPane.add(btnDeleteSelected, gbc_btnDeleteSelected);
		
		lblErrorMessage = new JLabel(" ");
		lblErrorMessage.setName("errorMessageLabel");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 5;
		contentPane.add(lblErrorMessage, gbc_lblNewLabel);
		
	}
	
	DefaultListModel<Student> getListStudentModel() {
		return listStudentsModel;
	}
	
	public void setSchoolController(SchoolController schoolController) {
		this.schoolController = schoolController;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentSwingView frame = new StudentSwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void showAllStudents(List<Student> students) {
		// TODO Auto-generated method stub
		students.stream().forEach(listStudentsModel::addElement);
	}

	@Override
	 public void showError(String message, Student student) {
		 lblErrorMessage.setText(message + ": " + student);
	}


	 @Override
	 public void studentAdded(Student student) {
		 listStudentsModel.addElement(student);
		 resetErrorLabel();
	 }
	 
	 @Override
	 public void studentRemoved(Student student) {
		 listStudentsModel.removeElement(student);
		 resetErrorLabel();
	 }
	 
	 private void resetErrorLabel() {
		 lblErrorMessage.setText(" ");
	 }


}
