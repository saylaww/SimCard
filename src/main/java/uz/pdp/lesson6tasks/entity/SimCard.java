package uz.pdp.lesson6tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String countIndex;

    private String code;

    private String number;

    @OneToOne
    private User user;

    private boolean status;

    @OneToOne
    private Tariff tariff;

    private Integer balance;
}
