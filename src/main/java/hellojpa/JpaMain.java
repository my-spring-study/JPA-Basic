package hellojpa;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import hellojpa.domain.Member;
import hellojpa.domain.type.Address;
import hellojpa.domain.type.AddressEntity;

public class JpaMain {

	public static void main(String[] args) {

		// 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		// 엔티티 매니저는 쓰레드간에 공유하면 안 된다.
		EntityManager em = emf.createEntityManager();

		// JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {

			Member member = new Member();
			member.setUsername("member1");
			member.setHomeAddress(new Address("homeCity", "street", "zipcode"));

			member.getFavoriteFoods().add("치킨");
			member.getFavoriteFoods().add("족발");
			member.getFavoriteFoods().add("피자");

			member.getAddressHistory().add(new AddressEntity("old1", "street", "zipcode"));
			member.getAddressHistory().add(new AddressEntity("old2", "street", "zipcode"));

			em.persist(member);

			em.flush();
			em.clear();

			Member findMember = em.find(Member.class, member.getId());

			// // 임베디드 값 타입 컬렉션 수정
			// findMember.getAddressHistory().remove(new Address("old1", "street", "zipcode"));
			// findMember.getAddressHistory().add(new Address("new1", "street", "zipcode"));

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
