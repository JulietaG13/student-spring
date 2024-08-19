package com.example.demo_spring.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
  
  private final StudentRepository studentRepository;
  
  @Autowired
  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }
  
  public List<Student> getStudents() {
    return studentRepository.findAll();
  }
  
  public void addStudent(Student student) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
    if (studentOptional.isPresent()) {
      throw new IllegalStateException("Student with email: " + student.getEmail() + " already exists");
    }
    studentRepository.save(student);
  }
  
  public void deleteStudent(Long studentId) {
    boolean exists = studentRepository.existsById(studentId);
    if (!exists) {
      throw new IllegalStateException("Student with id: " + studentId + " does not exist");
    }
    studentRepository.deleteById(studentId);
  }
  
  @Transactional
  public void updateStudent(Long studentId, String name, String email) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new IllegalStateException("Student with id: " + studentId + " does not exist"));
    
    if (name != null && !name.isEmpty() && !student.getName().equals(name)) {
      student.setName(name);
    }
    
    if (email != null && !email.isEmpty() && !student.getEmail().equals(email)) {
      if (studentRepository.findStudentByEmail(email).isPresent()) {
        throw new IllegalStateException("Student with email: " + email + " already exists");
      }
      student.setEmail(email);
    }
  }
}
