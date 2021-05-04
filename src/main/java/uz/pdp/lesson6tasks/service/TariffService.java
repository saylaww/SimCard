package uz.pdp.lesson6tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.lesson6tasks.controller.SimCardController;
import uz.pdp.lesson6tasks.dto.TariffDto;
import uz.pdp.lesson6tasks.entity.*;
import uz.pdp.lesson6tasks.entity.enums.RoleName;
import uz.pdp.lesson6tasks.repository.SimCardRepository;
import uz.pdp.lesson6tasks.repository.TariffRepository;
import uz.pdp.lesson6tasks.repository.USSDRepository;

import java.util.Optional;

@Service
public class TariffService {

    @Autowired
    USSDRepository ussdRepository;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    SimCardRepository simCardRepository;

    public Response add(TariffDto tariffDto) {
        Tariff tariff = new Tariff();

        tariff.setDuration(tariffDto.getDuration());
        tariff.setPrice(tariffDto.getPrice());
        tariff.setTitle(tariffDto.getTitle());
        tariff.setTransferPrice(tariffDto.getTransferPrice());

        final Optional<USSD> optionalUSSD =
                ussdRepository.findById(tariffDto.getUSSDId());
        if (optionalUSSD.isEmpty()) {
            return new Response("USSD code not found", false);
        }
        final USSD ussd = optionalUSSD.get();
        tariff.setUssd(ussd);
        final Tariff savedTariff = tariffRepository.save(tariff);
        return new Response("Tariff saved!", true);
    }

    public Response setTariff(USSD ussd, Integer simCardId) {
        final Optional<Tariff> optionalTariff =
                tariffRepository.findByUssd(ussd);
        if (optionalTariff.isEmpty()) {
            return new Response("Tariff not found", false);
        }
        final Tariff tariff = optionalTariff.get();

        final Role role = getRole();
        if (role.getRoleName().equals(RoleName.CUSTOMER) ||
                role.getRoleName().equals(RoleName.PHONE_NUMBER_MANAGER)) {
            final Optional<SimCard> optionalSimCard =
                    simCardRepository.findById(simCardId);
            if (optionalSimCard.isEmpty()) {
                return new Response("Sim Card not found!", false);
            }
            final SimCard simCard = optionalSimCard.get();
            simCard.setTariff(tariff);
            final SimCard savedSimCard = simCardRepository.save(simCard);
            return new Response(savedSimCard.getUser().getFirstName() + "'s tariff set/changed", true);
        }

        return null;
    }

    public Role getRole() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getPrincipal().equals("anonymousUser")) {
            final User user = (User) authentication.getPrincipal();
            return user.getRole();
        }
        return null;
    }

}
