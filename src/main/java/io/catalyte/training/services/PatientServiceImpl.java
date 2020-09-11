package io.catalyte.training.services;

import static io.catalyte.training.constants.StringConstants.*;


import io.catalyte.training.entitites.Patient;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.exceptions.UniqueFieldViolation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import io.catalyte.training.repositories.PatientRepository;

/**
 * service class which implements PatientService interface
 */
@Service
public class PatientServiceImpl implements PatientService {

  @Autowired
  PatientRepository patientRepository;

  /**
   * Gets patients from the database
   *
   * @param patient an optional sample to query against
   * @return a list of matching patients
   */
  public List<Patient> queryPatients(Patient patient) {

    try {
      if (patient.isEmpty()) {
        return patientRepository.findAll();
      } else {
        Example<Patient> patientExample = Example.of(patient);
        return patientRepository.findAll(patientExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

  }


  /**
   * Gets a patient by the patient's id
   *
   * @param id the patient's id
   * @return patient with said id
   */
  public Patient getPatientById(Long id) {

    try {
      Patient patient = patientRepository.findById(id).orElse(null);
      if (patient != null) {
        return patient;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound(BAD_REQUEST_PATIENT_NOT_FOUND);

  }

  /**
   * Adds a new patient to the database
   *
   * @param patient the patient being added
   * @return the added patient
   */
  public Patient addPatient(Patient patient) {

    boolean emailAlreadyExists;
    boolean stateIsValid;

    try {
      // check if email already exists
      emailAlreadyExists = patientRepository.existsByEmail(patient.getEmail());

      if (!emailAlreadyExists) {

        // assign patient to address before you call save
        return patientRepository.save(patient);

      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if made it to this point, email is not unique
    throw new UniqueFieldViolation(EMAIL_CONFLICT);

  }

  /**
   * Updates a patient with a specific id
   *
   * @param id       the id of the patient to be updated
   * @param patient the patient's new information
   * @return the patient's new information
   */
  public Patient updatePatientById(Long id, Patient patient) {

    Patient existingPatient;

    boolean emailIsSame;
    boolean newEmailIsUnique;
    String currentEmail;

    // get the new email from the patient passed int
    String newEmail = patient.getEmail();

    // check if id in path matches id in request body
    if (!patient.getId().equals(id)) {
      throw new BadDataResponse(BAD_REQUEST_ID);
    }


    try {

      // get the existing patient from the database
      existingPatient = patientRepository.findById(id).orElse(null);

      if (existingPatient != null) {

        // get the current email from the database
        currentEmail = existingPatient.getEmail();

        // see if new email already exists
        newEmailIsUnique = !patientRepository.existsByEmail(newEmail);

        // set local for email is same
        emailIsSame = currentEmail.equals(newEmail);

        // only continue if email has not changed, or new email is unique
        if (emailIsSame || newEmailIsUnique) {

          return patientRepository.save(patient);
        }
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if user was not found...
    if (existingPatient == null) {
      throw new ResourceNotFound(BAD_REQUEST_PATIENT_NOT_FOUND);
    }
    // patient was found so it must because of an email conflict
    else {
      throw new UniqueFieldViolation(EMAIL_CONFLICT);
    }

  }

  /**
   * Deletes a patient with a specified id
   *
   * @param id the patient's id
   */
  public void deletePatientById(Long id) {

    try {

      // if a patient exists for that id, delete it
      if (patientRepository.existsById(id)){
        patientRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it to this point, return a 404
    throw new ResourceNotFound(BAD_REQUEST_PATIENT_NOT_FOUND);
  }
}
