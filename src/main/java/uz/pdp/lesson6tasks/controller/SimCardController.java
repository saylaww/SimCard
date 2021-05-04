package uz.pdp.lesson6tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lesson6tasks.dto.SimCardDto;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.service.SimCardService;

@RestController
@RequestMapping("/simcard")
public class SimCardController {

    @Autowired
    SimCardService simCardService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody SimCardDto simCardDto) {
        final Response response = simCardService.add(simCardDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
}
