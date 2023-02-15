package jpql.domain;

import static javax.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	private String username;

	private int age;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	public Member() {
	}

	public void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}

	public void addOrder(Order order) {
		this.orders.add(order);
		order.setMember(this);
	}

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Team getTeam() {
		return team;
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", username='" + username + '\'' +
			", age=" + age +
			'}';

	}
}
