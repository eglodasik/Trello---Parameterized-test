package github;


import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class AuthTest {
private static final String TOKEN = "";
    @Test
    public void basicAuth(){
        given()
                .auth()
                .preemptive()
                .basic("", "")
                .when()
                .get("https://api.github.com/users")
                .then()
                .statusCode(SC_OK);

    }
@Test
    public void bearerToken(){
    given()
            .headers("Authorization", "Bearer " + TOKEN)
            .when()
            .get("https://api.github.com/users")
            .then()
            .statusCode(SC_OK);
}
@Test
    public void oAuth2(){
    given()
            .auth()
            .oauth2(TOKEN)
            .when()
            .get("https://api.github.com/users")
            .then()
            .statusCode(SC_OK);
}

}
