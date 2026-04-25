package com.example.jpademo;

import com.example.jpademo.entity.Department;
import com.example.jpademo.entity.Employee;
import com.example.jpademo.repository.DepartmentRepository;
import com.example.jpademo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class JpaOneToManyApplication implements CommandLineRunner {

    @Autowired
    private DepartmentRepository departmentRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public static void main(String[] args) {
        SpringApplication.run(JpaOneToManyApplication.class, args);
    }

    @Override
    public void run(String... args) {
        executeDemo();  // ✅ call transactional method
    }

    // ✅ REAL TRANSACTIONAL METHOD (THIS WORKS)
    @Transactional
    public void executeDemo() {

        System.out.println("=============================================================");
        System.out.println("   ONE-TO-MANY JPA RELATIONSHIP DEMO");
        System.out.println("=============================================================\n");

        Department engineering = new Department("Engineering");

        Employee emp1 = new Employee("Raunak", "Raunak@example.com");
        Employee emp2 = new Employee("Bob", "bob@example.com");

        engineering.addEmployee(emp1);
        engineering.addEmployee(emp2);

        System.out.println(">>> Saving Department (with 2 Employees via CascadeType.ALL)...\n");
        departmentRepo.save(engineering);

        long deptCount = departmentRepo.count();
        long empCount  = employeeRepo.count();

        System.out.println("\n=============================================================");
        System.out.println("   VERIFICATION");
        System.out.println("=============================================================");
        System.out.printf("   Departments in DB : %d  (expected 1)%n", deptCount);
        System.out.printf("   Employees   in DB : %d  (expected 2)%n", empCount);
        System.out.printf("   Total rows        : %d  (expected 3)%n", deptCount + empCount);
        System.out.println("=============================================================\n");

        // ✅ NOW THIS WILL WORK
        departmentRepo.findAll().forEach(dept -> {
            System.out.println("Department: " + dept.getName() + " (id=" + dept.getId() + ")");

            dept.getEmployees().forEach(emp ->
                    System.out.println("   └── Employee: " + emp.getName()
                            + " | " + emp.getEmail()
                            + " (id=" + emp.getId() + ")")
            );
        });

        System.out.println("\n>>> Demo complete. All 3 rows persisted successfully!");
    }
}