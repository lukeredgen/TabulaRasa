package com.arbysoft.tabularasa.service;

import com.arbysoft.tabularasa.service.dto.OrganisationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Organisation.
 */
public interface OrganisationService {

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save
     * @return the persisted entity
     */
    OrganisationDTO save(OrganisationDTO organisationDTO);

    /**
     *  Get all the organisations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrganisationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" organisation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrganisationDTO findOne(Long id);

    /**
     *  Delete the "id" organisation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
