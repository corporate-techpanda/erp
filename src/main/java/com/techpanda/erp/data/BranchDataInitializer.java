package com.techpanda.erp.data;

import com.techpanda.erp.branch.entity.Branch;
import com.techpanda.erp.branch.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class BranchDataInitializer implements CommandLineRunner {

    private final BranchRepository branchRepository;

    @Override
    public void run(String... args) {

        if (branchRepository.count() > 0) {
            return;
        }

        Branch branch = new Branch();

        branch.setName("T-Nagar");
        branch.setBranchCode("TP-01");
        branch.setLocation("Tnagar - Chennai");
        branch.setActive(true);

        branchRepository.save(branch);
    }
}