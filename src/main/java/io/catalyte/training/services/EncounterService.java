package io.catalyte.training.services;

import io.catalyte.training.entitites.Encounter;
import java.util.List;

public interface EncounterService {

  List<Encounter> queryEncountersByPatientId(Long id) throws Exception;

  Encounter getEncounterById(Long id) throws Exception;

  Encounter addEncounter(Encounter encounter) throws Exception;

  Encounter updateEncounterById(Long id, Encounter encounter) throws Exception;

  void deleteEncounterById(Long id) throws Exception;

}
