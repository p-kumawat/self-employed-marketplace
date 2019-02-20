package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.repository.BidRepository;
import com.intuit.cg.backendtechassessment.validator.BidValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.BIDS;

@Slf4j
@RestController
@RequestMapping(value = BIDS, produces = MediaType.APPLICATION_JSON_VALUE)
public class BidController {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidValidator bidValidator;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Bid> getBids() {
        return bidRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Bid getBid(@PathVariable("id") Bid bid) {
        return Optional.ofNullable(bid).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Bid updateBid(@PathVariable("id") Bid bid, @RequestBody Bid newBid) {
        bidValidator.validateNewBid(newBid,bid.getProject());
        bid.setAmount(newBid.getAmount());
        return bidRepository.save(bid);
    }
}
