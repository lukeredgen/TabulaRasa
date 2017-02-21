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
 * A FeatureFunction.
 */
@Entity
@Table(name = "feature_function")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FeatureFunction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "featurefunctions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReleaseFeature> releasefeatures = new HashSet<>();

    @ManyToMany(mappedBy = "featurefunctions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReleaseFeature> subfunctions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FeatureFunction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public FeatureFunction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ReleaseFeature> getReleasefeatures() {
        return releasefeatures;
    }

    public FeatureFunction releasefeatures(Set<ReleaseFeature> releaseFeatures) {
        this.releasefeatures = releaseFeatures;
        return this;
    }

    public FeatureFunction addReleasefeature(ReleaseFeature releaseFeature) {
        this.releasefeatures.add(releaseFeature);
        releaseFeature.getFeaturefunctions().add(this);
        return this;
    }

    public FeatureFunction removeReleasefeature(ReleaseFeature releaseFeature) {
        this.releasefeatures.remove(releaseFeature);
        releaseFeature.getFeaturefunctions().remove(this);
        return this;
    }

    public void setReleasefeatures(Set<ReleaseFeature> releaseFeatures) {
        this.releasefeatures = releaseFeatures;
    }

    public Set<ReleaseFeature> getSubfunctions() {
        return subfunctions;
    }

    public FeatureFunction subfunctions(Set<ReleaseFeature> releaseFeatures) {
        this.subfunctions = releaseFeatures;
        return this;
    }

    public FeatureFunction addSubfunction(ReleaseFeature releaseFeature) {
        this.subfunctions.add(releaseFeature);
        releaseFeature.getFeaturefunctions().add(this);
        return this;
    }

    public FeatureFunction removeSubfunction(ReleaseFeature releaseFeature) {
        this.subfunctions.remove(releaseFeature);
        releaseFeature.getFeaturefunctions().remove(this);
        return this;
    }

    public void setSubfunctions(Set<ReleaseFeature> releaseFeatures) {
        this.subfunctions = releaseFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeatureFunction featureFunction = (FeatureFunction) o;
        if (featureFunction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, featureFunction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FeatureFunction{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
