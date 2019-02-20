package com.intuit.cg.backendtechassessment.repository;

import com.intuit.cg.backendtechassessment.entity.Bid;
import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends CrudRepository<Bid, Long> {
    Bid findTopByProjectIdOrderByAmountAsc(Long projectId);
}
