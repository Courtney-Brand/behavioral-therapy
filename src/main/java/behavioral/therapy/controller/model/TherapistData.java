package behavioral.therapy.controller.model;

import behavioral.therapy.entity.Therapist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TherapistData {

	private Long therapistId;
	private String therapistName;
	private String therapistPhone;
	private String therapistAddress;
	private String therapistCertification;

	public TherapistData(Therapist therapist) {
		therapistId = therapist.getTherapistId();
		therapistName = therapist.getTherapistName();
		therapistPhone = therapist.getTherapistPhone();
		therapistAddress = therapist.getTherapistAddress();
		therapistCertification = therapist.getTherapistCertification();

	}
}
