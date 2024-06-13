package behavioral.therapy.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Therapist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long therapistId;

	private String therapistName;
	private String therapistPhone;
	private String therapistAddress;
	private String therapistCertification;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL)
	private Set<Client> clients = new HashSet<>();

}
