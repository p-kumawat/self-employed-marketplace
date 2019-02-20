package com.intuit.cg.backendtechassessment.validator;

import com.intuit.cg.backendtechassessment.controller.exception.InvalidProjectException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectBiddingClosedException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectInvalidBidAmountException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectMaxBidAmountExceededException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.testutil.GenerateTestData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pkumawat on 9/18/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BidValidatorTest {

    @InjectMocks
    private BidValidator bidValidator = new BidValidator();

    @Test
    public void validateNewBidTest() {
        Project project = GenerateTestData.createProject();
        Bid bid = GenerateTestData.createBid();
        boolean valid = bidValidator.validateNewBid(bid, project);
        Assert.assertEquals("Bid validation failed",valid,Boolean.TRUE);

    }

    @Test(expected =  InvalidProjectException.class)
    public void validateNewBidTest_InvalidProject() {
        Bid bid = GenerateTestData.createBid();
        boolean valid = bidValidator.validateNewBid(bid, null);

    }

    @Test(expected =  ProjectInvalidBidAmountException.class)
    public void validateNewBidTest_NegativeAmount() {
        Project project = GenerateTestData.createProject();
        Bid bid = GenerateTestData.createNegativeAmountBid();
        boolean valid = bidValidator.validateNewBid(bid, project);

    }

    @Test(expected =  ProjectMaxBidAmountExceededException.class)
    public void validateNewBidTest_MaxBidAmountExceeded() {
        Project project = GenerateTestData.createProject();
        Bid bid = GenerateTestData.createExceededAmountBid();
        boolean valid = bidValidator.validateNewBid(bid, project);

    }

    @Test(expected =  ProjectBiddingClosedException.class)
    public void validateNewBidTest_BiddingClosed() {
        Project project = GenerateTestData.createProjectExpiredDate();
        Bid bid = GenerateTestData.createBid();
        boolean valid = bidValidator.validateNewBid(bid, project);

    }

}
