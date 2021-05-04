package uz.pdp.lesson6tasks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Double price;

    private Double transferPrice;

    private Integer duration;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
    private List<Details> details;

    @OneToOne
    private USSD ussd;

}
