package hibernate.cachetest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HibernateTestCache {
    private SessionFactory sessionFactory;

    public HibernateTestCache() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
}
