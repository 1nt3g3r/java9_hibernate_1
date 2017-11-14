package hibernate.cachetest;

import javax.persistence.*;

@Entity
@Table(name = "CatDetails")
public class CatDetails {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COLOR")
    private String color;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "catDetails")
    private Cat cat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }
}
