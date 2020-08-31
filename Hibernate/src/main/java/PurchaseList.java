import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "PurchaseList")
@Data
public class PurchaseList
{
    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    private int price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
