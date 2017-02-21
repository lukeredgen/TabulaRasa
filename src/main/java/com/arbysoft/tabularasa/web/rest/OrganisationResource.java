package com.arbysoft.tabularasa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arbysoft.tabularasa.service.OrganisationService;
import com.arbysoft.tabularasa.web.rest.util.HeaderUtil;
import com.arbysoft.tabularasa.web.rest.util.PaginationUtil;
import com.arbysoft.tabularasa.service.dto.OrganisationDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Organisation.
 */
@RestController
@RequestMapping("/api")
public class OrganisationResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationResource.class);

    private static final String ENTITY_NAME = "organisation";
        
    private final OrganisationService organisationService;

    public OrganisationResource(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    /**
     * POST  /organisations : Create a new organisation.
     *
     * @param organisationDTO the organisationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organisationDTO, or with status 400 (Bad Request) if the organisation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organisations")
    @Timed
    public ResponseEntity<OrganisationDTO> createOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to save Organisation : {}", organisationDTO);
        if (organisationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new organisation cannot already have an ID")).body(null);
        }
        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity.created(new URI("/api/organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organisations : Updates an existing organisation.
     *
     * @param organisationDTO the organisationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organisationDTO,
     * or with status 400 (Bad Request) if the organisationDTO is not valid,
     * or with status 500 (Internal Server Error) if the organisationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organisations")
    @Timed
    public ResponseEntity<OrganisationDTO> updateOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to update Organisation : {}", organisationDTO);
        if (organisationDTO.getId() == null) {
            return createOrganisation(organisationDTO);
        }
        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organisations : get all the organisations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organisations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/organisations")
    @Timed
    public ResponseEntity<List<OrganisationDTO>> getAllOrganisations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Organisations");
        Page<OrganisationDTO> page = organisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organisations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organisations/:id : get the "id" organisation.
     *
     * @param id the id of the organisationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organisationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organisations/{id}")
    @Timed
    public ResponseEntity<OrganisationDTO> getOrganisation(@PathVariable Long id) {
        log.debug("REST request to get Organisation : {}", id);
        OrganisationDTO organisationDTO = organisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organisationDTO));
    }

    /**
     * DELETE  /organisations/:id : delete the "id" organisation.
     *
     * @param id the id of the organisationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organisations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete Organisation : {}", id);
        organisationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
