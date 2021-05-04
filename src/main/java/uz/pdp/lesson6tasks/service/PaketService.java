package uz.pdp.lesson6tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson6tasks.dto.PaketDto;
import uz.pdp.lesson6tasks.entity.Paket;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.repository.PaketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaketService {

    @Autowired
    PaketRepository paketRepository;

    public Response addServices(PaketDto paketDto) {

        boolean existsByName = paketRepository.existsByName(paketDto.getName());
        if (existsByName)
            return new Response("The service Name already exist!", false);

        Paket paket = new Paket();
        paket.setDescription(paketDto.getDescription());
        paket.setPrice(paketDto.getPrice());
        paketDto.setName(paketDto.getName());

        paketRepository.save(paket);
        return new Response("New service added", false);
    }

    public Response editService(Integer paketId, PaketDto paketDto) {

        Optional<Paket> optionalServices = paketRepository.findById(paketId);
        if (optionalServices.isEmpty())
            return new Response("Invalid Service Id", false);

        boolean existsByName = paketRepository.existsByNameAndIdNot(paketDto.getName(), paketId);
        if (existsByName)
            return new Response("The service Name already exist!", false);

        Paket paket = optionalServices.get();
        paket.setDescription(paketDto.getDescription());
        paket.setPrice(paketDto.getPrice());
        paket.setName(paketDto.getName());

        paketRepository.save(paket);
        return new Response("Service successfully edited", false);
    }

    public List<Paket> getServiceList() {
        return paketRepository.findAll();
    }

    public Response getServiceById(Integer serviceId) {

        Optional<Paket> optionalServices = paketRepository.findById(serviceId);
        return optionalServices.map(services ->
                new Response("This is Service", true, services))
                .orElseGet(() -> new Response("Invalid Service Id", false));
    }

    public Response deleteService(Integer serviceId) {

        Optional<Paket> optionalServices = paketRepository.findById(serviceId);

        if (optionalServices.isEmpty())
            return new Response("Invalid Service Id", false);
        paketRepository.deleteById(serviceId);
        return new Response("Service successfully deleted", true);
    }
}
