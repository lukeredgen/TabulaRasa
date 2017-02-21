package com.arbysoft.tabularasa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProjectRelease.
 */
@Entity
@Table(name = "project_release")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectRelease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codename", nullable = false)
    private String codename;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "version", nullable = false)
    private Long version;

    @ManyToOne
    private Project project;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_release_releasefeature",
               joinColumns = @JoinColumn(name="project_releases_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="releasefeatures_id", referencedColumnName="id"))
    private Set<ReleaseFeature> releasefeatures = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodename() {
        return codename;
    }

    public ProjectRelease codename(String codename) {
        this.codename = codename;
        return this;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getDescription() {
        return description;
    }

    public ProjectRelease description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVersion() {
        return version;
    }

    public ProjectRelease version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Project getProject() {
        return project;
    }

    public ProjectRelease project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<ReleaseFeature> getReleasefeatures() {
        return releasefeatures;
    }

    public ProjectRelease releasefeatures(Set<ReleaseFeature> releaseFeatures) {
        this.releasefeatures = releaseFeatures;
        return this;
    }

    public ProjectRelease addReleasefeature(ReleaseFeature releaseFeature) {
        this.releasefeatures.add(releaseFeature);
        releaseFeature.getProjectreleases().add(this);
        return this;
    }

    public ProjectRelease removeReleasefeature(ReleaseFeature releaseFeature) {
        this.releasefeatures.remove(releaseFeature);
        releaseFeature.getProjectreleases().remove(this);
        return this;
    }

    public void setReleasefeatures(Set<ReleaseFeature> releaseFeatures) {
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
        ProjectRelease projectRelease = (ProjectRelease) o;
        if (projectRelease.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, projectRelease.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectRelease{" +
            "id=" + id +
            ", codename='" + codename + "'" +
            ", description='" + description + "'" +
            ", version='" + version + "'" +
            '}';
    }
}
