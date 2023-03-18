package com.sanedge.myecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.sanedge.myecommerce.entity.ApplicationUser;

@Repository
public interface ApplicationUserRepository
        extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);
}
