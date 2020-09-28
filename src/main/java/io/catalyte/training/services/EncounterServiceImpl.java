package io.catalyte.training.services;

import static io.catalyte.training.constants.StringConstants.BAD_REQUEST_ID;
import static io.catalyte.training.constants.StringConstants.BAD_REQUEST_ENCOUNTER_NOT_FOUND;
import static io.catalyte.training.constants.StringConstants.BAD_REQUEST_PATIENT_NOT_FOUND;
import static io.catalyte.training.constants.StringConstants.EMAIL_CONFLICT;

import io.catalyte.training.entitites.Encounter;
import io.catalyte.training.entitites.Encounter;
import io.catalyte.training.entitites.Patient;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.exceptions.UniqueFieldViolation;
import io.catalyte.training.repositories.EncounterRepository;
import io.catalyte.training.repositories.EncounterRepository;
import io.catalyte.training.repositories.PatientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class EncounterServiceImpl implements EncounterService {

  @Autowired
  EncounterRepository encounterRepository;

  @Autowired
  PatientRepository patientRepository;

  /**
   * Gets encounters from the database
   *
   * @param id of encounter
   * @return a list of that encounter's encounters
   */
  public List<Encounter> queryEncountersByPatientId(Long id) {

    try {
      Encounter testEncounter = new Encounter();
      testEncounter.setPatientId(id);

      Example<Encounter> encounterExample = Example.of(testEncounter);

      List<Encounter> encounterList = encounterRepository.findAll(encounterExample);

      return encounterList;

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

  }


  /**
   * Gets a encounter by the encounter's id
   *
   * @param id the encounter's id
   * @return encounter with said id
   */
  public Encounter getEncounterById(Long id) {

    try {
      Encounter encounter = encounterRepository.findById(id).orElse(null);
      if (encounter != null) {
        return encounter;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound(BAD_REQUEST_ENCOUNTER_NOT_FOUND);

  }

  /**
   * Adds a new encounter to the database
   *
   * @param encounter the encounter being added
   * @return the added encounter
   */
  public Encounter addEncounter(Encounter encounter) {

    try {
      return encounterRepository.save(encounter);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * update encounter by an id, throws bad request if id of the path doesn't match the body, throws not found status if
   * an id isn't found
   *
   * @param id
   * @param encounter
   * @return encounter
   */
  public Encounter updateEncounterById(Long id, Encounter encounter) {

    Encounter existingEncounter;

    // check if id in path matches id in request body
    if (!encounter.getId().equals(id)) {
      throw new BadDataResponse(BAD_REQUEST_ID);
    }

    try {

      // get the existing customer from the database
      existingEncounter = encounterRepository.findById(id).orElse(null);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if the encounter exists, call the addEncounter method to save it to the database
    if (existingEncounter != null) {
      return this.addEncounter(encounter);
    }
    else
    {
      throw new ResourceNotFound(BAD_REQUEST_ENCOUNTER_NOT_FOUND);
    }

  }


  /**
   * Deletes a encounter with a specified id
   *
   * @param id the encounter's id
   */
  public void deleteEncounterById(Long id) {

    try {

      // if a encounter exists for that id, delete it
      if (encounterRepository.existsById(id)) {
        encounterRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it to this point, return a 404
    throw new ResourceNotFound(BAD_REQUEST_ENCOUNTER_NOT_FOUND);
  }
}


