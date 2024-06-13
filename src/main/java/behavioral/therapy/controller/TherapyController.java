package behavioral.therapy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import behavioral.therapy.controller.model.ClientData;
import behavioral.therapy.controller.model.ObjectiveData;
import behavioral.therapy.controller.model.TherapistData;
import behavioral.therapy.service.TherapyService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/behavioral_therapy")
@Slf4j

public class TherapyController {

	@Autowired
	private TherapyService therapyService;

	@PostMapping("/therapist")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TherapistData insertTherapist(@RequestBody TherapistData therapistData) {
		log.info("Creating therapist {}", therapistData);
		return therapyService.saveTherapist(therapistData);
	}

	@PostMapping("/therapist/{therapistId}/client")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClientData insertClient(@RequestBody ClientData clientData, @PathVariable Long therapistId) {
		Long clientId = clientData.getClientId();
		log.info("Creating client {}", clientData);
		return therapyService.saveClient(clientData, therapistId, clientId);
	}

	@PostMapping("/objective")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ObjectiveData insertObjective(@RequestBody ObjectiveData objectiveData) {
		log.info("Creating objective {}", objectiveData);
		return therapyService.saveObjective(objectiveData);
	}

	@GetMapping("/therapist")
	public List<TherapistData> retrieveAllTherapists() {
		log.info("Retrieving all therapists.");
		return therapyService.retrieveAllTherapists();
	}

	@GetMapping("/therapist/{therapistId}")
	public TherapistData retrieveTherapistrById(@PathVariable Long therapistId) {
		log.info("Retrieving therapist with ID={}", therapistId);
		return therapyService.retrieveTherapistById(therapistId);
	}

	// Shows one to many relationship between therapist and clients

	@GetMapping("/client/therapist/{therapistId}")
	public List<ClientData> retrieveCaseloadByTherapistId(@PathVariable Long therapistId) {
		log.info("Retrieving caseload for therapist with ID={}", therapistId);
		return therapyService.retrieveCaseloadByTherapistId(therapistId);
	}

	@GetMapping("/client")
	public List<ClientData> retrieveAllClients() {
		log.info("Retrieving a list of all clients.");
		return therapyService.retrieveAllClients();
	}

	@GetMapping("/client/{clientId}")
	public ClientData retrieveClientById(@PathVariable Long clientId) {
		log.info("Retrieving client with ID={}", clientId);
		return therapyService.retrieveClientById(clientId);
	}
// Shows many to many relationship between client and objective

	@GetMapping("/objective")
	public List<ObjectiveData> retrieveAllObjectives() {
		log.info("Retrieving a list of all objectives.");
		return therapyService.retrieveAllObjectives();
	}

	// Shows many to many relationship between client and objective

	@GetMapping("/objective/client/{clientId}")
	public List<ObjectiveData> retrieveListOfClientObjectives(@PathVariable Long clientId) {
		log.info("Retrieving a list of objectives for client with ID={}", clientId);
		return therapyService.retrieveListOfClientObjectives(clientId);
	}

	@PutMapping("/therapist/{therapistId}")
	public TherapistData updateTherapist(@PathVariable Long therapistId, @RequestBody TherapistData therapistData) {
		therapistData.setTherapistId(therapistId);
		log.info("Updating therapist {}", therapistData);
		return therapyService.saveTherapist(therapistData);
	}

	@PutMapping("/client/{clientId}/therapist/{therapistId}")
	public ClientData updateClient(@PathVariable Long clientId, @PathVariable Long therapistId,
			@RequestBody ClientData clientData) {
		clientData.setClientId(clientId);
		log.info("Updating client {}", clientData);
		return therapyService.saveClient(clientData, therapistId, clientId);
	}

	@DeleteMapping("/therapist/{therapistId}")
	public Map<String, String> deleteTherapistById(@PathVariable Long therapistId) {
		log.info("Deleting therapist with ID={}", therapistId);
		therapyService.deleteTherapistById(therapistId);
		return Map.of("message", "Deletion of therapist with ID" + therapistId + " was successful.");
	}

	@DeleteMapping("/client/{clientId}")
	public Map<String, String> deleteClientById(@PathVariable Long clientId) {
		log.info("Deleting client with ID={}", clientId);
		therapyService.deleteClientById(clientId);
		return Map.of("message", "Deletion of client with ID" + clientId + " was successful.");
	}
}
