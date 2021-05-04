package uz.pdp.lesson6tasks.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson6tasks.dto.TariffDto;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.entity.USSD;
import uz.pdp.lesson6tasks.service.TariffService;

@RestController
@RequestMapping("/tariff")
public class TariffController {

    TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody TariffDto tariffDto) {
        final Response response = tariffService.add(tariffDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/set")
    public HttpEntity<?> setTariff(@RequestParam Integer simCarId, @RequestBody USSD ussd) {
        final Response response = tariffService.setTariff(ussd, simCarId);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

}
