package com.intuit.cg.backendtechassessment.testutil;

import com.intuit.cg.backendtechassessment.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkumawat on 9/18/2018.
 */
public class GenerateTestData {

    public static Buyer createBuyer() {
        Buyer buyer1 = new Buyer();
        buyer1.setEmail("not_cool_guy@email.com");
        buyer1.setFirstName("not_cool");
        buyer1.setLastName("guy");
        buyer1.setPhoneNumber("555-555-5555");
        return buyer1;
    }

    public static Seller createSeller() {
        Seller seller1 = new Seller();
        seller1.setEmail("cool_guy@email.com");
        seller1.setFirstName("cool");
        seller1.setLastName("guy");
        seller1.setPhoneNumber("555-555-5555");
        return seller1;
    }

    public static Project createProject() {
        Project project1 = new Project();
        project1.setId(new Long(1));
        project1.setName("Project1");
        project1.setDescription("First Project");
        project1.setMaxBidAmount(new BigDecimal(1000));
        project1.setBidDeadline(LocalDateTime.now().plusMonths(1));
        return  project1;
    }

    public static ProjectAmountMap createProjectAmountMap() {
        ProjectAmountMap projectAmountMap = new ProjectAmountMap();
        projectAmountMap.setBuyer(createBuyer());
        projectAmountMap.setProject(createProject());
        projectAmountMap.setIncrementAmount(new BigDecimal(1));
        projectAmountMap.setLowestProjectBidAmount(new BigDecimal(150));
        projectAmountMap.setLastAutoBidAmount(new BigDecimal(150));
        return projectAmountMap;
    }

    public static Bid createBid() {
        Bid bid1 = new Bid();
        bid1.setAmount(new BigDecimal(100));
        bid1.setBuyer(createBuyer());
        bid1.setProject(createProject());
        return bid1;
    }

    public static Bid createNegativeAmountBid() {
        Bid bid1 = new Bid();
        bid1.setAmount(new BigDecimal(-10));
        bid1.setBuyer(createBuyer());
        bid1.setProject(createProject());
        return bid1;
    }

    public static Bid createExceededAmountBid() {
        Bid bid1 = new Bid();
        bid1.setAmount(new BigDecimal(2000));
        bid1.setBuyer(createBuyer());
        bid1.setProject(createProject());
        return bid1;
    }

    public static Project createProjectExpiredDate() {
        Project project1 = new Project();
        project1.setName("Project1");
        project1.setDescription("First Project");
        project1.setMaxBidAmount(new BigDecimal(1000));
        project1.setBidDeadline(LocalDateTime.now().minusDays(2));
        return  project1;
    }

    public static Buyer createBuyerWithProjectAmountList() {
        Buyer buyer1 = new Buyer();
        buyer1.setEmail("not_cool_guy@email.com");
        buyer1.setFirstName("not_cool");
        buyer1.setLastName("guy");
        buyer1.setPhoneNumber("555-555-5555");
        List<ProjectAmountMap> projectAmountMapList = new ArrayList<>();
        buyer1.setProjectAmountMap(projectAmountMapList);
        return buyer1;
    }
}
