package tests;

import models.login.LoginBodyResponseModel;
import models.login.LoginResponseModel;
import models.login.ErrorResponseModel;
import models.single.MainResourcesResponseModel;
import models.user.ListDataUserResponseModel;
import models.user.ListMainUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static specs.Specs.*;

@DisplayName("Testing Reqres.in")
public class ReqresTest extends TestBase {

    @DisplayName("REGISTER - SUCCESSFUL")
    @Test
    void successfulLoginTest() {

        LoginBodyResponseModel authData = new LoginBodyResponseModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        LoginResponseModel response = step("Make login request", () ->
                given(RequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(ResponseSpec)
                        .extract().as(LoginResponseModel.class));
        step("Verify response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));

    }

    @DisplayName("LIST USERS")
    @Test
    void listUsersTest() {

        ListMainUserResponseModel response = step("Requesting User Information", () ->
                given(RequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(ResponseSpec)
                        .extract().as(ListMainUserResponseModel.class));
        step("Checking the user's main page", () -> {
            assertThat(response.getPage(), equalTo(2));
            assertThat(response.getPerPage(), equalTo(6));
            assertThat(response.getTotal(), equalTo(12));
            assertThat(response.getTotalPages(), equalTo(2));
        });
        step("Verifying user data", () -> {
            List<ListDataUserResponseModel> data = response.getData();
            assertThat(data.get(5).getId(), equalTo(12));
            assertThat(data.get(5).getEmail(), equalTo("rachel.howell@reqres.in"));
            assertThat(data.get(5).getFirstName(), equalTo("Rachel"));
            assertThat(data.get(5).getLastName(), equalTo("Howell"));
            assertThat(data.get(5).getAvatar(), equalTo("https://reqres.in/img/faces/12-image.jpg"));
        });
        step("Checking support information", () -> {
            assertThat(response.getSupport().getUrl(), equalTo("https://reqres.in/#support-heading"));
            assertThat(response.getSupport().getText(), equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
        });

    }

    @DisplayName("DELETE")
    @Test
    void deleteUsersTest() {

        step("Сhecking the response status(204) when deleting a user", () ->
                given(RequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(userDeletionResponseSpec));

    }

    @DisplayName("SINGLE <RESOURCE>")
    @Test
    void singleResourceTest() {

        MainResourcesResponseModel response = step("User Resources", () ->
                given(RequestSpec)
                .when()
                .get("/unknown/2")
                .then()
                .spec(ResponseSpec))
                .extract().as(MainResourcesResponseModel.class);
        step("Checking the user's data resources", () -> {
            assertThat(response.getData().getId(), equalTo(2));
            assertThat(response.getData().getName(), equalTo("fuchsia rose"));
            assertThat(response.getData().getYear(), equalTo(2001));
            assertThat(response.getData().getColor(), equalTo("#C74375"));
            assertThat(response.getData().getPantoneValue(), equalTo("17-2031"));
        });
        step("Checking the user's support resources", () -> {
            assertThat(response.getSupport().getUrl(), equalTo("https://reqres.in/#support-heading"));
            assertThat(response.getSupport().getText(), equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
        });
    }

    @DisplayName("LOGIN - UNSUCCESSFUL")
    @Test
    void unSuccessfulPasswordTest() {

        LoginBodyResponseModel authData = new LoginBodyResponseModel();
        authData.setEmail("peter@klaven");

        ErrorResponseModel response = step("Missing password", () ->
                given(RequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(badResponseSpec)
                        .extract().as(ErrorResponseModel.class));
        step("Verify response", () ->
                assertEquals("Missing password", response.getError()));

    }
}
