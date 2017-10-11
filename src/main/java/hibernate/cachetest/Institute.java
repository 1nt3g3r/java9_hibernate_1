package hibernate.cachetest;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="INSTITUTE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Institute {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="NAME", length = 255)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institute", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Student> students;

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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Institute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                //", students='" + students + '\'' +
                '}';
    }
}
