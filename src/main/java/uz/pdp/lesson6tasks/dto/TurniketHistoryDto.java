package uz.pdp.lesson6tasks.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TurniketHistoryDto {

    private Integer turniketId;
    private UUID userId;
    private Integer typeId;
}
