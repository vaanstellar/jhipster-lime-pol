package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.UserSuspension;
import sg.com.income.lifeinsurance.repository.UserSuspensionRepository;
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
 * Integration tests for the {@Link UserSuspensionResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class UserSuspensionResourceIT {

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_RETRY_COUNT = 1;
    private static final Integer UPDATED_RETRY_COUNT = 2;

    private static final Long DEFAULT_SUSPENSION_TIME_IN_MINUTES = 1L;
    private static final Long UPDATED_SUSPENSION_TIME_IN_MINUTES = 2L;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserSuspensionRepository userSuspensionRepository;

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

    private MockMvc restUserSuspensionMockMvc;

    private UserSuspension userSuspension;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserSuspensionResource userSuspensionResource = new UserSuspensionResource(userSuspensionRepository);
        this.restUserSuspensionMockMvc = MockMvcBuilders.standaloneSetup(userSuspensionResource)
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
    public static UserSuspension createEntity(EntityManager em) {
        UserSuspension userSuspension = new UserSuspension()
            .emailId(DEFAULT_EMAIL_ID)
            .retryCount(DEFAULT_RETRY_COUNT)
            .suspensionTimeInMinutes(DEFAULT_SUSPENSION_TIME_IN_MINUTES)
            .reason(DEFAULT_REASON)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return userSuspension;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSuspension createUpdatedEntity(EntityManager em) {
        UserSuspension userSuspension = new UserSuspension()
            .emailId(UPDATED_EMAIL_ID)
            .retryCount(UPDATED_RETRY_COUNT)
            .suspensionTimeInMinutes(UPDATED_SUSPENSION_TIME_IN_MINUTES)
            .reason(UPDATED_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return userSuspension;
    }

    @BeforeEach
    public void initTest() {
        userSuspension = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSuspension() throws Exception {
        int databaseSizeBeforeCreate = userSuspensionRepository.findAll().size();

        // Create the UserSuspension
        restUserSuspensionMockMvc.perform(post("/api/user-suspensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSuspension)))
            .andExpect(status().isCreated());

        // Validate the UserSuspension in the database
        List<UserSuspension> userSuspensionList = userSuspensionRepository.findAll();
        assertThat(userSuspensionList).hasSize(databaseSizeBeforeCreate + 1);
        UserSuspension testUserSuspension = userSuspensionList.get(userSuspensionList.size() - 1);
        assertThat(testUserSuspension.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testUserSuspension.getRetryCount()).isEqualTo(DEFAULT_RETRY_COUNT);
        assertThat(testUserSuspension.getSuspensionTimeInMinutes()).isEqualTo(DEFAULT_SUSPENSION_TIME_IN_MINUTES);
        assertThat(testUserSuspension.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testUserSuspension.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserSuspension.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createUserSuspensionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSuspensionRepository.findAll().size();

        // Create the UserSuspension with an existing ID
        userSuspension.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSuspensionMockMvc.perform(post("/api/user-suspensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSuspension)))
            .andExpect(status().isBadRequest());

        // Validate the UserSuspension in the database
        List<UserSuspension> userSuspensionList = userSuspensionRepository.findAll();
        assertThat(userSuspensionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserSuspensions() throws Exception {
        // Initialize the database
        userSuspensionRepository.saveAndFlush(userSuspension);

        // Get all the userSuspensionList
        restUserSuspensionMockMvc.perform(get("/api/user-suspensions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSuspension.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].retryCount").value(hasItem(DEFAULT_RETRY_COUNT)))
            .andExpect(jsonPath("$.[*].suspensionTimeInMinutes").value(hasItem(DEFAULT_SUSPENSION_TIME_IN_MINUTES.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserSuspension() throws Exception {
        // Initialize the database
        userSuspensionRepository.saveAndFlush(userSuspension);

        // Get the userSuspension
        restUserSuspensionMockMvc.perform(get("/api/user-suspensions/{id}", userSuspension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userSuspension.getId().intValue()))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.retryCount").value(DEFAULT_RETRY_COUNT))
            .andExpect(jsonPath("$.suspensionTimeInMinutes").value(DEFAULT_SUSPENSION_TIME_IN_MINUTES.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserSuspension() throws Exception {
        // Get the userSuspension
        restUserSuspensionMockMvc.perform(get("/api/user-suspensions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSuspension() throws Exception {
        // Initialize the database
        userSuspensionRepository.saveAndFlush(userSuspension);

        int databaseSizeBeforeUpdate = userSuspensionRepository.findAll().size();

        // Update the userSuspension
        UserSuspension updatedUserSuspension = userSuspensionRepository.findById(userSuspension.getId()).get();
        // Disconnect from session so that the updates on updatedUserSuspension are not directly saved in db
        em.detach(updatedUserSuspension);
        updatedUserSuspension
            .emailId(UPDATED_EMAIL_ID)
            .retryCount(UPDATED_RETRY_COUNT)
            .suspensionTimeInMinutes(UPDATED_SUSPENSION_TIME_IN_MINUTES)
            .reason(UPDATED_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restUserSuspensionMockMvc.perform(put("/api/user-suspensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserSuspension)))
            .andExpect(status().isOk());

        // Validate the UserSuspension in the database
        List<UserSuspension> userSuspensionList = userSuspensionRepository.findAll();
        assertThat(userSuspensionList).hasSize(databaseSizeBeforeUpdate);
        UserSuspension testUserSuspension = userSuspensionList.get(userSuspensionList.size() - 1);
        assertThat(testUserSuspension.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testUserSuspension.getRetryCount()).isEqualTo(UPDATED_RETRY_COUNT);
        assertThat(testUserSuspension.getSuspensionTimeInMinutes()).isEqualTo(UPDATED_SUSPENSION_TIME_IN_MINUTES);
        assertThat(testUserSuspension.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testUserSuspension.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserSuspension.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSuspension() throws Exception {
        int databaseSizeBeforeUpdate = userSuspensionRepository.findAll().size();

        // Create the UserSuspension

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSuspensionMockMvc.perform(put("/api/user-suspensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSuspension)))
            .andExpect(status().isBadRequest());

        // Validate the UserSuspension in the database
        List<UserSuspension> userSuspensionList = userSuspensionRepository.findAll();
        assertThat(userSuspensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserSuspension() throws Exception {
        // Initialize the database
        userSuspensionRepository.saveAndFlush(userSuspension);

        int databaseSizeBeforeDelete = userSuspensionRepository.findAll().size();

        // Delete the userSuspension
        restUserSuspensionMockMvc.perform(delete("/api/user-suspensions/{id}", userSuspension.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSuspension> userSuspensionList = userSuspensionRepository.findAll();
        assertThat(userSuspensionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSuspension.class);
        UserSuspension userSuspension1 = new UserSuspension();
        userSuspension1.setId(1L);
        UserSuspension userSuspension2 = new UserSuspension();
        userSuspension2.setId(userSuspension1.getId());
        assertThat(userSuspension1).isEqualTo(userSuspension2);
        userSuspension2.setId(2L);
        assertThat(userSuspension1).isNotEqualTo(userSuspension2);
        userSuspension1.setId(null);
        assertThat(userSuspension1).isNotEqualTo(userSuspension2);
    }
}
