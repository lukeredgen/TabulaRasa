package com.arbysoft.tabularasa.service.mapper;

import com.arbysoft.tabularasa.domain.*;
import com.arbysoft.tabularasa.service.dto.ProjectReleaseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProjectRelease and its DTO ProjectReleaseDTO.
 */
@Mapper(componentModel = "spring", uses = {ReleaseFeatureMapper.class, })
public interface ProjectReleaseMapper {

    @Mapping(source = "project.id", target = "projectId")
    ProjectReleaseDTO projectReleaseToProjectReleaseDTO(ProjectRelease projectRelease);

    List<ProjectReleaseDTO> projectReleasesToProjectReleaseDTOs(List<ProjectRelease> projectReleases);

    @Mapping(source = "projectId", target = "project")
    ProjectRelease projectReleaseDTOToProjectRelease(ProjectReleaseDTO projectReleaseDTO);

    List<ProjectRelease> projectReleaseDTOsToProjectReleases(List<ProjectReleaseDTO> projectReleaseDTOs);

    default Project projectFromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }

    default ReleaseFeature releaseFeatureFromId(Long id) {
        if (id == null) {
            return null;
        }
        ReleaseFeature releaseFeature = new ReleaseFeature();
        releaseFeature.setId(id);
        return releaseFeature;
    }
}
