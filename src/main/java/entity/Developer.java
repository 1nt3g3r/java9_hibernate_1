package entity;





import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table (name = "developer")
public class Developer {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String speciality;

    @Column
    private BigDecimal salary;

    @OneToOne
    @JoinColumn (name = "cat_id", unique = true)
    private Cat cat;

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = BigDecimal.valueOf(salary);
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", surname='" + surname + '\'' +
                ", speciality='" + speciality + '\'' +
                ", salary=" + salary +
                ", cat=" + cat +
                '}';
    }
}
