package com.arbysoft.tabularasa.web.rest;

import com.arbysoft.tabularasa.TabulaRasaApp;

import com.arbysoft.tabularasa.domain.FeatureFunction;
import com.arbysoft.tabularasa.repository.FeatureFunctionRepository;
import com.arbysoft.tabularasa.service.FeatureFunctionService;
import com.arbysoft.tabularasa.service.dto.FeatureFunctionDTO;
import com.arbysoft.tabularasa.service.mapper.FeatureFunctionMapper;

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
 * Test class for the FeatureFunctionResource REST controller.
 *
 * @see FeatureFunctionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabulaRasaApp.class)
public class FeatureFunctionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FeatureFunctionRepository featureFunctionRepository;

    @Autowired
    private FeatureFunctionMapper featureFunctionMapper;

    @Autowired
    private FeatureFunctionService featureFunctionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restFeatureFunctionMockMvc;

    private FeatureFunction featureFunction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeatureFunctionResource featureFunctionResource = new FeatureFunctionResource(featureFunctionService);
        this.restFeatureFunctionMockMvc = MockMvcBuilders.standaloneSetup(featureFunctionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeatureFunction createEntity(EntityManager em) {
        FeatureFunction featureFunction = new FeatureFunction()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return featureFunction;
    }

    @Before
    public void initTest() {
        featureFunction = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeatureFunction() throws Exception {
        int databaseSizeBeforeCreate = featureFunctionRepository.findAll().size();

        // Create the FeatureFunction
        FeatureFunctionDTO featureFunctionDTO = featureFunctionMapper.featureFunctionToFeatureFunctionDTO(featureFunction);

        restFeatureFunctionMockMvc.perform(post("/api/feature-functions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureFunctionDTO)))
            .andExpect(status().isCreated());

        // Validate the FeatureFunction in the database
        List<FeatureFunction> featureFunctionList = featureFunctionRepository.findAll();
        assertThat(featureFunctionList).hasSize(databaseSizeBeforeCreate + 1);
        FeatureFunction testFeatureFunction = featureFunctionList.get(featureFunctionList.size() - 1);
        assertThat(testFeatureFunction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeatureFunction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFeatureFunctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featureFunctionRepository.findAll().size();

        // Create the FeatureFunction with an existing ID
        FeatureFunction existingFeatureFunction = new FeatureFunction();
        existingFeatureFunction.setId(1L);
        FeatureFunctionDTO existingFeatureFunctionDTO = featureFunctionMapper.featureFunctionToFeatureFunctionDTO(existingFeatureFunction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureFunctionMockMvc.perform(post("/api/feature-functions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFeatureFunctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FeatureFunction> featureFunctionList = featureFunctionRepository.findAll();
        assertThat(featureFunctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureFunctionRepository.findAll().size();
        // set the field null
        featureFunction.setName(null);

        // Create the FeatureFunction, which fails.
        FeatureFunctionDTO featureFunctionDTO = featureFunctionMapper.featureFunctionToFeatureFunctionDTO(featureFunction);

        restFeatureFunctionMockMvc.perform(post("/api/feature-functions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureFunctionDTO)))
            .andExpect(status().isBadRequest());

        List<FeatureFunction> featureFunctionList = featureFunctionRepository.findAll();
        assertThat(featureFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeatureFunctions() throws Exception {
        // Initialize the database
        featureFunctionRepository.saveAndFlush(featureFunction);

        // Get all the featureFunctionList
        restFeatureFunctionMockMvc.perform(get("/api/feature-functions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(featureFunction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFeatureFunction() throws Exception {
        // Initialize the database
        featureFunctionRepository.saveAndFlush(featureFunction);

        // Get the featureFunction
        restFeatureFunctionMockMvc.perform(get("/api/feature-functions/{id}", featureFunction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(featureFunction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeatureFunction() throws Exception {
        // Get the featureFunction
        restFeatureFunctionMockMvc.perform(get("/api/feature-functions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeatureFunction() throws Exception {
        // Initialize the database
        featureFunctionRepository.saveAndFlush(featureFunction);
        int databaseSizeBeforeUpdate = featureFunctionRepository.findAll().size();

        // Update the featureFunction
        FeatureFunction updatedFeatureFunction = featureFunctionRepository.findOne(featureFunction.getId());
        updatedFeatureFunction
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        FeatureFunctionDTO featureFunctionDTO = featureFunctionMapper.featureFunctionToFeatureFunctionDTO(updatedFeatureFunction);

        restFeatureFunctionMockMvc.perform(put("/api/feature-functions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureFunctionDTO)))
            .andExpect(status().isOk());

        // Validate the FeatureFunction in the database
        List<FeatureFunction> featureFunctionList = featureFunctionRepository.findAll();
        assertThat(featureFunctionList).hasSize(databaseSizeBeforeUpdate);
        FeatureFunction testFeatureFunction = featureFunctionList.get(featureFunctionList.size() - 1);
        assertThat(testFeatureFunction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeatureFunction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFeatureFunction() throws Exception {
        int databaseSizeBeforeUpdate = featureFunctionRepository.findAll().size();

        // Create the FeatureFunction
        FeatureFunctionDTO featureFunctionDTO = featureFunctionMapper.featureFunctionToFeatureFunctionDTO(featureFunction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeatureFunctionMockMvc.perform(put("/api/feature-functions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureFunctionDTO)))
            .andExpect(status().isCreated());

        // Validate the FeatureFunction in the database
        List<FeatureFunction> featureFunctionList = featureFunctionRepository.findAll();
        assertThat(featureFunctionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFeatureFunction() throws Exception {
        // Initialize the database
        featureFunctionRepository.saveAndFlush(featureFunction);
        int databaseSizeBeforeDelete = featureFunctionRepository.findAll().size();

        // Get the featureFunction
        restFeatureFunctionMockMvc.perform(delete("/api/feature-functions/{id}", featureFunction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FeatureFunction> featureFunctionList = featureFunctionRepository.findAll();
        assertThat(featureFunctionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeatureFunction.class);
    }
}
