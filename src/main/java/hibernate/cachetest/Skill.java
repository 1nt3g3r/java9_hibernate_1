package hibernate.cachetest;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "SKILL_NAME")
    private String skillName;

    @ManyToMany
    @JoinTable(name = "human_skill",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "human_id"))
    private Set<Human> humans;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Set<Human> getHumans() {
        return humans;
    }

    public void setHumans(Set<Human> humans) {
        this.humans = humans;
    }
}
