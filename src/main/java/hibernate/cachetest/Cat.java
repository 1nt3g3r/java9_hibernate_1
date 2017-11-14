package hibernate.cachetest;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Cat")
public class Cat {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "WEIGHT")
    private float weight;

    @OneToOne
    @JoinColumn(name = "cat_details_id", referencedColumnName = "id")
    private CatDetails catDetails;

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private Human owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public CatDetails getCatDetails() {
        return catDetails;
    }

    public void setCatDetails(CatDetails catDetails) {
        this.catDetails = catDetails;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                '}';
    }
}
