package com.cashonline.controller;

import com.cashonline.dto.LoanDTO;
import com.cashonline.dto.PageDTO;
import com.cashonline.service.LoanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoanController {

    private Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private LoanService loanService;

    @PostMapping("/loans/add")
    public ResponseEntity createLoan(@RequestBody LoanDTO loanDTO){
        logger.info("-------------------SERVICE------------------------");
        logger.info("Call service >>createLoan<< ");
        loanService.addLoan(loanDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/loans")
    public ResponseEntity<PageDTO> getLoans(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue="0", required=false) int user_id){
        logger.info("-------------------SERVICE------------------------");
        logger.info("Call service >>getLoans<< ");

        return new ResponseEntity<>(loanService.getAllBy(page, size, user_id), HttpStatus.OK);
    }
}
