package ge.tbc.soap.tests;


import com.example.springboot.soap.interfaces.*;
import ge.tbc.soap.utils.EmployeeSoapClient;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;

import static ge.tbc.soap.data.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class EmployeeSoapTest {

    private EmployeeSoapClient apiClient;
    private EmployeeInfo targetEmployee;

    @BeforeClass
    public void setupData() throws DatatypeConfigurationException {
        apiClient = new EmployeeSoapClient();

        targetEmployee = new ObjectFactory().createEmployeeInfo()
                .withEmployeeId(TARGET_EMPLOYEE_ID)
                .withName(TARGET_EMPLOYEE_NAME)
                .withDepartment(TARGET_EMPLOYEE_DEPARTMENT)
                .withPhone(TARGET_EMPLOYEE_PHONE)
                .withAddress(TARGET_EMPLOYEE_ADDRESS)
                .withSalary(new BigDecimal(TARGET_EMPLOYEE_SALARY))
                .withEmail(TARGET_EMPLOYEE_EMAIL)
                .withBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(TARGET_EMPLOYEE_BIRTH_DATE));
    }

    @Test
    public void addEmployeeTest() {
        Response response = apiClient.addEmployee(targetEmployee);
        XmlPath xmlPath = new XmlPath(response.asString());

        assertThat(xmlPath.getString(ADD_EMPLOYEE_STATUS_PATH), equalTo(CONTENT_ADDED_SUCCESSFULLY));
    }

    @Test(dependsOnMethods = "addEmployeeTest")
    public void getEmployeeByIdTest() {
        EmployeeInfo retrievedEmployee = apiClient.getEmployee(TARGET_EMPLOYEE_ID).getEmployeeInfo();

        assertThat(retrievedEmployee.getName(), equalTo(targetEmployee.getName()));
        assertThat(retrievedEmployee.getDepartment(), equalTo(targetEmployee.getDepartment()));
        assertThat(retrievedEmployee.getEmail(), equalTo(targetEmployee.getEmail()));
    }

    @Test(dependsOnMethods = "getEmployeeByIdTest")
    public void updateEmployeeTest() {
        targetEmployee.withDepartment(UPDATED_EMPLOYEE_DEPARTMENT)
                .withSalary(new BigDecimal(UPDATED_EMPLOYEE_SALARY));

        UpdateEmployeeResponse updateResponse = apiClient.updateEmployee(targetEmployee);
        assertThat(updateResponse.getServiceStatus().getStatus(), equalTo(SUCCESS_STATUS));

        EmployeeInfo retrievedEmployee = apiClient.getEmployee(TARGET_EMPLOYEE_ID).getEmployeeInfo();
        assertThat(retrievedEmployee.getDepartment(), equalTo(UPDATED_EMPLOYEE_DEPARTMENT));
        assertThat(retrievedEmployee.getSalary(), equalTo(new BigDecimal(UPDATED_EMPLOYEE_SALARY)));
    }

    @Test(dependsOnMethods = "updateEmployeeTest")
    public void deleteEmployeeTest() {
        Response response = apiClient.deleteEmployee(TARGET_EMPLOYEE_ID);
        XmlPath xmlPath = new XmlPath(response.asString());

        assertThat(xmlPath.getString(DELETE_EMPLOYEE_STATUS_PATH), equalTo(SUCCESS_STATUS));

        Response getResponse = apiClient.getEmployeeRaw(TARGET_EMPLOYEE_ID);
        assertThat(getResponse.statusCode(), equalTo(500));

        XmlPath errorPath = new XmlPath(getResponse.asString());
        assertThat(errorPath.getString(FAULT_STRING_PATH), notNullValue());
    }
}