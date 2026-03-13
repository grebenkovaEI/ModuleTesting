package office;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestService {

    @BeforeEach
    void setUp() {
        Service.createDB();
    }

    @Test
    void addDepartmentTest() {
        int count = Service.getCount("Department");
        Service.addDepartment(new Department(4, "Management"));
        Assertions.assertEquals(count + 1, Service.getCount("Department"));
    }

    @Test
    void removeDepartment() {
        Service.removeDepartment(new Department(1, "Accounting"));
        Assertions.assertEquals(2, Service.getCount("Department"));
    }

    @Test
    void addEmployeeTest() {
        int count = Service.getCount("Employee");
        Service.addEmployee(new Employee(7, "Elena", 2));
        Assertions.assertEquals(count + 1, Service.getCount("Employee"));
    }

    @Test
    void removeEmployeeTest() {
        Service.removeEmployee(new Employee(3, "Liz", 2));
        Assertions.assertEquals(5, Service.getCount("Employee"));
    }

    @Test
    void deleteCascadeTest() {
        Service.removeDepartment(new Department(2, "IT"));
        Assertions.assertEquals(3, Service.getCount("Employee"));
    }


}
