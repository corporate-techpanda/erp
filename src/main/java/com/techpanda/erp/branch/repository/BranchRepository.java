package com.techpanda.erp.branch.repository;

import com.techpanda.erp.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Optional<Branch> findByName(String name);
    Optional<Branch> findByBranchCode(String branchCode);
}
