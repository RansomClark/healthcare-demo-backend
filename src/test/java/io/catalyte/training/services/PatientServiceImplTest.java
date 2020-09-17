package io.catalyte.training.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import io.catalyte.training.entitites.Patient;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.exceptions.UniqueFieldViolation;
import io.catalyte.training.repositories.PatientRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.UnexpectedTypeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.server.ResponseStatusException;

public class PatientServiceImplTest {

  @Mock
  PatientRepository mockPatientRepository;

  @InjectMocks
  PatientServiceImpl mockPatientServiceImpl;

  List<Patient> patientList = new ArrayList<>();


  Patient patientOne = new Patient();


  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);

    patientOne.setId(1L);
        patientOne.setEmail("hulk@gmail.com");

    patientList.add(patientOne);


    //when statements for happy paths
    when(mockPatientRepository.findAll()).thenReturn(patientList);

    when(mockPatientRepository.findAll(any(Example.class))).thenReturn(patientList);

    when(mockPatientRepository.findById(any(Long.class))).thenReturn(Optional.of(patientOne));

    when(mockPatientRepository.existsByEmail(any(String.class))).thenReturn(false);

    when(mockPatientRepository.existsById(any(Long.class))).thenReturn(true);

    when(mockPatientRepository.save(any(Patient.class))).thenReturn(patientOne);

  }

  @Test
  public void testQueryPatientsNullExample() {

    List<Patient> actualResult = mockPatientServiceImpl.queryPatients(new Patient());

    Assert.assertEquals(patientList, actualResult);

  }

  @Test
  public void testQueryPatientsNonNullExample() {

    List<Patient> actualResult = mockPatientServiceImpl.queryPatients(patientOne);

    Assert.assertEquals(patientList, actualResult);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testQueryPatientsDBError() {

    when(mockPatientRepository.findAll()).thenThrow(CannotCreateTransactionException.class);

    List<Patient> actualResult = mockPatientServiceImpl.queryPatients(new Patient());

  }

  @Test(expected = ServiceUnavailable.class)
  public void testQueryPatientsUnexpectedError() {

    when(mockPatientRepository.findAll()).thenThrow(UnexpectedTypeException.class);

    List<Patient> actualResult = mockPatientServiceImpl.queryPatients(new Patient());

  }


  @Test
  public void testGetPatientByIdReturnsPatient() throws Exception {

    Patient actualResult = mockPatientServiceImpl.getPatientById(1L);

    Assert.assertEquals(patientOne, actualResult);

  }

  @Test(expected = ResourceNotFound.class)
  public void testGetPatientByIdIdNotFound() throws Exception {

    when(mockPatientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    Patient actualResult = mockPatientServiceImpl.getPatientById(1L);
    Assert.assertNull(actualResult);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testGetPatientByIdDBError() throws Exception {

    when(mockPatientRepository.findById(any(Long.class)))
        .thenThrow(CannotCreateTransactionException.class);

    Patient actualResult = mockPatientServiceImpl.getPatientById(1L);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testGetPatientByIdUnexpectedError() throws Exception {

    when(mockPatientRepository.findById(any(Long.class)))
        .thenThrow(UnexpectedTypeException.class);

    Patient actualResult = mockPatientServiceImpl.getPatientById(1L);

  }

  @Test
  public void testAddPatientReturnsPatient() throws Exception {

    Patient actualResult = mockPatientServiceImpl.addPatient(patientOne);

    Assert.assertEquals(patientOne, actualResult);

  }

  @Test(expected = UniqueFieldViolation.class)
  public void testAddPatientEmailConflict() throws Exception {

    when(mockPatientRepository.existsByEmail(any(String.class))).thenReturn(true);

    Patient actualResult = mockPatientServiceImpl.addPatient(patientOne);

  }


  @Test(expected = ServiceUnavailable.class)
  public void testAddPatientDBError() throws Exception {

    when(mockPatientRepository.save(any(Patient.class)))
        .thenThrow(CannotCreateTransactionException.class);

    Patient actualResult = mockPatientServiceImpl.addPatient(patientOne);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testAddPatientUnexpectedError() throws Exception {

    when(mockPatientRepository.save(any(Patient.class)))
        .thenThrow(UnexpectedTypeException.class);

    Patient actualResult = mockPatientServiceImpl.addPatient(patientOne);

  }

  @Test
  public void testUpdatePatientByIdReturnsPatient() throws Exception {

    patientOne.setFirstName("Hollywood Hulk Hogan");

    mockPatientServiceImpl.updatePatientById(1L, patientOne);

    Assert.assertSame("Hollywood Hulk Hogan", patientOne.getFirstName());

  }

  @Test(expected = ResourceNotFound.class)
  public void testUpdatePatientByIdIdNotFound() throws Exception {

    when(mockPatientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    patientOne.setFirstName("Hollywood Hulk Hogan");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, patientOne);

    Assert.assertNull(result);

  }

  @Test(expected = BadDataResponse.class)
  public void testUpdatePatientByIdIdDoesNotMatch() throws Exception {

    patientOne.setId(3L);

    Patient result = mockPatientServiceImpl.updatePatientById(1L, patientOne);

  }


  @Test(expected = UniqueFieldViolation.class)
  public void testUpdatePatientByIdEmailConflict() throws Exception {

    Patient updatedPatient = new Patient();

    when(mockPatientRepository.existsByEmail(any(String.class))).thenReturn(true);

    updatedPatient.setId(1L);
    updatedPatient.setFirstName("Hulk Hogan");
    //email doesn't have to exist
    updatedPatient.setEmail("bret@gmail.com");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, updatedPatient);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testUpdatePatientByIdDBError() throws Exception {

    when(mockPatientRepository.findById(any(Long.class)))
        .thenThrow(CannotCreateTransactionException.class);

    patientOne.setFirstName("Hollywood Hulk Hogan");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, patientOne);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testUpdatePatientByIdUnexpectedError() throws Exception {

    when(mockPatientRepository.findById(any(Long.class)))
        .thenThrow(UnexpectedTypeException.class);

    patientOne.setFirstName("Hollywood Hulk Hogan");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, patientOne);

  }

  @Test
  public void testDeletePatientById204Returned() throws Exception {

    mockPatientServiceImpl.deletePatientById(1L);

    //check method was called
    verify(mockPatientRepository).deleteById(any());

  }

  @Test(expected = ResourceNotFound.class)
  public void testDeletePatientByIdIdNotFound() throws Exception {

    when(mockPatientRepository.existsById(any(Long.class))).thenReturn(false);

    doThrow(ResponseStatusException.class).when(mockPatientRepository).deleteById(any(Long.class));

    mockPatientServiceImpl.deletePatientById(1L);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testDeletePatientByIdDBError() throws Exception {

    doThrow(CannotCreateTransactionException.class).when(mockPatientRepository)
        .existsById(any(Long.class));

    mockPatientServiceImpl.deletePatientById(1L);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testDeletePatientByIdUnexpectedError() throws Exception {

    doThrow(UnexpectedTypeException.class).when(mockPatientRepository)
        .existsById(any(Long.class));

    mockPatientServiceImpl.deletePatientById(1L);

  }

}