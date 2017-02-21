package com.arbysoft.tabularasa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arbysoft.tabularasa.service.FeatureFunctionService;
import com.arbysoft.tabularasa.web.rest.util.HeaderUtil;
import com.arbysoft.tabularasa.web.rest.util.PaginationUtil;
import com.arbysoft.tabularasa.service.dto.FeatureFunctionDTO;
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
 * REST controller for managing FeatureFunction.
 */
@RestController
@RequestMapping("/api")
public class FeatureFunctionResource {

    private final Logger log = LoggerFactory.getLogger(FeatureFunctionResource.class);

    private static final String ENTITY_NAME = "featureFunction";
        
    private final FeatureFunctionService featureFunctionService;

    public FeatureFunctionResource(FeatureFunctionService featureFunctionService) {
        this.featureFunctionService = featureFunctionService;
    }

    /**
     * POST  /feature-functions : Create a new featureFunction.
     *
     * @param featureFunctionDTO the featureFunctionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new featureFunctionDTO, or with status 400 (Bad Request) if the featureFunction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feature-functions")
    @Timed
    public ResponseEntity<FeatureFunctionDTO> createFeatureFunction(@Valid @RequestBody FeatureFunctionDTO featureFunctionDTO) throws URISyntaxException {
        log.debug("REST request to save FeatureFunction : {}", featureFunctionDTO);
        if (featureFunctionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new featureFunction cannot already have an ID")).body(null);
        }
        FeatureFunctionDTO result = featureFunctionService.save(featureFunctionDTO);
        return ResponseEntity.created(new URI("/api/feature-functions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feature-functions : Updates an existing featureFunction.
     *
     * @param featureFunctionDTO the featureFunctionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated featureFunctionDTO,
     * or with status 400 (Bad Request) if the featureFunctionDTO is not valid,
     * or with status 500 (Internal Server Error) if the featureFunctionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feature-functions")
    @Timed
    public ResponseEntity<FeatureFunctionDTO> updateFeatureFunction(@Valid @RequestBody FeatureFunctionDTO featureFunctionDTO) throws URISyntaxException {
        log.debug("REST request to update FeatureFunction : {}", featureFunctionDTO);
        if (featureFunctionDTO.getId() == null) {
            return createFeatureFunction(featureFunctionDTO);
        }
        FeatureFunctionDTO result = featureFunctionService.save(featureFunctionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, featureFunctionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feature-functions : get all the featureFunctions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of featureFunctions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/feature-functions")
    @Timed
    public ResponseEntity<List<FeatureFunctionDTO>> getAllFeatureFunctions(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FeatureFunctions");
        Page<FeatureFunctionDTO> page = featureFunctionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feature-functions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feature-functions/:id : get the "id" featureFunction.
     *
     * @param id the id of the featureFunctionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the featureFunctionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/feature-functions/{id}")
    @Timed
    public ResponseEntity<FeatureFunctionDTO> getFeatureFunction(@PathVariable Long id) {
        log.debug("REST request to get FeatureFunction : {}", id);
        FeatureFunctionDTO featureFunctionDTO = featureFunctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(featureFunctionDTO));
    }

    /**
     * DELETE  /feature-functions/:id : delete the "id" featureFunction.
     *
     * @param id the id of the featureFunctionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feature-functions/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeatureFunction(@PathVariable Long id) {
        log.debug("REST request to delete FeatureFunction : {}", id);
        featureFunctionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
