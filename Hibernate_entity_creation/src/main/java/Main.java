import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main
{
    public static Session session = HibernateUtils.sessionFactory.openSession();
    public static Course courseId;
    public static Student studentId;

    public static void main(String[] args)
    {
        List<PurchaseList> purchaseLists = queryPurchaseList();
        Transaction transaction = session.beginTransaction();

        for(PurchaseList purchase : purchaseLists)
        {
            courseId = queryCourse(purchase);
            studentId = queryStudent(purchase);

            LinkedPurchaseList.LinkedPurchaseListID id = new LinkedPurchaseList.LinkedPurchaseListID();
            id.setCourseId(courseId.getId());
            id.setStudentId(studentId.getId());

            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setId(id);
            linkedPurchaseList.setCourseName(courseId.getName());
            linkedPurchaseList.setStudentName(studentId.getName());
            linkedPurchaseList.setSubscriptionDate(purchase.getSubscriptionDate());
            linkedPurchaseList.setPrice(purchase.getPrice());
            session.saveOrUpdate(linkedPurchaseList);
        }

        transaction.commit();
        session.close();

    }

    private static List<PurchaseList>  queryPurchaseList()
    {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PurchaseList> query = builder.createQuery(PurchaseList.class);
        Root<PurchaseList> root = query.from(PurchaseList.class);
        query.select(root);
        return session.createQuery(query).getResultList();
    }

    private static Course queryCourse(PurchaseList purchase)
    {
        Query course = session.createQuery("FROM " + Course.class.getSimpleName() + " WHERE name =:courseName");
        course.setParameter("courseName", purchase.getId().getCourseName());
        return (Course) course.getSingleResult();
    }

    private static Student queryStudent(PurchaseList purchase)
    {
        Query student = session.createQuery("FROM " + Student.class.getSimpleName() + " WHERE name =:studentName");
        student.setParameter("studentName", purchase.getId().getStudentName());
        return (Student) student.getSingleResult();
    }


}
