package com.intuit.cg.backendtechassessment.validator;

import com.intuit.cg.backendtechassessment.controller.exception.*;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.ProjectAmountMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by pkumawat on 9/18/2018.
 */
@Component
public class BidValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidValidator.class);

    /**
     *  Fix for problem 1:
     *   a) Validations on bid amount.
     *   b) Validation on project Id.
     *   c) Validation on Bid date.
     */
    public boolean validateNewBid(Bid bid, Project project) {
        // Validate that the project id exists.
        if (project == null) {
            LOGGER.error("No matching project found with specified project Id.");
            throw new InvalidProjectException();
        }
        if (isValidBidAmount(bid, project) && isValidBidDate(project))
            LOGGER.info("Bid date and amount validation successful.");
        return Boolean.TRUE;
    }

    /**
     * Validate Bid Amount. It must be:
     * a) Greater than zero.
     * b) Less than maximum project bid amount.
     */
    public boolean isValidBidAmount(Bid bid, Project project) {
        if (bid.getAmount().compareTo((new BigDecimal(0))) == -1) {
            LOGGER.error("Project Bid amount must be postive value.");
            throw new ProjectInvalidBidAmountException();
        }
        if (bid.getAmount().compareTo(project.getMaxBidAmount()) == 1) {
            LOGGER.error("Project Maximum Bid amount exceeded.");
            throw new ProjectMaxBidAmountExceededException();
        }
        return Boolean.TRUE;
    }

    /**
     * Validate Bid date.
     * Bid date must be less than or equal to BidDeadline date.
     */
    private boolean isValidBidDate(Project project) {
        LocalDateTime currentTime = LocalDateTime.now();

        if (project.getBidDeadline().isBefore(currentTime)) {
            LOGGER.error("Project time is before the current date time");
            throw new ProjectBiddingClosedException();
        }
        //return project.getBidDeadline().isBefore(currentTime) ? Boolean.FALSE : Boolean.TRUE;
        return Boolean.TRUE;
    }

    public boolean validdateLowestBidAmount(ProjectAmountMap projectAmountMap) {
        if (projectAmountMap.getLowestProjectBidAmount().compareTo((new BigDecimal(0))) == -1 ||
                projectAmountMap.getIncrementAmount().compareTo((new BigDecimal(0))) == -1 ) {
            LOGGER.error("LowestProjectBid amount and Increment amount must be postive value.");
            throw new InvalidAmountException();
        }
        if (projectAmountMap.getProject().getMaxBidAmount()!=null &&
                projectAmountMap.getLowestProjectBidAmount().compareTo(projectAmountMap.getProject().getMaxBidAmount()) == 1) {
            LOGGER.error("Project Maximum Bid amount exceeded.");
            throw new ProjectMaxBidAmountExceededException();
        }
        return Boolean.TRUE;
    }

}
