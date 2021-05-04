package uz.pdp.lesson6tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson6tasks.dto.TurniketDto;
import uz.pdp.lesson6tasks.dto.TurniketHistoryDto;
import uz.pdp.lesson6tasks.entity.*;
import uz.pdp.lesson6tasks.entity.enums.TurniketType;
import uz.pdp.lesson6tasks.repository.AddressRepository;
import uz.pdp.lesson6tasks.repository.TurniketHistoryRepository;
import uz.pdp.lesson6tasks.repository.TurniketRepository;
import uz.pdp.lesson6tasks.repository.UserRepository;

import java.util.Optional;

@Service
public class TurniketService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TurniketRepository turniketRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TurniketHistoryRepository turniketHistoryRepository;

    public Response add(TurniketDto turniketDto) {

        final Optional<Address> optionalAddress = addressRepository.findById(turniketDto.getLocationId());
        if (optionalAddress.isEmpty()) {
            return new Response("Address ID not found!", false);
        }
        final Address address = optionalAddress.get();
        Turniket turniket = new Turniket();
        turniket.setLocation(address);
        final Turniket savedTurniket = turniketRepository.save(turniket);
        return new Response("Turniket added!", true, savedTurniket);
    }

    public Response use(TurniketHistoryDto turniketHistoryDto) {
        TurniketHistory turniketHistory = new TurniketHistory();
        if (turniketHistoryDto.getTypeId() == 0)
            turniketHistory.setType(TurniketType.EXIT);
        turniketHistory.setType(TurniketType.ENTER);

        final boolean exists = userRepository.existsById(turniketHistoryDto.getUserId());
        if (!exists) {
            return new Response("User not found", false);
        }
        turniketHistory.setCreatedBy(turniketHistoryDto.getUserId());

        final Optional<Turniket> optionalTurniket = turniketRepository.findById(turniketHistoryDto.getTurniketId());
        if (optionalTurniket.isEmpty()) {
            return new Response("Turniket not found", false);
        }
        final Turniket turniket = optionalTurniket.get();
        turniketHistory.setTurniket(turniket);
        final TurniketHistory savedHistory = turniketHistoryRepository.save(turniketHistory);

        return new Response("Turniket history saved", true, savedHistory);
    }
}
