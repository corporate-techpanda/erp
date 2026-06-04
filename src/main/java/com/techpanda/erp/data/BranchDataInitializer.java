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

        Branch headOffice = new Branch();
        headOffice.setName("Head Office - Only For Super Admin");
        headOffice.setBranchCode("HO001");
        headOffice.setLocation("Chennai HQ");
        headOffice.setActive(true);

        Branch tnagar = new Branch();
        tnagar.setName("T-Nagar");
        tnagar.setBranchCode("TP001");
        tnagar.setLocation("T-Nagar, Chennai");
        tnagar.setActive(true);

        branchRepository.save(headOffice);
        branchRepository.save(tnagar);
    }
}