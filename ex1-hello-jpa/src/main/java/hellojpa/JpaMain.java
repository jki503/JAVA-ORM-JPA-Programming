package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Team team = new Team("teamA");

            Member member1 = new Member("jung");
            Member member2 = new Member("kim");
            member1.updateTeam(team);
            member2.updateTeam(team);

            em.persist(team);
            em.persist(member1);
            em.persist(member2);

            em.flush();
            em.clear();

            //이제 양방향 확인해보기

            Member findMember = em.find(Member.class, member1.getId());
            findMember.getTeam().getMembers().forEach(member -> System.out.println(member.getName()));


            System.out.println("===");

            tx.commit();//4. 실제 쿼리 날아가는 시점
        }catch (Exception e){
            tx.rollback();
        }

        em.close();

        emf.close();
    }
}
