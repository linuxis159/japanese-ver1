package com.study.japanese.repository;

import com.study.japanese.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

    Optional<User> findById(String username);

    Optional<User> findByEmail(String email);






}
