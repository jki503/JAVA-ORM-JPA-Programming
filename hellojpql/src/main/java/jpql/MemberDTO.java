package jpql;

import java.util.StringJoiner;

public class MemberDTO {

    private String username;
    private int age;

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MemberDTO.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("age=" + age)
                .toString();
    }
}
