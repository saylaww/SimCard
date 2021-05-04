package uz.pdp.lesson6tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lesson6tasks.dto.BranchCompanyDto;
import uz.pdp.lesson6tasks.dto.HeadCompanyDto;
import uz.pdp.lesson6tasks.entity.*;
import uz.pdp.lesson6tasks.entity.enums.RoleName;
import uz.pdp.lesson6tasks.repository.*;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    public Response addHeadCompany(HeadCompanyDto headCompanyDto) {
        Company company = new Company();

        Address address = new Address();
        address.setStreet(headCompanyDto.getAddress().getStreet());
        final Region region = regionRepository.save(headCompanyDto.getAddress().getRegion());

        address.setRegion(region);
        address.setHomeNumber(headCompanyDto.getAddress().getHomeNumber());
        final Address savedAddress = addressRepository.save(address);

        User user = new User();
        final User director = headCompanyDto.getDirector();
        user.setFirstName(director.getFirstName());
        user.setLastName(director.getLastName());
        user.setEmail(director.getEmail());
        user.setPassword(passwordEncoder.encode(director.getPassword()));
        user.setRole(director.getRole());
        user.setCompany(null);
        user.setPassportId(director.getPassportId());
        user.setPhysicalBody(director.isPhysicalBody());
        final User save = userRepository.save(user);

        company.setAddress(savedAddress);
        company.setDirector(save);
        company.setParent(null);
        final Company savedCompany = companyRepository.save(company);

        user.setCompany(savedCompany);
        userRepository.save(user);
        return new Response("Head Company saved", true, savedCompany);
    }

    public Response addCompanyBranch(BranchCompanyDto branchCompanyDto) {
        final Role role = getRole();

        if (role.getRoleName().equals(RoleName.DIRECTOR)) {
            Company company = new Company();
            final Optional<User> optionalUser = userRepository.findById(branchCompanyDto.getDirectorId());
            if (optionalUser.isEmpty()) {
                return new Response("Director not found", false);
            }
            final User director = optionalUser.get();
            company.setDirector(director);
            Address address = new Address();
            address.setHomeNumber(branchCompanyDto.getAddress().getHomeNumber());
            address.setRegion(branchCompanyDto.getAddress().getRegion());
            address.setStreet(branchCompanyDto.getAddress().getStreet());
            final Address savedAddress = addressRepository.save(address);
            company.setAddress(savedAddress);
            final Company save = companyRepository.save(company);
            regionRepository.save(branchCompanyDto.getAddress().getRegion());
            return new Response("Company branch saved", true, save);
        } else {
            return new Response("You don't have privileges", false);
        }
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
