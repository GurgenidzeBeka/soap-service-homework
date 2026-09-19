package ge.tbc.soap.client;

import ge.tbc.soap.models.rest.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static ge.tbc.soap.data.Constants.REST_AUTH_BASE_URL;
import static io.restassured.RestAssured.given;

public class AuthRestClient {

    // Centralize standard headers and URL
    private final RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(REST_AUTH_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public AuthenticationResponse registerUser(RegisterUserRequest request) {
        return given().spec(baseSpec)
                .body(request)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthenticationResponse.class);
    }

    public AuthenticationResponse authenticateUser(LoginRequest request) {
        return given().spec(baseSpec)
                .body(request)
                .when()
                .post("/api/v1/auth/authenticate")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthenticationResponse.class);
    }

    public void changeEmail(ChangeEmailRequest request, String bearerToken) {
        given().spec(baseSpec)
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .when()
                .post("/api/v1/user/change-email")
                .then()
                .statusCode(200);
    }
}