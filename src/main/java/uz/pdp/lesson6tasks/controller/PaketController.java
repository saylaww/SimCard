package uz.pdp.lesson6tasks.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson6tasks.dto.PaketDto;
import uz.pdp.lesson6tasks.entity.Paket;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.service.PaketService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/services")
public class PaketController {

    final PaketService paketService;

    public PaketController(PaketService paketService) {
        this.paketService = paketService;
    }

    @PostMapping
    public HttpEntity<?> addService(@RequestBody PaketDto paketDto) {
        Response apiResponse = paketService.addServices(paketDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping(value = "/{userId}")
    public HttpEntity<?> editService(@PathVariable Integer userId, @RequestBody PaketDto paketDto) {
        Response apiResponse = paketService.editService(userId, paketDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getServiceList() {
        List<Paket> usersByPage = paketService.getServiceList();
        return ResponseEntity.ok(usersByPage);
    }

    // Get user by id
    @GetMapping(value = "/{serviceId}")
    public HttpEntity<?> getServiceById(@PathVariable Integer serviceId) {
        Response apiResponse = paketService.getServiceById(serviceId);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping(value = "/{serviceId}")
    public HttpEntity<?> deleteUser(@PathVariable Integer serviceId) {
        Response apiResponse = paketService.deleteService(serviceId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
