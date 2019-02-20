package com.intuit.cg.backendtechassessment.processor;

import com.intuit.cg.backendtechassessment.controller.exception.InvalidBuyerException;
import com.intuit.cg.backendtechassessment.controller.exception.InvalidProjectException;
import com.intuit.cg.backendtechassessment.controller.exception.LowestBidAmountNotExist;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.ProjectAmountMap;
import com.intuit.cg.backendtechassessment.repository.BidRepository;
import com.intuit.cg.backendtechassessment.repository.ProjectAmountMapRepository;
import com.intuit.cg.backendtechassessment.validator.BidValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by pkumawat on 9/18/2018.
 */
@Component
public class AutobidProcessor {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidValidator bidValidator;

    @Autowired
    private ProjectAmountMapRepository projectAmountMapRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AutobidProcessor.class);
    /**
     * Create Autobid
     */
    public Project createAutoBid(Project project, Buyer buyer ) {

        validateProjectAndBuyer(project, buyer);
        ProjectAmountMap  projectAmountMap = getProjectAmountMap(project, buyer);

        // Create Bid object for Autobid
        Bid autoBid = new Bid();
        autoBid.setBuyer(buyer);
        autoBid.setProject(project);
        autoBid.setAmount(projectAmountMap.getLastAutoBidAmount().add(projectAmountMap.getIncrementAmount()) );

        // Valdidate Autobid with all validations for new bid.
        bidValidator.validateNewBid(autoBid,project);

        // Save Autobid
        LOGGER.info("Saving Auto Bid.");
        bidRepository.save(autoBid);

        // Increment the LastAutoBidAmount in ProjectAmountMap.
        incrementLastAutoBidAmount(projectAmountMap);

        return project;
    }


    /**
     * Validate the project and buyer are valid.
     */
    private void validateProjectAndBuyer(Project project, Buyer buyer ) {

        if(project == null) {
            LOGGER.error("No matching project found with specified project Id.");
            throw new InvalidProjectException();
        }
        if (buyer == null) {
            LOGGER.error("No matching buyer found with specified buyer Id.");
            throw new InvalidBuyerException();
        }
    }

    /**
     * Validate if the Buyer has set the Lowest bid amount for project
     *      that he/she is trying to autobid.
     *  If not, then project cannot be autobid.
     */
    private ProjectAmountMap getProjectAmountMap(Project project, Buyer buyer ) {
        ProjectAmountMap  projectAmountMap = null;
        for(ProjectAmountMap tempProjectAmountMap : buyer.getProjectAmountMap()) {
            LOGGER.info("project ids : "+tempProjectAmountMap.getProject().getId()+" "+project.getId());
            if(tempProjectAmountMap.getProject().getId().equals(project.getId()) ) {
                LOGGER.info("ProjectAmountMap found for project.");
                projectAmountMap = tempProjectAmountMap;
                break;
            }
        }
        if(projectAmountMap == null) {
            throw new LowestBidAmountNotExist();
        }
        return projectAmountMap;
    }

    /*
     * Update the LastAutoBidAmount by the IncrementAmount.
     */
    private void incrementLastAutoBidAmount(ProjectAmountMap  projectAmountMap ) {
        projectAmountMap.setLastAutoBidAmount(projectAmountMap.getLastAutoBidAmount().add(projectAmountMap.getIncrementAmount()) );
        projectAmountMapRepository.save(projectAmountMap);
    }
}
