package com.techpanda.erp.branch.dto;

public record CreateBranchRequest(
        String name,
        String branchCode,
        String location
) {
}
