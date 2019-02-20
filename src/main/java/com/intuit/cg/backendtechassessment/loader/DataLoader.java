package com.intuit.cg.backendtechassessment.loader;

import com.intuit.cg.backendtechassessment.entity.*;
import com.intuit.cg.backendtechassessment.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private ProjectAmountMapRepository projectAmountMapRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Seller seller1 = new Seller();
        seller1.setEmail("cool_guy@email.com");
        seller1.setFirstName("cool");
        seller1.setLastName("guy");
        seller1.setPhoneNumber("555-555-5555");

        seller1 = sellerRepository.save(seller1);

        Project project1 = new Project();
        project1.setName("Project1");
        project1.setDescription("First Project");
        project1.setMaxBidAmount(new BigDecimal(1000));
        project1.setBidDeadline(LocalDateTime.now().plusMonths(1));
        project1.setSeller(seller1);

        project1 = projectRepository.save(project1);

        Buyer buyer1 = new Buyer();
        buyer1.setEmail("not_cool_guy@email.com");
        buyer1.setFirstName("not_cool");
        buyer1.setLastName("guy");
        buyer1.setPhoneNumber("555-555-5555");
        buyer1 = buyerRepository.save(buyer1);

        Bid bid1 = new Bid();
        bid1.setAmount(new BigDecimal(100));
        bid1.setBuyer(buyer1);
        bid1.setProject(project1);
        bid1 = bidRepository.save(bid1);

        ProjectAmountMap projectAmountMap = new ProjectAmountMap();
        projectAmountMap.setBuyer(buyer1);
        projectAmountMap.setProject(project1);
        projectAmountMap.setIncrementAmount(new BigDecimal(1));
        projectAmountMap.setLowestProjectBidAmount(new BigDecimal(150));
        projectAmountMap.setLastAutoBidAmount(new BigDecimal(150));
        projectAmountMap = projectAmountMapRepository.save(projectAmountMap);
    }
}
