package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.service.IAccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/customer/")
public class CustomerController {

    IAccountsService accountsService;

    CustomerController(IAccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/details")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestParam String mobileNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(accountsService.fetchCustomerDetails(mobileNumber));
    }
}
