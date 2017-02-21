package com.arbysoft.tabularasa.repository;

import com.arbysoft.tabularasa.domain.Organisation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Organisation entity.
 */
@SuppressWarnings("unused")
public interface OrganisationRepository extends JpaRepository<Organisation,Long> {

    @Query("select organisation from Organisation organisation where organisation.member.login = ?#{principal.username}")
    List<Organisation> findByMemberIsCurrentUser();

}
