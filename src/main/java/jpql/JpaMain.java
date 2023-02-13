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

			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			em.persist(member);

			em.flush();
			em.clear();

			// 엔티티 프로젝션
			/*
				JOIN 문은 성능 튜닝할 여지가 높으므로 JPQL문에서도 명시적으로 JOIN을 드러내는 것이 좋다.
			 */
			List<Team> result1 = em.createQuery("select m.team from Member m", Team.class) // 지양
				.getResultList();

			List<Team> result2 = em.createQuery("select t from Member m join m.team t", Team.class) // 지향
				.getResultList();

			// 임베디드 타입 프로젝션
			List<Address> addresses = em.createQuery("select o.address from Order o", Address.class)
				.getResultList();

			// 스칼라 타입 프로젝션
			List<String> result4 = em.createQuery("select distinct m.username from Member m", String.class)
				.getResultList();

			List result5 = em.createQuery("select distinct m.username, m.age from Member m")
				.getResultList();

			/*
				NEW 명령어 - 단순 값을 DTO로 바로 조회
			 */
			List<MemberDTO> result6 = em.createQuery(
				"select new jpql.dto.MemberDTO(m.username, m.age) "
					+ "from Member m", MemberDTO.class
				)
				.getResultList();

			MemberDTO memberDTO = result6.get(0);
			System.out.println("memberDTO.getAge() = " + memberDTO.getAge());


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