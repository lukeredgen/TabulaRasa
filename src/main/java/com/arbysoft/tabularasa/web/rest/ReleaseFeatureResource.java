package com.arbysoft.tabularasa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arbysoft.tabularasa.service.ReleaseFeatureService;
import com.arbysoft.tabularasa.web.rest.util.HeaderUtil;
import com.arbysoft.tabularasa.web.rest.util.PaginationUtil;
import com.arbysoft.tabularasa.service.dto.ReleaseFeatureDTO;
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
 * REST controller for managing ReleaseFeature.
 */
@RestController
@RequestMapping("/api")
public class ReleaseFeatureResource {

    private final Logger log = LoggerFactory.getLogger(ReleaseFeatureResource.class);

    private static final String ENTITY_NAME = "releaseFeature";
        
    private final ReleaseFeatureService releaseFeatureService;

    public ReleaseFeatureResource(ReleaseFeatureService releaseFeatureService) {
        this.releaseFeatureService = releaseFeatureService;
    }

    /**
     * POST  /release-features : Create a new releaseFeature.
     *
     * @param releaseFeatureDTO the releaseFeatureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new releaseFeatureDTO, or with status 400 (Bad Request) if the releaseFeature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/release-features")
    @Timed
    public ResponseEntity<ReleaseFeatureDTO> createReleaseFeature(@Valid @RequestBody ReleaseFeatureDTO releaseFeatureDTO) throws URISyntaxException {
        log.debug("REST request to save ReleaseFeature : {}", releaseFeatureDTO);
        if (releaseFeatureDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new releaseFeature cannot already have an ID")).body(null);
        }
        ReleaseFeatureDTO result = releaseFeatureService.save(releaseFeatureDTO);
        return ResponseEntity.created(new URI("/api/release-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /release-features : Updates an existing releaseFeature.
     *
     * @param releaseFeatureDTO the releaseFeatureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated releaseFeatureDTO,
     * or with status 400 (Bad Request) if the releaseFeatureDTO is not valid,
     * or with status 500 (Internal Server Error) if the releaseFeatureDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/release-features")
    @Timed
    public ResponseEntity<ReleaseFeatureDTO> updateReleaseFeature(@Valid @RequestBody ReleaseFeatureDTO releaseFeatureDTO) throws URISyntaxException {
        log.debug("REST request to update ReleaseFeature : {}", releaseFeatureDTO);
        if (releaseFeatureDTO.getId() == null) {
            return createReleaseFeature(releaseFeatureDTO);
        }
        ReleaseFeatureDTO result = releaseFeatureService.save(releaseFeatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, releaseFeatureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /release-features : get all the releaseFeatures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of releaseFeatures in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/release-features")
    @Timed
    public ResponseEntity<List<ReleaseFeatureDTO>> getAllReleaseFeatures(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ReleaseFeatures");
        Page<ReleaseFeatureDTO> page = releaseFeatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/release-features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /release-features/:id : get the "id" releaseFeature.
     *
     * @param id the id of the releaseFeatureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the releaseFeatureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/release-features/{id}")
    @Timed
    public ResponseEntity<ReleaseFeatureDTO> getReleaseFeature(@PathVariable Long id) {
        log.debug("REST request to get ReleaseFeature : {}", id);
        ReleaseFeatureDTO releaseFeatureDTO = releaseFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(releaseFeatureDTO));
    }

    /**
     * DELETE  /release-features/:id : delete the "id" releaseFeature.
     *
     * @param id the id of the releaseFeatureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/release-features/{id}")
    @Timed
    public ResponseEntity<Void> deleteReleaseFeature(@PathVariable Long id) {
        log.debug("REST request to delete ReleaseFeature : {}", id);
        releaseFeatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
