package io.catalyte.training.controllers;

import static io.catalyte.training.constants.StringConstants.CONTEXT_PATIENTS;

import io.catalyte.training.entitites.Patient;
import io.catalyte.training.services.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * holds crud methods for the patient entity
 */
@RestController
@RequestMapping(CONTEXT_PATIENTS)
public class PatientController {

  @Autowired
  PatientService patientService;

  /**
   * gives me all patients if I pass a null patient or patients matching an example with non-null
   * patient
   *
   * @param patient patient object which can have null or non-null fields, returns status 200
   * @return List of patients
   * @throws Exception
   */
  @GetMapping
  @ApiOperation("Gets all patients, or all patients matching an example with patient fields")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Patient.class)
  })
  public ResponseEntity<List<Patient>> queryPatients(Patient patient) throws Exception {

    return new ResponseEntity<>(patientService.queryPatients(patient), HttpStatus.OK);

  }

  /**
   * Gets patient by id.
   *
   * @param id the patient's id from the path variable
   * @return the patient with said id
   */
  @GetMapping(value = "/{id}")
  @ApiOperation("Finds a Patient by Id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Patient.class),
      @ApiResponse(code = 404, message = "NOT FOUND")
  })
  public ResponseEntity<Patient> getPatientById(@PathVariable Long id) throws Exception {

    return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);

  }

  /**
   * Adds new patient to the database.
   *
   * @param patient the patient from the request body being added
   * @return the patient if correctly added
   */
  @PostMapping
  @ApiOperation("Adds a new patient to the database")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "CREATED", response = Patient.class),
      @ApiResponse(code = 400, message = "BAD REQUEST")
  })
  public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient)
      throws Exception {

    return new ResponseEntity<>(patientService.addPatient(patient), HttpStatus.CREATED);
  }

  /**
   * Update patient by id patient.
   *
   * @param id       the id of the patient to be updated from the path variable
   * @param patient the patient's new information from the request body
   * @return the patient if correctly input
   */
  @PutMapping(value = "/{id}")
  @ApiOperation("Updates a Patient by Id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Patient.class),
      @ApiResponse(code = 404, message = "NOT FOUND"),
      @ApiResponse(code = 400, message = "BAD REQUEST")
  })
  public ResponseEntity<Patient> updatePatientById(@PathVariable Long id,
      @Valid @RequestBody Patient patient)
      throws Exception {

    return new ResponseEntity<>(patientService.updatePatientById(id, patient), HttpStatus.OK);
  }

  /**
   * Delete patient by id.
   *
   * @param id the patient's id from the path variable
   */
  @DeleteMapping(value = "/{id}")
  @ApiOperation("Deletes a Patient by Id")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "NO CONTENT"),
      @ApiResponse(code = 404, message = "NOT FOUND")
  })
  public ResponseEntity deletePatientById(@PathVariable Long id) throws Exception {

    patientService.deletePatientById(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
