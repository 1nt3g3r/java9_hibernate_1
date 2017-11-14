package hibernate.cachetest;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Human")
public class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "owner")
    private Set<Cat> cats;

    @ManyToMany
    @JoinTable(name = "human_skill",
        joinColumns = @JoinColumn(name = "human_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Cat> getCats() {
        return cats;
    }

    public void setCats(Set<Cat> cats) {
        this.cats = cats;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
}
