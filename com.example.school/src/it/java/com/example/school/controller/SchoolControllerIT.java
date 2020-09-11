package com.example.school.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import com.example.school.repository.mongo.StudentMongoRepository;
import com.example.school.view.StudentView;
import com.mongodb.MongoClient;

public class SchoolControllerIT {
	
	@Mock
	private StudentView studentView;
	
	private StudentRepository studentRepository;
	
	private SchoolController schoolController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		studentRepository = new StudentMongoRepository(
				new MongoClient("localhost"));
		//explicit empty the database through the repository
		for (Student student : studentRepository.findAll()) {
			studentRepository.delete(student.getId());
		}
		schoolController = new SchoolController(studentView, studentRepository);
	}
	
	 @Test
	 public void testAllStudents() {
		 Student student = new Student("1", "test");
		 studentRepository.save(student);
		 schoolController.allStudents();
		 verify(studentView)
		 .showAllStudents(asList(student));
	 }
	 
	 @Test
	 public void testNewStudent() {
		 Student student = new Student("1", "test");
		 schoolController.newStudent(student);
		 verify(studentView).studentAdded(student);
	 }
	 
	 @Test
	 public void testDeleteStudent() {
		 Student studentToDelete = new Student("1", "test");
		 studentRepository.save(studentToDelete);
		 schoolController.deleteStudent(studentToDelete);
		 verify(studentView).studentRemoved(studentToDelete);
	 }

}
