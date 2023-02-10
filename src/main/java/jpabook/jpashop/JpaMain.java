package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;

public class JpaMain {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Delivery delivery = new Delivery();
			OrderItem orderItem1 = new OrderItem();
			OrderItem orderItem2 = new OrderItem();

			Order order = new Order();
			order.setDelivery(delivery);
			order.addOrderItem(orderItem1);
			order.addOrderItem(orderItem2);

			em.persist(order);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();

	}
}
