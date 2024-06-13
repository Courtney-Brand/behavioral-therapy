package behavioral.therapy.controller.model;

import java.util.HashSet;
import java.util.Set;

import behavioral.therapy.entity.Client;
import behavioral.therapy.entity.Objective;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientData {

	private Long clientId;
	private String clientName;
	private String clientGuardianName;
	private String clientGuardianPhone;
	private String clientAddress;
	private Long clientApprovedHours;
	private TherapistData therapist;
	private Set<TherapyObjective> objectives = new HashSet<>();

	public ClientData(Client client) {
		clientId = client.getClientId();
		clientName = client.getClientName();
		clientGuardianName = client.getClientGuardianName();
		clientGuardianPhone = client.getClientGuardianPhone();
		clientAddress = client.getClientAddress();
		clientApprovedHours = client.getClientApprovedHours();
		therapist = new TherapistData(client.getTherapist());

		for (Objective objective : client.getObjectives()) {
			objectives.add(new TherapyObjective(objective));
		}

	}

	@Data
	@NoArgsConstructor
	static class TherapyObjective {

		private Long objectiveId;
		private String objectiveTitle;
		private String objectiveSetting;
		private String objectiveDiscription;

		public TherapyObjective(Objective objective) {
			objectiveId = objective.getObjectiveId();
			objectiveTitle = objective.getObjectiveTitle();
			objectiveSetting = objective.getObjectiveSetting();
			objectiveDiscription = objective.getObjectiveDiscription();

		}
	}
}
