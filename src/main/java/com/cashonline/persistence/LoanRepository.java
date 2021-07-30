package com.cashonline.persistence;

import com.cashonline.persistence.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByUserId(int id);

    Page<Loan> findByUserId(int userId, Pageable pageable);

    void deleteByUserId (int id);


}

