package com.arbysoft.tabularasa.service.mapper;

import com.arbysoft.tabularasa.domain.*;
import com.arbysoft.tabularasa.service.dto.ReleaseFeatureDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ReleaseFeature and its DTO ReleaseFeatureDTO.
 */
@Mapper(componentModel = "spring", uses = {FeatureFunctionMapper.class, FeatureFunctionMapper.class, })
public interface ReleaseFeatureMapper {

    ReleaseFeatureDTO releaseFeatureToReleaseFeatureDTO(ReleaseFeature releaseFeature);

    List<ReleaseFeatureDTO> releaseFeaturesToReleaseFeatureDTOs(List<ReleaseFeature> releaseFeatures);

    @Mapping(target = "projectreleases", ignore = true)
    ReleaseFeature releaseFeatureDTOToReleaseFeature(ReleaseFeatureDTO releaseFeatureDTO);

    List<ReleaseFeature> releaseFeatureDTOsToReleaseFeatures(List<ReleaseFeatureDTO> releaseFeatureDTOs);

    default FeatureFunction featureFunctionFromId(Long id) {
        if (id == null) {
            return null;
        }
        FeatureFunction featureFunction = new FeatureFunction();
        featureFunction.setId(id);
        return featureFunction;
    }
}
