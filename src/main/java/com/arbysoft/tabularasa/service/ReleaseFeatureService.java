package com.arbysoft.tabularasa.service;

import com.arbysoft.tabularasa.service.dto.ReleaseFeatureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ReleaseFeature.
 */
public interface ReleaseFeatureService {

    /**
     * Save a releaseFeature.
     *
     * @param releaseFeatureDTO the entity to save
     * @return the persisted entity
     */
    ReleaseFeatureDTO save(ReleaseFeatureDTO releaseFeatureDTO);

    /**
     *  Get all the releaseFeatures.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReleaseFeatureDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" releaseFeature.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReleaseFeatureDTO findOne(Long id);

    /**
     *  Delete the "id" releaseFeature.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
