package com.techpanda.erp.data;

import com.techpanda.erp.role.entity.Role;
import com.techpanda.erp.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRole("SUPER_ADMIN", 8);
        createRole("ADMIN", 7);
        createRole("SALES_HEAD", 6);
        createRole("ACCOUNTS", 6);
        createRole("ACADEMIC_HEAD", 5);
        createRole("COUNSELLOR", 4);
        createRole("TELECALLER_HEAD",4);
        createRole("TRAINER", 2);
        createRole("TELECALLER", 1);
    }
    private void createRole(String roleName, int roleLevel) {
        if(roleRepository.existsByName(roleName)){
            return;
        }

        Role role = new Role();
        role.setName(roleName);
        role.setLevel(roleLevel);
        role.setActive(true);

        roleRepository.save(role);
    }
}
