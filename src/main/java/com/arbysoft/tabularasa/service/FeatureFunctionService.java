package com.arbysoft.tabularasa.service;

import com.arbysoft.tabularasa.service.dto.FeatureFunctionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing FeatureFunction.
 */
public interface FeatureFunctionService {

    /**
     * Save a featureFunction.
     *
     * @param featureFunctionDTO the entity to save
     * @return the persisted entity
     */
    FeatureFunctionDTO save(FeatureFunctionDTO featureFunctionDTO);

    /**
     *  Get all the featureFunctions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FeatureFunctionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" featureFunction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FeatureFunctionDTO findOne(Long id);

    /**
     *  Delete the "id" featureFunction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
