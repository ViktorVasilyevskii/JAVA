import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Subscriptions")
@IdClass(Subscription.SubscriptionID.class)
@Data
public class Subscription
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Data
    public static class SubscriptionID implements Serializable
    {
        private Course course;
        private Student student;
    }

}
