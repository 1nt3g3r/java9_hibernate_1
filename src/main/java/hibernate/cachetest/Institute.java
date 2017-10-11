package hibernate.cachetest;

import javax.persistence.*;

@Entity
@Table(name="institute")
public class Institute {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name", length = 255)
    private String name;

    public Institute(String name) {
        this.name = name;
    }

    public Institute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Institute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
