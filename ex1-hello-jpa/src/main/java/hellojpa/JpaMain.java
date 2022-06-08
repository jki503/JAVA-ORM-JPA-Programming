package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            //1. collection 세팅
            Set<String> favoriteFoods = new HashSet<>();
            favoriteFoods.add("햄버거");
            favoriteFoods.add("치킨");

            List<AddressEntity> addressHistory = new ArrayList<>();
            addressHistory.add(new AddressEntity(new Address("city1", "street1", "zipcode1")));
            addressHistory.add(new AddressEntity(new Address("city2", "street2", "zipcode2")));

            // 2. member 생성
            Member member = new Member(
                    "hey",
                    new Address("city","street", "zipcode"),
                    favoriteFoods,
                    addressHistory
            );

            // 3. 영속화
            em.persist(member);

            tx.commit();//4. 실제 쿼리 날아가는 시점
        }catch (Exception e){
            System.out.println("안되자나");
            tx.rollback();
        }

        em.close();

        emf.close();
    }
}
