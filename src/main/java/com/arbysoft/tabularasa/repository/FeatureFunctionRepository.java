package com.arbysoft.tabularasa.repository;

import com.arbysoft.tabularasa.domain.FeatureFunction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FeatureFunction entity.
 */
@SuppressWarnings("unused")
public interface FeatureFunctionRepository extends JpaRepository<FeatureFunction,Long> {

}
