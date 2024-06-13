package behavioral.therapy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import behavioral.therapy.entity.Therapist;

public interface TherapistDao extends JpaRepository<Therapist, Long> {

}
