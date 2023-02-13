package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpql.domain.Member;

public class JpaMain {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {

			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			em.persist(member);
			
			Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
				.setParameter("username", member.getUsername())
				.getSingleResult();

			System.out.println("result.getUsername() = " + result.getUsername());

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

		emf.close();
	}
}