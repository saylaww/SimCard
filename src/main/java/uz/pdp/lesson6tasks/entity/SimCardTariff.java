package uz.pdp.lesson6tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SimCardTariff {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private SimCard simCard;

    @ManyToOne
    private Tariff tariff;

    private boolean status = true;         // true - active  false - not active

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dateOfPurchase;      // Paket xarid qilingan sana va vaqt

    @Transient
    private Timestamp expireDate;
}
