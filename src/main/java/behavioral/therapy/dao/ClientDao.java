package behavioral.therapy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import behavioral.therapy.entity.Client;

public interface ClientDao extends JpaRepository<Client, Long> {

}
