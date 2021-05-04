package uz.pdp.lesson6tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.lesson6tasks.dto.SimCardDto;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.entity.SimCard;
import uz.pdp.lesson6tasks.entity.Tariff;
import uz.pdp.lesson6tasks.entity.User;
import uz.pdp.lesson6tasks.entity.enums.RoleName;
import uz.pdp.lesson6tasks.repository.RoleRepository;
import uz.pdp.lesson6tasks.repository.SimCardRepository;
import uz.pdp.lesson6tasks.repository.TariffRepository;
import uz.pdp.lesson6tasks.repository.UserRepository;

import java.util.Optional;

@Service
public class SimCardService {

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public Response add(SimCardDto simCardDto) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return new Response("Anonymous User", false);
        }
        final User u = (User) authentication.getPrincipal();

        if (!u.getRole().equals(roleRepository.findByRoleName(RoleName.PHONE_NUMBER_MANAGER).get())) {
            return new Response("Only Phone Number Manager can add SimCard", false);
        }
        SimCard simCard = new SimCard();

        if (!simCardDto.getCountryIndex().equals("+998")) {
            return new Response("Country index should be +998", false);
        }

        simCard.setCountIndex(simCardDto.getCountryIndex());
        simCard.setCode(simCardDto.getCode());

        final boolean exists = simCardRepository.existsByNumber(simCardDto.getNumber());
        if (exists) {
            return new Response("Number already exists", false);
        }

        final Optional<Tariff> optionalTariff = tariffRepository.findById(simCardDto.getTariffId());
        if (optionalTariff.isEmpty()) {
            return new Response("Tariff id not found", false);
        }
        final Tariff tariff = optionalTariff.get();
        simCard.setTariff(tariff);

        simCard.setNumber(simCardDto.getNumber());
        simCard.setStatus(true);

        final Optional<User> optionalUser = userRepository.findById(simCardDto.getCustomerId());
        if (optionalUser.isEmpty()) {
            return new Response("", false);
        }
        final User user = optionalUser.get();

        simCard.setUser(user);

        final SimCard savedSimCard = simCardRepository.save(simCard);
        return new Response("SimCard saved!", true, savedSimCard);
    }
}

