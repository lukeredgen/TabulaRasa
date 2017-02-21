package com.arbysoft.tabularasa.service.impl;

import com.arbysoft.tabularasa.service.ReleaseFeatureService;
import com.arbysoft.tabularasa.domain.ReleaseFeature;
import com.arbysoft.tabularasa.repository.ReleaseFeatureRepository;
import com.arbysoft.tabularasa.service.dto.ReleaseFeatureDTO;
import com.arbysoft.tabularasa.service.mapper.ReleaseFeatureMapper;
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
 * Service Implementation for managing ReleaseFeature.
 */
@Service
@Transactional
public class ReleaseFeatureServiceImpl implements ReleaseFeatureService{

    private final Logger log = LoggerFactory.getLogger(ReleaseFeatureServiceImpl.class);
    
    private final ReleaseFeatureRepository releaseFeatureRepository;

    private final ReleaseFeatureMapper releaseFeatureMapper;

    public ReleaseFeatureServiceImpl(ReleaseFeatureRepository releaseFeatureRepository, ReleaseFeatureMapper releaseFeatureMapper) {
        this.releaseFeatureRepository = releaseFeatureRepository;
        this.releaseFeatureMapper = releaseFeatureMapper;
    }

    /**
     * Save a releaseFeature.
     *
     * @param releaseFeatureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReleaseFeatureDTO save(ReleaseFeatureDTO releaseFeatureDTO) {
        log.debug("Request to save ReleaseFeature : {}", releaseFeatureDTO);
        ReleaseFeature releaseFeature = releaseFeatureMapper.releaseFeatureDTOToReleaseFeature(releaseFeatureDTO);
        releaseFeature = releaseFeatureRepository.save(releaseFeature);
        ReleaseFeatureDTO result = releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(releaseFeature);
        return result;
    }

    /**
     *  Get all the releaseFeatures.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReleaseFeatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReleaseFeatures");
        Page<ReleaseFeature> result = releaseFeatureRepository.findAll(pageable);
        return result.map(releaseFeature -> releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(releaseFeature));
    }

    /**
     *  Get one releaseFeature by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReleaseFeatureDTO findOne(Long id) {
        log.debug("Request to get ReleaseFeature : {}", id);
        ReleaseFeature releaseFeature = releaseFeatureRepository.findOneWithEagerRelationships(id);
        ReleaseFeatureDTO releaseFeatureDTO = releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(releaseFeature);
        return releaseFeatureDTO;
    }

    /**
     *  Delete the  releaseFeature by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReleaseFeature : {}", id);
        releaseFeatureRepository.delete(id);
    }
}
