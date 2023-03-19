package hellojpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

import hellojpa.domain.type.BaseEntity;

@Entity
public class Team extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "TEAM_ID")
	private Long id;

	private String name;

	// @BatchSize(size = 100) // global 옵션으로 hibernate.default_batch_fetch_size 사용 권장
	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>(); // 양방향 연관관계

	public void addMember(Member member) {
		member.setTeam(this);
		this.members.add(member);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Member> getMembers() {
		return members;
	}
}
