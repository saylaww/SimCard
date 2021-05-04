package uz.pdp.lesson6tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lesson6tasks.dto.BranchCompanyDto;
import uz.pdp.lesson6tasks.dto.HeadCompanyDto;
import uz.pdp.lesson6tasks.entity.Response;
import uz.pdp.lesson6tasks.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @PostMapping("/addBranch")
    public HttpEntity<?> addCompanyBranch(@RequestBody BranchCompanyDto branchCompanyDto) {
        final Response response = companyService.addCompanyBranch(branchCompanyDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    @PostMapping("/add")
    public HttpEntity<?> addHeadCompany(@RequestBody HeadCompanyDto headCompanyDto) {
        final Response response = companyService.addHeadCompany(headCompanyDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
}
