package com.arbysoft.tabularasa.service.mapper;

import com.arbysoft.tabularasa.domain.*;
import com.arbysoft.tabularasa.service.dto.OrganisationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Organisation and its DTO OrganisationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface OrganisationMapper {

    @Mapping(source = "member.id", target = "memberId")
    OrganisationDTO organisationToOrganisationDTO(Organisation organisation);

    List<OrganisationDTO> organisationsToOrganisationDTOs(List<Organisation> organisations);

    @Mapping(target = "projects", ignore = true)
    @Mapping(source = "memberId", target = "member")
    Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO);

    List<Organisation> organisationDTOsToOrganisations(List<OrganisationDTO> organisationDTOs);
}
