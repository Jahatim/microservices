package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/customer/")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    IAccountsService accountsService;

    CustomerController(IAccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/details")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestHeader(name = "timbank_traceid") String traceId, @RequestParam String mobileNumber) {
        LOGGER.info("Inside getCustomerDetails, traceId:{}", traceId);
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.fetchCustomerDetails(mobileNumber, traceId));
    }
}
