package uz.pdp.lesson6tasks.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TariffDto {

    private String title;
    private Double price;
    private Double transferPrice;
    private Integer duration;
    private Set<Integer> detailsId;
    private Integer USSDId;

}
