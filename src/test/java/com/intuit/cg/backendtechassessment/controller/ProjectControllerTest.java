package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.BackendTechAssessmentApplication;
import com.intuit.cg.backendtechassessment.BackendTechAssessmentApplicationTests;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.processor.AutobidProcessor;
import com.intuit.cg.backendtechassessment.repository.BidRepository;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.testutil.GenerateTestData;
import com.intuit.cg.backendtechassessment.validator.BidValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pkumawat on 9/18/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController = new ProjectController();

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private BidValidator bidValidator;

    @Mock
    private AutobidProcessor autobidProcessor;

    @Test
    public void createProjectBidTest() {
        Bid bid = GenerateTestData.createBid();
        Mockito.when(bidRepository.save(Mockito.any(Bid.class))).thenReturn(bid);
        Bid newBid = projectController.createProjectBid(bid.getProject(),bid);
        Assert.assertEquals("Bid validation failed.", newBid, bid);
    }

    @Test
    public void addProjectTest() {
        Project project = GenerateTestData.createProject();
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
        Project newProject = projectController.addProjects(project);
        Assert.assertEquals("Project validation failed.", newProject, project);
    }

    @Test
    public void createAutobidTest() {
        Project project = GenerateTestData.createProject();
        Buyer buyer  = GenerateTestData.createBuyer();
        Bid bid = GenerateTestData.createBid();
        Mockito.when(autobidProcessor.createAutoBid(Mockito.any(Project.class),Mockito.any(Buyer.class))).thenReturn(project);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
        Mockito.when(bidRepository.save(Mockito.any(Bid.class))).thenReturn(bid);
        Project newProject = projectController.createAutobid(project,buyer);
        Assert.assertEquals("Project validation failed.", newProject, project);
    }

}
