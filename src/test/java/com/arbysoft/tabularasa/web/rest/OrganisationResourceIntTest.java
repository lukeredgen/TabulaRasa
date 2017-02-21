package com.arbysoft.tabularasa.web.rest;

import com.arbysoft.tabularasa.TabulaRasaApp;

import com.arbysoft.tabularasa.domain.Organisation;
import com.arbysoft.tabularasa.repository.OrganisationRepository;
import com.arbysoft.tabularasa.service.OrganisationService;
import com.arbysoft.tabularasa.service.dto.OrganisationDTO;
import com.arbysoft.tabularasa.service.mapper.OrganisationMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrganisationResource REST controller.
 *
 * @see OrganisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabulaRasaApp.class)
public class OrganisationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationMapper organisationMapper;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restOrganisationMockMvc;

    private Organisation organisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganisationResource organisationResource = new OrganisationResource(organisationService);
        this.restOrganisationMockMvc = MockMvcBuilders.standaloneSetup(organisationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisation createEntity(EntityManager em) {
        Organisation organisation = new Organisation()
                .name(DEFAULT_NAME);
        return organisation;
    }

    @Before
    public void initTest() {
        organisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisation() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);

        restOrganisationMockMvc.perform(post("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate + 1);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOrganisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();

        // Create the Organisation with an existing ID
        Organisation existingOrganisation = new Organisation();
        existingOrganisation.setId(1L);
        OrganisationDTO existingOrganisationDTO = organisationMapper.organisationToOrganisationDTO(existingOrganisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationMockMvc.perform(post("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationRepository.findAll().size();
        // set the field null
        organisation.setName(null);

        // Create the Organisation, which fails.
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);

        restOrganisationMockMvc.perform(post("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isBadRequest());

        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrganisations() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList
        restOrganisationMockMvc.perform(get("/api/organisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get the organisation
        restOrganisationMockMvc.perform(get("/api/organisations/{id}", organisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organisation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganisation() throws Exception {
        // Get the organisation
        restOrganisationMockMvc.perform(get("/api/organisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation
        Organisation updatedOrganisation = organisationRepository.findOne(organisation.getId());
        updatedOrganisation
                .name(UPDATED_NAME);
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(updatedOrganisation);

        restOrganisationMockMvc.perform(put("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganisationMockMvc.perform(put("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);
        int databaseSizeBeforeDelete = organisationRepository.findAll().size();

        // Get the organisation
        restOrganisationMockMvc.perform(delete("/api/organisations/{id}", organisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organisation.class);
    }
}
