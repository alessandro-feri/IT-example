package com.example.school.controller;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static java.util.Arrays.asList;

import org.mockito.MockitoAnnotations;

import com.example.school.controller.SchoolController;
import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import com.example.school.view.StudentView;

public class SchoolControllerTest {
		
		@Mock
		private StudentRepository studentRepository;
		
		@Mock
		private StudentView studentView;
		
		@InjectMocks
		private SchoolController schoolController;

		@Before
		public void setUp() throws Exception {
			MockitoAnnotations.initMocks(this);
		}
		
		@Test
		public void testAllStudents() {
			List<Student> students = asList(new Student());
			when(studentRepository.findAll()).thenReturn(students);
			schoolController.allStudents();
			verify(studentView).showAllStudents(students);
		}
		
		@Test
		public void testNewStudentWhenStudentDoesNotAlreadyExist() {
			Student student = new Student("1", "test");
			when(studentRepository.findById("1")).thenReturn(null);
			schoolController.newStudent(student);
			InOrder inOrder = inOrder(studentRepository, studentView);
			inOrder.verify(studentRepository).save(student);
			inOrder.verify(studentView).studentAdded(student);
		}
		
		@Test
		public void testNewStudentWhenStudentAlreadyExists() {
			Student studentToAdd = new Student("1", "test");
			Student existingStudent = new Student("1", "name");
			when(studentRepository.findById("1")).thenReturn(existingStudent);
			schoolController.newStudent(studentToAdd);
			verify(studentView).showError("Already existing student with id 1", existingStudent);
			verifyNoMoreInteractions(ignoreStubs(studentRepository));
		}
		
		@Test
		public void testDeleteStudentWhenStudentDoesNotAlreadyExist() {
			Student student = new Student("1", "test");
			when(studentRepository.findById("1")).thenReturn(null);
			schoolController.deleteStudent(student);
			verify(studentView).showError("No existing student with id 1", student);
			verifyNoMoreInteractions(ignoreStubs(studentRepository));
		}
		
		
		@Test
		public void testDeleteStudentWhenStudentAlreadyExists() {
			Student studentToDelete = new Student("1", "test");
			when(studentRepository.findById("1")).thenReturn(studentToDelete);
			schoolController.deleteStudent(studentToDelete);
			InOrder inOrder = inOrder(studentRepository, studentView);
			inOrder.verify(studentRepository).delete(studentToDelete.getId());
			inOrder.verify(studentView).studentRemoved(studentToDelete);			
		}
		
	
}
