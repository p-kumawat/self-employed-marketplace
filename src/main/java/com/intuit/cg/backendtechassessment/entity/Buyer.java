package com.intuit.cg.backendtechassessment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.intuit.cg.backendtechassessment.entity.common.User;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Buyer extends User {

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;

    /**
     * Added the List of ProjectAmountMap to store the lowest bid amount for each project.
     */
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectAmountMap> projectAmountMap;

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<ProjectAmountMap> getProjectAmountMap() {
        return projectAmountMap;
    }

    public void setProjectAmountMap(List<ProjectAmountMap> projectAmountMap) {
        this.projectAmountMap = projectAmountMap;
    }
}
