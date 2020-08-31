import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class SubscriptionID implements Serializable {

    @OneToOne
    private Student student;

    @OneToOne
    private Course course;
}
