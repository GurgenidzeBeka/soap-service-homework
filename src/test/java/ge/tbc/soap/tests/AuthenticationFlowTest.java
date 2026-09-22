package ge.tbc.soap.tests;

import com.example.springboot.soap.interfaces.EmployeeInfo;
import ge.tbc.soap.client.AuthRestClient;
import ge.tbc.soap.client.EmployeeSoapClient;
import ge.tbc.soap.config.DataBaseConfig;
import ge.tbc.soap.models.db.Employee;
import ge.tbc.soap.models.rest.AuthenticationResponse;
import ge.tbc.soap.models.rest.ChangeEmailRequest;
import ge.tbc.soap.models.rest.LoginRequest;
import ge.tbc.soap.models.rest.RegisterUserRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static ge.tbc.soap.data.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthenticationFlowTest {

    private AuthRestClient authClient;
    private EmployeeSoapClient soapClient;

    private String uniqueEmail;
    private String bearerToken;

    @BeforeClass
    public void setup() {
        authClient = new AuthRestClient();
        soapClient = new EmployeeSoapClient();

        long timestamp = System.currentTimeMillis();
        uniqueEmail = "user" + timestamp + "@tbc.ge";

        Employee coreEmployee = new Employee();
        coreEmployee.setEmployeeId(timestamp);
        coreEmployee.setName(AUTH_FIRST_NAME);
        coreEmployee.setDepartment(AUTH_DEPT);
        coreEmployee.setPhone(AUTH_PHONE);
        coreEmployee.setAddress(DEFAULT_EMP_ADDRESS);
        coreEmployee.setSalary(AUTH_SALARY);
        coreEmployee.setEmail(uniqueEmail);
        coreEmployee.setBirthDate(LocalDate.of(1990, 1, 1));

        DataBaseConfig.employeeMapper().insertEmployee(coreEmployee);
    }

    @Test(priority = 1)
    public void registerUserTest() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname(AUTH_FIRST_NAME);
        registerRequest.setLastname(AUTH_LAST_NAME);
        registerRequest.setEmail(uniqueEmail);
        registerRequest.setPassword(AUTH_PASSWORD);
        registerRequest.setRole(RegisterUserRequest.RoleEnum.USER);

        AuthenticationResponse response = authClient.registerUser(registerRequest);

        assertThat(REG_TOKEN_MSG, response.getAccessToken(), notNullValue());
    }

    @Test(priority = 2, dependsOnMethods = "registerUserTest")
    public void authenticateUserTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(uniqueEmail);
        loginRequest.setPassword(AUTH_PASSWORD);

        AuthenticationResponse response = authClient.authenticateUser(loginRequest);

        bearerToken = response.getAccessToken();
        assertThat(LOGIN_TOKEN_MSG, bearerToken, notNullValue());
    }

    @Test(priority = 3, dependsOnMethods = "authenticateUserTest")
    public void changeEmailAndValidateViaSoapTest() {
        String newEmail = "updated." + uniqueEmail;

        ChangeEmailRequest emailRequest = new ChangeEmailRequest();
        emailRequest.setNewEmail(newEmail);
        authClient.changeEmail(emailRequest, bearerToken);

        EmployeeInfo soapEmployee = soapClient.getEmployeeByEmail(newEmail).getEmployeeInfo();

        assertThat(SOAP_EMAIL_UPDATE_MSG, soapEmployee.getEmail(), equalTo(newEmail));
        assertThat(FIRST_NAME_UNCHANGED_MSG, soapEmployee.getName(), equalTo(AUTH_FIRST_NAME));
    }
}