package io.catalyte.training.data;

import io.catalyte.training.entitites.Patient;
import io.catalyte.training.repositories.PatientRepository;
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

  }


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
}