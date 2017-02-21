package com.arbysoft.tabularasa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ReleaseFeature entity.
 */
public class ReleaseFeatureDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Set<FeatureFunctionDTO> featurefunctions = new HashSet<>();

    private Set<FeatureFunctionDTO> featurefunctions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FeatureFunctionDTO> getFeaturefunctions() {
        return featurefunctions;
    }

    public void setFeaturefunctions(Set<FeatureFunctionDTO> featureFunctions) {
        this.featurefunctions = featureFunctions;
    }

    public Set<FeatureFunctionDTO> getFeaturefunctions() {
        return featurefunctions;
    }

    public void setFeaturefunctions(Set<FeatureFunctionDTO> featureFunctions) {
        this.featurefunctions = featureFunctions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReleaseFeatureDTO releaseFeatureDTO = (ReleaseFeatureDTO) o;

        if ( ! Objects.equals(id, releaseFeatureDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReleaseFeatureDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
