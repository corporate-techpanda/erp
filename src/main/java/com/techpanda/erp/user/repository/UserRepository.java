package com.techpanda.erp.user.repository;

import com.techpanda.erp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
    SELECT u
    FROM User u
    JOIN FETCH u.role
    JOIN FETCH u.branch
    WHERE u.email = :email
""")
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByBranchId(Long id);
}
