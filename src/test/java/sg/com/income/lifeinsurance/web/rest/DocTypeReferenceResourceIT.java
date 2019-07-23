package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.DocTypeReference;
import sg.com.income.lifeinsurance.repository.DocTypeReferenceRepository;
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
 * Integration tests for the {@Link DocTypeReferenceResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class DocTypeReferenceResourceIT {

    private static final String DEFAULT_DOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_STORAGE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_STORAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DocTypeReferenceRepository docTypeReferenceRepository;

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

    private MockMvc restDocTypeReferenceMockMvc;

    private DocTypeReference docTypeReference;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocTypeReferenceResource docTypeReferenceResource = new DocTypeReferenceResource(docTypeReferenceRepository);
        this.restDocTypeReferenceMockMvc = MockMvcBuilders.standaloneSetup(docTypeReferenceResource)
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
    public static DocTypeReference createEntity(EntityManager em) {
        DocTypeReference docTypeReference = new DocTypeReference()
            .docType(DEFAULT_DOC_TYPE)
            .docStorage(DEFAULT_DOC_STORAGE)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return docTypeReference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocTypeReference createUpdatedEntity(EntityManager em) {
        DocTypeReference docTypeReference = new DocTypeReference()
            .docType(UPDATED_DOC_TYPE)
            .docStorage(UPDATED_DOC_STORAGE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return docTypeReference;
    }

    @BeforeEach
    public void initTest() {
        docTypeReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocTypeReference() throws Exception {
        int databaseSizeBeforeCreate = docTypeReferenceRepository.findAll().size();

        // Create the DocTypeReference
        restDocTypeReferenceMockMvc.perform(post("/api/doc-type-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docTypeReference)))
            .andExpect(status().isCreated());

        // Validate the DocTypeReference in the database
        List<DocTypeReference> docTypeReferenceList = docTypeReferenceRepository.findAll();
        assertThat(docTypeReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        DocTypeReference testDocTypeReference = docTypeReferenceList.get(docTypeReferenceList.size() - 1);
        assertThat(testDocTypeReference.getDocType()).isEqualTo(DEFAULT_DOC_TYPE);
        assertThat(testDocTypeReference.getDocStorage()).isEqualTo(DEFAULT_DOC_STORAGE);
        assertThat(testDocTypeReference.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDocTypeReference.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDocTypeReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docTypeReferenceRepository.findAll().size();

        // Create the DocTypeReference with an existing ID
        docTypeReference.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocTypeReferenceMockMvc.perform(post("/api/doc-type-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docTypeReference)))
            .andExpect(status().isBadRequest());

        // Validate the DocTypeReference in the database
        List<DocTypeReference> docTypeReferenceList = docTypeReferenceRepository.findAll();
        assertThat(docTypeReferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocTypeReferences() throws Exception {
        // Initialize the database
        docTypeReferenceRepository.saveAndFlush(docTypeReference);

        // Get all the docTypeReferenceList
        restDocTypeReferenceMockMvc.perform(get("/api/doc-type-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docTypeReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].docStorage").value(hasItem(DEFAULT_DOC_STORAGE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDocTypeReference() throws Exception {
        // Initialize the database
        docTypeReferenceRepository.saveAndFlush(docTypeReference);

        // Get the docTypeReference
        restDocTypeReferenceMockMvc.perform(get("/api/doc-type-references/{id}", docTypeReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(docTypeReference.getId().intValue()))
            .andExpect(jsonPath("$.docType").value(DEFAULT_DOC_TYPE.toString()))
            .andExpect(jsonPath("$.docStorage").value(DEFAULT_DOC_STORAGE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocTypeReference() throws Exception {
        // Get the docTypeReference
        restDocTypeReferenceMockMvc.perform(get("/api/doc-type-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocTypeReference() throws Exception {
        // Initialize the database
        docTypeReferenceRepository.saveAndFlush(docTypeReference);

        int databaseSizeBeforeUpdate = docTypeReferenceRepository.findAll().size();

        // Update the docTypeReference
        DocTypeReference updatedDocTypeReference = docTypeReferenceRepository.findById(docTypeReference.getId()).get();
        // Disconnect from session so that the updates on updatedDocTypeReference are not directly saved in db
        em.detach(updatedDocTypeReference);
        updatedDocTypeReference
            .docType(UPDATED_DOC_TYPE)
            .docStorage(UPDATED_DOC_STORAGE)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restDocTypeReferenceMockMvc.perform(put("/api/doc-type-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocTypeReference)))
            .andExpect(status().isOk());

        // Validate the DocTypeReference in the database
        List<DocTypeReference> docTypeReferenceList = docTypeReferenceRepository.findAll();
        assertThat(docTypeReferenceList).hasSize(databaseSizeBeforeUpdate);
        DocTypeReference testDocTypeReference = docTypeReferenceList.get(docTypeReferenceList.size() - 1);
        assertThat(testDocTypeReference.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testDocTypeReference.getDocStorage()).isEqualTo(UPDATED_DOC_STORAGE);
        assertThat(testDocTypeReference.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDocTypeReference.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocTypeReference() throws Exception {
        int databaseSizeBeforeUpdate = docTypeReferenceRepository.findAll().size();

        // Create the DocTypeReference

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocTypeReferenceMockMvc.perform(put("/api/doc-type-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docTypeReference)))
            .andExpect(status().isBadRequest());

        // Validate the DocTypeReference in the database
        List<DocTypeReference> docTypeReferenceList = docTypeReferenceRepository.findAll();
        assertThat(docTypeReferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocTypeReference() throws Exception {
        // Initialize the database
        docTypeReferenceRepository.saveAndFlush(docTypeReference);

        int databaseSizeBeforeDelete = docTypeReferenceRepository.findAll().size();

        // Delete the docTypeReference
        restDocTypeReferenceMockMvc.perform(delete("/api/doc-type-references/{id}", docTypeReference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocTypeReference> docTypeReferenceList = docTypeReferenceRepository.findAll();
        assertThat(docTypeReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocTypeReference.class);
        DocTypeReference docTypeReference1 = new DocTypeReference();
        docTypeReference1.setId(1L);
        DocTypeReference docTypeReference2 = new DocTypeReference();
        docTypeReference2.setId(docTypeReference1.getId());
        assertThat(docTypeReference1).isEqualTo(docTypeReference2);
        docTypeReference2.setId(2L);
        assertThat(docTypeReference1).isNotEqualTo(docTypeReference2);
        docTypeReference1.setId(null);
        assertThat(docTypeReference1).isNotEqualTo(docTypeReference2);
    }
}
