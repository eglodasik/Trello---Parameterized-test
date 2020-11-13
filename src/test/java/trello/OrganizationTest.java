package trello;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class OrganizationTest {
    private static final String KEY = "";
    private static final String TOKEN = "";

    private static Stream<Arguments> createOrganizationData() {

        return Stream.of(
                Arguments.of("This is display name", "desc", "This is name", "https://website.org"),
                Arguments.of("This is display name", "desc", "This is name", "https://website.org"),
                Arguments.of("This is display name", "desc", "abc", "https://website.org"),
                Arguments.of("This is display name", "desc", "This_is_name", "https://website.org"),
                Arguments.of("This is display name", "desc", "name123", "https://website.org"));
    }



    @DisplayName("Create organization with valid data")
    @ParameterizedTest(name = "DisplayName: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationData")
    public void createOrganization( String displayName, String desc, String name, String website) {


        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("displayName", displayName)
                .queryParam("desc", desc)
                .queryParam("name", name)
                .queryParam("website", website)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("displayName")).isEqualTo(displayName);

        final String organizationId = json.getString("id");


        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + organizationId)
                .then()
                .statusCode(200);

    }


    private static Stream<Arguments> createOrganizationInvalidData(){
        return Stream.of(
             //  Arguments.of("Too short name", "desc", "12", "https://website.org"),
              //  Arguments.of("12", "Short displayName","name", "https://website.org" ),
              //  Arguments.of("Not unique name", "desc","This is name", "https://website.org" ),
              //  Arguments.of("Not unique name", "desc","This is name", "http://website.org" ),
              // Arguments.of("Name with special signs", "desc","!@#$", "http://website.org" ),
            //    Arguments.of("Name with lowercase letters", "desc","small letters name", "http://website.org" ),
                Arguments.of("Name with underscores signs", "desc","name_with_underscores_sings", "http://website.org" )
             //   Arguments.of("Name with uppercase letters", "desc","UPPERCASE LETTERS", "http://website.org" ),
            //   Arguments.of("Not valid URL", "desc","This is name", "ee/website.org" )

        );
    }


    @DisplayName("Create organization with invalid data")
    @ParameterizedTest(name = "DisplayName: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationInvalidData")
    public void createOrganizationInvalidData( String displayName, String desc, String name, String website) {


        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("displayName", displayName)
                .queryParam("desc", desc)
                .queryParam("name", name)
                .queryParam("website", website)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(400)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
       assertThat(json.getString("displayName")).isEqualTo(displayName);
        assertThat(json.getString("name")).isEqualTo(name);
       assertThat(json.getString("desc")).isEqualTo(desc);
       assertThat(json.getString("website")).isEqualTo(website);

        final String organizationId2 = json.getString("id");


        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + organizationId2)
                .then()
                .statusCode(200);


    }
}
