import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "LinkedPurchaseList")
@Data
public class LinkedPurchaseList
{
    @EmbeddedId
    private LinkedPurchaseListID id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "student_name")
    private String studentName;

    private int price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Embeddable
    @Data
    public static class LinkedPurchaseListID implements Serializable
    {
        @Column(name = "course_id", nullable = false)
        private int courseId;

        @Column(name = "student_id", nullable = false)
        private int studentId;

    }
}
