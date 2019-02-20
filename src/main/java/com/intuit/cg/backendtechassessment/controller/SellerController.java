package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.Seller;
import com.intuit.cg.backendtechassessment.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.SELLERS;

@Slf4j
@RestController
@RequestMapping(value = SELLERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerController {

    @Autowired
    private SellerRepository sellerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Seller getSeller(@PathVariable("id") Seller seller) {
        return Optional
                .ofNullable(seller)
                .orElseThrow(NotFoundException::new);
    }

    @RequestMapping(value = "/{id}/projects", method = RequestMethod.GET)
    public List<Project> getSellerProjects(@PathVariable("id") Seller seller) {
        return Optional
                .ofNullable(seller)
                .orElseThrow(NotFoundException::new)
                .getProjects();
    }

    /**
     *   problem 2:
     *      Added Post method to add the New Seller
     */
    @RequestMapping(method = RequestMethod.POST)
    public Seller addSeller(@RequestBody Seller seller) {
        return sellerRepository.save(seller);
    }
}
