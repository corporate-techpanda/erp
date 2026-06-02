package com.techpanda.erp.user.permission;

import com.techpanda.erp.role.entity.Role;
import com.techpanda.erp.role.enums.RoleType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserCreationPermission {
    private UserCreationPermission(){

    }

    private static final Map<RoleType, Set<RoleType>>
                        ALLOWED_CREATIONS = Map.of(
                                RoleType.SUPER_ADMIN,
                                Set.of(
                                        RoleType.ADMIN,
                                        RoleType.SALES_HEAD,
                                        RoleType.TELECALLER_HEAD,
                                        RoleType.TELECALLER,
                                        RoleType.COUNSELLOR,
                                        RoleType.ACADEMIC_HEAD,
                                        RoleType.TRAINER,
                                        RoleType.ACCOUNTS
                                    ),
                                RoleType.ADMIN,
                                Set.of(
                                        RoleType.SALES_HEAD,
                                        RoleType.TELECALLER_HEAD,
                                        RoleType.TELECALLER,
                                        RoleType.COUNSELLOR,
                                        RoleType.ACADEMIC_HEAD,
                                        RoleType.TRAINER,
                                        RoleType.ACCOUNTS
                                )
//                                RoleType.SALES_HEAD,
//                                Set.of(
//                                        RoleType.TELECALLER_HEAD,
//                                        RoleType.TELECALLER,
//                                        RoleType.COUNSELLOR
//                                ),

//                                RoleType.TELECALLER_HEAD,
//                                Set.of(
//                                        RoleType.TELECALLER
//                                ),

//                                RoleType.ACADEMIC_HEAD,
//                                Set.of(
//                                        RoleType.TRAINER
//                                )
            );
    public static boolean canCreate(
            RoleType creator,
            RoleType targetRole
    ) {

        return ALLOWED_CREATIONS
                .getOrDefault(
                        creator,
                        Set.of()
                )
                .contains(targetRole);
    }
}
