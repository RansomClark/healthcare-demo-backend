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

  List<Patient> customerList = new ArrayList<>();


  Patient customerOne = new Patient();


  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);

    customerOne.setId(1L);
        customerOne.setEmail("hulk@gmail.com");

    customerList.add(customerOne);


    //when statements for happy paths
    when(mockPatientRepository.findAll()).thenReturn(customerList);

    when(mockPatientRepository.findAll(any(Example.class))).thenReturn(customerList);

    when(mockPatientRepository.findById(any(Long.class))).thenReturn(Optional.of(customerOne));

    when(mockPatientRepository.existsByEmail(any(String.class))).thenReturn(false);

    when(mockPatientRepository.existsById(any(Long.class))).thenReturn(true);

    when(mockPatientRepository.save(any(Patient.class))).thenReturn(customerOne);

  }

  @Test
  public void testQueryPatientsNullExample() {

    List<Patient> actualResult = mockPatientServiceImpl.queryPatients(new Patient());

    Assert.assertEquals(customerList, actualResult);

  }

  @Test
  public void testQueryPatientsNonNullExample() {

    List<Patient> actualResult = mockPatientServiceImpl.queryPatients(customerOne);

    Assert.assertEquals(customerList, actualResult);

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

    Assert.assertEquals(customerOne, actualResult);

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

    Patient actualResult = mockPatientServiceImpl.addPatient(customerOne);

    Assert.assertEquals(customerOne, actualResult);

  }

  @Test(expected = UniqueFieldViolation.class)
  public void testAddPatientEmailConflict() throws Exception {

    when(mockPatientRepository.existsByEmail(any(String.class))).thenReturn(true);

    Patient actualResult = mockPatientServiceImpl.addPatient(customerOne);

  }

  @Test(expected = BadDataResponse.class)
  public void testAddPatientInvalidState() throws Exception {

    customerOne.setState("ZX");

    Patient actualResult = mockPatientServiceImpl.addPatient(customerOne);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testAddPatientDBError() throws Exception {

    when(mockPatientRepository.save(any(Patient.class)))
        .thenThrow(CannotCreateTransactionException.class);

    Patient actualResult = mockPatientServiceImpl.addPatient(customerOne);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testAddPatientUnexpectedError() throws Exception {

    when(mockPatientRepository.save(any(Patient.class)))
        .thenThrow(UnexpectedTypeException.class);

    Patient actualResult = mockPatientServiceImpl.addPatient(customerOne);

  }

  @Test
  public void testUpdatePatientByIdReturnsPatient() throws Exception {

    customerOne.setFirstName("Hollywood Hulk Hogan");

    mockPatientServiceImpl.updatePatientById(1L, customerOne);

    Assert.assertSame("Hollywood Hulk Hogan", customerOne.getFirstName());

  }

  @Test(expected = ResourceNotFound.class)
  public void testUpdatePatientByIdIdNotFound() throws Exception {

    when(mockPatientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    customerOne.setFirstName("Hollywood Hulk Hogan");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, customerOne);

    Assert.assertNull(result);

  }

  @Test(expected = BadDataResponse.class)
  public void testUpdatePatientByIdIdDoesNotMatch() throws Exception {

    customerOne.setId(3L);

    Patient result = mockPatientServiceImpl.updatePatientById(1L, customerOne);

  }

  @Test(expected = BadDataResponse.class)
  public void testUpdatePatientByIdInvalidState() throws Exception {

    customerOne.setState("ZX");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, customerOne);

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

    customerOne.setFirstName("Hollywood Hulk Hogan");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, customerOne);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testUpdatePatientByIdUnexpectedError() throws Exception {

    when(mockPatientRepository.findById(any(Long.class)))
        .thenThrow(UnexpectedTypeException.class);

    customerOne.setFirstName("Hollywood Hulk Hogan");

    Patient result = mockPatientServiceImpl.updatePatientById(1L, customerOne);

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