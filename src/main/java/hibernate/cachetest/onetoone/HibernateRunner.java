package hibernate.cachetest.onetoone;

import entity.Cat;
import entity.Developer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;


public class HibernateRunner {
    private SessionFactory sessionFactory;

    public HibernateRunner() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        System.out.println("sessionfactory initialized sucsessfuly");
    }

    public void createDeveloper(Developer developer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        if (developer.getCat() != null) {
            session.saveOrUpdate(developer.getCat());
        }

        session.save(developer);
        transaction.commit();
        session.close();
    }

    public Developer getById(long id) {
        Session session = sessionFactory.openSession();
        Developer developer = (Developer) session.get(Developer.class, id);
        session.close();
        return developer;
    }

    public List<Developer> getAll() {
        Session session = sessionFactory.openSession();
        List<Developer> developers =
                session.createQuery("from Developer d where d.salary >=0.4").list();

        session.close();
        return developers;
    }

    public void updateDeveloper(Developer developer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(developer);
        transaction.commit();
        session.close();
    }

    public void deleteDeveloper(Developer developer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(developer);
        transaction.commit();
        session.close();
    }

    public Developer randomDeveloper() {
        Random random = new Random();
        Developer developer = new Developer();
        developer.setFirstname(Float.toString(random.nextFloat()));
        developer.setSurname(Float.toString(random.nextFloat()));
        developer.setSpeciality(Float.toString(random.nextFloat()));
        developer.setSalary(BigDecimal.valueOf(random.nextDouble()));
        developer.setLastname(Float.toString(random.nextFloat()));

        return developer;
    }


    public static void main(String[] args) {
        HibernateRunner hibernateRunner = new HibernateRunner();

//        Developer developer = hibernateRunner.getById(2L);
//        System.out.println(developer);
        Cat cat = new Cat();
        cat.setName("Vasya33");
        cat.setSex(true);

        Developer developer1 = hibernateRunner.randomDeveloper();
        developer1.setCat(cat);
        cat.setDeveloper(developer1);
        hibernateRunner.createDeveloper(developer1);

        System.out.println(cat.getId());

        Developer developer2 = hibernateRunner.randomDeveloper();
        developer2.setCat(cat);
        cat.setDeveloper(developer2);
        hibernateRunner.createDeveloper(developer2);


//
//        Developer developer = hibernateRunner.randomDeveloper();
//        developer.setCat(cat);
//        cat.setDeveloper(developer);
//
//        hibernateRunner.createDeveloper(developer);

//        Developer developer = hibernateRunner.getById(2L);
//        System.out.println("Before -- " + developer);
//        developer.setSalary(400000L);
//        hibernateRunner.updateDeveloper(developer);
//        developer = hibernateRunner.getById(2L);
//        System.out.println("After -- " + developer);
    }

}
