package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // TEAM
            Team teamA = new Team("팀A");
            Team teamB = new Team("팀B");
            Team teamC = new Team("팀C");
            em.persist(teamA);
            em.persist(teamB);
            em.persist(teamC);

            //MEMBER
            Member member1 = new Member("회원1", 10);
            Member member2 = new Member("회원2", 10);
            Member member3 = new Member("회원3", 10);
            Member member4 = new Member("회원4", 10);
            member1.changeTeam(teamA);
            member2.changeTeam(teamA);
            member3.changeTeam(teamB);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            String queryString = "update Member m set m.age = m.age * 2 where m.age < 20";

            //Flush만 된상 황이라 clear하고 사용해야해
            int resultCount = em.createQuery(queryString)
                            .executeUpdate();

            em.clear();

            // 영속성 컨텍스트에 member1의 데이터가 남아있어서, 엔티티 동일성 보장해준다..
            // clear 날려줘야해..
            Member findMember = em.find(Member.class, member1.getId());

            System.out.println(member1 == findMember);
            System.out.println(findMember.getAge());

            tx.commit();//4. 실제 쿼리 날아가는 시점
        } catch (Exception e) {
            System.out.println(e.getMessage() + e);
            tx.rollback();
        }

        em.close();

        emf.close();
    }
}
