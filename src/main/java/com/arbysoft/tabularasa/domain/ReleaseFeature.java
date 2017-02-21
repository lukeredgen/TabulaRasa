package com.arbysoft.tabularasa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ReleaseFeature.
 */
@Entity
@Table(name = "release_feature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReleaseFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "release_feature_featurefunction",
               joinColumns = @JoinColumn(name="release_features_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="featurefunctions_id", referencedColumnName="id"))
    private Set<FeatureFunction> featurefunctions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "release_feature_featurefunction",
               joinColumns = @JoinColumn(name="release_features_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="featurefunctions_id", referencedColumnName="id"))
    private Set<FeatureFunction> featurefunctions = new HashSet<>();

    @ManyToMany(mappedBy = "releasefeatures")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjectRelease> projectreleases = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ReleaseFeature name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ReleaseFeature description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FeatureFunction> getFeaturefunctions() {
        return featurefunctions;
    }

    public ReleaseFeature featurefunctions(Set<FeatureFunction> featureFunctions) {
        this.featurefunctions = featureFunctions;
        return this;
    }

    public ReleaseFeature addFeaturefunction(FeatureFunction featureFunction) {
        this.featurefunctions.add(featureFunction);
        featureFunction.getReleasefeatures().add(this);
        return this;
    }

    public ReleaseFeature removeFeaturefunction(FeatureFunction featureFunction) {
        this.featurefunctions.remove(featureFunction);
        featureFunction.getReleasefeatures().remove(this);
        return this;
    }

    public void setFeaturefunctions(Set<FeatureFunction> featureFunctions) {
        this.featurefunctions = featureFunctions;
    }

    public Set<FeatureFunction> getFeaturefunctions() {
        return featurefunctions;
    }

    public ReleaseFeature featurefunctions(Set<FeatureFunction> featureFunctions) {
        this.featurefunctions = featureFunctions;
        return this;
    }

    public ReleaseFeature addFeaturefunction(FeatureFunction featureFunction) {
        this.featurefunctions.add(featureFunction);
        featureFunction.getSubfunctions().add(this);
        return this;
    }

    public ReleaseFeature removeFeaturefunction(FeatureFunction featureFunction) {
        this.featurefunctions.remove(featureFunction);
        featureFunction.getSubfunctions().remove(this);
        return this;
    }

    public void setFeaturefunctions(Set<FeatureFunction> featureFunctions) {
        this.featurefunctions = featureFunctions;
    }

    public Set<ProjectRelease> getProjectreleases() {
        return projectreleases;
    }

    public ReleaseFeature projectreleases(Set<ProjectRelease> projectReleases) {
        this.projectreleases = projectReleases;
        return this;
    }

    public ReleaseFeature addProjectrelease(ProjectRelease projectRelease) {
        this.projectreleases.add(projectRelease);
        projectRelease.getReleasefeatures().add(this);
        return this;
    }

    public ReleaseFeature removeProjectrelease(ProjectRelease projectRelease) {
        this.projectreleases.remove(projectRelease);
        projectRelease.getReleasefeatures().remove(this);
        return this;
    }

    public void setProjectreleases(Set<ProjectRelease> projectReleases) {
        this.projectreleases = projectReleases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReleaseFeature releaseFeature = (ReleaseFeature) o;
        if (releaseFeature.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, releaseFeature.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReleaseFeature{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
