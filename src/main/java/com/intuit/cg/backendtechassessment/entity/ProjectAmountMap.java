package com.intuit.cg.backendtechassessment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.intuit.cg.backendtechassessment.entity.common.Auditable;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by pkumawat on 9/18/2018.
 */
@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectAmountMap extends Auditable {

    /**
     * Fix to problem 3.
     *  Entity with Project Id and LowestBidAmount for that project.
     *  Automatic Bid Increment amount.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Buyer buyer;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @NotNull
    @Min(value = 0)
    private BigDecimal lowestProjectBidAmount;

    @NotNull
    @Min(value = 0)
    private BigDecimal lastAutoBidAmount;

    @NotNull
    @Min(value = 0)
    private BigDecimal incrementAmount;

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public BigDecimal getLowestProjectBidAmount() {
        return lowestProjectBidAmount;
    }

    public void setLowestProjectBidAmount(BigDecimal lowestProjectBidAmount) {
        this.lowestProjectBidAmount = lowestProjectBidAmount;
    }

    public BigDecimal getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(BigDecimal incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public BigDecimal getLastAutoBidAmount() {
        return lastAutoBidAmount;
    }

    public void setLastAutoBidAmount(BigDecimal lastAutoBidAmount) {
        this.lastAutoBidAmount = lastAutoBidAmount;
    }
}
