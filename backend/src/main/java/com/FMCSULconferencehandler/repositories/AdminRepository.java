package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findByLoginAndPass(String login, String pass);    // Check if login is UNIQUE

}
