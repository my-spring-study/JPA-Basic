package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import hellojpa.domain.Member;
import hellojpa.domain.Team;

public class CollectionFetchJoin {

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

			// Query 1. DB 입장에서 일대다 조인은 데이터 뻥튀기가 된다.
			/*String query = "select t "
				+ "from Team t "
				+ "join fetch t.members";

			List<Team> result = em.createQuery(query, Team.class)
				.getResultList();

			for (Team team : result) {
				System.out.println("team = " + team.getName() + " | " + "member count = " + team.getMembers().size());
			}*/

			// Query 2. 페치 조인과 DISTINCT -> SQL에 DISTINCT 추가 & 애플리케이션에서 엔티티 중복 제거
			String query = "select distinct t "
				+ "from Team t "
				+ "join fetch t.members";

			List<Team> result = em.createQuery(query, Team.class)
				.getResultList();

			for (Team team : result) {
				System.out.println("team = " + team.getName() + " | " + "member count = " + team.getMembers().size());
			}

			em.flush();
			em.clear();

			System.out.println("##############");

			// Query 3. 페치조인과 일반 조인의 차이
			String query3 = "select distinct t "
				+ "from Team t "
				+ "join t.members m"; // 일반 조인은 SQL에 조인이 추가 될 뿐 연관된 엔티티를 퍼올리진 않는다.

			List<Team> result3 = em.createQuery(query3, Team.class)
				.getResultList();

			for (Team team : result3) {
				System.out.println("team = " + team.getName() + " | " + "member count = " + team.getMembers().size());
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
