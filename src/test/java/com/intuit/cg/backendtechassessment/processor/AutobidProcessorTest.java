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
import com.intuit.cg.backendtechassessment.testutil.GenerateTestData;
import com.intuit.cg.backendtechassessment.validator.BidValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.AssertTrue;

/**
 * Created by pkumawat on 9/18/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AutobidProcessorTest {

    @InjectMocks
    private AutobidProcessor autobidProcessor;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private ProjectAmountMapRepository projectAmountMapRepository;

    @Mock
    private BidValidator bidValidator;

    @Test
    public void createAutoBidTest() {
        Project project = GenerateTestData.createProject();
        Buyer buyer = GenerateTestData.createBuyerWithProjectAmountList();
        ProjectAmountMap projectAmountMap = GenerateTestData.createProjectAmountMap();
        buyer.getProjectAmountMap().add(projectAmountMap);
        Bid bid = GenerateTestData.createBid();
        Mockito.when(bidRepository.save(Mockito.any(Bid.class))).thenReturn(bid);
        Project retProject = autobidProcessor.createAutoBid(project,buyer);
        Assert.assertEquals("Project Id validation failed",retProject.getId(),project.getId());
    }

    @Test(expected = LowestBidAmountNotExist.class)
    public void createAutoBidTest_MissingProjectList() {
        Project project = GenerateTestData.createProject();
        Buyer buyer = GenerateTestData.createBuyerWithProjectAmountList();
        Bid bid = GenerateTestData.createBid();
        Mockito.when(bidRepository.save(Mockito.any(Bid.class))).thenReturn(bid);
        Project retProject = autobidProcessor.createAutoBid(project,buyer);
    }

    @Test(expected = InvalidProjectException.class)
    public void createAutoBidTest_InvalidProject() {
        Project project = GenerateTestData.createProject();
        Buyer buyer = GenerateTestData.createBuyerWithProjectAmountList();
        Bid bid = GenerateTestData.createBid();
        Mockito.when(bidRepository.save(Mockito.any(Bid.class))).thenReturn(bid);
        Project retProject = autobidProcessor.createAutoBid(null,buyer);
    }

    @Test(expected = InvalidBuyerException.class)
    public void createAutoBidTest_InvalidBuyer() {
        Project project = GenerateTestData.createProject();
        Buyer buyer = null;
        Bid bid = GenerateTestData.createBid();
        Mockito.when(bidRepository.save(Mockito.any(Bid.class))).thenReturn(bid);
        Project retProject = autobidProcessor.createAutoBid(project,buyer);
    }
}


