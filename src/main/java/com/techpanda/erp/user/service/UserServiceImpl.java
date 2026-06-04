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
import com.techpanda.erp.user.dto.UpdateUserStatusRequest;
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

        if ("HO001".equals(branch.getBranchCode())
                && targetRole != RoleType.SUPER_ADMIN) {

            throw new BranchAccessDeniedException(
                    "Only SUPER_ADMIN can belong to Head Office branch"
            );
        }

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
                savedUser.getEnabled(),
                savedUser.getCreatedBy().getFirstName()
        );
    }

    @Override
    public List<UserResponse> getUsers() {
//        Getting the current logged in user
        CustomUserDetails userDetails = securityUtils.getCurrentUser();
        User currentUser = userDetails.getUser();
        RoleType currentRole = RoleType.valueOf(currentUser.getRole().getName());

        List<User> users;
        List<UserResponse> userResponses = new ArrayList<>();

        if(currentRole.equals(RoleType.SUPER_ADMIN)) {
//        Can See all the datas form all the branch
            users = userRepository.findAll();

        }else if(currentRole.equals(RoleType.ADMIN)) {
//            Can see all the user from the specific logged in branch
            users = userRepository.findByBranchId(currentUser.getBranch().getId());
        }else {
            throw new UnauthorizedAccess("You are not allowed to get users from this end point");
        }

        return users.stream()
                .map(user-> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getEmail(),
                        user.getRole().getName(),
                        user.getBranch().getName(),
                        user.getEnabled(),
                        user.getCreatedBy() != null ? user.getCreatedBy().getFirstName() : "SYSTEM"
                )
            ).toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        CustomUserDetails userDetails = securityUtils.getCurrentUser();
        User currentUser = userDetails.getUser();
        RoleType currentRole = RoleType.valueOf(currentUser.getRole().getName());
        User targetUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("No User found for the given ID : " + id)
        );

        if(currentRole.equals(RoleType.SUPER_ADMIN)) {
            return mapToResponse(targetUser);
        }

        // ADMIN can view only same branch users
        if (currentRole == RoleType.ADMIN) {
            if (!currentUser.getBranch().getBranchCode()
                    .equals(
                            targetUser.getBranch().getBranchCode()
                    )) {

                throw new UnauthorizedAccess(
                        "You are not allowed to access this user"
                );
            }

            return mapToResponse(targetUser);
        }

        throw new UnauthorizedAccess(
                "You are not allowed to access this user"
        );
    }



    private UserResponse mapToResponse(
            User user
    ) {

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getEmail(),
                user.getRole().getName(),
                user.getBranch().getName(),
                user.getEnabled(),
                user.getCreatedBy() != null ? user.getCreatedBy().getFirstName() : "SYSTEM"
        );
    }

    @Override
    public UserResponse updateUserStatus(Long id, UpdateUserStatusRequest updateUserStatusRequest) {
        User currentUser =
                securityUtils.getCurrentUser().
                        getUser();
        RoleType currentRole =
                RoleType.valueOf(
                        currentUser.getRole().getName()
                );

        User targetUser =
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new UserNotFoundException(
                                        "User not found  for the given ID : " + id
                                )
                        );

        if (targetUser.getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccess(
                    "You cannot Disable Yourself"
            );
        }

        if(targetUser.getRole().getName().equals(RoleType.SUPER_ADMIN.name())) {
            // Only SUPER_ADMIN can manage SUPER_ADMIN
            if (currentRole != RoleType.SUPER_ADMIN) {
                throw new UnauthorizedAccess(
                        "You cannot manage SUPER_ADMIN users"
                );
            }
            // Cannot disable yourself
//            if (targetUser.getId()
//                    .equals(currentUser.getId())) {
//
//                throw new UnauthorizedAccess(
//                        "You cannot disable your own account"
//                );
//            }
        }
        targetUser.setEnabled(updateUserStatusRequest.enabled());
        User savedUser = userRepository.save(targetUser);
        return mapToResponse(savedUser);
    }

}
