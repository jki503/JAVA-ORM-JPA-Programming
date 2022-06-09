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

            em.flush();
            em.clear();

            String jpql = "select distinct t from Team t join fetch t.members where t.name ='팀A'";
            List<Team> resultList = em.createQuery(jpql, Team.class)
                            .getResultList();

            for (Team team : resultList){
                System.out.println("teamName = " + team.getName() + "|" + team.getMembers().size());
            }

            tx.commit();//4. 실제 쿼리 날아가는 시점
        } catch (Exception e) {
            System.out.println(e.getMessage() + e);
            tx.rollback();
        }

        em.close();

        emf.close();
    }
}
