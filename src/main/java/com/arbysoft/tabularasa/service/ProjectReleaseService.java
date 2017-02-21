package com.arbysoft.tabularasa.service;

import com.arbysoft.tabularasa.service.dto.ProjectReleaseDTO;
import java.util.List;

/**
 * Service Interface for managing ProjectRelease.
 */
public interface ProjectReleaseService {

    /**
     * Save a projectRelease.
     *
     * @param projectReleaseDTO the entity to save
     * @return the persisted entity
     */
    ProjectReleaseDTO save(ProjectReleaseDTO projectReleaseDTO);

    /**
     *  Get all the projectReleases.
     *  
     *  @return the list of entities
     */
    List<ProjectReleaseDTO> findAll();

    /**
     *  Get the "id" projectRelease.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProjectReleaseDTO findOne(Long id);

    /**
     *  Delete the "id" projectRelease.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
