package com.cashonline.service;

import com.cashonline.controller.UserController;
import com.cashonline.dto.LoanDTO;
import com.cashonline.dto.PageDTO;
import com.cashonline.dto.UserDTO;
import com.cashonline.exeptions.InvalidEmailException;
import com.cashonline.exeptions.UserNotFoundException;
import com.cashonline.mapper.UserMapper;
import com.cashonline.persistence.LoanRepository;
import com.cashonline.persistence.UserRepository;
import com.cashonline.persistence.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    public PageDTO getAllBy(int pageNumber, int pageSize, int userId) {
        int pageN = pageNumber-1;
        Pageable page = PageRequest.of(pageN, pageSize);
        if(userId == 0) {
            logger.info("Service >>getLoans<< SEARCH all loans");
            logger.info("Params PAGE: "+pageNumber + " SIZE: "+pageSize);

            logger.info("-HTTP STATUS OK-");

            return userMapper.createPageDTO(loanRepository.findAll(page));
        }
        else {
            logger.info("Service >>getLoans<< SEARCH loans with user id " + userId);
            logger.info("Params PAGE: "+pageNumber + " SIZE: "+pageSize+" USER ID: " + userId);

            logger.info("-HTTP STATUS OK-");

            return userMapper.createPageDTO(loanRepository.findByUserId(userId, page));
        }
    }


    public void addLoan(LoanDTO loanDTO) {
        int userID = (int)loanDTO.getUserId();

        User user = userRepository.findById(userID);
        try{
            if(user==null){
                logger.info("Service >>createLoan<< FAIL to create loan. [User ID: "+ userID + "]");
                throw new UserNotFoundException(String.valueOf(userID));
            }
        } catch (Exception e){
            logger.error("*EXCEPTION* Exception details "+"[" + e.getMessage()+ "]");
            throw e;
        }

        logger.info("Service >>createLoan<< CREATE loan with this information " +"[" + "Total: " + loanDTO.getTotal() + " | "
                +" User ID: " + loanDTO.getUserId() +
                "]");

        logger.info("-HTTP STATUS OK-");
        loanRepository.save(userMapper.createLoan(loanDTO));
    }
}
