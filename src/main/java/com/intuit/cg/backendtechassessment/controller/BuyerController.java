package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.ProjectAmountMap;
import com.intuit.cg.backendtechassessment.repository.BuyerRepository;
import com.intuit.cg.backendtechassessment.repository.ProjectAmountMapRepository;
import com.intuit.cg.backendtechassessment.validator.BidValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.BUYERS;

@Slf4j
@RestController
@RequestMapping(value = BUYERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class BuyerController {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProjectAmountMapRepository projectAmountMapRepository;

    @Autowired
    private BidValidator bidValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerController.class);

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Buyer> getBuyers() {
        return buyerRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Buyer getBuyer(@PathVariable("id") Buyer buyer) {
        return Optional.ofNullable(buyer)
                .orElseThrow(NotFoundException::new);
    }

    @RequestMapping(value = "/{id}/bids", method = RequestMethod.GET)
    public List<Bid> getBuyerBids(@NotNull @PathVariable("id") Buyer buyer) {
        return Optional
                .ofNullable(buyer)
                .orElseThrow(NotFoundException::new).getBids();
    }

    /**
     * Fix for problem 2:
     *      Added Post method to add new Buyers.
     */
    @RequestMapping(method = RequestMethod.POST)
    public Buyer addBuyer(@RequestBody Buyer buyer) {

        return buyerRepository.save(buyer);
    }

    /**
     * Fix for problem 3:
     *      Added Post method to add the List having project Id and LowestBidAmount for each project.
     */
    @RequestMapping(value = "/{id}/lowest-bid-amount", method = RequestMethod.POST)
    public ProjectAmountMap addLowestBidAmountList(@PathVariable("id") Buyer buyer, @RequestBody ProjectAmountMap projectAmountMap) {
        projectAmountMap.setBuyer(buyer);
        projectAmountMap.setLastAutoBidAmount(projectAmountMap.getLowestProjectBidAmount());
        bidValidator.validdateLowestBidAmount(projectAmountMap);
        LOGGER.info("Saving ProjectAmountMap.");
        return projectAmountMapRepository.save(projectAmountMap);
    }
}
