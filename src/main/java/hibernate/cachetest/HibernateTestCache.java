package hibernate.cachetest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateTestCache {
    private static long time;
    private SessionFactory sessionFactory;

    public HibernateTestCache() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void clearAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DELETE FROM institute").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void addInstitute(Institute institute) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(institute);
        transaction.commit();
        session.close();
    }

    public void addInstitutes(int amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < amount; i++) {
            session.persist(new Institute("I" + i));
        }
        transaction.commit();
        session.close();
    }

    public List<Integer> getInstitutesIds() {
        Session session = sessionFactory.openSession();
        List<Integer> result = session.createSQLQuery("select id from institute").list();
        session.close();
        return result;
    }

    public int getFirstInstitutedId() {
        return getInstitutesIds().get(0);
    }

    public void levelOneCacheWithDifferentSessions() {
        int id = getFirstInstitutedId();

        Session session = sessionFactory.openSession();
            session.get(Institute.class, id);
        session.close();

        session = sessionFactory.openSession();
            session.get(Institute.class, id);
        session.close();
    }

    public void levelOneCacheInTheSameSession() {
        int id = getFirstInstitutedId();

        Session session = sessionFactory.openSession();
            session.get(Institute.class, id);
            session.get(Institute.class, id);
        session.close();
    }

    private static void startTimer() {
        time = System.currentTimeMillis();
    }

    private static void endTimer() {
        long end = System.currentTimeMillis();
        System.out.println("EXECUTE TIME: " + (end - time));
    }

    public static void main( String[] args ) {
        demonstrateLevelOneCacheWork();
    }

    private static void demonstrateLevelOneCacheWork() {
        HibernateTestCache app = new HibernateTestCache();

        app.clearAll();
        app.addInstitutes(1);

        System.out.println();
        System.out.println("Begin level one cache DIFFERENT sessions");
        app.levelOneCacheWithDifferentSessions();
        System.out.println("End level one cache DIFFERENT sessions");

        System.out.println();

        System.out.println("Begin level one cache THE SAME session");
        app.levelOneCacheInTheSameSession();
        System.out.println("End level one cache THE SAME session");
    }
}