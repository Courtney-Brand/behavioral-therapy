package behavioral.therapy.controller.model;

import java.util.HashSet;
import java.util.Set;

import behavioral.therapy.entity.Client;
import behavioral.therapy.entity.Objective;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ObjectiveData {
	private Long objectiveId;
	private String objectiveTitle;
	private String objectiveSetting;
	private String objectiveDiscription;
	private Set<TherapyClient> clients = new HashSet<>();

	public ObjectiveData(Objective objective) {
		objectiveId = objective.getObjectiveId();
		objectiveTitle = objective.getObjectiveTitle();
		objectiveSetting = objective.getObjectiveSetting();
		objectiveDiscription = objective.getObjectiveDiscription();

		for (Client client : objective.getClients()) {
			clients.add(new TherapyClient(client));
		}
	}

	@Data
	@NoArgsConstructor
	static class TherapyClient {

		private Long clientId;
		private String clientName;
		private String clientGuardianName;
		private String clientGuardianPhone;
		private String clientAddress;
		private Long clientApprovedHours;
		private TherapistData therapist;

		public TherapyClient(Client client) {

			clientId = client.getClientId();
			clientName = client.getClientName();
			clientGuardianName = client.getClientGuardianName();
			clientGuardianPhone = client.getClientGuardianPhone();
			clientAddress = client.getClientAddress();
			clientApprovedHours = client.getClientApprovedHours();
			therapist = new TherapistData(client.getTherapist());
		}
	}
}
