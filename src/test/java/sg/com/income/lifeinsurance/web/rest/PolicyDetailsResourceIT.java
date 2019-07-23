package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.PolicyDetails;
import sg.com.income.lifeinsurance.repository.PolicyDetailsRepository;
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
 * Integration tests for the {@Link PolicyDetailsResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class PolicyDetailsResourceIT {

    private static final String DEFAULT_POLICY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PLAN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PLAN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_AGENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RIDER_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_RIDER_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_BY_CALL = "AAAAA";
    private static final String UPDATED_CONTACT_BY_CALL = "BBBBB";

    private static final String DEFAULT_CONTACT_BY_SMS = "AAAAA";
    private static final String UPDATED_CONTACT_BY_SMS = "BBBBB";

    private static final String DEFAULT_NRIC = "AAAAAAAAA";
    private static final String UPDATED_NRIC = "BBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "A";
    private static final String UPDATED_GENDER = "B";

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION_LEVEL = "A";
    private static final String UPDATED_EDUCATION_LEVEL = "B";

    private static final String DEFAULT_RESIDENTIAL_POSTAL_CODE = "AAAAAA";
    private static final String UPDATED_RESIDENTIAL_POSTAL_CODE = "BBBBBB";

    private static final String DEFAULT_RESIDENTIAL_UNIT_NO = "AAAAAAA";
    private static final String UPDATED_RESIDENTIAL_UNIT_NO = "BBBBBBB";

    private static final String DEFAULT_RESIDENTIAL_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENTIAL_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENTIAL_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENTIAL_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENTIAL_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENTIAL_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENTIAL_ADDRESS_4 = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENTIAL_ADDRESS_4 = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENTIAL_SAME_AS_MAILING = "AAAAA";
    private static final String UPDATED_RESIDENTIAL_SAME_AS_MAILING = "BBBBB";

    private static final String DEFAULT_MAILING_POSTAL_CODE = "AAAAAA";
    private static final String UPDATED_MAILING_POSTAL_CODE = "BBBBBB";

    private static final String DEFAULT_MAILING_UNIT_NO = "AAAAAAA";
    private static final String UPDATED_MAILING_UNIT_NO = "BBBBBBB";

    private static final String DEFAULT_MAILING_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_MAILING_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MAILING_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_MAILING_ADDRESS_4 = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_ADDRESS_4 = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENTIAL_STATUS = "AAA";
    private static final String UPDATED_RESIDENTIAL_STATUS = "BBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_OF_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_OF_BIRTH = "AAA";
    private static final String UPDATED_COUNTRY_OF_BIRTH = "BBB";

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION_CODE = "AAAA";
    private static final String UPDATED_OCCUPATION_CODE = "BBBB";

    private static final String DEFAULT_NAME_OF_EMPLOYER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_EMPLOYER = "BBBBBBBBBB";

    private static final String DEFAULT_ANNUAL_INCOME = "AAAAAAAAAA";
    private static final String UPDATED_ANNUAL_INCOME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "A";
    private static final String UPDATED_MARITAL_STATUS = "B";

    private static final String DEFAULT_UINFIN = "AAAAAAAAA";
    private static final String UPDATED_UINFIN = "BBBBBBBBB";

    private static final String DEFAULT_MY_INFO_VERIFIED = "AAAAAAAAAA";
    private static final String UPDATED_MY_INFO_VERIFIED = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PolicyDetailsRepository policyDetailsRepository;

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

    private MockMvc restPolicyDetailsMockMvc;

    private PolicyDetails policyDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolicyDetailsResource policyDetailsResource = new PolicyDetailsResource(policyDetailsRepository);
        this.restPolicyDetailsMockMvc = MockMvcBuilders.standaloneSetup(policyDetailsResource)
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
    public static PolicyDetails createEntity(EntityManager em) {
        PolicyDetails policyDetails = new PolicyDetails()
            .policyCode(DEFAULT_POLICY_CODE)
            .planCode(DEFAULT_PLAN_CODE)
            .planType(DEFAULT_PLAN_TYPE)
            .agentCode(DEFAULT_AGENT_CODE)
            .status(DEFAULT_STATUS)
            .riderNames(DEFAULT_RIDER_NAMES)
            .contactByCall(DEFAULT_CONTACT_BY_CALL)
            .contactBySMS(DEFAULT_CONTACT_BY_SMS)
            .nric(DEFAULT_NRIC)
            .name(DEFAULT_NAME)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .emailId(DEFAULT_EMAIL_ID)
            .phoneNo(DEFAULT_PHONE_NO)
            .educationLevel(DEFAULT_EDUCATION_LEVEL)
            .residentialPostalCode(DEFAULT_RESIDENTIAL_POSTAL_CODE)
            .residentialUnitNo(DEFAULT_RESIDENTIAL_UNIT_NO)
            .residentialAddress1(DEFAULT_RESIDENTIAL_ADDRESS_1)
            .residentialAddress2(DEFAULT_RESIDENTIAL_ADDRESS_2)
            .residentialAddress3(DEFAULT_RESIDENTIAL_ADDRESS_3)
            .residentialAddress4(DEFAULT_RESIDENTIAL_ADDRESS_4)
            .residentialSameAsMailing(DEFAULT_RESIDENTIAL_SAME_AS_MAILING)
            .mailingPostalCode(DEFAULT_MAILING_POSTAL_CODE)
            .mailingUnitNo(DEFAULT_MAILING_UNIT_NO)
            .mailingAddress1(DEFAULT_MAILING_ADDRESS_1)
            .mailingAddress2(DEFAULT_MAILING_ADDRESS_2)
            .mailingAddress3(DEFAULT_MAILING_ADDRESS_3)
            .mailingAddress4(DEFAULT_MAILING_ADDRESS_4)
            .occupation(DEFAULT_OCCUPATION)
            .residentialStatus(DEFAULT_RESIDENTIAL_STATUS)
            .nationality(DEFAULT_NATIONALITY)
            .placeOfNationality(DEFAULT_PLACE_OF_NATIONALITY)
            .countryOfBirth(DEFAULT_COUNTRY_OF_BIRTH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .occupationCode(DEFAULT_OCCUPATION_CODE)
            .nameOfEmployer(DEFAULT_NAME_OF_EMPLOYER)
            .annualIncome(DEFAULT_ANNUAL_INCOME)
            .addressType(DEFAULT_ADDRESS_TYPE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .uinfin(DEFAULT_UINFIN)
            .myInfoVerified(DEFAULT_MY_INFO_VERIFIED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return policyDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyDetails createUpdatedEntity(EntityManager em) {
        PolicyDetails policyDetails = new PolicyDetails()
            .policyCode(UPDATED_POLICY_CODE)
            .planCode(UPDATED_PLAN_CODE)
            .planType(UPDATED_PLAN_TYPE)
            .agentCode(UPDATED_AGENT_CODE)
            .status(UPDATED_STATUS)
            .riderNames(UPDATED_RIDER_NAMES)
            .contactByCall(UPDATED_CONTACT_BY_CALL)
            .contactBySMS(UPDATED_CONTACT_BY_SMS)
            .nric(UPDATED_NRIC)
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .emailId(UPDATED_EMAIL_ID)
            .phoneNo(UPDATED_PHONE_NO)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .residentialPostalCode(UPDATED_RESIDENTIAL_POSTAL_CODE)
            .residentialUnitNo(UPDATED_RESIDENTIAL_UNIT_NO)
            .residentialAddress1(UPDATED_RESIDENTIAL_ADDRESS_1)
            .residentialAddress2(UPDATED_RESIDENTIAL_ADDRESS_2)
            .residentialAddress3(UPDATED_RESIDENTIAL_ADDRESS_3)
            .residentialAddress4(UPDATED_RESIDENTIAL_ADDRESS_4)
            .residentialSameAsMailing(UPDATED_RESIDENTIAL_SAME_AS_MAILING)
            .mailingPostalCode(UPDATED_MAILING_POSTAL_CODE)
            .mailingUnitNo(UPDATED_MAILING_UNIT_NO)
            .mailingAddress1(UPDATED_MAILING_ADDRESS_1)
            .mailingAddress2(UPDATED_MAILING_ADDRESS_2)
            .mailingAddress3(UPDATED_MAILING_ADDRESS_3)
            .mailingAddress4(UPDATED_MAILING_ADDRESS_4)
            .occupation(UPDATED_OCCUPATION)
            .residentialStatus(UPDATED_RESIDENTIAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .placeOfNationality(UPDATED_PLACE_OF_NATIONALITY)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .occupationCode(UPDATED_OCCUPATION_CODE)
            .nameOfEmployer(UPDATED_NAME_OF_EMPLOYER)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .addressType(UPDATED_ADDRESS_TYPE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .uinfin(UPDATED_UINFIN)
            .myInfoVerified(UPDATED_MY_INFO_VERIFIED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return policyDetails;
    }

    @BeforeEach
    public void initTest() {
        policyDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyDetails() throws Exception {
        int databaseSizeBeforeCreate = policyDetailsRepository.findAll().size();

        // Create the PolicyDetails
        restPolicyDetailsMockMvc.perform(post("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDetails)))
            .andExpect(status().isCreated());

        // Validate the PolicyDetails in the database
        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyDetails testPolicyDetails = policyDetailsList.get(policyDetailsList.size() - 1);
        assertThat(testPolicyDetails.getPolicyCode()).isEqualTo(DEFAULT_POLICY_CODE);
        assertThat(testPolicyDetails.getPlanCode()).isEqualTo(DEFAULT_PLAN_CODE);
        assertThat(testPolicyDetails.getPlanType()).isEqualTo(DEFAULT_PLAN_TYPE);
        assertThat(testPolicyDetails.getAgentCode()).isEqualTo(DEFAULT_AGENT_CODE);
        assertThat(testPolicyDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolicyDetails.getRiderNames()).isEqualTo(DEFAULT_RIDER_NAMES);
        assertThat(testPolicyDetails.getContactByCall()).isEqualTo(DEFAULT_CONTACT_BY_CALL);
        assertThat(testPolicyDetails.getContactBySMS()).isEqualTo(DEFAULT_CONTACT_BY_SMS);
        assertThat(testPolicyDetails.getNric()).isEqualTo(DEFAULT_NRIC);
        assertThat(testPolicyDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPolicyDetails.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPolicyDetails.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPolicyDetails.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testPolicyDetails.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testPolicyDetails.getEducationLevel()).isEqualTo(DEFAULT_EDUCATION_LEVEL);
        assertThat(testPolicyDetails.getResidentialPostalCode()).isEqualTo(DEFAULT_RESIDENTIAL_POSTAL_CODE);
        assertThat(testPolicyDetails.getResidentialUnitNo()).isEqualTo(DEFAULT_RESIDENTIAL_UNIT_NO);
        assertThat(testPolicyDetails.getResidentialAddress1()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_1);
        assertThat(testPolicyDetails.getResidentialAddress2()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_2);
        assertThat(testPolicyDetails.getResidentialAddress3()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_3);
        assertThat(testPolicyDetails.getResidentialAddress4()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_4);
        assertThat(testPolicyDetails.getResidentialSameAsMailing()).isEqualTo(DEFAULT_RESIDENTIAL_SAME_AS_MAILING);
        assertThat(testPolicyDetails.getMailingPostalCode()).isEqualTo(DEFAULT_MAILING_POSTAL_CODE);
        assertThat(testPolicyDetails.getMailingUnitNo()).isEqualTo(DEFAULT_MAILING_UNIT_NO);
        assertThat(testPolicyDetails.getMailingAddress1()).isEqualTo(DEFAULT_MAILING_ADDRESS_1);
        assertThat(testPolicyDetails.getMailingAddress2()).isEqualTo(DEFAULT_MAILING_ADDRESS_2);
        assertThat(testPolicyDetails.getMailingAddress3()).isEqualTo(DEFAULT_MAILING_ADDRESS_3);
        assertThat(testPolicyDetails.getMailingAddress4()).isEqualTo(DEFAULT_MAILING_ADDRESS_4);
        assertThat(testPolicyDetails.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testPolicyDetails.getResidentialStatus()).isEqualTo(DEFAULT_RESIDENTIAL_STATUS);
        assertThat(testPolicyDetails.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPolicyDetails.getPlaceOfNationality()).isEqualTo(DEFAULT_PLACE_OF_NATIONALITY);
        assertThat(testPolicyDetails.getCountryOfBirth()).isEqualTo(DEFAULT_COUNTRY_OF_BIRTH);
        assertThat(testPolicyDetails.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testPolicyDetails.getOccupationCode()).isEqualTo(DEFAULT_OCCUPATION_CODE);
        assertThat(testPolicyDetails.getNameOfEmployer()).isEqualTo(DEFAULT_NAME_OF_EMPLOYER);
        assertThat(testPolicyDetails.getAnnualIncome()).isEqualTo(DEFAULT_ANNUAL_INCOME);
        assertThat(testPolicyDetails.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testPolicyDetails.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testPolicyDetails.getUinfin()).isEqualTo(DEFAULT_UINFIN);
        assertThat(testPolicyDetails.getMyInfoVerified()).isEqualTo(DEFAULT_MY_INFO_VERIFIED);
        assertThat(testPolicyDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPolicyDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPolicyDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyDetailsRepository.findAll().size();

        // Create the PolicyDetails with an existing ID
        policyDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyDetailsMockMvc.perform(post("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyDetails in the database
        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNricIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyDetailsRepository.findAll().size();
        // set the field null
        policyDetails.setNric(null);

        // Create the PolicyDetails, which fails.

        restPolicyDetailsMockMvc.perform(post("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDetails)))
            .andExpect(status().isBadRequest());

        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyDetailsRepository.findAll().size();
        // set the field null
        policyDetails.setEmailId(null);

        // Create the PolicyDetails, which fails.

        restPolicyDetailsMockMvc.perform(post("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDetails)))
            .andExpect(status().isBadRequest());

        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyDetailsRepository.findAll().size();
        // set the field null
        policyDetails.setPhoneNo(null);

        // Create the PolicyDetails, which fails.

        restPolicyDetailsMockMvc.perform(post("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDetails)))
            .andExpect(status().isBadRequest());

        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolicyDetails() throws Exception {
        // Initialize the database
        policyDetailsRepository.saveAndFlush(policyDetails);

        // Get all the policyDetailsList
        restPolicyDetailsMockMvc.perform(get("/api/policy-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyCode").value(hasItem(DEFAULT_POLICY_CODE.toString())))
            .andExpect(jsonPath("$.[*].planCode").value(hasItem(DEFAULT_PLAN_CODE.toString())))
            .andExpect(jsonPath("$.[*].planType").value(hasItem(DEFAULT_PLAN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].agentCode").value(hasItem(DEFAULT_AGENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].riderNames").value(hasItem(DEFAULT_RIDER_NAMES.toString())))
            .andExpect(jsonPath("$.[*].contactByCall").value(hasItem(DEFAULT_CONTACT_BY_CALL.toString())))
            .andExpect(jsonPath("$.[*].contactBySMS").value(hasItem(DEFAULT_CONTACT_BY_SMS.toString())))
            .andExpect(jsonPath("$.[*].nric").value(hasItem(DEFAULT_NRIC.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO.toString())))
            .andExpect(jsonPath("$.[*].educationLevel").value(hasItem(DEFAULT_EDUCATION_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].residentialPostalCode").value(hasItem(DEFAULT_RESIDENTIAL_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].residentialUnitNo").value(hasItem(DEFAULT_RESIDENTIAL_UNIT_NO.toString())))
            .andExpect(jsonPath("$.[*].residentialAddress1").value(hasItem(DEFAULT_RESIDENTIAL_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].residentialAddress2").value(hasItem(DEFAULT_RESIDENTIAL_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].residentialAddress3").value(hasItem(DEFAULT_RESIDENTIAL_ADDRESS_3.toString())))
            .andExpect(jsonPath("$.[*].residentialAddress4").value(hasItem(DEFAULT_RESIDENTIAL_ADDRESS_4.toString())))
            .andExpect(jsonPath("$.[*].residentialSameAsMailing").value(hasItem(DEFAULT_RESIDENTIAL_SAME_AS_MAILING.toString())))
            .andExpect(jsonPath("$.[*].mailingPostalCode").value(hasItem(DEFAULT_MAILING_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].mailingUnitNo").value(hasItem(DEFAULT_MAILING_UNIT_NO.toString())))
            .andExpect(jsonPath("$.[*].mailingAddress1").value(hasItem(DEFAULT_MAILING_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].mailingAddress2").value(hasItem(DEFAULT_MAILING_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].mailingAddress3").value(hasItem(DEFAULT_MAILING_ADDRESS_3.toString())))
            .andExpect(jsonPath("$.[*].mailingAddress4").value(hasItem(DEFAULT_MAILING_ADDRESS_4.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].residentialStatus").value(hasItem(DEFAULT_RESIDENTIAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].placeOfNationality").value(hasItem(DEFAULT_PLACE_OF_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].countryOfBirth").value(hasItem(DEFAULT_COUNTRY_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].occupationCode").value(hasItem(DEFAULT_OCCUPATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].nameOfEmployer").value(hasItem(DEFAULT_NAME_OF_EMPLOYER.toString())))
            .andExpect(jsonPath("$.[*].annualIncome").value(hasItem(DEFAULT_ANNUAL_INCOME.toString())))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].uinfin").value(hasItem(DEFAULT_UINFIN.toString())))
            .andExpect(jsonPath("$.[*].myInfoVerified").value(hasItem(DEFAULT_MY_INFO_VERIFIED.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPolicyDetails() throws Exception {
        // Initialize the database
        policyDetailsRepository.saveAndFlush(policyDetails);

        // Get the policyDetails
        restPolicyDetailsMockMvc.perform(get("/api/policy-details/{id}", policyDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyDetails.getId().intValue()))
            .andExpect(jsonPath("$.policyCode").value(DEFAULT_POLICY_CODE.toString()))
            .andExpect(jsonPath("$.planCode").value(DEFAULT_PLAN_CODE.toString()))
            .andExpect(jsonPath("$.planType").value(DEFAULT_PLAN_TYPE.toString()))
            .andExpect(jsonPath("$.agentCode").value(DEFAULT_AGENT_CODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.riderNames").value(DEFAULT_RIDER_NAMES.toString()))
            .andExpect(jsonPath("$.contactByCall").value(DEFAULT_CONTACT_BY_CALL.toString()))
            .andExpect(jsonPath("$.contactBySMS").value(DEFAULT_CONTACT_BY_SMS.toString()))
            .andExpect(jsonPath("$.nric").value(DEFAULT_NRIC.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO.toString()))
            .andExpect(jsonPath("$.educationLevel").value(DEFAULT_EDUCATION_LEVEL.toString()))
            .andExpect(jsonPath("$.residentialPostalCode").value(DEFAULT_RESIDENTIAL_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.residentialUnitNo").value(DEFAULT_RESIDENTIAL_UNIT_NO.toString()))
            .andExpect(jsonPath("$.residentialAddress1").value(DEFAULT_RESIDENTIAL_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.residentialAddress2").value(DEFAULT_RESIDENTIAL_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.residentialAddress3").value(DEFAULT_RESIDENTIAL_ADDRESS_3.toString()))
            .andExpect(jsonPath("$.residentialAddress4").value(DEFAULT_RESIDENTIAL_ADDRESS_4.toString()))
            .andExpect(jsonPath("$.residentialSameAsMailing").value(DEFAULT_RESIDENTIAL_SAME_AS_MAILING.toString()))
            .andExpect(jsonPath("$.mailingPostalCode").value(DEFAULT_MAILING_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.mailingUnitNo").value(DEFAULT_MAILING_UNIT_NO.toString()))
            .andExpect(jsonPath("$.mailingAddress1").value(DEFAULT_MAILING_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.mailingAddress2").value(DEFAULT_MAILING_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.mailingAddress3").value(DEFAULT_MAILING_ADDRESS_3.toString()))
            .andExpect(jsonPath("$.mailingAddress4").value(DEFAULT_MAILING_ADDRESS_4.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.residentialStatus").value(DEFAULT_RESIDENTIAL_STATUS.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.placeOfNationality").value(DEFAULT_PLACE_OF_NATIONALITY.toString()))
            .andExpect(jsonPath("$.countryOfBirth").value(DEFAULT_COUNTRY_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.occupationCode").value(DEFAULT_OCCUPATION_CODE.toString()))
            .andExpect(jsonPath("$.nameOfEmployer").value(DEFAULT_NAME_OF_EMPLOYER.toString()))
            .andExpect(jsonPath("$.annualIncome").value(DEFAULT_ANNUAL_INCOME.toString()))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.uinfin").value(DEFAULT_UINFIN.toString()))
            .andExpect(jsonPath("$.myInfoVerified").value(DEFAULT_MY_INFO_VERIFIED.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyDetails() throws Exception {
        // Get the policyDetails
        restPolicyDetailsMockMvc.perform(get("/api/policy-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyDetails() throws Exception {
        // Initialize the database
        policyDetailsRepository.saveAndFlush(policyDetails);

        int databaseSizeBeforeUpdate = policyDetailsRepository.findAll().size();

        // Update the policyDetails
        PolicyDetails updatedPolicyDetails = policyDetailsRepository.findById(policyDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPolicyDetails are not directly saved in db
        em.detach(updatedPolicyDetails);
        updatedPolicyDetails
            .policyCode(UPDATED_POLICY_CODE)
            .planCode(UPDATED_PLAN_CODE)
            .planType(UPDATED_PLAN_TYPE)
            .agentCode(UPDATED_AGENT_CODE)
            .status(UPDATED_STATUS)
            .riderNames(UPDATED_RIDER_NAMES)
            .contactByCall(UPDATED_CONTACT_BY_CALL)
            .contactBySMS(UPDATED_CONTACT_BY_SMS)
            .nric(UPDATED_NRIC)
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .emailId(UPDATED_EMAIL_ID)
            .phoneNo(UPDATED_PHONE_NO)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .residentialPostalCode(UPDATED_RESIDENTIAL_POSTAL_CODE)
            .residentialUnitNo(UPDATED_RESIDENTIAL_UNIT_NO)
            .residentialAddress1(UPDATED_RESIDENTIAL_ADDRESS_1)
            .residentialAddress2(UPDATED_RESIDENTIAL_ADDRESS_2)
            .residentialAddress3(UPDATED_RESIDENTIAL_ADDRESS_3)
            .residentialAddress4(UPDATED_RESIDENTIAL_ADDRESS_4)
            .residentialSameAsMailing(UPDATED_RESIDENTIAL_SAME_AS_MAILING)
            .mailingPostalCode(UPDATED_MAILING_POSTAL_CODE)
            .mailingUnitNo(UPDATED_MAILING_UNIT_NO)
            .mailingAddress1(UPDATED_MAILING_ADDRESS_1)
            .mailingAddress2(UPDATED_MAILING_ADDRESS_2)
            .mailingAddress3(UPDATED_MAILING_ADDRESS_3)
            .mailingAddress4(UPDATED_MAILING_ADDRESS_4)
            .occupation(UPDATED_OCCUPATION)
            .residentialStatus(UPDATED_RESIDENTIAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .placeOfNationality(UPDATED_PLACE_OF_NATIONALITY)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .occupationCode(UPDATED_OCCUPATION_CODE)
            .nameOfEmployer(UPDATED_NAME_OF_EMPLOYER)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .addressType(UPDATED_ADDRESS_TYPE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .uinfin(UPDATED_UINFIN)
            .myInfoVerified(UPDATED_MY_INFO_VERIFIED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPolicyDetailsMockMvc.perform(put("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolicyDetails)))
            .andExpect(status().isOk());

        // Validate the PolicyDetails in the database
        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeUpdate);
        PolicyDetails testPolicyDetails = policyDetailsList.get(policyDetailsList.size() - 1);
        assertThat(testPolicyDetails.getPolicyCode()).isEqualTo(UPDATED_POLICY_CODE);
        assertThat(testPolicyDetails.getPlanCode()).isEqualTo(UPDATED_PLAN_CODE);
        assertThat(testPolicyDetails.getPlanType()).isEqualTo(UPDATED_PLAN_TYPE);
        assertThat(testPolicyDetails.getAgentCode()).isEqualTo(UPDATED_AGENT_CODE);
        assertThat(testPolicyDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicyDetails.getRiderNames()).isEqualTo(UPDATED_RIDER_NAMES);
        assertThat(testPolicyDetails.getContactByCall()).isEqualTo(UPDATED_CONTACT_BY_CALL);
        assertThat(testPolicyDetails.getContactBySMS()).isEqualTo(UPDATED_CONTACT_BY_SMS);
        assertThat(testPolicyDetails.getNric()).isEqualTo(UPDATED_NRIC);
        assertThat(testPolicyDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolicyDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPolicyDetails.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPolicyDetails.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testPolicyDetails.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testPolicyDetails.getEducationLevel()).isEqualTo(UPDATED_EDUCATION_LEVEL);
        assertThat(testPolicyDetails.getResidentialPostalCode()).isEqualTo(UPDATED_RESIDENTIAL_POSTAL_CODE);
        assertThat(testPolicyDetails.getResidentialUnitNo()).isEqualTo(UPDATED_RESIDENTIAL_UNIT_NO);
        assertThat(testPolicyDetails.getResidentialAddress1()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_1);
        assertThat(testPolicyDetails.getResidentialAddress2()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_2);
        assertThat(testPolicyDetails.getResidentialAddress3()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_3);
        assertThat(testPolicyDetails.getResidentialAddress4()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_4);
        assertThat(testPolicyDetails.getResidentialSameAsMailing()).isEqualTo(UPDATED_RESIDENTIAL_SAME_AS_MAILING);
        assertThat(testPolicyDetails.getMailingPostalCode()).isEqualTo(UPDATED_MAILING_POSTAL_CODE);
        assertThat(testPolicyDetails.getMailingUnitNo()).isEqualTo(UPDATED_MAILING_UNIT_NO);
        assertThat(testPolicyDetails.getMailingAddress1()).isEqualTo(UPDATED_MAILING_ADDRESS_1);
        assertThat(testPolicyDetails.getMailingAddress2()).isEqualTo(UPDATED_MAILING_ADDRESS_2);
        assertThat(testPolicyDetails.getMailingAddress3()).isEqualTo(UPDATED_MAILING_ADDRESS_3);
        assertThat(testPolicyDetails.getMailingAddress4()).isEqualTo(UPDATED_MAILING_ADDRESS_4);
        assertThat(testPolicyDetails.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testPolicyDetails.getResidentialStatus()).isEqualTo(UPDATED_RESIDENTIAL_STATUS);
        assertThat(testPolicyDetails.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPolicyDetails.getPlaceOfNationality()).isEqualTo(UPDATED_PLACE_OF_NATIONALITY);
        assertThat(testPolicyDetails.getCountryOfBirth()).isEqualTo(UPDATED_COUNTRY_OF_BIRTH);
        assertThat(testPolicyDetails.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testPolicyDetails.getOccupationCode()).isEqualTo(UPDATED_OCCUPATION_CODE);
        assertThat(testPolicyDetails.getNameOfEmployer()).isEqualTo(UPDATED_NAME_OF_EMPLOYER);
        assertThat(testPolicyDetails.getAnnualIncome()).isEqualTo(UPDATED_ANNUAL_INCOME);
        assertThat(testPolicyDetails.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testPolicyDetails.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testPolicyDetails.getUinfin()).isEqualTo(UPDATED_UINFIN);
        assertThat(testPolicyDetails.getMyInfoVerified()).isEqualTo(UPDATED_MY_INFO_VERIFIED);
        assertThat(testPolicyDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPolicyDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyDetails() throws Exception {
        int databaseSizeBeforeUpdate = policyDetailsRepository.findAll().size();

        // Create the PolicyDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyDetailsMockMvc.perform(put("/api/policy-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyDetails in the database
        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolicyDetails() throws Exception {
        // Initialize the database
        policyDetailsRepository.saveAndFlush(policyDetails);

        int databaseSizeBeforeDelete = policyDetailsRepository.findAll().size();

        // Delete the policyDetails
        restPolicyDetailsMockMvc.perform(delete("/api/policy-details/{id}", policyDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PolicyDetails> policyDetailsList = policyDetailsRepository.findAll();
        assertThat(policyDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyDetails.class);
        PolicyDetails policyDetails1 = new PolicyDetails();
        policyDetails1.setId(1L);
        PolicyDetails policyDetails2 = new PolicyDetails();
        policyDetails2.setId(policyDetails1.getId());
        assertThat(policyDetails1).isEqualTo(policyDetails2);
        policyDetails2.setId(2L);
        assertThat(policyDetails1).isNotEqualTo(policyDetails2);
        policyDetails1.setId(null);
        assertThat(policyDetails1).isNotEqualTo(policyDetails2);
    }
}
