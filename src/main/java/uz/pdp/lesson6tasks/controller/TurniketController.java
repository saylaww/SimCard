package uz.pdp.lesson6tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lesson6tasks.dto.TurniketDto;
import uz.pdp.lesson6tasks.dto.TurniketHistoryDto;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.entity.TurniketHistory;
import uz.pdp.lesson6tasks.service.TurniketService;

@RestController
@RequestMapping("/turniket")
public class TurniketController {

    @Autowired
    TurniketService turniketService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody TurniketDto turniketDto) {
        final Response response = turniketService.add(turniketDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/use")
    public HttpEntity<?> use(@RequestBody TurniketHistoryDto turniketHistory) {
        final Response response = turniketService.use(turniketHistory);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
}
