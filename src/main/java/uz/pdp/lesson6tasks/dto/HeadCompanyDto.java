package uz.pdp.lesson6tasks.dto;

import lombok.Data;
import uz.pdp.lesson6tasks.entity.Address;
import uz.pdp.lesson6tasks.entity.User;

@Data
public class HeadCompanyDto {

    private User director;
    private Address address;

}
