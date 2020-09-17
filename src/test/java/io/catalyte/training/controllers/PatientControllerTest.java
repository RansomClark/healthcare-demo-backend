package io.catalyte.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.entitites.Encounter;
import io.catalyte.training.entitites.Patient;
import io.catalyte.training.repositories.EncounterRepository;
import io.catalyte.training.repositories.PatientRepository;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatientControllerTest {

  @Autowired
  PatientRepository patientRepository;

  @Autowired
  EncounterRepository encounterRepository;

  @Autowired
  private static MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  ObjectMapper mapper = new ObjectMapper();

  private static final String CONTEXT = "/patients";

  // Runs before each test
  @Before
  public void before() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void test1_queryPatients() throws Exception {

    // Retrieve all patients from database
    String retType =
        mockMvc
            .perform(get(CONTEXT))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);

  }


  @Test
  public void test3_getPatient() throws Exception {

    // Retrieve the first patient and confirm it matches what we posted
    String retType = mockMvc
        .perform(get(CONTEXT + "/1"))
        .andExpect(jsonPath("$.firstName").value("Mark"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test4_addPatient() throws Exception {

    Patient patient4 = new Patient();

    patient4.setId(4L);
    patient4.setFirstName("name");
    patient4.setEmail("name@catalyte.io");

    String patientAsJson = mapper.writeValueAsString(patient4);

    String retType = mockMvc
        .perform(post(CONTEXT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(patientAsJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.*", hasSize(14)))
        .andReturn()
        .getResponse()
        .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test5_updatePatient() throws Exception {

    Patient patient4 = new Patient();

    patient4.setId(2L);
    patient4.setFirstName("Update");
    patient4.setEmail("name2@catalyte.io");

    String patientAsJson = mapper.writeValueAsString(patient4);

    String retType =
        mockMvc
            .perform(put(CONTEXT + "/2", patientAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .content(patientAsJson))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);
  }

  @Test
  public void test6_deletePatient() throws Exception {
    mockMvc
        .perform(delete(CONTEXT + "/2")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void test1_queryEncountersByPatientId() throws Exception {

    // Retrieve all encounters from database for a particular patient
    String retType =
        mockMvc
            .perform(get(CONTEXT + "/1/encounters"))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentType();

    Assert.assertEquals("application/json", retType);

  }


}