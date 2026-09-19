package ge.tbc.soap.data;



import java.math.BigDecimal;
import java.util.List;

public class Constants {
    public static final String COUNTRY_INFO_BASE_URL = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
    public static final String LIST_OF_CONTINENTS_ENDPOINT = COUNTRY_INFO_BASE_URL + "/ListOfContinentsByName";
    public static final String EMPLOYEE_SERVICE_URL = "http://localhost:8087/ws";

    public static final List<String> EXPECTED_CONTINENTS = List.of(
            "Africa", "Antarctica", "Asia", "Europe", "Ocenania", "The Americas"
    );
    public static final List<String> EXPECTED_A_CA_CONTINENTS = List.of("Africa", "Antarctica");
    public static final String EXPECTED_O_CONTINENT = "Ocenania";

    public static final long TARGET_EMPLOYEE_ID = 1L;
    public static final String TARGET_EMPLOYEE_NAME = "Trevor";
    public static final String TARGET_EMPLOYEE_DEPARTMENT = "Economics";
    public static final String TARGET_EMPLOYEE_PHONE = "555-0123";
    public static final String TARGET_EMPLOYEE_ADDRESS = "Tbilisi";
    public static final String TARGET_EMPLOYEE_SALARY = "2500.00";
    public static final String TARGET_EMPLOYEE_EMAIL = "trevor@example.com";
    public static final String TARGET_EMPLOYEE_BIRTH_DATE = "1998-05-15";

    public static final String UPDATED_EMPLOYEE_DEPARTMENT = "Senior Economics";
    public static final String UPDATED_EMPLOYEE_SALARY = "5000.00";
    public static final String SUCCESS_STATUS = "SUCCESS";

    public static final String CONTINENT_NAMES_PATH = "ArrayOftContinent.tContinent.sName";
    public static final String LAST_CONTINENT_NAME_PATH = "ArrayOftContinent.tContinent[-1].sName";
    public static final String CONTINENT_CODES_PATH = "ArrayOftContinent.tContinent.sCode";
    public static final String FILTER_CONTINENTS_START_WITH_O_PATH =
            "ArrayOftContinent.tContinent.findAll { it.sCode.text().startsWith('O') }.sName";
    public static final String FILTER_CONTINENTS_A_AND_CA_PATH =
            "ArrayOftContinent.tContinent.sName.findAll { it.text().startsWith('A') && it.text().endsWith('ca') }";
    public static final String ADD_EMPLOYEE_STATUS_PATH = "Envelope.Body.addEmployeeResponse.serviceStatus.message";
    public static final String DELETE_EMPLOYEE_STATUS_PATH = "Envelope.Body.deleteEmployeeResponse.serviceStatus.status";
    public static final String FAULT_STRING_PATH = "Envelope.Body.Fault.faultstring";

    public static final String ONLY_LETTERS_AND_SPACES_REGEX = "^[a-zA-Z\\s]+$";
    public static final String TWO_UPPERCASE_LETTERS_REGEX = "^[A-Z]{2}$";
    public static final String CONTENT_ADDED_SUCCESSFULLY = "Content Added Successfully";
    public static final String EMPTY_SOAP_ACTION = "";




    public static final String DEFAULT_EMP_NAME = "Shota Rustaveli";
    public static final String DEFAULT_EMP_PHONE = "555-1234";
    public static final String DEFAULT_EMP_ADDRESS = "Tbilisi, Georgia";

    public static final String INIT_DEPT = "Finance";
    public static final String UPDATED_DEPT = "Literature";
    public static final String FINAL_DEPT = "History";

    public static final BigDecimal INIT_SALARY = new BigDecimal("5000.00");
    public static final BigDecimal UPDATED_SALARY = new BigDecimal("8000.00");
    public static final BigDecimal FINAL_SALARY = new BigDecimal("9500.00");


    public static final String EMP_DELETED_MSG = "Employee should be deleted from DB";



    public static final String AUTH_FIRST_NAME = "Ilia";
    public static final String AUTH_LAST_NAME = "Chavchavadze";
    public static final String AUTH_PASSWORD = "SecurePassword123!";
    public static final String AUTH_DEPT = "IT";
    public static final String AUTH_PHONE = "555-9999";
    public static final BigDecimal AUTH_SALARY = new BigDecimal("4000.00");

    public static final String REG_TOKEN_MSG = "Registration should return a token";
    public static final String LOGIN_TOKEN_MSG = "Login should return a valid Bearer token";
    public static final String SOAP_EMAIL_UPDATE_MSG = "SOAP should return the updated email";
    public static final String FIRST_NAME_UNCHANGED_MSG = "First name should remain unchanged";
}