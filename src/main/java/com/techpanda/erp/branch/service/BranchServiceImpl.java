package com.techpanda.erp.branch.service;

import com.techpanda.erp.branch.dto.BranchResponse;
import com.techpanda.erp.branch.dto.CreateBranchRequest;
import com.techpanda.erp.branch.dto.UpdateBranchRequest;
import com.techpanda.erp.branch.dto.UpdateBranchStatusRequest;
import com.techpanda.erp.branch.entity.Branch;
import com.techpanda.erp.branch.repository.BranchRepository;
import com.techpanda.erp.common.exception.BranchAccessDeniedException;
import com.techpanda.erp.common.exception.BranchAlreadyExists;
import com.techpanda.erp.common.exception.BranchNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public BranchResponse createBranch(CreateBranchRequest createBranchRequest) {
        if(branchRepository.existsByName(
                createBranchRequest.name()
        )){
            throw new BranchAlreadyExists("Branch already exists with the same name!", HttpStatus.CONFLICT);
        }

        if(branchRepository.existsByBranchCode(createBranchRequest.branchCode())){
            throw new BranchAlreadyExists("Branch already exists with the same branchCode!", HttpStatus.CONFLICT);
        }

        Branch branch = new Branch();
        branch.setName(createBranchRequest.name());
        branch.setBranchCode(createBranchRequest.branchCode());
        branch.setLocation(createBranchRequest.location());
        branch.setActive(true);

        Branch savedBranch = branchRepository.save(branch);
        return mapToResponse(savedBranch); // Map branch to the Response DTO
    }

    @Override
    public List<BranchResponse> getBranches() {
        return branchRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BranchResponse getBranchById(Long branchId) {
        Optional<Branch> branch = branchRepository.findById(branchId);
        return branch.map(this::mapToResponse).orElseThrow(
                () -> new BranchNotFoundException("Branch not found with the specified id: " + branchId)
        );
    }

    @Override
    public BranchResponse updateBranch(Long id, UpdateBranchRequest request) {
        Branch branch = branchRepository.findById(id).orElseThrow(
                () -> new BranchNotFoundException("Branch not found with the specified id: " + id)
        );
        if("HO001".equals(
                branch.getBranchCode()
        )){
            throw new BranchAccessDeniedException("Head Office cannot be changed!");
        }
        branch.setName(request.name());
        branch.setLocation(request.location());
        Branch updatedBranch = branchRepository.save(branch);
        return mapToResponse(updatedBranch);
    }

    @Override
    public BranchResponse updateBranchStatus(Long id, UpdateBranchStatusRequest request) {
        Branch branch =
                branchRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Branch not found"
                                )
                        );

        if ("HO001".equals(
                branch.getBranchCode()
        )) {

            throw new RuntimeException(
                    "Head Office cannot be disabled"
            );
        }

        branch.setActive(
                request.active()
        );

        Branch updatedBranch =
                branchRepository.save(branch);

        return mapToResponse(updatedBranch);

    }


// Helper Function
    private BranchResponse mapToResponse(
            Branch branch
    ) {

        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getBranchCode(),
                branch.getLocation(),
                branch.getActive()
        );
    }
}
