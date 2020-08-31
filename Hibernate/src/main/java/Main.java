import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


import java.util.List;


public class Main {


    public static void main(String[] args)
    {
        Connection connection = Connection.getInstance();
        Session session = connection.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();


        Course course = session.get(Course.class, 3);
        List<Student> studentList2 = course.getStudents();
        studentList2.forEach(st -> System.out.println(st.getRegistrationDate()));

        Course c = session.get(Course.class, 3);
        Teacher teacherList = c.getTeacher();
        System.out.println(teacherList.getName() + " | " + teacherList.getAge());

        Query queryCourse = session.createQuery("FROM Course WHERE id = 2");
        List<Course> courseList = queryCourse.list();
        courseList.forEach(courses -> System.out.println("Course = " + courses.getId() + " | Course_name: " + course.getName() + " | Teacher = " + courses.getTeacher().getName()));

        Query queryStudent = session.createQuery("FROM Student WHERE id = 4");
        List<Student> studentList = queryStudent.list();
        studentList.forEach(student -> System.out.println(student.getRegistrationDate() ));

        Query querySubscription = session.createQuery("FROM Subscription WHERE student_id = 3");
        List<Subscription> subscriptionList = querySubscription.list();
        subscriptionList.forEach(subscription -> {

                System.out.println(subscription.getSubscriptionDate());
                });



        transaction.commit();
        session.close();
    }

}

class Connection
{
    private static volatile Connection instance;
    private static final String XML_FILE = "hibernate.cfg.xml";

    private static SessionFactory sessionFactory;

    private Connection()
    {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(XML_FILE).build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public static Connection getInstance()
    {
        if (instance == null) {
            synchronized (Connection.class) {
                if (instance == null)
                    instance = new Connection();
            }
        }
        return instance;
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}