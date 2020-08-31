import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Subscriptions")
@Data
@IdClass(SubscriptionID.class)
public class Subscription {

    @Id
    private Student student;
    @Id
    private Course course;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "subscription_date")
    private Date subscriptionDate;

}
