package com.login.repository;

import com.login.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    // Find by userid
    Optional<Login> findByUserid(String userid);

    // Find by mobile
    Optional<Login> findByMobile(String mobile);


}