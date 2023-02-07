package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

public class JpaMain {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {

			Order order = em.find(Order.class, 1L);
			Long memberId = order.getMemberId();

			// 외래키로 다시 조회. 객체지향적이지 않다.
			Member member = em.find(Member.class, memberId);

			// 연관관계 매핑 (참조와 외래키를 매핑) 하는 방식이 객체지향적이다.
			Member member = order.getMember();

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();

	}
}
