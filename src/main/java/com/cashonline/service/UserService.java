package com.cashonline.service;

import com.cashonline.controller.UserController;
import com.cashonline.dto.UserDTO;
import com.cashonline.exeptions.InvalidEmailException;
import com.cashonline.exeptions.UserNotFoundException;
import com.cashonline.mapper.UserMapper;
import com.cashonline.persistence.LoanRepository;
import com.cashonline.persistence.UserRepository;
import com.cashonline.persistence.entity.Loan;
import com.cashonline.persistence.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService{

    private Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private UserMapper userMapper;

    public UserDTO getUser(int id) {
        User user = userRepository.findById(id);
        List<Loan> loans = loanRepository.findByUserId(id);

        try{
            if(user==null){
                logger.info("Service >>getUser<< not found the user with this id");
                throw new UserNotFoundException(String.valueOf(id));
            }
        } catch (Exception e){
            logger.error("*EXCEPTION* Exception details "+ "[" + e.getMessage() + "]");
            throw e;
        }

        logger.info("Service >>getUser<< found the user with this information " +"[Id: "+ user.getId() + " | "
                + " Email: " + user.getEmail() + " | "
                +" Firstname: " + user.getFirstname() + " | "
                +" Lastname: " + user.getLastname() + " | "
                +" Loans: " + loans.size() +"]");

        logger.info("-HTTP STATUS OK-");

        return userMapper.createUserDTO(user, loans);
    }

    public void addUser(UserDTO userDTO) {

        try{
            if(!emailValidator.test(userDTO.getEmail())){
                logger.info("Service >>createUser<< FAIL to validate email. [Email: "+ userDTO.getEmail() + "]");
                throw new InvalidEmailException(userDTO.getEmail());
            }
        } catch(Exception e){
            logger.error("*EXCEPTION* Exception details "+ "[" + e.getMessage() + "]");
            throw e;
        }

        logger.info("Service >>createUser<< CREATE the user with this information " +"[" + "Email: " + userDTO.getEmail() + " | "
                +" Firstname: " + userDTO.getFirstname() + " | "
                +" Lastname: " + userDTO.getLastname() + "]");

        logger.info("-HTTP STATUS OK-");

        userRepository.save(userMapper.createUser(userDTO));
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id);
        try{
            if(user!=null){
                userRepository.deleteById(id);
                loanRepository.deleteByUserId(id);
            } else{
                logger.info("Service >>deleteUser<< FAIL to deleted the user with this information. " +
                        "[Id: "+ id + "]");
                throw new UserNotFoundException(String.valueOf(id));
            }
        } catch (Exception e){
            logger.error("*EXCEPTION* Exception details "+"[" + e.getMessage()+ "]");
            throw e;
        }

        logger.info("Service >>deleteUser<< DELETE the user with this information. " +
                "[Id: "+ id + "]");

        logger.info("-HTTP STATUS OK-");
    }

    public UserDTO getUserByEmail(String email){
        return userMapper.createUserDTO(userRepository.findByEmail(email));
    }

}
