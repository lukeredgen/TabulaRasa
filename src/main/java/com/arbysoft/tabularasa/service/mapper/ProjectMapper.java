package com.arbysoft.tabularasa.service.mapper;

import com.arbysoft.tabularasa.domain.*;
import com.arbysoft.tabularasa.service.dto.ProjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMapper {

    @Mapping(source = "organisation.id", target = "organisationId")
    ProjectDTO projectToProjectDTO(Project project);

    List<ProjectDTO> projectsToProjectDTOs(List<Project> projects);

    @Mapping(source = "organisationId", target = "organisation")
    @Mapping(target = "projectreleases", ignore = true)
    Project projectDTOToProject(ProjectDTO projectDTO);

    List<Project> projectDTOsToProjects(List<ProjectDTO> projectDTOs);

    default Organisation organisationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Organisation organisation = new Organisation();
        organisation.setId(id);
        return organisation;
    }
}
