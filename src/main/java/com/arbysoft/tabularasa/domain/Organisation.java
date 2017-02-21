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
 * A Organisation.
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "organisation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    @ManyToOne
    private User member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Organisation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Organisation projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Organisation addProject(Project project) {
        this.projects.add(project);
        project.setOrganisation(this);
        return this;
    }

    public Organisation removeProject(Project project) {
        this.projects.remove(project);
        project.setOrganisation(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public User getMember() {
        return member;
    }

    public Organisation member(User user) {
        this.member = user;
        return this;
    }

    public void setMember(User user) {
        this.member = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organisation organisation = (Organisation) o;
        if (organisation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, organisation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
