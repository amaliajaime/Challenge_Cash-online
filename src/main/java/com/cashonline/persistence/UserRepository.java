package com.cashonline.persistence;

import com.cashonline.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer>{

    User findById(int id);
    User findByEmail(String email);

}
