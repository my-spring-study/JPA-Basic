package jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import jpql.domain.Address;
import jpql.domain.Member;
import jpql.domain.Order;
import jpql.domain.Team;
import jpql.dto.MemberDTO;

public class JpaMain {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {

			for (int i = 0; i < 100; i++) {
				Member member = new Member();
				member.setUsername("member" + i);
				member.setAge(i);
				em.persist(member);
			}

			em.flush();
			em.clear();

			List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
				.setFirstResult(0) // 조회 시작 위치 (0부터 시작)
				.setMaxResults(10) // 조회할 데이터 수
				.getResultList();

			for (Member member : result) {
				System.out.println("member = " + member);
			}

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