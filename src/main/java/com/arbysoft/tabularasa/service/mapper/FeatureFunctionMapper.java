package com.arbysoft.tabularasa.service.mapper;

import com.arbysoft.tabularasa.domain.*;
import com.arbysoft.tabularasa.service.dto.FeatureFunctionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FeatureFunction and its DTO FeatureFunctionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeatureFunctionMapper {

    FeatureFunctionDTO featureFunctionToFeatureFunctionDTO(FeatureFunction featureFunction);

    List<FeatureFunctionDTO> featureFunctionsToFeatureFunctionDTOs(List<FeatureFunction> featureFunctions);

    @Mapping(target = "releasefeatures", ignore = true)
    @Mapping(target = "subfunctions", ignore = true)
    FeatureFunction featureFunctionDTOToFeatureFunction(FeatureFunctionDTO featureFunctionDTO);

    List<FeatureFunction> featureFunctionDTOsToFeatureFunctions(List<FeatureFunctionDTO> featureFunctionDTOs);
}
