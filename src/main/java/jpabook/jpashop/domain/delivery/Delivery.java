package jpabook.jpashop.domain.delivery;

import static javax.persistence.FetchType.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import jpabook.jpashop.domain.common.BaseEntity;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.type.Address;

@Entity
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "DELIVERY_ID")
	private Long id;

	@OneToOne(mappedBy = "delivery", fetch = LAZY)
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeliveryStatus getStatus() {
		return status;
	}

	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
