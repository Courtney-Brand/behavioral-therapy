package behavioral.therapy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import behavioral.therapy.controller.model.ObjectiveData;
import behavioral.therapy.entity.Objective;

public interface ObjectiveDao extends JpaRepository<Objective, Long> {

	List<ObjectiveData> save(List<Objective> objectives);

}
