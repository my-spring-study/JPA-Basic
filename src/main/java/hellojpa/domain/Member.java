package hellojpa.domain;

import static javax.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import hellojpa.domain.type.Address;
import hellojpa.domain.type.BaseEntity;
import hellojpa.domain.type.Period;

@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Embedded
	private Period workPeriod;

	@Embedded
	private Address homeAddress;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
		@AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE")),
	})
	private Address companyAddress; // 한 엔티티에 같은 임베디드 타입을 중복해서 사용하는 경우는 드물다.

	@ElementCollection
	@CollectionTable(name = "FAVORITE_FOODS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
	@Column(name = "FOOD_NAME")
	private Set<String> favoriteFoods = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
	private List<Address> addressHistory = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "LOCKER_ID", unique = true)
	private Locker locker;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setWorkPeriod(Period workPeriod) {
		this.workPeriod = workPeriod;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Set<String> getFavoriteFoods() {
		return favoriteFoods;
	}

	public List<Address> getAddressHistory() {
		return addressHistory;
	}
}
