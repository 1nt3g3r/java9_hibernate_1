package hibernate.cachetest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;

public class CatStorage {
    private SessionFactory sessionFactory;

    public CatStorage() {
        sessionFactory =
                new Configuration().configure().buildSessionFactory();
    }

    public void updateCat(Cat cat) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(cat);

        transaction.commit();
        session.close();
    }

    public Cat createCat(Cat cat) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(cat);

        transaction.commit();
        session.close();

        return cat;
    }

    public List<Cat> listCats() {
        Session session = sessionFactory.openSession();
        List<Cat> cats = session.createQuery("from Cat").list();
        session.close();
        return cats;
    }

    public void deleteCat(Cat cat) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(cat);

        transaction.commit();
        session.close();
    }

    public void deleteCat(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery("DELETE FROM Cat WHERE ID=" + id).executeUpdate();

        transaction.commit();
        session.close();
    }


    public static void main(String[] args) {
        CatStorage storage = new CatStorage();
//
//        Cat cat = new Cat();
//        cat.setFirstName("Tom");
//        cat.setBirthday(new Date());
//        cat.setWeight(5f);
//        cat = storage.createCat(cat);
//
//        cat.setFirstName("Jerry");
//        storage.updateCat(cat);
    }
}
