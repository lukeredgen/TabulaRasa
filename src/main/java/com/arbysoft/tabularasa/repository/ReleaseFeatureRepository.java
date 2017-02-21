package com.arbysoft.tabularasa.repository;

import com.arbysoft.tabularasa.domain.ReleaseFeature;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ReleaseFeature entity.
 */
@SuppressWarnings("unused")
public interface ReleaseFeatureRepository extends JpaRepository<ReleaseFeature,Long> {

    @Query("select distinct releaseFeature from ReleaseFeature releaseFeature left join fetch releaseFeature.featurefunctions left join fetch releaseFeature.featurefunctions")
    List<ReleaseFeature> findAllWithEagerRelationships();

    @Query("select releaseFeature from ReleaseFeature releaseFeature left join fetch releaseFeature.featurefunctions left join fetch releaseFeature.featurefunctions where releaseFeature.id =:id")
    ReleaseFeature findOneWithEagerRelationships(@Param("id") Long id);

}
