package com.techpanda.erp.branch.repository;

import com.techpanda.erp.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);
    Optional<Branch> findByBranchCode(String branchCode);

    boolean existsByName(String name);
    boolean existsByBranchCode(String branchCode);
}
