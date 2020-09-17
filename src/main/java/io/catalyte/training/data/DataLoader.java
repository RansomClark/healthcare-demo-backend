package io.catalyte.training.data;

import io.catalyte.training.entitites.Encounter;
import io.catalyte.training.entitites.Patient;
import io.catalyte.training.repositories.EncounterRepository;
import io.catalyte.training.repositories.PatientRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * dataloader loads io.catalyte.training.data in the database at when the application is ran
 */
@Component
public class DataLoader implements CommandLineRunner {

  @Autowired
  private PatientRepository patientRepository;

  @Autowired
  private EncounterRepository encounterRepository;

  private Encounter encounterOne;
  private Encounter encounterTwo;

  private Patient patientOne;
  private Patient patientTwo;

  /**
   * class which loads all entities
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {

    loadPatients();
    loadEncounters();

  }

  /**
   * Sets properties of patients and loads 2 into the database
   */
  private void loadPatients() {

    //create patients
    patientOne = new Patient(

        1L,
        "Mark",
        "Marky",
        "111-11-1111",
        "markmarky@email.com",
        "Marksville",
        "Mark Street",
        "IL",
        "11111",
        11,
        111,
        111,
        "Blue Cross Blue Shield",
        "Male"
    );

    patientTwo = new Patient(
        2L,
        "Matthew",
        "Matthias",
        "222-22-2222",
        "matthewmatthias@email.com",
        "Matthewsville",
        "Matthew Street",
        "IL",
        "22222",
        22,
        222,
        222,
        "Blue Cross Blue Shield",
        "Female"
    );

    //save patients
    patientRepository.save(patientOne);
    patientRepository.save(patientTwo);


  }

  /**
   * sets properties of encounters and loads them into the database
   */
  private void loadEncounters() {
    encounterOne = new Encounter(
        1L, 1L, "new encounter", "N3W 3C3", "New Hospital",
        "123.456.789-00", "Z99", 100, 10, "chiefComplaint", 100,
        100, 100, new Date()
    );

    encounterTwo = new Encounter(
        2L, 1L, "notes2", "N3W 2D2", "provider2",
        "123.456.789-00", "I10", 200, 20, "chief Complaint2", 200,
        200, 200, new Date()
    );
    //save patients
    encounterRepository.save(encounterOne);
    encounterRepository.save(encounterTwo);
  }
}