package io.catalyte.training.services;


    import io.catalyte.training.entitites.Patient;
    import java.util.List;

/**
 * patient service interface with crud methods for a patient
 */
public interface PatientService {

  List<Patient> queryPatients(Patient patient) throws Exception;

  Patient getPatientById(Long id) throws Exception;

  Patient addPatient(Patient patient) throws Exception;

  Patient updatePatientById(Long id, Patient patient) throws Exception;

  void deletePatientById(Long id) throws Exception;

}
