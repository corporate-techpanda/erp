package com.techpanda.erp.user.service;

import com.techpanda.erp.branch.entity.Branch;
import com.techpanda.erp.branch.repository.BranchRepository;
import com.techpanda.erp.common.exception.*;
import com.techpanda.erp.role.entity.Role;
import com.techpanda.erp.role.enums.RoleType;
import com.techpanda.erp.role.repository.RoleRepository;
import com.techpanda.erp.security.model.CustomUserDetails;
import com.techpanda.erp.security.util.SecurityUtils;
import com.techpanda.erp.user.dto.CreateUserRequest;
import com.techpanda.erp.user.dto.UserResponse;
import com.techpanda.erp.user.entity.User;
import com.techpanda.erp.user.permission.UserCreationPermission;
import com.techpanda.erp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private BranchRepository branchRepository;
    final private PasswordEncoder passwordEncoder;
    final private SecurityUtils securityUtils;


    @Override
    public UserResponse createUser(CreateUserRequest request) {

        CustomUserDetails userDetails =
                securityUtils.getCurrentUser();

        User creator = userDetails.getUser();
        RoleType creatorRole =
                RoleType.valueOf(
                        creator.getRole().getName()
                );

        RoleType targetRole;
        try {
            targetRole = RoleType.valueOf(request.role());
        } catch (IllegalArgumentException ex) {
            throw new RoleNotFoundException(
                    "Invalid role: " + request.role()
            );
        }

        // Role hierarchy validation
        if (!UserCreationPermission.canCreate(
                creatorRole,
                targetRole
        )) {

            throw new UnauthorizedRoleCreationException(
                    "You are not allowed to create this role"
            );
        }

        // Branch validation
        if (!creatorRole.equals(RoleType.SUPER_ADMIN)
                &&
                !Objects.equals(
                        creator.getBranch().getId(),
                        request.branchId()
                )) {

            throw new BranchAccessDeniedException(
                    "You can create users only in your branch"
            );
        }

        // Email validation
        if (userRepository.existsByEmail(
                request.email()
        )) {

            throw new DuplicateEmailException(
                    "Email already exists"
            );
        }

        // Fetch Role
        Role role = roleRepository
                .findByName(request.role())
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role not found"
                        )
                );

        Branch branch = branchRepository
                .findById(Math.toIntExact(request.branchId()))
                .orElseThrow(() ->
                        new BranchNotFoundException(
                                "Branch not found"
                        )
                );

        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setMobileNumber(request.mobileNumber());
        user.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );
        user.setRole(role);
        user.setBranch(branch);
        user.setEnabled(true);
        user.setCreatedBy(creator);

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getEmail(),
                savedUser.getRole().getName(),
                savedUser.getBranch().getName(),
                savedUser.getEnabled()
        );
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getFirstName(),
                    user.getEmail(),
                    user.getRole().getName(),
                    user.getBranch().getName(),
                    user.getEnabled()
            );
            userResponses.add(userResponse);
        }
        return userResponses;
    }
}
