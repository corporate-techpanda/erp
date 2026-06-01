package com.techpanda.erp.data;

import com.techpanda.erp.branch.entity.Branch;
import com.techpanda.erp.branch.repository.BranchRepository;
import com.techpanda.erp.role.constants.RoleConstants;
import com.techpanda.erp.role.entity.Role;
import com.techpanda.erp.role.repository.RoleRepository;
import com.techpanda.erp.user.entity.User;
import com.techpanda.erp.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${erp.super.user.email}")
    private String superUserEmail;

    @Value("${erp.super.user.password}")
    private String superUserPassword;

    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail("superadmin@techpanda.com")) {
            return;
        }

        Role role = roleRepository
                .findByName(RoleConstants.SUPER_ADMIN)
                .orElseThrow();

        Branch branch = branchRepository
                .findByBranchCode("TP-01")
                .orElseThrow();

        User user = new User();

        user.setEmail(superUserEmail);
        user.setFirstName("Super");
        user.setLastName("Admin");
        user.setPassword(
                passwordEncoder.encode(superUserPassword)
        );

        user.setRole(role);
        user.setBranch(branch);
        user.setEnabled(true);

        userRepository.save(user);
    }
}