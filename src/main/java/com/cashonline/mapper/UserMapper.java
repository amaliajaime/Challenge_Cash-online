package com.cashonline.mapper;

import com.cashonline.dto.LoanDTO;
import com.cashonline.dto.PageDTO;
import com.cashonline.dto.UserDTO;
import com.cashonline.persistence.entity.Loan;
import com.cashonline.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public LoanDTO createLoanDTO(Loan loan){
        LoanDTO loanDTO = new LoanDTO(loan.getTotal(), loan.getUserId());
        loanDTO.setId(loan.getId());
        return loanDTO;
    }

    public Loan createLoan(LoanDTO loanDTO){
        Loan loan = new Loan();
        loan.setTotal(loanDTO.getTotal());
        loan.setUserId((int)loanDTO.getUserId());

        return loan;
    }

    public List<LoanDTO> createLoansDTO(List<Loan> loans){
        List<LoanDTO>loansDTO = new ArrayList();
        for (Loan l : loans) {
            loansDTO.add(createLoanDTO(l));
        }

        return loansDTO;
    }

    public PageDTO createPageDTO(Page<Loan>loans){
        PageDTO pageDTO = new PageDTO(createLoansDTO(loans.getContent()), loans.getNumber()+1, loans.getSize(), loans.getTotalElements());

        return pageDTO;
    }

    public UserDTO createUserDTO(User user) {
        List<LoanDTO>loans = new ArrayList<>();

        UserDTO userDTO = new UserDTO(
                user.getEmail(),
                user.getFirstname(),
                user.getLastname());

        userDTO.setId(user.getId());
        userDTO.setLoans(loans);

        return userDTO;
    }

    public UserDTO createUserDTO(User user , List<Loan>loans) {
        UserDTO userDTO = new UserDTO(
                user.getEmail(),
                user.getFirstname(),
                user.getLastname());

        userDTO.setId(user.getId());
        userDTO.setLoans(createLoansDTO(loans));

        return userDTO;
    }

    public User createUser(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());

        return user;
    }
}
