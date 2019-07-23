package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.PolicyPaymentDetails;
import sg.com.income.lifeinsurance.repository.PolicyPaymentDetailsRepository;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static sg.com.income.lifeinsurance.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PolicyPaymentDetailsResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class PolicyPaymentDetailsResourceIT {

    private static final String DEFAULT_PAYMENT_TRANSACTION_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TRANSACTION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PRN_NO = "AAAAAAAAAA";
    private static final String UPDATED_PRN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ENCRYPTED_PRN_NO = "AAAAAAAAAA";
    private static final String UPDATED_ENCRYPTED_PRN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_FIRST_PREMIUM = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_FIRST_PREMIUM = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_ESB_PAYMENT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_ESB_PAYMENT_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PolicyPaymentDetailsRepository policyPaymentDetailsRepository;

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

    private MockMvc restPolicyPaymentDetailsMockMvc;

    private PolicyPaymentDetails policyPaymentDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolicyPaymentDetailsResource policyPaymentDetailsResource = new PolicyPaymentDetailsResource(policyPaymentDetailsRepository);
        this.restPolicyPaymentDetailsMockMvc = MockMvcBuilders.standaloneSetup(policyPaymentDetailsResource)
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
    public static PolicyPaymentDetails createEntity(EntityManager em) {
        PolicyPaymentDetails policyPaymentDetails = new PolicyPaymentDetails()
            .paymentTransactionNo(DEFAULT_PAYMENT_TRANSACTION_NO)
            .prnNo(DEFAULT_PRN_NO)
            .encryptedPrnNo(DEFAULT_ENCRYPTED_PRN_NO)
            .totalFirstPremium(DEFAULT_TOTAL_FIRST_PREMIUM)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .esbPaymentMode(DEFAULT_ESB_PAYMENT_MODE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .createdTime(DEFAULT_CREATED_TIME)
            .modifiedTime(DEFAULT_MODIFIED_TIME);
        return policyPaymentDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyPaymentDetails createUpdatedEntity(EntityManager em) {
        PolicyPaymentDetails policyPaymentDetails = new PolicyPaymentDetails()
            .paymentTransactionNo(UPDATED_PAYMENT_TRANSACTION_NO)
            .prnNo(UPDATED_PRN_NO)
            .encryptedPrnNo(UPDATED_ENCRYPTED_PRN_NO)
            .totalFirstPremium(UPDATED_TOTAL_FIRST_PREMIUM)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .esbPaymentMode(UPDATED_ESB_PAYMENT_MODE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdTime(UPDATED_CREATED_TIME)
            .modifiedTime(UPDATED_MODIFIED_TIME);
        return policyPaymentDetails;
    }

    @BeforeEach
    public void initTest() {
        policyPaymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyPaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = policyPaymentDetailsRepository.findAll().size();

        // Create the PolicyPaymentDetails
        restPolicyPaymentDetailsMockMvc.perform(post("/api/policy-payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyPaymentDetails)))
            .andExpect(status().isCreated());

        // Validate the PolicyPaymentDetails in the database
        List<PolicyPaymentDetails> policyPaymentDetailsList = policyPaymentDetailsRepository.findAll();
        assertThat(policyPaymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyPaymentDetails testPolicyPaymentDetails = policyPaymentDetailsList.get(policyPaymentDetailsList.size() - 1);
        assertThat(testPolicyPaymentDetails.getPaymentTransactionNo()).isEqualTo(DEFAULT_PAYMENT_TRANSACTION_NO);
        assertThat(testPolicyPaymentDetails.getPrnNo()).isEqualTo(DEFAULT_PRN_NO);
        assertThat(testPolicyPaymentDetails.getEncryptedPrnNo()).isEqualTo(DEFAULT_ENCRYPTED_PRN_NO);
        assertThat(testPolicyPaymentDetails.getTotalFirstPremium()).isEqualTo(DEFAULT_TOTAL_FIRST_PREMIUM);
        assertThat(testPolicyPaymentDetails.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testPolicyPaymentDetails.getEsbPaymentMode()).isEqualTo(DEFAULT_ESB_PAYMENT_MODE);
        assertThat(testPolicyPaymentDetails.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPolicyPaymentDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPolicyPaymentDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testPolicyPaymentDetails.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testPolicyPaymentDetails.getModifiedTime()).isEqualTo(DEFAULT_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void createPolicyPaymentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyPaymentDetailsRepository.findAll().size();

        // Create the PolicyPaymentDetails with an existing ID
        policyPaymentDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyPaymentDetailsMockMvc.perform(post("/api/policy-payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyPaymentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyPaymentDetails in the database
        List<PolicyPaymentDetails> policyPaymentDetailsList = policyPaymentDetailsRepository.findAll();
        assertThat(policyPaymentDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPolicyPaymentDetails() throws Exception {
        // Initialize the database
        policyPaymentDetailsRepository.saveAndFlush(policyPaymentDetails);

        // Get all the policyPaymentDetailsList
        restPolicyPaymentDetailsMockMvc.perform(get("/api/policy-payment-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyPaymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentTransactionNo").value(hasItem(DEFAULT_PAYMENT_TRANSACTION_NO.toString())))
            .andExpect(jsonPath("$.[*].prnNo").value(hasItem(DEFAULT_PRN_NO.toString())))
            .andExpect(jsonPath("$.[*].encryptedPrnNo").value(hasItem(DEFAULT_ENCRYPTED_PRN_NO.toString())))
            .andExpect(jsonPath("$.[*].totalFirstPremium").value(hasItem(DEFAULT_TOTAL_FIRST_PREMIUM.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].esbPaymentMode").value(hasItem(DEFAULT_ESB_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPolicyPaymentDetails() throws Exception {
        // Initialize the database
        policyPaymentDetailsRepository.saveAndFlush(policyPaymentDetails);

        // Get the policyPaymentDetails
        restPolicyPaymentDetailsMockMvc.perform(get("/api/policy-payment-details/{id}", policyPaymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyPaymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.paymentTransactionNo").value(DEFAULT_PAYMENT_TRANSACTION_NO.toString()))
            .andExpect(jsonPath("$.prnNo").value(DEFAULT_PRN_NO.toString()))
            .andExpect(jsonPath("$.encryptedPrnNo").value(DEFAULT_ENCRYPTED_PRN_NO.toString()))
            .andExpect(jsonPath("$.totalFirstPremium").value(DEFAULT_TOTAL_FIRST_PREMIUM.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.esbPaymentMode").value(DEFAULT_ESB_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.modifiedTime").value(DEFAULT_MODIFIED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyPaymentDetails() throws Exception {
        // Get the policyPaymentDetails
        restPolicyPaymentDetailsMockMvc.perform(get("/api/policy-payment-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyPaymentDetails() throws Exception {
        // Initialize the database
        policyPaymentDetailsRepository.saveAndFlush(policyPaymentDetails);

        int databaseSizeBeforeUpdate = policyPaymentDetailsRepository.findAll().size();

        // Update the policyPaymentDetails
        PolicyPaymentDetails updatedPolicyPaymentDetails = policyPaymentDetailsRepository.findById(policyPaymentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPolicyPaymentDetails are not directly saved in db
        em.detach(updatedPolicyPaymentDetails);
        updatedPolicyPaymentDetails
            .paymentTransactionNo(UPDATED_PAYMENT_TRANSACTION_NO)
            .prnNo(UPDATED_PRN_NO)
            .encryptedPrnNo(UPDATED_ENCRYPTED_PRN_NO)
            .totalFirstPremium(UPDATED_TOTAL_FIRST_PREMIUM)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .esbPaymentMode(UPDATED_ESB_PAYMENT_MODE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .createdTime(UPDATED_CREATED_TIME)
            .modifiedTime(UPDATED_MODIFIED_TIME);

        restPolicyPaymentDetailsMockMvc.perform(put("/api/policy-payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolicyPaymentDetails)))
            .andExpect(status().isOk());

        // Validate the PolicyPaymentDetails in the database
        List<PolicyPaymentDetails> policyPaymentDetailsList = policyPaymentDetailsRepository.findAll();
        assertThat(policyPaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PolicyPaymentDetails testPolicyPaymentDetails = policyPaymentDetailsList.get(policyPaymentDetailsList.size() - 1);
        assertThat(testPolicyPaymentDetails.getPaymentTransactionNo()).isEqualTo(UPDATED_PAYMENT_TRANSACTION_NO);
        assertThat(testPolicyPaymentDetails.getPrnNo()).isEqualTo(UPDATED_PRN_NO);
        assertThat(testPolicyPaymentDetails.getEncryptedPrnNo()).isEqualTo(UPDATED_ENCRYPTED_PRN_NO);
        assertThat(testPolicyPaymentDetails.getTotalFirstPremium()).isEqualTo(UPDATED_TOTAL_FIRST_PREMIUM);
        assertThat(testPolicyPaymentDetails.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testPolicyPaymentDetails.getEsbPaymentMode()).isEqualTo(UPDATED_ESB_PAYMENT_MODE);
        assertThat(testPolicyPaymentDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPolicyPaymentDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPolicyPaymentDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testPolicyPaymentDetails.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testPolicyPaymentDetails.getModifiedTime()).isEqualTo(UPDATED_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = policyPaymentDetailsRepository.findAll().size();

        // Create the PolicyPaymentDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyPaymentDetailsMockMvc.perform(put("/api/policy-payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyPaymentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyPaymentDetails in the database
        List<PolicyPaymentDetails> policyPaymentDetailsList = policyPaymentDetailsRepository.findAll();
        assertThat(policyPaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolicyPaymentDetails() throws Exception {
        // Initialize the database
        policyPaymentDetailsRepository.saveAndFlush(policyPaymentDetails);

        int databaseSizeBeforeDelete = policyPaymentDetailsRepository.findAll().size();

        // Delete the policyPaymentDetails
        restPolicyPaymentDetailsMockMvc.perform(delete("/api/policy-payment-details/{id}", policyPaymentDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PolicyPaymentDetails> policyPaymentDetailsList = policyPaymentDetailsRepository.findAll();
        assertThat(policyPaymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyPaymentDetails.class);
        PolicyPaymentDetails policyPaymentDetails1 = new PolicyPaymentDetails();
        policyPaymentDetails1.setId(1L);
        PolicyPaymentDetails policyPaymentDetails2 = new PolicyPaymentDetails();
        policyPaymentDetails2.setId(policyPaymentDetails1.getId());
        assertThat(policyPaymentDetails1).isEqualTo(policyPaymentDetails2);
        policyPaymentDetails2.setId(2L);
        assertThat(policyPaymentDetails1).isNotEqualTo(policyPaymentDetails2);
        policyPaymentDetails1.setId(null);
        assertThat(policyPaymentDetails1).isNotEqualTo(policyPaymentDetails2);
    }
}
