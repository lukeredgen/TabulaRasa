package com.arbysoft.tabularasa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arbysoft.tabularasa.service.ProjectReleaseService;
import com.arbysoft.tabularasa.web.rest.util.HeaderUtil;
import com.arbysoft.tabularasa.service.dto.ProjectReleaseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing ProjectRelease.
 */
@RestController
@RequestMapping("/api")
public class ProjectReleaseResource {

    private final Logger log = LoggerFactory.getLogger(ProjectReleaseResource.class);

    private static final String ENTITY_NAME = "projectRelease";
        
    private final ProjectReleaseService projectReleaseService;

    public ProjectReleaseResource(ProjectReleaseService projectReleaseService) {
        this.projectReleaseService = projectReleaseService;
    }

    /**
     * POST  /project-releases : Create a new projectRelease.
     *
     * @param projectReleaseDTO the projectReleaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectReleaseDTO, or with status 400 (Bad Request) if the projectRelease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-releases")
    @Timed
    public ResponseEntity<ProjectReleaseDTO> createProjectRelease(@Valid @RequestBody ProjectReleaseDTO projectReleaseDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectRelease : {}", projectReleaseDTO);
        if (projectReleaseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new projectRelease cannot already have an ID")).body(null);
        }
        ProjectReleaseDTO result = projectReleaseService.save(projectReleaseDTO);
        return ResponseEntity.created(new URI("/api/project-releases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-releases : Updates an existing projectRelease.
     *
     * @param projectReleaseDTO the projectReleaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectReleaseDTO,
     * or with status 400 (Bad Request) if the projectReleaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectReleaseDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-releases")
    @Timed
    public ResponseEntity<ProjectReleaseDTO> updateProjectRelease(@Valid @RequestBody ProjectReleaseDTO projectReleaseDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectRelease : {}", projectReleaseDTO);
        if (projectReleaseDTO.getId() == null) {
            return createProjectRelease(projectReleaseDTO);
        }
        ProjectReleaseDTO result = projectReleaseService.save(projectReleaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectReleaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-releases : get all the projectReleases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projectReleases in body
     */
    @GetMapping("/project-releases")
    @Timed
    public List<ProjectReleaseDTO> getAllProjectReleases() {
        log.debug("REST request to get all ProjectReleases");
        return projectReleaseService.findAll();
    }

    /**
     * GET  /project-releases/:id : get the "id" projectRelease.
     *
     * @param id the id of the projectReleaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectReleaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/project-releases/{id}")
    @Timed
    public ResponseEntity<ProjectReleaseDTO> getProjectRelease(@PathVariable Long id) {
        log.debug("REST request to get ProjectRelease : {}", id);
        ProjectReleaseDTO projectReleaseDTO = projectReleaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectReleaseDTO));
    }

    /**
     * DELETE  /project-releases/:id : delete the "id" projectRelease.
     *
     * @param id the id of the projectReleaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-releases/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectRelease(@PathVariable Long id) {
        log.debug("REST request to delete ProjectRelease : {}", id);
        projectReleaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
