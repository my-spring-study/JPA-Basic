package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import hellojpa.domain.Member;
import hellojpa.domain.Team;

public class FetchJoinWithPagingApi {

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

			/**
			 * 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없다.
			 */

			// 하이버네이트에서 컬렉션 페치 조인 & 페이징 API를 사용하면 경고메시지를 남기면서 메모리에서 페이징 처리가 된다.
			// 경고 문구: "firstResult/maxResults specified with collection fetch; applying in memory!"
/*			String query = "select t "
				+ "from Team t "
				+ "join fetch t.members";

			List<Team> result = em.createQuery(query, Team.class)
				.setFirstResult(0)
				.setMaxResults(1)
				.getResultList();*/

			// 해결 방법 1: 일대다 조인을 다대일 조인으로 방향을 바꿔서 페치 조인
			/*String query1 = "select m from Member m join fetch m.team t";
			List<Team> result1 = em.createQuery(query1, Team.class)
				.setFirstResult(0)
				.setMaxResults(1)
				.getResultList();*/

			// 해결 방법 2: @BatchSize 또는 (global) hibernate.default_batch_fetch_size 이용 -> IN 쿼리 사용
			// 쿼리가 N + 1 이 아니라 테이블 수만큼 맞출 수 있다 -> 최적화 완료
			String query = "select t from Team t";
			List<Team> result = em.createQuery(query, Team.class)
				.setFirstResult(0)
				.setMaxResults(2)
				.getResultList(); // 리스트에 담긴 Team을 IN쿼리로 100개씩 넘긴다.

			System.out.println("result size = " + result.size());

			for (Team team : result) {
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
