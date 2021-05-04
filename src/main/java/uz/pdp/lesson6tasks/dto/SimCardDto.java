package uz.pdp.lesson6tasks.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SimCardDto {

    private String countryIndex;
    private String code;
    private String number;
    private UUID customerId;
    private Integer tariffId;

}

