package io.catalyte.training.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.catalyte.training.entitites.Encounter;
import io.catalyte.training.exceptions.BadDataResponse;
import io.catalyte.training.exceptions.ResourceNotFound;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.exceptions.UniqueFieldViolation;
import io.catalyte.training.repositories.EncounterRepository;
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

public class EncounterServiceImplTest {

  @Mock
  EncounterRepository mockEncounterRepository;

  @InjectMocks
  EncounterServiceImpl mockEncounterServiceImpl;

  List<Encounter> encounterList = new ArrayList<>();


  Encounter encounterOne = new Encounter();


  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);

    encounterOne.setId(1L);

    encounterList.add(encounterOne);

    //when statements for happy paths
    when(mockEncounterRepository.findAll()).thenReturn(encounterList);

    when(mockEncounterRepository.findAll(any(Example.class))).thenReturn(encounterList);

    when(mockEncounterRepository.findById(any(Long.class))).thenReturn(Optional.of(encounterOne));

    when(mockEncounterRepository.existsById(any(Long.class))).thenReturn(true);

    when(mockEncounterRepository.save(any(Encounter.class))).thenReturn(encounterOne);

  }

  @Test
  public void testQueryEncountersNullExample() {

    List<Encounter> actualResult = mockEncounterServiceImpl.queryEncountersByPatientId(1L);

    Assert.assertEquals(encounterList, actualResult);

  }

  @Test
  public void testQueryEncountersNonNullExample() {

    List<Encounter> actualResult = mockEncounterServiceImpl.queryEncountersByPatientId(3L);

    Assert.assertEquals(encounterList, actualResult);

  }


  @Test
  public void testGetEncounterByIdReturnsEncounter() throws Exception {

    Encounter actualResult = mockEncounterServiceImpl.getEncounterById(1L);

    Assert.assertEquals(encounterOne, actualResult);

  }

  @Test(expected = ResourceNotFound.class)
  public void testGetEncounterByIdIdNotFound() throws Exception {

    when(mockEncounterRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    Encounter actualResult = mockEncounterServiceImpl.getEncounterById(1L);
    Assert.assertNull(actualResult);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testGetEncounterByIdDBError() throws Exception {

    when(mockEncounterRepository.findById(any(Long.class)))
        .thenThrow(CannotCreateTransactionException.class);

    Encounter actualResult = mockEncounterServiceImpl.getEncounterById(1L);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testGetEncounterByIdUnexpectedError() throws Exception {

    when(mockEncounterRepository.findById(any(Long.class)))
        .thenThrow(UnexpectedTypeException.class);

    Encounter actualResult = mockEncounterServiceImpl.getEncounterById(1L);

  }

  @Test
  public void testAddEncounterReturnsEncounter() throws Exception {

    Encounter actualResult = mockEncounterServiceImpl.addEncounter(encounterOne);

    Assert.assertEquals(encounterOne, actualResult);

  }


  @Test(expected = ServiceUnavailable.class)
  public void testAddEncounterDBError() throws Exception {

    when(mockEncounterRepository.save(any(Encounter.class)))
        .thenThrow(CannotCreateTransactionException.class);

    Encounter actualResult = mockEncounterServiceImpl.addEncounter(encounterOne);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testAddEncounterUnexpectedError() throws Exception {

    when(mockEncounterRepository.save(any(Encounter.class)))
        .thenThrow(UnexpectedTypeException.class);

    Encounter actualResult = mockEncounterServiceImpl.addEncounter(encounterOne);

  }

  @Test
  public void testUpdateEncounterByIdReturnsEncounter() throws Exception {

    encounterOne.setNotes("Hollywood Hulk Hogan");

    mockEncounterServiceImpl.updateEncounterById(1L, encounterOne);

    Assert.assertSame("Hollywood Hulk Hogan", encounterOne.getNotes());

  }

  @Test(expected = BadDataResponse.class)
  public void testUpdateEncounterByIdIdDoesNotMatch() throws Exception {

    encounterOne.setId(3L);

    Encounter result = mockEncounterServiceImpl.updateEncounterById(1L, encounterOne);

  }


  @Test(expected = ServiceUnavailable.class)
  public void testUpdateEncounterByIdDBError() throws Exception {

    when(mockEncounterRepository.findById(any(Long.class)))
        .thenThrow(CannotCreateTransactionException.class);

    encounterOne.setNotes("Hollywood Hulk Hogan");

    Encounter result = mockEncounterServiceImpl.updateEncounterById(1L, encounterOne);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testUpdateEncounterByIdUnexpectedError() throws Exception {

    when(mockEncounterRepository.findById(any(Long.class)))
        .thenThrow(UnexpectedTypeException.class);

    encounterOne.setNotes("Hollywood Hulk Hogan");

    Encounter result = mockEncounterServiceImpl.updateEncounterById(1L, encounterOne);

  }

  @Test
  public void testDeleteEncounterById204Returned() throws Exception {

    mockEncounterServiceImpl.deleteEncounterById(1L);

    //check method was called
    verify(mockEncounterRepository).deleteById(any());

  }

  @Test(expected = ResourceNotFound.class)
  public void testDeleteEncounterByIdIdNotFound() throws Exception {

    when(mockEncounterRepository.existsById(any(Long.class))).thenReturn(false);

    doThrow(ResponseStatusException.class).when(mockEncounterRepository)
        .deleteById(any(Long.class));

    mockEncounterServiceImpl.deleteEncounterById(1L);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testDeleteEncounterByIdDBError() throws Exception {

    doThrow(CannotCreateTransactionException.class).when(mockEncounterRepository)
        .existsById(any(Long.class));

    mockEncounterServiceImpl.deleteEncounterById(1L);

  }

  @Test(expected = ServiceUnavailable.class)
  public void testDeleteEncounterByIdUnexpectedError() throws Exception {

    doThrow(UnexpectedTypeException.class).when(mockEncounterRepository)
        .existsById(any(Long.class));

    mockEncounterServiceImpl.deleteEncounterById(1L);

  }

}

