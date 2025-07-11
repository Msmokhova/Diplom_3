package api;

import io.restassured.response.Response;
import utils.User;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    public Response registerUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .post(BASE_URL + "/auth/register");
    }

    public String getAccessToken(User user) {
        Response response = given()
                .contentType("application/json")
                .body(user)
                .post(BASE_URL + "/auth/login");

        return response.jsonPath().getString("accessToken");
    }

    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(BASE_URL + "/auth/user")
                .then()
                .statusCode(202);
    }
}