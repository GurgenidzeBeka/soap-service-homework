package ge.tbc.soap.client;

import ge.tbc.soap.models.rest.*;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthRestClient {

    private static final String BASE_URL = "http://localhost:8086";

    public AuthenticationResponse registerUser(RegisterUserRequest request) {
        return given()
                .log().all() // <-- ADD THIS
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .log().all() // <-- ADD THIS
                .statusCode(200)
                .extract()
                .as(AuthenticationResponse.class);
    }

    public AuthenticationResponse authenticateUser(LoginRequest request) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/auth/authenticate")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthenticationResponse.class);
    }

    public void changeEmail(ChangeEmailRequest request, String bearerToken) {
        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .when()
                .post("/api/v1/user/change-email")
                .then()
                .statusCode(200);
    }
}