import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teachers")
@Data
public class Teacher
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int salary;

    private int age;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;
}
