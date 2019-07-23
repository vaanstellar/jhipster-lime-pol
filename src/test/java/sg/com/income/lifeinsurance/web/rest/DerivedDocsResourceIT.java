package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.DerivedDocs;
import sg.com.income.lifeinsurance.repository.DerivedDocsRepository;
import sg.com.income.lifeinsurance.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static sg.com.income.lifeinsurance.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DerivedDocsResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class DerivedDocsResourceIT {

    private static final String DEFAULT_DOC_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_DOC_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DerivedDocsRepository derivedDocsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDerivedDocsMockMvc;

    private DerivedDocs derivedDocs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DerivedDocsResource derivedDocsResource = new DerivedDocsResource(derivedDocsRepository);
        this.restDerivedDocsMockMvc = MockMvcBuilders.standaloneSetup(derivedDocsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DerivedDocs createEntity(EntityManager em) {
        DerivedDocs derivedDocs = new DerivedDocs()
            .docContent(DEFAULT_DOC_CONTENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return derivedDocs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DerivedDocs createUpdatedEntity(EntityManager em) {
        DerivedDocs derivedDocs = new DerivedDocs()
            .docContent(UPDATED_DOC_CONTENT)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return derivedDocs;
    }

    @BeforeEach
    public void initTest() {
        derivedDocs = createEntity(em);
    }

    @Test
    @Transactional
    public void createDerivedDocs() throws Exception {
        int databaseSizeBeforeCreate = derivedDocsRepository.findAll().size();

        // Create the DerivedDocs
        restDerivedDocsMockMvc.perform(post("/api/derived-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(derivedDocs)))
            .andExpect(status().isCreated());

        // Validate the DerivedDocs in the database
        List<DerivedDocs> derivedDocsList = derivedDocsRepository.findAll();
        assertThat(derivedDocsList).hasSize(databaseSizeBeforeCreate + 1);
        DerivedDocs testDerivedDocs = derivedDocsList.get(derivedDocsList.size() - 1);
        assertThat(testDerivedDocs.getDocContent()).isEqualTo(DEFAULT_DOC_CONTENT);
        assertThat(testDerivedDocs.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDerivedDocs.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDerivedDocsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = derivedDocsRepository.findAll().size();

        // Create the DerivedDocs with an existing ID
        derivedDocs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDerivedDocsMockMvc.perform(post("/api/derived-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(derivedDocs)))
            .andExpect(status().isBadRequest());

        // Validate the DerivedDocs in the database
        List<DerivedDocs> derivedDocsList = derivedDocsRepository.findAll();
        assertThat(derivedDocsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDerivedDocs() throws Exception {
        // Initialize the database
        derivedDocsRepository.saveAndFlush(derivedDocs);

        // Get all the derivedDocsList
        restDerivedDocsMockMvc.perform(get("/api/derived-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(derivedDocs.getId().intValue())))
            .andExpect(jsonPath("$.[*].docContent").value(hasItem(DEFAULT_DOC_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDerivedDocs() throws Exception {
        // Initialize the database
        derivedDocsRepository.saveAndFlush(derivedDocs);

        // Get the derivedDocs
        restDerivedDocsMockMvc.perform(get("/api/derived-docs/{id}", derivedDocs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(derivedDocs.getId().intValue()))
            .andExpect(jsonPath("$.docContent").value(DEFAULT_DOC_CONTENT.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDerivedDocs() throws Exception {
        // Get the derivedDocs
        restDerivedDocsMockMvc.perform(get("/api/derived-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDerivedDocs() throws Exception {
        // Initialize the database
        derivedDocsRepository.saveAndFlush(derivedDocs);

        int databaseSizeBeforeUpdate = derivedDocsRepository.findAll().size();

        // Update the derivedDocs
        DerivedDocs updatedDerivedDocs = derivedDocsRepository.findById(derivedDocs.getId()).get();
        // Disconnect from session so that the updates on updatedDerivedDocs are not directly saved in db
        em.detach(updatedDerivedDocs);
        updatedDerivedDocs
            .docContent(UPDATED_DOC_CONTENT)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restDerivedDocsMockMvc.perform(put("/api/derived-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDerivedDocs)))
            .andExpect(status().isOk());

        // Validate the DerivedDocs in the database
        List<DerivedDocs> derivedDocsList = derivedDocsRepository.findAll();
        assertThat(derivedDocsList).hasSize(databaseSizeBeforeUpdate);
        DerivedDocs testDerivedDocs = derivedDocsList.get(derivedDocsList.size() - 1);
        assertThat(testDerivedDocs.getDocContent()).isEqualTo(UPDATED_DOC_CONTENT);
        assertThat(testDerivedDocs.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDerivedDocs.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDerivedDocs() throws Exception {
        int databaseSizeBeforeUpdate = derivedDocsRepository.findAll().size();

        // Create the DerivedDocs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDerivedDocsMockMvc.perform(put("/api/derived-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(derivedDocs)))
            .andExpect(status().isBadRequest());

        // Validate the DerivedDocs in the database
        List<DerivedDocs> derivedDocsList = derivedDocsRepository.findAll();
        assertThat(derivedDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDerivedDocs() throws Exception {
        // Initialize the database
        derivedDocsRepository.saveAndFlush(derivedDocs);

        int databaseSizeBeforeDelete = derivedDocsRepository.findAll().size();

        // Delete the derivedDocs
        restDerivedDocsMockMvc.perform(delete("/api/derived-docs/{id}", derivedDocs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DerivedDocs> derivedDocsList = derivedDocsRepository.findAll();
        assertThat(derivedDocsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DerivedDocs.class);
        DerivedDocs derivedDocs1 = new DerivedDocs();
        derivedDocs1.setId(1L);
        DerivedDocs derivedDocs2 = new DerivedDocs();
        derivedDocs2.setId(derivedDocs1.getId());
        assertThat(derivedDocs1).isEqualTo(derivedDocs2);
        derivedDocs2.setId(2L);
        assertThat(derivedDocs1).isNotEqualTo(derivedDocs2);
        derivedDocs1.setId(null);
        assertThat(derivedDocs1).isNotEqualTo(derivedDocs2);
    }
}
