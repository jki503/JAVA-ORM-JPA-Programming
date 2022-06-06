package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    protected Team(){}

    public Team(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member){
        member.updateTeam(this);
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
