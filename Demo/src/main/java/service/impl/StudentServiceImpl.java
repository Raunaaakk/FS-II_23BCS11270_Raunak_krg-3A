package service.impl;

import Dto.StudentDTO;
import repo.StudentRepo;
import service.StudentService;
import java.util.*;
import org.springframework.stereotype.Service;
import entity.Student;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepo studentRepo;
    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student createStudent(StudentDTO dto)
    {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setCourse(dto.getCourseName());

        return studentRepo.save(student);
    }
}
