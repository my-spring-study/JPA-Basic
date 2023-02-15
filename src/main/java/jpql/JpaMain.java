package jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpql.domain.Member;
import jpql.domain.Team;

public class JpaMain {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {

			Team team = new Team();
			team.setName("teamA");
			em.persist(team);

			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);

			member.changeTeam(team);

			em.persist(member);

			em.flush();
			em.clear();

			// 내부 조인
			String innerJoinQuery = "select m from Member m inner join m.team t where t.name = :teamName";
			List<Member> result = em.createQuery(innerJoinQuery, Member.class)
				.setParameter("teamName", team.getName())
				.getResultList();

			// 외부 조인
			String outerJoinQuery = "select m from Member m left join m.team t";
			List<Member> result2 = em.createQuery(outerJoinQuery, Member.class)
				.getResultList();

			// 세타 조인
			String thetaJoinQuery = "select m from Member m, Team t where m.username = t.name";
			List<Member> result3 = em.createQuery(thetaJoinQuery, Member.class)
				.getResultList();

			// JOIN ON 절: 1. 조인대상을 필터링 하고 조인
			// 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
			String joinOnQuery = "select m from Member m left join m.team t on t.name = 'teamA'";
			List<Member> result4 = em.createQuery(joinOnQuery, Member.class)
				.getResultList();

			// JOIN ON 절: 2. 연관관계 없는 엔티티 외부 조인
			// 회원의 이름과 팀의 이름이 같은 대상 외부 조인
			String joinOnQuery2 = "select m from Member m left join Team t on m.username = t.name";
			List<Member> result5 = em.createQuery(joinOnQuery2, Member.class)
				.getResultList();

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