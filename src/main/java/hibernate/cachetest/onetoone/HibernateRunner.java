package hibernate.cachetest.onetoone;

import entity.Car;
import entity.Developer;
import entity.Project;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


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

        if (developer.getCars() != null){
            for (Car car : developer.getCars()) {
                session.saveOrUpdate(car);
            }
        }

        if (developer.getProjects() != null) {
            for(Project p: developer.getProjects()) {
                session.saveOrUpdate(p);
            }
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

    public Car randomCar() {
        Random random = new Random();
        Car car = new Car();
        car.setBrand(Float.toString(random.nextFloat()));
        car.setColor(Float.toString(random.nextFloat()));

        return car;
    }

    public Project randomProject() {
        Random random = new Random();
        Project project = new Project();
        project.setName("name-" + random.nextInt());
        project.setCost(random.nextInt(100000));
        return project;
    }

    public void setProjects(Developer developer, Project ... projects) {
        Set<Project> projectSet = new HashSet<Project>();
        for(Project p: projects) {
            projectSet.add(p);

            if (p.getDevelopers() == null) {
                p.setDevelopers(new HashSet<Developer>());
            }
            p.getDevelopers().add(developer);
        }
        developer.setProjects(projectSet);
    }

    public void setDevelopers(Project project, Developer ... developers) {
        Set<Developer> developerSet = new HashSet<Developer>();
        for(Developer d: developers) {
            developerSet.add(d);

            if (d.getProjects() == null) {
                d.setProjects(new HashSet<Project>());
            }
            d.getProjects().add(project);
        }
        project.setDevelopers(developerSet);
    }


    public static void main(String[] args) {
        HibernateRunner hibernateRunner = new HibernateRunner();

        List<Developer> developers = new ArrayList<Developer>();
        for(int i = 0; i < 20000; i++) {
            developers.add(hibernateRunner.randomDeveloper());
        }

        hibernateRunner.createDevelopers(developers);

//        hibernateRunner.printProjectCostWithDevelopers();
       // System.out.println(hibernateRunner.selectProjectSum());
        //
//        for(int i = 0; i < 10; i++) {
//            hibernateRunner.saveProject(hibernateRunner.randomProject());
//        }
        //
//        Project project = hibernateRunner.randomProject();
//
//        Developer developer = hibernateRunner.randomDeveloper();
//
//        //Список проектов
//        Set<Project> projects = new HashSet<Project>();
//        projects.add(project);
//
//        //Список разработчиков
//        Set<Developer> developers = new HashSet<Developer>();
//        developers.add(developer);
//
//        //Связь
//        project.setDevelopers(developers);
//        developer.setProjects(projects);
//
//        //Сохраняем
//        hibernateRunner.createDeveloper(developer);


//        Project p1 = hibernateRunner.randomProject();
//        Project p2 = hibernateRunner.randomProject();
//        Project p3 = hibernateRunner.randomProject();
//        Developer dev1 = hibernateRunner.randomDeveloper();
//        Developer dev2 = hibernateRunner.randomDeveloper();
//        Developer dev3 = hibernateRunner.randomDeveloper();
//        hibernateRunner.setDevelopers(p1, dev1, dev3);
//        hibernateRunner.setDevelopers(p2, dev2, dev3);
//        hibernateRunner.setDevelopers(p3, dev1, dev2, dev3);

    }

    private long selectProjectSum() {
        String hqlQuery = "select sum(cost) from Project";

        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hqlQuery);

        long result = (Long) query.uniqueResult();

        return result;
    }

    private void printProjectCostWithDevelopers() {
        String sqlQuery = "select id, cost from project where\n" +
                "id in \n" +
                "(select project_id from project_developer)";

        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(sqlQuery);

        List<Object[]> rows = (List<Object[]>) query.list();

        for(Object[] row: rows) {
            BigInteger id = (BigInteger) row[0];
            int cost = (Integer) row[1];

            System.out.println(id + ", " + cost);
        }
    }

    private void saveProject(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(project);
        transaction.commit();
        session.close();
    }

    private void createDevelopers(List<Developer> developers) {
        Session session = sessionFactory.openSession();
        session.setFlushMode(FlushMode.MANUAL);

        Transaction transaction = session.beginTransaction();
        for(int i = 0; i < developers.size(); i++) {
            session.save(developers.get(i));

            if (i%100 == 0) {
                System.out.println(i);

                session.flush();
                transaction.commit();
                transaction = session.beginTransaction();

                session.clear();
            }

        }

        //transaction.commit();
        session.close();
    }

}
