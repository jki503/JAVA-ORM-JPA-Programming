# 객체지향 쿼리 언어2 - 중급 문법

## 경로 표현식

</br>

|                 경로 표현식                 |
| :-----------------------------------------: |
| ![경로 표현식](../res/section11_course.png) |

</br>

> 경로 표현식에 따라서 내부 동작방식에 따라 결과가 달라진다.

</br>

- 경로 표현식 용어 정리
  - 상태 필드 : 단순히 값을 저장하기 위한 필드
    - 경로 탐색의 끝, `탐색 X`
    - ex) m.username
  - 연관 필드 : 연관관계를 위한 필드
    - 단일 값 연관 필드
      - 묵시적 내부 조인 발생, `탐색 O`
      - @ManyToOne, @OneToOne, 대상이 엔티티
        - ex) m.team
    - 컬렉션 값 연관 필드
      - 묵시적 내부 조인 발생, `탐색 X`
      - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
      - @OneToMany, @ManyToMany, 대상이 컬렉션
        - ex) m.orders

</br>

```java
select m.team.name From Member m
```

> m.team은 연관 필드로 `묵시적 내부조인 발생`
> m.team.name은 상태필드로 `경로 탐색의 끝`

</br>

```java

tx.begin();

        try {

            Team team = new Team("teamA");
            em.persist(team);

            Member member = new Member("member1", 10);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select t.members From Team t";
            List resultList = em.createQuery(query, Collection.class)
                            .getResultList();

            resultList.forEach(System.out::println);

            tx.commit();//4. 실제 쿼리 날아가는 시점
        } catch (Exception e) {
            System.out.println(e.getMessage() + e);
            tx.rollback();
        }

```

|               컬렉션 값 연관 필드               |
| :---------------------------------------------: |
| ![컬렉션 결과](../res/section11_collection.png) |

</br>

- 묵시적 조인 시 주의 사항
  - 항상 내부 조인
  - 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야 함
  - 경로 탐색은 주로 SELECT, WHERE절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM JOIN절에 영향을 줌

</br>

- 그냥 명시적 조인을 사용해라
  - 조인이 SQL 튜닝에 중요 포인트다
  - 묵시적 조인은 조인이 일어난느 상황을 한눈에 파악하기 어렵다.
    - 유지보수 어려워..

</br>

## 페치 조인 1 - 기본

</br>

- SQL 조인 종류 X
- JPQL에서 `성능 최적화`를 위해 제공
- 연관된 엔티티나 컬렉션을 `SQL 한 번에 함께 조회`하는 기능
- `join fetch 명령어` 사용
- 페치조인::= [LEFT[OUTER]|INNER] JOIN FETCH 조인 경로
  - LEFT JOIN FETCH
  - JOIN FETCH

</br>

|                       예시                       |
| :----------------------------------------------: |
| ![예시](../res/section11_fetch_join_example.png) |

</br>

> 멤버 1,2 -> team A  
> 멤버 3은 -> team B

</br>

```java

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

            String jpql = "select m from Member m join fetch m.team";
            List<Member> members = em.createQuery(jpql, Member.class)
                            .getResultList();

            for (Member member : members){
                System.out.println("username = " + member.getUsername());
                System.out.println("teamName = " + member.getTeam().getName());
            }

            tx.commit();//4. 실제 쿼리 날아가는 시점
        } catch (Exception e) {
            System.out.println(e.getMessage() + e);
            tx.rollback();
        }

```

|                    실행 코드 결과                     |
| :---------------------------------------------------: |
| ![코드 결과](../res/section11_fetch_join_console.png) |

</br>

> 실제 쿼리가 실행 될 때, Team은 프록시가 아닌 실제 객체로 들어온다.  
> 쿼리 한 개로 모든 객체를 불러옴으로 N+1 쿼리문제 해소

</br>

> inner join임으로 member4는 미출력  
> left join으로 할 경우 member의 team은 null임으로 exception

</br>

> 또한 내가 지연 로딩으로 설정하더라도 항상 fetch join이 우선이다!

</br>

- 컬렉션 페치 조인

</br>

```java

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

            String jpql = "select t from Team t join fetch t.members";
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

```

</br>

|                 1:N Collection 뻥튀기                  |
| :----------------------------------------------------: |
| ![컬렉션 뻥튀기](../res/section11_collection_boom.png) |

</br>

> 위의 예제 사진을 보면, Team과 Member를 조인하는데,  
> 이때 회원1, 회원2가 TeamA에 속해 있어 join 연산으로 인해 `중복컬럼`이 발생한다.
> `DISTINCT`를 사용해야한다!

</br>

- 페치 조인과 DISTINCT
  - SQL의 DISTINCT는 중복된 결과를 제거하는 명령
  - JPQL의 DISTINCT 2가지 기능 제공
    - SQL에 DISTINCT 추가
    - 애플리케이션에서 엔티티 중복 제거

> SQL에 DISTINCT를 추가해서  
> 애플리케이션에 올라온 엔티티가 중복일경우  
> `같은 식별자를 가진 Team Entity 제거`

</br>

- 페치 조인과 일반 조인의 차이
  - 일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음

</br>

## 페치 조인 2 - 한계

</br>

- 페치 조인 대상에는 별칭을 줄 수 없다.
  - 하이버네이트는 가능, 가급적 사용 X
    - 나와 연관된 엔티티를 가져오겠다는 것인데 추가작업을 하겠다?
- 둘 이상의 컬렉션은 페치 조인 X
- 컬렉션을 페치 조인하면 `페이징 API`(setFirstResult, setMaxResults) 사용 불가
  - 일대일 다대일같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
  - 하이버네이트는 경고 로그를 남기고 메모리에서 페이징 -> 매우 위험!

</br>

## 다형성 쿼리

</br>

</br>

## 엔티티 직접 사용

</br>

</br>

## Named 쿼리

</br>

</br>

## 벌크 연산
