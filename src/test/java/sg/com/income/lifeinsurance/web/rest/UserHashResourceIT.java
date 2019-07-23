package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.UserHash;
import sg.com.income.lifeinsurance.repository.UserHashRepository;
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
 * Integration tests for the {@Link UserHashResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class UserHashResourceIT {

    private static final String DEFAULT_NRIC = "AAAAAAAAAA";
    private static final String UPDATED_NRIC = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_1 = "AAAAAAAAAA";
    private static final String UPDATED_HASH_1 = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_2 = "AAAAAAAAAA";
    private static final String UPDATED_HASH_2 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserHashRepository userHashRepository;

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

    private MockMvc restUserHashMockMvc;

    private UserHash userHash;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserHashResource userHashResource = new UserHashResource(userHashRepository);
        this.restUserHashMockMvc = MockMvcBuilders.standaloneSetup(userHashResource)
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
    public static UserHash createEntity(EntityManager em) {
        UserHash userHash = new UserHash()
            .nric(DEFAULT_NRIC)
            .hash1(DEFAULT_HASH_1)
            .hash2(DEFAULT_HASH_2)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return userHash;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserHash createUpdatedEntity(EntityManager em) {
        UserHash userHash = new UserHash()
            .nric(UPDATED_NRIC)
            .hash1(UPDATED_HASH_1)
            .hash2(UPDATED_HASH_2)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return userHash;
    }

    @BeforeEach
    public void initTest() {
        userHash = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserHash() throws Exception {
        int databaseSizeBeforeCreate = userHashRepository.findAll().size();

        // Create the UserHash
        restUserHashMockMvc.perform(post("/api/user-hashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userHash)))
            .andExpect(status().isCreated());

        // Validate the UserHash in the database
        List<UserHash> userHashList = userHashRepository.findAll();
        assertThat(userHashList).hasSize(databaseSizeBeforeCreate + 1);
        UserHash testUserHash = userHashList.get(userHashList.size() - 1);
        assertThat(testUserHash.getNric()).isEqualTo(DEFAULT_NRIC);
        assertThat(testUserHash.getHash1()).isEqualTo(DEFAULT_HASH_1);
        assertThat(testUserHash.getHash2()).isEqualTo(DEFAULT_HASH_2);
        assertThat(testUserHash.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserHash.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createUserHashWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userHashRepository.findAll().size();

        // Create the UserHash with an existing ID
        userHash.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserHashMockMvc.perform(post("/api/user-hashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userHash)))
            .andExpect(status().isBadRequest());

        // Validate the UserHash in the database
        List<UserHash> userHashList = userHashRepository.findAll();
        assertThat(userHashList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserHashes() throws Exception {
        // Initialize the database
        userHashRepository.saveAndFlush(userHash);

        // Get all the userHashList
        restUserHashMockMvc.perform(get("/api/user-hashes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userHash.getId().intValue())))
            .andExpect(jsonPath("$.[*].nric").value(hasItem(DEFAULT_NRIC.toString())))
            .andExpect(jsonPath("$.[*].hash1").value(hasItem(DEFAULT_HASH_1.toString())))
            .andExpect(jsonPath("$.[*].hash2").value(hasItem(DEFAULT_HASH_2.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserHash() throws Exception {
        // Initialize the database
        userHashRepository.saveAndFlush(userHash);

        // Get the userHash
        restUserHashMockMvc.perform(get("/api/user-hashes/{id}", userHash.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userHash.getId().intValue()))
            .andExpect(jsonPath("$.nric").value(DEFAULT_NRIC.toString()))
            .andExpect(jsonPath("$.hash1").value(DEFAULT_HASH_1.toString()))
            .andExpect(jsonPath("$.hash2").value(DEFAULT_HASH_2.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserHash() throws Exception {
        // Get the userHash
        restUserHashMockMvc.perform(get("/api/user-hashes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserHash() throws Exception {
        // Initialize the database
        userHashRepository.saveAndFlush(userHash);

        int databaseSizeBeforeUpdate = userHashRepository.findAll().size();

        // Update the userHash
        UserHash updatedUserHash = userHashRepository.findById(userHash.getId()).get();
        // Disconnect from session so that the updates on updatedUserHash are not directly saved in db
        em.detach(updatedUserHash);
        updatedUserHash
            .nric(UPDATED_NRIC)
            .hash1(UPDATED_HASH_1)
            .hash2(UPDATED_HASH_2)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restUserHashMockMvc.perform(put("/api/user-hashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserHash)))
            .andExpect(status().isOk());

        // Validate the UserHash in the database
        List<UserHash> userHashList = userHashRepository.findAll();
        assertThat(userHashList).hasSize(databaseSizeBeforeUpdate);
        UserHash testUserHash = userHashList.get(userHashList.size() - 1);
        assertThat(testUserHash.getNric()).isEqualTo(UPDATED_NRIC);
        assertThat(testUserHash.getHash1()).isEqualTo(UPDATED_HASH_1);
        assertThat(testUserHash.getHash2()).isEqualTo(UPDATED_HASH_2);
        assertThat(testUserHash.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserHash.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserHash() throws Exception {
        int databaseSizeBeforeUpdate = userHashRepository.findAll().size();

        // Create the UserHash

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserHashMockMvc.perform(put("/api/user-hashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userHash)))
            .andExpect(status().isBadRequest());

        // Validate the UserHash in the database
        List<UserHash> userHashList = userHashRepository.findAll();
        assertThat(userHashList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserHash() throws Exception {
        // Initialize the database
        userHashRepository.saveAndFlush(userHash);

        int databaseSizeBeforeDelete = userHashRepository.findAll().size();

        // Delete the userHash
        restUserHashMockMvc.perform(delete("/api/user-hashes/{id}", userHash.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserHash> userHashList = userHashRepository.findAll();
        assertThat(userHashList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserHash.class);
        UserHash userHash1 = new UserHash();
        userHash1.setId(1L);
        UserHash userHash2 = new UserHash();
        userHash2.setId(userHash1.getId());
        assertThat(userHash1).isEqualTo(userHash2);
        userHash2.setId(2L);
        assertThat(userHash1).isNotEqualTo(userHash2);
        userHash1.setId(null);
        assertThat(userHash1).isNotEqualTo(userHash2);
    }
}
