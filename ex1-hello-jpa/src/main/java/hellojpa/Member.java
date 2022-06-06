package hellojpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "TEAM_ID")
    private Team team;

    protected Member(){}

    public Member(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }


    public void updateName(String name) {
        this.name = name;
    }

    public void updateTeam(Team team) {
        if(Objects.nonNull(this.team)){
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Member.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
