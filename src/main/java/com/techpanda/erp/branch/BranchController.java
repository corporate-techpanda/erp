package com.techpanda.erp.branch;

import com.techpanda.erp.branch.dto.BranchResponse;
import com.techpanda.erp.branch.dto.CreateBranchRequest;
import com.techpanda.erp.branch.dto.UpdateBranchRequest;
import com.techpanda.erp.branch.dto.UpdateBranchStatusRequest;
import com.techpanda.erp.branch.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class BranchController {

    private final BranchService  branchService;

    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(
            @Valid @RequestBody CreateBranchRequest createBranchRequest
    ){
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                branchService.createBranch(createBranchRequest)
        );
    }

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches(){
        return ResponseEntity.status(HttpStatus.OK).body(
                branchService.getBranches()
        );
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchResponse> getBranch(
            @PathVariable Long branchId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                branchService.getBranchById(branchId)
        );
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<BranchResponse> updateBranch(
            @Valid @RequestBody UpdateBranchRequest updateBranchRequest,
            @PathVariable Long  branchId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                branchService.updateBranch(branchId, updateBranchRequest)
        );
    }

    @PatchMapping("/{branchId}/status")
    public ResponseEntity<BranchResponse> updateBranchStatus(
            @Valid @RequestBody UpdateBranchStatusRequest updateBranchStatusRequest,
            @PathVariable Long  branchId
            ){
        return  ResponseEntity.status(HttpStatus.OK).body(
                branchService.updateBranchStatus(branchId, updateBranchStatusRequest)
        );
    }

}
