package hibernate.cachetest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HibernateTestCache {
    public static final int BATCH_SIZE = 50;

    private static long time;
    private SessionFactory sessionFactory;

    public HibernateTestCache() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void clearAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DELETE FROM STUDENT").executeUpdate();
        session.createSQLQuery("DELETE FROM INSTITUTE").executeUpdate();
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

    public Institute getInstituteById(int id) {
        Session session = sessionFactory.openSession();
        Institute result = (Institute) session.get(Institute.class, id);
        session.close();
        return result;
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

    public void addStudents(Institute institute, int amount) {
        Set<Student> students = new HashSet<Student>();

        for(int i = 0; i < amount; i++) {
            Student student = new Student("Name " + i, "Last name " + i);
            students.add(student);
        }

        institute.setStudents(students);
    }

    public List<Integer> getInstitutesIds() {
        Session session = sessionFactory.openSession();
        List<Integer> result = session.createQuery("select id from Institute").setCacheable(true).list();
        session.close();
        return result;
    }

    public int getFirstInstitutedId() {
        return getInstitutesIds().get(0);
    }

    public void readFirstInsituteInDifferentSessions() {
        int id = getFirstInstitutedId();

        Session session = sessionFactory.openSession();
            session.get(Institute.class, id);
        session.close();

        session = sessionFactory.openSession();
            session.get(Institute.class, id);
        session.close();
    }

    public void readFirstInstituteInTheSameSession() {
        int id = getFirstInstitutedId();

        Session session = sessionFactory.openSession();
            session.get(Institute.class, id);
            session.get(Institute.class, id);
        session.close();
    }

    private static void startTimer() {
        time = System.currentTimeMillis();
    }

    private static void stopTimer() {
        long end = System.currentTimeMillis();
        System.out.println("EXECUTE TIME: " + (end - time));
    }

    public static void main( String[] args ) {
        //demonstrateLevelOneCacheWork();
        //demonstrateLevelTwoCacheWork();
        //demonstrateLevelTwoDependenciesCacheWork();
        demonstrateBatching();
    }

    private static void demonstrateLevelOneCacheWork() {
        HibernateTestCache app = new HibernateTestCache();

        app.clearAll();
        app.addInstitutes(1);

        System.out.println();
        System.out.println("Begin level one cache DIFFERENT sessions");
        app.readFirstInsituteInDifferentSessions();
        System.out.println("End level one cache DIFFERENT sessions");

        System.out.println();

        System.out.println("Begin level one cache THE SAME session");
        app.readFirstInstituteInTheSameSession();
        System.out.println("End level one cache THE SAME session");
    }

    private static void demonstrateLevelTwoCacheWork() {
        HibernateTestCache app = new HibernateTestCache();

        app.clearAll();
        app.addInstitutes(1);

        System.out.println();
        System.out.println("Begin level two cache DIFFERENT sessions");
        app.readFirstInsituteInDifferentSessions();
        System.out.println("End level two cache DIFFERENT sessions");
    }

    private static void demonstrateLevelTwoDependenciesCacheWork() {
        HibernateTestCache app = new HibernateTestCache();

        app.clearAll();

        Institute institute = new Institute("Test institute");
        app.addStudents(institute, 1);
        app.addInstitute(institute);

        System.out.println();
        System.out.println("Begin level two cache DIFFERENT sessions");
        startTimer();
        for(int i = 0; i < 50000; i++) {
            app.readFirstInsituteInDifferentSessions();
        }
        stopTimer();
        System.out.println("End level two cache DIFFERENT sessions");
    }

    private static void demonstrateBatching() {
        HibernateTestCache app = new HibernateTestCache();
        app.clearAll();



        Session session = app.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        startTimer();

        for(int i = 0; i < 10000; i++) {

            Institute institute = new Institute("Institute " + i);
            app.addStudents(institute, 1);
            session.save(institute);

            if (i%BATCH_SIZE == 0) {
                session.flush();
                session.clear();
            }

            if (i%1000 == 0) {
                System.out.println(i);
            }
        }

        transaction.commit();
        session.close();

        stopTimer();
    }
}