package ge.tbc.soap.tests;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

import static ge.tbc.soap.data.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PublicContinentTest {

    @Test
    public void validateContinentsXmlPath() {
        String xmlResponse = RestAssured.given()
                .get(LIST_OF_CONTINENTS_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().asString();

        XmlPath xmlPath = new XmlPath(xmlResponse);


        List<String> allNames = xmlPath.getList(CONTINENT_NAMES_PATH, String.class);
        assertThat(allNames, hasSize(EXPECTED_CONTINENTS.size()));

        assertThat(allNames, containsInAnyOrder(EXPECTED_CONTINENTS.toArray()));

        String lastName = xmlPath.getString(LAST_CONTINENT_NAME_PATH);
        assertThat(lastName, equalTo(EXPECTED_CONTINENTS.getLast()));

        assertThat(allNames, everyItem(matchesPattern(ONLY_LETTERS_AND_SPACES_REGEX)));

        assertThat(allNames.size(), equalTo(new HashSet<>(allNames).size()));

        String oContinent = xmlPath.getString(FILTER_CONTINENTS_START_WITH_O_PATH);
        assertThat(oContinent, equalTo(EXPECTED_O_CONTINENT));

        List<String> aContinents = xmlPath.getList(FILTER_CONTINENTS_A_AND_CA_PATH, String.class);
        assertThat(aContinents, containsInAnyOrder(EXPECTED_A_CA_CONTINENTS.toArray()));

        List<String> allCodes = xmlPath.getList(CONTINENT_CODES_PATH, String.class);
        assertThat(allCodes, everyItem(matchesPattern(TWO_UPPERCASE_LETTERS_REGEX)));
    }
}