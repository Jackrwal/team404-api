package com.team404.kainosproject.integrationtests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import org.json.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;

// https://spring.io/guides/gs/testing-web/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JobRoleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void when_gettingFirstRowFromJobRoleTable_Expect_ReturnsTestJobRow(){
        final JSONArray jobRoles = new JSONArray(restTemplate
                .getForEntity(createURLWithPort("/job-roles"), String.class)
                .getBody());

        final JSONObject firstObj = (JSONObject) jobRoles.get(0);

        assertAll("Should contain Test Job Row",
                () -> assertEquals("Head of test job", firstObj.get("title")),
                () -> assertEquals("full_time", firstObj.get("contractType")),
                () -> assertEquals("Test Link", firstObj.get("sharePointLink"))
        );
    }

    @Test
    public void when_gettingJobSpecificationByID_Expect_TestRowLocationsAndDescription(){
        final JSONObject jobRole = new JSONObject(restTemplate
                .getForEntity(createURLWithPort("/job-roles/" + 1), String.class)
                .getBody());

        assertAll("Should contain Test Job Row",
                () -> assertEquals("Head of test job", jobRole.get("title")),
                () -> assertEquals(
                        "[{\"name\":\"London\"},{\"name\":\"Birmingham\"},{\"name\":\"Gdansk\"}]",
                        jobRole.getJSONArray("locations").toString()
                ),
                () -> assertEquals(3, jobRole.getJSONArray("locations").length()),
                () -> assertEquals("<b>What you will be expected to do </b> " +
                        "<br/> Day to day you will be expected to be a test entry in our tables.",
                        jobRole.get("description")),
                () -> assertEquals("full_time", jobRole.get("contractType"))
        );
    }

    @Test
    public void when_wrongIDForJobSpecification_Expect_JobSpecificationNotFound(){
        final int jobRolesSize = new JSONArray(restTemplate
                .getForEntity(createURLWithPort("/job-roles"), String.class)
                .getBody()).length();

        assertAll("Should return 404 Status",
                () -> assertEquals(restTemplate
                                .getForEntity(createURLWithPort("/job-roles/" + (-1)), String.class)
                                .getStatusCode(), ResponseEntity.notFound().build().getStatusCode()),

                () -> assertEquals(restTemplate
                        .getForEntity(createURLWithPort("/job-roles/" + (0)), String.class)
                        .getStatusCode(), ResponseEntity.notFound().build().getStatusCode()),

                () -> assertEquals(restTemplate
                        .getForEntity(createURLWithPort("/job-roles/" + (jobRolesSize + 1)), String.class)
                        .getStatusCode(), ResponseEntity.notFound().build().getStatusCode())
        );
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}

/* TODO This starts the server on a random port to test the full application
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SomeTestClass {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void SomeTest() throws Exception{

        restTemplate.getForObject();

    }

}
*/

// To test just a data source it seems we can use @MockBean to mock a repository.

/* Using Mock MVC tests the requests without starting a full web application

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class JobRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void SomeTest() throws Exception{
        mockMVC.perform(...)
    }

}
 */
