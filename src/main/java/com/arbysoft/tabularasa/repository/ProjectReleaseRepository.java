package com.arbysoft.tabularasa.repository;

import com.arbysoft.tabularasa.domain.ProjectRelease;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectRelease entity.
 */
@SuppressWarnings("unused")
public interface ProjectReleaseRepository extends JpaRepository<ProjectRelease,Long> {

    @Query("select distinct projectRelease from ProjectRelease projectRelease left join fetch projectRelease.releasefeatures")
    List<ProjectRelease> findAllWithEagerRelationships();

    @Query("select projectRelease from ProjectRelease projectRelease left join fetch projectRelease.releasefeatures where projectRelease.id =:id")
    ProjectRelease findOneWithEagerRelationships(@Param("id") Long id);

}
