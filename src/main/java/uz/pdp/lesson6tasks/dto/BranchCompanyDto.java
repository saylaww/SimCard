package uz.pdp.lesson6tasks.dto;

import lombok.Data;
import uz.pdp.lesson6tasks.entity.Address;

import java.util.UUID;

@Data
public class BranchCompanyDto {

    private UUID directorId;
    private Address address;
    private Integer parentId;

}
