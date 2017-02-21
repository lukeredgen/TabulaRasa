package com.arbysoft.tabularasa.web.rest;

import com.arbysoft.tabularasa.TabulaRasaApp;

import com.arbysoft.tabularasa.domain.ReleaseFeature;
import com.arbysoft.tabularasa.repository.ReleaseFeatureRepository;
import com.arbysoft.tabularasa.service.ReleaseFeatureService;
import com.arbysoft.tabularasa.service.dto.ReleaseFeatureDTO;
import com.arbysoft.tabularasa.service.mapper.ReleaseFeatureMapper;

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
 * Test class for the ReleaseFeatureResource REST controller.
 *
 * @see ReleaseFeatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabulaRasaApp.class)
public class ReleaseFeatureResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReleaseFeatureRepository releaseFeatureRepository;

    @Autowired
    private ReleaseFeatureMapper releaseFeatureMapper;

    @Autowired
    private ReleaseFeatureService releaseFeatureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restReleaseFeatureMockMvc;

    private ReleaseFeature releaseFeature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReleaseFeatureResource releaseFeatureResource = new ReleaseFeatureResource(releaseFeatureService);
        this.restReleaseFeatureMockMvc = MockMvcBuilders.standaloneSetup(releaseFeatureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReleaseFeature createEntity(EntityManager em) {
        ReleaseFeature releaseFeature = new ReleaseFeature()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return releaseFeature;
    }

    @Before
    public void initTest() {
        releaseFeature = createEntity(em);
    }

    @Test
    @Transactional
    public void createReleaseFeature() throws Exception {
        int databaseSizeBeforeCreate = releaseFeatureRepository.findAll().size();

        // Create the ReleaseFeature
        ReleaseFeatureDTO releaseFeatureDTO = releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(releaseFeature);

        restReleaseFeatureMockMvc.perform(post("/api/release-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(releaseFeatureDTO)))
            .andExpect(status().isCreated());

        // Validate the ReleaseFeature in the database
        List<ReleaseFeature> releaseFeatureList = releaseFeatureRepository.findAll();
        assertThat(releaseFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        ReleaseFeature testReleaseFeature = releaseFeatureList.get(releaseFeatureList.size() - 1);
        assertThat(testReleaseFeature.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReleaseFeature.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReleaseFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = releaseFeatureRepository.findAll().size();

        // Create the ReleaseFeature with an existing ID
        ReleaseFeature existingReleaseFeature = new ReleaseFeature();
        existingReleaseFeature.setId(1L);
        ReleaseFeatureDTO existingReleaseFeatureDTO = releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(existingReleaseFeature);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReleaseFeatureMockMvc.perform(post("/api/release-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReleaseFeatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ReleaseFeature> releaseFeatureList = releaseFeatureRepository.findAll();
        assertThat(releaseFeatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = releaseFeatureRepository.findAll().size();
        // set the field null
        releaseFeature.setName(null);

        // Create the ReleaseFeature, which fails.
        ReleaseFeatureDTO releaseFeatureDTO = releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(releaseFeature);

        restReleaseFeatureMockMvc.perform(post("/api/release-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(releaseFeatureDTO)))
            .andExpect(status().isBadRequest());

        List<ReleaseFeature> releaseFeatureList = releaseFeatureRepository.findAll();
        assertThat(releaseFeatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReleaseFeatures() throws Exception {
        // Initialize the database
        releaseFeatureRepository.saveAndFlush(releaseFeature);

        // Get all the releaseFeatureList
        restReleaseFeatureMockMvc.perform(get("/api/release-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(releaseFeature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReleaseFeature() throws Exception {
        // Initialize the database
        releaseFeatureRepository.saveAndFlush(releaseFeature);

        // Get the releaseFeature
        restReleaseFeatureMockMvc.perform(get("/api/release-features/{id}", releaseFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(releaseFeature.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReleaseFeature() throws Exception {
        // Get the releaseFeature
        restReleaseFeatureMockMvc.perform(get("/api/release-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReleaseFeature() throws Exception {
        // Initialize the database
        releaseFeatureRepository.saveAndFlush(releaseFeature);
        int databaseSizeBeforeUpdate = releaseFeatureRepository.findAll().size();

        // Update the releaseFeature
        ReleaseFeature updatedReleaseFeature = releaseFeatureRepository.findOne(releaseFeature.getId());
        updatedReleaseFeature
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        ReleaseFeatureDTO releaseFeatureDTO = releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(updatedReleaseFeature);

        restReleaseFeatureMockMvc.perform(put("/api/release-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(releaseFeatureDTO)))
            .andExpect(status().isOk());

        // Validate the ReleaseFeature in the database
        List<ReleaseFeature> releaseFeatureList = releaseFeatureRepository.findAll();
        assertThat(releaseFeatureList).hasSize(databaseSizeBeforeUpdate);
        ReleaseFeature testReleaseFeature = releaseFeatureList.get(releaseFeatureList.size() - 1);
        assertThat(testReleaseFeature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReleaseFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReleaseFeature() throws Exception {
        int databaseSizeBeforeUpdate = releaseFeatureRepository.findAll().size();

        // Create the ReleaseFeature
        ReleaseFeatureDTO releaseFeatureDTO = releaseFeatureMapper.releaseFeatureToReleaseFeatureDTO(releaseFeature);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReleaseFeatureMockMvc.perform(put("/api/release-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(releaseFeatureDTO)))
            .andExpect(status().isCreated());

        // Validate the ReleaseFeature in the database
        List<ReleaseFeature> releaseFeatureList = releaseFeatureRepository.findAll();
        assertThat(releaseFeatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReleaseFeature() throws Exception {
        // Initialize the database
        releaseFeatureRepository.saveAndFlush(releaseFeature);
        int databaseSizeBeforeDelete = releaseFeatureRepository.findAll().size();

        // Get the releaseFeature
        restReleaseFeatureMockMvc.perform(delete("/api/release-features/{id}", releaseFeature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReleaseFeature> releaseFeatureList = releaseFeatureRepository.findAll();
        assertThat(releaseFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReleaseFeature.class);
    }
}
