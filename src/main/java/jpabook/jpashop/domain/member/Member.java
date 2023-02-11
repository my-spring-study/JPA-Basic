package jpabook.jpashop.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.common.BaseEntity;
import jpabook.jpashop.domain.type.Address;

@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	private String name;

	@Embedded
	private Address address;

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
}
