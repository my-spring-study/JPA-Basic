package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import hellojpa.domain.Member;
import hellojpa.domain.Team;

public class FetchJoinExample {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {

			// 팀
			Team teamA = new Team();
			teamA.setName("팀A");
			em.persist(teamA);

			Team teamB = new Team();
			teamB.setName("팀B");
			em.persist(teamB);

			// 회원: member1, 2 는 팀A 소속. member3은 팀B 소속
			Member member1 = new Member();
			member1.setUsername("회원1");
			member1.setTeam(teamA);
			em.persist(member1);

			Member member2 = new Member();
			member2.setUsername("회원2");
			member2.setTeam(teamA);
			em.persist(member2);

			Member member3 = new Member();
			member3.setUsername("회원3");
			member3.setTeam(teamB);
			em.persist(member3);

			em.flush();
			em.clear();

			// Query 1: N + 1 문제 발생
			/*
			String query = "select m from Member m";

			List<Member> findMembers = em.createQuery(query, Member.class)
				.getResultList();

			for (Member member : findMembers) {
				System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
				// 회원1, 팀A(SQL)
				// 회원2, 팀A(1차 캐시) <- SQL 쿼리문 안 나감
				// 회원3, 팀B(SQL)

				// 회원 100명 -> N + 1: 첫 번째 쿼리 결과로 얻은 개수 만큼 추가 쿼리가 나감
			}
			*/

			String fetchJoinQuery = "select m from Member m join fetch m.team";
			List<Member> findMembers = em.createQuery(fetchJoinQuery, Member.class)
				.getResultList();

			for (Member member : findMembers) {
				System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
			}

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
