package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.ProjectAmountMap;
import com.intuit.cg.backendtechassessment.processor.AutobidProcessor;
import com.intuit.cg.backendtechassessment.repository.BidRepository;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.validator.BidValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.PROJECTS;

@Slf4j
@RestController
@RequestMapping(value = PROJECTS)
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private BidValidator bidValidator;
    @Autowired
    private AutobidProcessor autobidProcessor;


    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project getProject(@PathVariable("id") Project project) {
        return Optional
                .ofNullable(project)
                .orElseThrow(NotFoundException::new);
    }

    @RequestMapping(value = "/{id}/bids", method = RequestMethod.GET)
    public List<Bid> getProjectBids(@PathVariable("id") Project project) {
        return Optional
                .ofNullable(project)
                .orElseThrow(NotFoundException::new)
                .getBids();
    }

    /**
     * Fix for problem 1:
     *      Added Bid Validation before saving the bid.
     */
    @RequestMapping(value = "/{id}/bids", method = RequestMethod.POST)
    public Bid createProjectBid(@PathVariable("id") Project project, @RequestBody Bid bid) {
        bidValidator.validateNewBid(bid,project);
        bid.setProject(project);
        LOGGER.info("Saving bid.");
        return bidRepository.save(bid);
    }

    @RequestMapping(value = "/{id}/bids/best", method = RequestMethod.GET)
    public Bid getProjectBidWithLowestAmount(@PathVariable("id") Long id) {
        return Optional
                .ofNullable(bidRepository.findTopByProjectIdOrderByAmountAsc(id))
                .orElseThrow(NotFoundException::new);
    }

    /**
     * Fix for problem 2:
     *      Added Post method to add the New Projects
     */
    @RequestMapping(method = RequestMethod.POST)
    public Project addProjects(@RequestBody Project project) {
        LOGGER.info("Saving Project with project name : "+project.getName());
        return projectRepository.save(project);
    }

    /**
     * Fix for problem 3:
     *      Added Post method to Autobid for a project.
     */
    @RequestMapping(value = "/{id}/autobid/{buyer_id}", method = RequestMethod.POST)
    public Project createAutobid(@PathVariable("id") Project project, @PathVariable("buyer_id") Buyer buyer) {
        LOGGER.info("Saving Autobid for project with project name : "+project.getName());
        return autobidProcessor.createAutoBid(project, buyer);
    }
}
