package com.arbysoft.tabularasa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProjectRelease entity.
 */
public class ProjectReleaseDTO implements Serializable {

    private Long id;

    @NotNull
    private String codename;

    private String description;

    @NotNull
    private Long version;

    private Long projectId;

    private Set<ReleaseFeatureDTO> releasefeatures = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Set<ReleaseFeatureDTO> getReleasefeatures() {
        return releasefeatures;
    }

    public void setReleasefeatures(Set<ReleaseFeatureDTO> releaseFeatures) {
        this.releasefeatures = releaseFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectReleaseDTO projectReleaseDTO = (ProjectReleaseDTO) o;

        if ( ! Objects.equals(id, projectReleaseDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectReleaseDTO{" +
            "id=" + id +
            ", codename='" + codename + "'" +
            ", description='" + description + "'" +
            ", version='" + version + "'" +
            '}';
    }
}
