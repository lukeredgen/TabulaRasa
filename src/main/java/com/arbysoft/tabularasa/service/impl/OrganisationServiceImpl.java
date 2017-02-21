package com.arbysoft.tabularasa.service.impl;

import com.arbysoft.tabularasa.service.OrganisationService;
import com.arbysoft.tabularasa.domain.Organisation;
import com.arbysoft.tabularasa.repository.OrganisationRepository;
import com.arbysoft.tabularasa.service.dto.OrganisationDTO;
import com.arbysoft.tabularasa.service.mapper.OrganisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Organisation.
 */
@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService{

    private final Logger log = LoggerFactory.getLogger(OrganisationServiceImpl.class);
    
    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;

    public OrganisationServiceImpl(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
    }

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganisationDTO save(OrganisationDTO organisationDTO) {
        log.debug("Request to save Organisation : {}", organisationDTO);
        Organisation organisation = organisationMapper.organisationDTOToOrganisation(organisationDTO);
        organisation = organisationRepository.save(organisation);
        OrganisationDTO result = organisationMapper.organisationToOrganisationDTO(organisation);
        return result;
    }

    /**
     *  Get all the organisations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organisations");
        Page<Organisation> result = organisationRepository.findAll(pageable);
        return result.map(organisation -> organisationMapper.organisationToOrganisationDTO(organisation));
    }

    /**
     *  Get one organisation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrganisationDTO findOne(Long id) {
        log.debug("Request to get Organisation : {}", id);
        Organisation organisation = organisationRepository.findOne(id);
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);
        return organisationDTO;
    }

    /**
     *  Delete the  organisation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organisation : {}", id);
        organisationRepository.delete(id);
    }
}
