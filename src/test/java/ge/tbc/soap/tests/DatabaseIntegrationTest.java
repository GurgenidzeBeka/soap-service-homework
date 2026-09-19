package ge.tbc.soap.tests;

import com.example.springboot.soap.interfaces.EmployeeInfo;
import ge.tbc.soap.client.EmployeeSoapClient;
import ge.tbc.soap.models.db.Employee;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.time.LocalDate;

import static ge.tbc.soap.config.DataBaseConfig.employeeMapper;
import static ge.tbc.soap.data.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DatabaseIntegrationTest {

    private final EmployeeSoapClient soapClient = new EmployeeSoapClient();
    private Employee sharedTestEmployee;

    @BeforeClass
    public void generateTestData() {
        long dynamicId = System.currentTimeMillis();

        sharedTestEmployee = new Employee();
        sharedTestEmployee.setEmployeeId(dynamicId);
        sharedTestEmployee.setName(DEFAULT_EMP_NAME);
        sharedTestEmployee.setPhone(DEFAULT_EMP_PHONE);
        sharedTestEmployee.setAddress(DEFAULT_EMP_ADDRESS);
        sharedTestEmployee.setDepartment(INIT_DEPT);
        sharedTestEmployee.setSalary(INIT_SALARY);
        sharedTestEmployee.setEmail("user" + dynamicId + "@tbc.ge");
        sharedTestEmployee.setBirthDate(LocalDate.of(1837, 11, 8));
    }

    @Test(priority = 1)
    public void validateDbInsertViaSoapTest() {
        employeeMapper().insertEmployee(sharedTestEmployee);

        EmployeeInfo soapEmployee = soapClient.getEmployee(sharedTestEmployee.getEmployeeId()).getEmployeeInfo();

        assertThat(soapEmployee.getName(), equalTo(sharedTestEmployee.getName()));
        assertThat(soapEmployee.getEmail(), equalTo(sharedTestEmployee.getEmail()));
        assertThat(soapEmployee.getDepartment(), equalTo(INIT_DEPT));
    }

    @Test(priority = 2, dependsOnMethods = "validateDbInsertViaSoapTest")
    public void validateSoapUpdateViaDbTest() {
        EmployeeInfo updatePayload = new EmployeeInfo();
        updatePayload.setEmployeeId(sharedTestEmployee.getEmployeeId());
        updatePayload.setName(sharedTestEmployee.getName());
        updatePayload.setPhone(sharedTestEmployee.getPhone());
        updatePayload.setAddress(sharedTestEmployee.getAddress());

        updatePayload.setDepartment(UPDATED_DEPT);
        updatePayload.setSalary(UPDATED_SALARY);
        updatePayload.setEmail("updated." + sharedTestEmployee.getEmail());

        soapClient.updateEmployee(updatePayload);

        Employee dbEmployee = employeeMapper().getEmployeeById(sharedTestEmployee.getEmployeeId());

        assertThat(dbEmployee.getDepartment(), equalTo(UPDATED_DEPT));
        assertThat(dbEmployee.getSalary(), equalTo(UPDATED_SALARY));
        assertThat(dbEmployee.getEmail(), equalTo(updatePayload.getEmail()));

        sharedTestEmployee.setEmail(updatePayload.getEmail());
    }

    @Test(priority = 3, dependsOnMethods = "validateSoapUpdateViaDbTest")
    public void validateDbUpdateViaSoapTest() {
        Employee dbEmployee = employeeMapper().getEmployeeById(sharedTestEmployee.getEmployeeId());
        dbEmployee.setDepartment(FINAL_DEPT);
        dbEmployee.setSalary(FINAL_SALARY);

        employeeMapper().updateEmployee(dbEmployee);


        EmployeeInfo soapEmployee = soapClient.getEmployee(sharedTestEmployee.getEmployeeId()).getEmployeeInfo();

        assertThat(soapEmployee.getDepartment(), equalTo(FINAL_DEPT));
        assertThat(soapEmployee.getSalary(), equalTo(FINAL_SALARY));
    }

    @Test(priority = 4, dependsOnMethods = "validateDbUpdateViaSoapTest")
    public void validateSoapDeleteViaDbTest() {
        soapClient.deleteEmployee(sharedTestEmployee.getEmployeeId());

        int employeeCount = employeeMapper().checkEmployeeExists(sharedTestEmployee.getEmployeeId());

        assertThat(EMP_DELETED_MSG, employeeCount, equalTo(0));
    }
}