package com.techpanda.erp.branch.service;

import com.techpanda.erp.branch.dto.BranchResponse;
import com.techpanda.erp.branch.dto.CreateBranchRequest;
import com.techpanda.erp.branch.dto.UpdateBranchRequest;
import com.techpanda.erp.branch.dto.UpdateBranchStatusRequest;

import java.util.List;

public interface BranchService {

    BranchResponse createBranch(CreateBranchRequest createBranchRequest); // To create a new branch

    List<BranchResponse> getBranches(); // To get all the branch

    BranchResponse getBranchById(Long  branchId); // to find a specific branch

    BranchResponse updateBranch( Long id, UpdateBranchRequest request); // Update branch name and Location

    BranchResponse updateBranchStatus( Long id, UpdateBranchStatusRequest request);

}
