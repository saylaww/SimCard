package uz.pdp.lesson6tasks.dto;

import lombok.Data;
import uz.pdp.lesson6tasks.entity.USSD;

import java.util.Set;
@Data
public class PaketDto {

    private String description;

    private Integer price;

    private String name;

    private Integer detailId;

    private Set<USSD> ussdCodes;
}
