package io.catalyte.training.repositories;

    import io.catalyte.training.entitites.Encounter;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

/**
 * patient repo which stores patients and has a derived method called existsByEmail
 */
@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {


}
