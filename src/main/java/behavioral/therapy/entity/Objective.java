package behavioral.therapy.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Objective {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long objectiveId;
	
	private String objectiveTitle;
	private String objectiveSetting;

	@Column(length = 2048)
	private String objectiveDiscription;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "objectives")
	private Set<Client> clients = new HashSet<>();
}
