package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.ProjectAmountMap;
import com.intuit.cg.backendtechassessment.repository.BuyerRepository;
import com.intuit.cg.backendtechassessment.repository.ProjectAmountMapRepository;
import com.intuit.cg.backendtechassessment.testutil.GenerateTestData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pkumawat on 9/18/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BuyerControllerTest {


    @InjectMocks
    private BuyerController buyerController = new BuyerController();

    @Mock
    BuyerRepository buyerRepository;

    @Mock
    private ProjectAmountMapRepository projectAmountMapRepository;

    @Test
    public void addBuyerTest() {
        Buyer buyer = GenerateTestData.createBuyer();
        Mockito.when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyer);
        Buyer newBuyer = buyerController.addBuyer(buyer);
        Assert.assertEquals("Buyer validation failed.",newBuyer,buyer);
    }

    @Test
    public void addLowestBidAmountListTest() {

        ProjectAmountMap projectAmountMap = GenerateTestData.createProjectAmountMap();
        Mockito.when(projectAmountMapRepository.save(Mockito.any(ProjectAmountMap.class))).thenReturn(projectAmountMap);
        ProjectAmountMap newProjectAmountMap = buyerController.addLowestBidAmountList(GenerateTestData.createBuyer(),projectAmountMap);
        Assert.assertEquals("ProjectAmountMap validation failed.",newProjectAmountMap,projectAmountMap);
    }
}
