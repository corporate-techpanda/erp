package com.techpanda.erp.branch.entity;

import com.techpanda.erp.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "branches")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String branchCode;

    private String location;

    @Column(nullable = false)
    private Boolean active = true;
}
