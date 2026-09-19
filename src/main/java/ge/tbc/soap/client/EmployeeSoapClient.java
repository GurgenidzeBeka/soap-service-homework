package ge.tbc.soap.client;

import com.example.springboot.soap.interfaces.*;
import ge.tbc.soap.utils.Marshall;
import ge.tbc.soap.utils.SoapServiceSender;
import ge.tbc.soap.utils.Unmarshall;
import io.restassured.response.Response;

import static ge.tbc.soap.data.Constants.*;

public class EmployeeSoapClient {

    private final ObjectFactory factory = new ObjectFactory();

    public Response addEmployee(EmployeeInfo employeeInfo) {
        AddEmployeeRequest request = factory.createAddEmployeeRequest().withEmployeeInfo(employeeInfo);
        String requestBody = Marshall.marshallSoapRequest(request);
        return SoapServiceSender.send(EMPLOYEE_SERVICE_URL, EMPTY_SOAP_ACTION, requestBody);
    }

    public GetEmployeeByIdResponse getEmployee(long id) {
        Response response = getEmployeeRaw(id);
        return Unmarshall.unmarshallResponse(response.asString(), GetEmployeeByIdResponse.class);
    }

    public Response getEmployeeRaw(long id) {
        GetEmployeeByIdRequest request = factory.createGetEmployeeByIdRequest().withEmployeeId(id);
        String requestBody = Marshall.marshallSoapRequest(request);
        return SoapServiceSender.send(EMPLOYEE_SERVICE_URL, EMPTY_SOAP_ACTION, requestBody);
    }

    public UpdateEmployeeResponse updateEmployee(EmployeeInfo employeeInfo) {
        UpdateEmployeeRequest request = factory.createUpdateEmployeeRequest().withEmployeeInfo(employeeInfo);
        String requestBody = Marshall.marshallSoapRequest(request);
        Response response = SoapServiceSender.send(EMPLOYEE_SERVICE_URL, EMPTY_SOAP_ACTION, requestBody);
        return Unmarshall.unmarshallResponse(response.asString(), UpdateEmployeeResponse.class);
    }

    public Response deleteEmployee(long id) {
        DeleteEmployeeRequest request = factory.createDeleteEmployeeRequest().withEmployeeId(id);
        String requestBody = Marshall.marshallSoapRequest(request);
        return SoapServiceSender.send(EMPLOYEE_SERVICE_URL, EMPTY_SOAP_ACTION, requestBody);
    }


    public GetEmployeeByIdResponse getEmployeeByEmail(String email) {
        GetEmployeeByEmailRequest request = factory.createGetEmployeeByEmailRequest().withEmail(email);
        String requestBody = Marshall.marshallSoapRequest(request);
        Response response = SoapServiceSender.send(EMPLOYEE_SERVICE_URL, EMPTY_SOAP_ACTION, requestBody);

        GetEmployeeByEmailResponse emailResponse = Unmarshall.unmarshallResponse(response.asString(), GetEmployeeByEmailResponse.class);

        GetEmployeeByIdResponse standardResponse = new GetEmployeeByIdResponse();
        standardResponse.setEmployeeInfo(emailResponse.getEmployeeInfo());
        return standardResponse;
    }



}
