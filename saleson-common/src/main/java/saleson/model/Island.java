package saleson.model;

import lombok.Getter;
import lombok.Setter;
import saleson.common.enumeration.IslandType;
import saleson.model.base.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name="OP_ISLAND")
//@EntityListeners(value= {AuditingEntityListener.class})
@Getter @Setter
public class Island extends BaseEntity {
	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 7)
	private String zipcode;

	@Column(length = 255)
	private String address;

	@Column(length = 20)
    @Enumerated(EnumType.STRING)
	private IslandType islandType;

	@Column(length = 11)
	private Integer extraCharge;

}
