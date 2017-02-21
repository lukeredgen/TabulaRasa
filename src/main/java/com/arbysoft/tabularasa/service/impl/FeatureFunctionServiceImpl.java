package com.arbysoft.tabularasa.service.impl;

import com.arbysoft.tabularasa.service.FeatureFunctionService;
import com.arbysoft.tabularasa.domain.FeatureFunction;
import com.arbysoft.tabularasa.repository.FeatureFunctionRepository;
import com.arbysoft.tabularasa.service.dto.FeatureFunctionDTO;
import com.arbysoft.tabularasa.service.mapper.FeatureFunctionMapper;
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
 * Service Implementation for managing FeatureFunction.
 */
@Service
@Transactional
public class FeatureFunctionServiceImpl implements FeatureFunctionService{

    private final Logger log = LoggerFactory.getLogger(FeatureFunctionServiceImpl.class);
    
    private final FeatureFunctionRepository featureFunctionRepository;

    private final FeatureFunctionMapper featureFunctionMapper;

    public FeatureFunctionServiceImpl(FeatureFunctionRepository featureFunctionRepository, FeatureFunctionMapper featureFunctionMapper) {
        this.featureFunctionRepository = featureFunctionRepository;
        this.featureFunctionMapper = featureFunctionMapper;
    }

    /**
     * Save a featureFunction.
     *
     * @param featureFunctionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FeatureFunctionDTO save(FeatureFunctionDTO featureFunctionDTO) {
        log.debug("Request to save FeatureFunction : {}", featureFunctionDTO);
        FeatureFunction featureFunction = featureFunctionMapper.featureFunctionDTOToFeatureFunction(featureFunctionDTO);
        featureFunction = featureFunctionRepository.save(featureFunction);
        FeatureFunctionDTO result = featureFunctionMapper.featureFunctionToFeatureFunctionDTO(featureFunction);
        return result;
    }

    /**
     *  Get all the featureFunctions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FeatureFunctionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FeatureFunctions");
        Page<FeatureFunction> result = featureFunctionRepository.findAll(pageable);
        return result.map(featureFunction -> featureFunctionMapper.featureFunctionToFeatureFunctionDTO(featureFunction));
    }

    /**
     *  Get one featureFunction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FeatureFunctionDTO findOne(Long id) {
        log.debug("Request to get FeatureFunction : {}", id);
        FeatureFunction featureFunction = featureFunctionRepository.findOne(id);
        FeatureFunctionDTO featureFunctionDTO = featureFunctionMapper.featureFunctionToFeatureFunctionDTO(featureFunction);
        return featureFunctionDTO;
    }

    /**
     *  Delete the  featureFunction by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeatureFunction : {}", id);
        featureFunctionRepository.delete(id);
    }
}
