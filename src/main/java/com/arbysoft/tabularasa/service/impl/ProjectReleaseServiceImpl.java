package com.arbysoft.tabularasa.service.impl;

import com.arbysoft.tabularasa.service.ProjectReleaseService;
import com.arbysoft.tabularasa.domain.ProjectRelease;
import com.arbysoft.tabularasa.repository.ProjectReleaseRepository;
import com.arbysoft.tabularasa.service.dto.ProjectReleaseDTO;
import com.arbysoft.tabularasa.service.mapper.ProjectReleaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProjectRelease.
 */
@Service
@Transactional
public class ProjectReleaseServiceImpl implements ProjectReleaseService{

    private final Logger log = LoggerFactory.getLogger(ProjectReleaseServiceImpl.class);
    
    private final ProjectReleaseRepository projectReleaseRepository;

    private final ProjectReleaseMapper projectReleaseMapper;

    public ProjectReleaseServiceImpl(ProjectReleaseRepository projectReleaseRepository, ProjectReleaseMapper projectReleaseMapper) {
        this.projectReleaseRepository = projectReleaseRepository;
        this.projectReleaseMapper = projectReleaseMapper;
    }

    /**
     * Save a projectRelease.
     *
     * @param projectReleaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectReleaseDTO save(ProjectReleaseDTO projectReleaseDTO) {
        log.debug("Request to save ProjectRelease : {}", projectReleaseDTO);
        ProjectRelease projectRelease = projectReleaseMapper.projectReleaseDTOToProjectRelease(projectReleaseDTO);
        projectRelease = projectReleaseRepository.save(projectRelease);
        ProjectReleaseDTO result = projectReleaseMapper.projectReleaseToProjectReleaseDTO(projectRelease);
        return result;
    }

    /**
     *  Get all the projectReleases.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectReleaseDTO> findAll() {
        log.debug("Request to get all ProjectReleases");
        List<ProjectReleaseDTO> result = projectReleaseRepository.findAllWithEagerRelationships().stream()
            .map(projectReleaseMapper::projectReleaseToProjectReleaseDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one projectRelease by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectReleaseDTO findOne(Long id) {
        log.debug("Request to get ProjectRelease : {}", id);
        ProjectRelease projectRelease = projectReleaseRepository.findOneWithEagerRelationships(id);
        ProjectReleaseDTO projectReleaseDTO = projectReleaseMapper.projectReleaseToProjectReleaseDTO(projectRelease);
        return projectReleaseDTO;
    }

    /**
     *  Delete the  projectRelease by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectRelease : {}", id);
        projectReleaseRepository.delete(id);
    }
}
