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
            //비영속

            // 1. select
            Member member = em.find(Member.class, 1L);

            System.out.println("======before======");

            // 2. 비영속 상태
            em.remove(member);

            // 3. 이름 조회
            System.out.println(member.getName());
            System.out.println("======after======");

            tx.commit();//4. 실제 쿼리 날아가는 시점
        }catch (Exception e){
            tx.rollback();
        }

        em.close();

        emf.close();
    }
}
