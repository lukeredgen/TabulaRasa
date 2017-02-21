package com.arbysoft.tabularasa.web.rest;

import com.arbysoft.tabularasa.TabulaRasaApp;

import com.arbysoft.tabularasa.domain.ProjectRelease;
import com.arbysoft.tabularasa.repository.ProjectReleaseRepository;
import com.arbysoft.tabularasa.service.ProjectReleaseService;
import com.arbysoft.tabularasa.service.dto.ProjectReleaseDTO;
import com.arbysoft.tabularasa.service.mapper.ProjectReleaseMapper;

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
 * Test class for the ProjectReleaseResource REST controller.
 *
 * @see ProjectReleaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabulaRasaApp.class)
public class ProjectReleaseResourceIntTest {

    private static final String DEFAULT_CODENAME = "AAAAAAAAAA";
    private static final String UPDATED_CODENAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    @Autowired
    private ProjectReleaseRepository projectReleaseRepository;

    @Autowired
    private ProjectReleaseMapper projectReleaseMapper;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectReleaseMockMvc;

    private ProjectRelease projectRelease;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectReleaseResource projectReleaseResource = new ProjectReleaseResource(projectReleaseService);
        this.restProjectReleaseMockMvc = MockMvcBuilders.standaloneSetup(projectReleaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRelease createEntity(EntityManager em) {
        ProjectRelease projectRelease = new ProjectRelease()
                .codename(DEFAULT_CODENAME)
                .description(DEFAULT_DESCRIPTION)
                .version(DEFAULT_VERSION);
        return projectRelease;
    }

    @Before
    public void initTest() {
        projectRelease = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectRelease() throws Exception {
        int databaseSizeBeforeCreate = projectReleaseRepository.findAll().size();

        // Create the ProjectRelease
        ProjectReleaseDTO projectReleaseDTO = projectReleaseMapper.projectReleaseToProjectReleaseDTO(projectRelease);

        restProjectReleaseMockMvc.perform(post("/api/project-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectReleaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectRelease in the database
        List<ProjectRelease> projectReleaseList = projectReleaseRepository.findAll();
        assertThat(projectReleaseList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectRelease testProjectRelease = projectReleaseList.get(projectReleaseList.size() - 1);
        assertThat(testProjectRelease.getCodename()).isEqualTo(DEFAULT_CODENAME);
        assertThat(testProjectRelease.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectRelease.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createProjectReleaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectReleaseRepository.findAll().size();

        // Create the ProjectRelease with an existing ID
        ProjectRelease existingProjectRelease = new ProjectRelease();
        existingProjectRelease.setId(1L);
        ProjectReleaseDTO existingProjectReleaseDTO = projectReleaseMapper.projectReleaseToProjectReleaseDTO(existingProjectRelease);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectReleaseMockMvc.perform(post("/api/project-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProjectReleaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjectRelease> projectReleaseList = projectReleaseRepository.findAll();
        assertThat(projectReleaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectReleaseRepository.findAll().size();
        // set the field null
        projectRelease.setCodename(null);

        // Create the ProjectRelease, which fails.
        ProjectReleaseDTO projectReleaseDTO = projectReleaseMapper.projectReleaseToProjectReleaseDTO(projectRelease);

        restProjectReleaseMockMvc.perform(post("/api/project-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectReleaseDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectRelease> projectReleaseList = projectReleaseRepository.findAll();
        assertThat(projectReleaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectReleaseRepository.findAll().size();
        // set the field null
        projectRelease.setVersion(null);

        // Create the ProjectRelease, which fails.
        ProjectReleaseDTO projectReleaseDTO = projectReleaseMapper.projectReleaseToProjectReleaseDTO(projectRelease);

        restProjectReleaseMockMvc.perform(post("/api/project-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectReleaseDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectRelease> projectReleaseList = projectReleaseRepository.findAll();
        assertThat(projectReleaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectReleases() throws Exception {
        // Initialize the database
        projectReleaseRepository.saveAndFlush(projectRelease);

        // Get all the projectReleaseList
        restProjectReleaseMockMvc.perform(get("/api/project-releases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectRelease.getId().intValue())))
            .andExpect(jsonPath("$.[*].codename").value(hasItem(DEFAULT_CODENAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }

    @Test
    @Transactional
    public void getProjectRelease() throws Exception {
        // Initialize the database
        projectReleaseRepository.saveAndFlush(projectRelease);

        // Get the projectRelease
        restProjectReleaseMockMvc.perform(get("/api/project-releases/{id}", projectRelease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectRelease.getId().intValue()))
            .andExpect(jsonPath("$.codename").value(DEFAULT_CODENAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectRelease() throws Exception {
        // Get the projectRelease
        restProjectReleaseMockMvc.perform(get("/api/project-releases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectRelease() throws Exception {
        // Initialize the database
        projectReleaseRepository.saveAndFlush(projectRelease);
        int databaseSizeBeforeUpdate = projectReleaseRepository.findAll().size();

        // Update the projectRelease
        ProjectRelease updatedProjectRelease = projectReleaseRepository.findOne(projectRelease.getId());
        updatedProjectRelease
                .codename(UPDATED_CODENAME)
                .description(UPDATED_DESCRIPTION)
                .version(UPDATED_VERSION);
        ProjectReleaseDTO projectReleaseDTO = projectReleaseMapper.projectReleaseToProjectReleaseDTO(updatedProjectRelease);

        restProjectReleaseMockMvc.perform(put("/api/project-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectReleaseDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectRelease in the database
        List<ProjectRelease> projectReleaseList = projectReleaseRepository.findAll();
        assertThat(projectReleaseList).hasSize(databaseSizeBeforeUpdate);
        ProjectRelease testProjectRelease = projectReleaseList.get(projectReleaseList.size() - 1);
        assertThat(testProjectRelease.getCodename()).isEqualTo(UPDATED_CODENAME);
        assertThat(testProjectRelease.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectRelease.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectRelease() throws Exception {
        int databaseSizeBeforeUpdate = projectReleaseRepository.findAll().size();

        // Create the ProjectRelease
        ProjectReleaseDTO projectReleaseDTO = projectReleaseMapper.projectReleaseToProjectReleaseDTO(projectRelease);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectReleaseMockMvc.perform(put("/api/project-releases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectReleaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectRelease in the database
        List<ProjectRelease> projectReleaseList = projectReleaseRepository.findAll();
        assertThat(projectReleaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectRelease() throws Exception {
        // Initialize the database
        projectReleaseRepository.saveAndFlush(projectRelease);
        int databaseSizeBeforeDelete = projectReleaseRepository.findAll().size();

        // Get the projectRelease
        restProjectReleaseMockMvc.perform(delete("/api/project-releases/{id}", projectRelease.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectRelease> projectReleaseList = projectReleaseRepository.findAll();
        assertThat(projectReleaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectRelease.class);
    }
}
