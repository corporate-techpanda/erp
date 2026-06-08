package com.techpanda.erp.branch.dto;

public record BranchResponse(
        Long id,
        String name,
        String branchCode,
        String location,
        Boolean active
) {
}
