package jsonplaceholder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTest {
    @Test
    public void createNewUser() {
        User user = new User();
        user.setName("Ewelina");
        user.setName("ejakas");
        user.setEmail("e.jakas@onet.pl");
        user.setPhone("987654321");
        user.setWebsite("www.jakas.pl");

        Geo geo = new Geo();
        geo.setLat("-37.1541");
        geo.setLng("81.2321");

        Address address = new Address();
        address.setStreet("Ulica");
        address.setSuite("Apt 2");
        address.setCity("Warszawa");
        address.setZipcode("31-855");
        address.setGeo(geo);

        user.setAddress(address);

        Company company = new Company();
        company.setName("IT Company");
        company.setCatchPhrase("This is company");
        company.setBs("Bs");

        user.setCompany(company);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());
    }
}
