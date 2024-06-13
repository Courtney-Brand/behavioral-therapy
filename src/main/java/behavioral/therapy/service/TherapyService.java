package behavioral.therapy.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import behavioral.therapy.controller.model.ClientData;
import behavioral.therapy.controller.model.ObjectiveData;
import behavioral.therapy.controller.model.TherapistData;
import behavioral.therapy.dao.ClientDao;
import behavioral.therapy.dao.ObjectiveDao;
import behavioral.therapy.dao.TherapistDao;
import behavioral.therapy.entity.Client;
import behavioral.therapy.entity.Objective;
import behavioral.therapy.entity.Therapist;

@Service
public class TherapyService {

	@Autowired
	private TherapistDao therapistDao;

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private ObjectiveDao objectiveDao;

	@Transactional(readOnly = false)
	public TherapistData saveTherapist(TherapistData therapistData) {
		Long therapistId = therapistData.getTherapistId();
		Therapist therapist = findOrCreateTherapist(therapistId);

		setFieldsInTherapist(therapist, therapistData);
		return new TherapistData(therapistDao.save(therapist));
	}

	private void setFieldsInTherapist(Therapist therapist, TherapistData therapistData) {
		therapist.setTherapistName(therapistData.getTherapistName());
		therapist.setTherapistPhone(therapistData.getTherapistPhone());
		therapist.setTherapistAddress(therapistData.getTherapistAddress());
		therapist.setTherapistCertification(therapistData.getTherapistCertification());
	}

	private Therapist findOrCreateTherapist(Long therapistId) {
		Therapist therapist;
		if (Objects.isNull(therapistId)) {
			therapist = new Therapist();
		} else {
			therapist = findTherapistById(therapistId);
		}
		return therapist;
	}

	private Therapist findTherapistById(Long therapistId) {
		return therapistDao.findById(therapistId)
				.orElseThrow(() -> new NoSuchElementException("Therapist with ID=" + therapistId + " does not exist."));
	}

	@Transactional(readOnly = false)
	public ClientData saveClient(ClientData clientData, Long therapistId, Long clientId) {
		Therapist therapist = findTherapistById(therapistId);
		List<Objective> objectives = objectiveDao.findAll();
		Client client = findOrCreateClient(clientId);

		setFieldsInClient(client, clientData);
		client.setTherapist(therapist);
		therapist.getClients().add(client);

		for (Objective objective : objectives) {
			objective.getClients().add(client);
			client.getObjectives().add(objective);
		}

		Client dbClient = clientDao.save(client);
		return new ClientData(dbClient);

	}

	private void setFieldsInClient(Client client, ClientData clientData) {
		client.setClientName(clientData.getClientName());
		client.setClientGuardianName(clientData.getClientGuardianName());
		client.setClientGuardianPhone(clientData.getClientGuardianPhone());
		client.setClientAddress(clientData.getClientAddress());
		client.setClientApprovedHours(clientData.getClientApprovedHours());

	}

	private Client findOrCreateClient(Long clientId) {
		Client client;
		if (Objects.isNull(clientId)) {
			client = new Client();
		} else {
			client = findClientById(clientId);
		}
		return client;
	}

	private Client findClientById(Long clientId) {
		return clientDao.findById(clientId)
				.orElseThrow(() -> new NoSuchElementException("Client with ID=" + clientId + " does not exist."));
	}

	@Transactional(readOnly = false)
	public ObjectiveData saveObjective(ObjectiveData objectiveData) {
		Long objectiveId = objectiveData.getObjectiveId();
		Objective objective = findOrCreateObjective(objectiveId);

		setFieldsInObjective(objective, objectiveData);
		return new ObjectiveData(objectiveDao.save(objective));
	}

	private void setFieldsInObjective(Objective objective, ObjectiveData objectiveData) {
		objective.setObjectiveTitle(objectiveData.getObjectiveTitle());
		objective.setObjectiveSetting(objectiveData.getObjectiveSetting());
		objective.setObjectiveDiscription(objectiveData.getObjectiveDiscription());
	}

	private Objective findOrCreateObjective(Long objectiveId) {
		Objective objective;
		if (Objects.isNull(objectiveId)) {
			objective = new Objective();
		} else {
			objective = findObjectiveById(objectiveId);
		}
		return objective;
	}

	private Objective findObjectiveById(Long objectiveId) {
		return objectiveDao.findById(objectiveId)
				.orElseThrow(() -> new NoSuchElementException("Objective with ID=" + objectiveId + " does not exist."));
	}

	@Transactional(readOnly = true)
	public List<TherapistData> retrieveAllTherapists() {
		List<Therapist> therapists = therapistDao.findAll();
		List<TherapistData> therapistData = new LinkedList<>();

		for (Therapist therapist : therapists) {
			therapistData.add(new TherapistData(therapist));
		}
		return therapistData;
	}

	@Transactional(readOnly = true)
	public TherapistData retrieveTherapistById(Long therapistId) {
		Therapist therapist = findTherapistById(therapistId);
		return new TherapistData(therapist);
	}

	@Transactional(readOnly = true)
	public List<ObjectiveData> retrieveAllObjectives() {
		List<Objective> objectives = objectiveDao.findAll();
		List<ObjectiveData> response = new LinkedList<>();

		for (Objective objective : objectives) {
			response.add(new ObjectiveData(objective));
		}
		return response;
	}

	@Transactional(readOnly = true)
	public List<ClientData> retrieveCaseloadByTherapistId(Long therapistId) {
		List<Client> clients = clientDao.findAll();
		List<ClientData> caseload = new LinkedList<>();

		for (Client client : clients) {
			if (therapistId.equals(client.getTherapist().getTherapistId())) {
				caseload.add(new ClientData(client));
			}
		}

		return caseload;
	}

	@Transactional(readOnly = true)
	public List<ClientData> retrieveAllClients() {
		List<Client> clients = clientDao.findAll();
		List<ClientData> clientData = new LinkedList<>();

		for (Client client : clients) {
			clientData.add(new ClientData(client));
		}
		return clientData;
	}

	@Transactional(readOnly = true)
	public ClientData retrieveClientById(Long clientId) {
		Client client = findClientById(clientId);
		return new ClientData(client);
	}

	@Transactional(readOnly = false)
	public void deleteTherapistById(Long therapistId) {
		Therapist therapist = findTherapistById(therapistId);
		therapistDao.delete(therapist);
	}

	@Transactional(readOnly = false)
	public void deleteClientById(Long clientId) {
		Client client = findClientById(clientId);
		clientDao.delete(client);
	}

	@Transactional(readOnly = true)
	public List<ObjectiveData> retrieveListOfClientObjectives(Long clientId) {
		List<Objective> clientObjectives = objectiveDao.findAll();
		List<ObjectiveData> clientObjectiveData = new LinkedList<>();

		Client client = findClientById(clientId);

		for (Objective objective : clientObjectives) {
			if (objective.getClients().contains(client)) {
				clientObjectiveData.add(new ObjectiveData(objective));
			}
		}

		return clientObjectiveData;
	}

}
