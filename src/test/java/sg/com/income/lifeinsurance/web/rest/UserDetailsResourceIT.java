package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.UserDetails;
import sg.com.income.lifeinsurance.repository.UserDetailsRepository;
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
 * Integration tests for the {@Link UserDetailsResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class UserDetailsResourceIT {

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

    private static final String DEFAULT_MAILING_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MAILING_UNIT_NO = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_UNIT_NO = "BBBBBBBBBB";

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

    private static final String DEFAULT_OCCUPATION_CODE = "AAAA";
    private static final String UPDATED_OCCUPATION_CODE = "BBBB";

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

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserDetailsRepository userDetailsRepository;

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

    private MockMvc restUserDetailsMockMvc;

    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserDetailsResource userDetailsResource = new UserDetailsResource(userDetailsRepository);
        this.restUserDetailsMockMvc = MockMvcBuilders.standaloneSetup(userDetailsResource)
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
    public static UserDetails createEntity(EntityManager em) {
        UserDetails userDetails = new UserDetails()
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
            .occupationCode(DEFAULT_OCCUPATION_CODE)
            .residentialStatus(DEFAULT_RESIDENTIAL_STATUS)
            .nationality(DEFAULT_NATIONALITY)
            .placeOfNationality(DEFAULT_PLACE_OF_NATIONALITY)
            .countryOfBirth(DEFAULT_COUNTRY_OF_BIRTH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .nameOfEmployer(DEFAULT_NAME_OF_EMPLOYER)
            .annualIncome(DEFAULT_ANNUAL_INCOME)
            .addressType(DEFAULT_ADDRESS_TYPE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .uinfin(DEFAULT_UINFIN)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return userDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetails createUpdatedEntity(EntityManager em) {
        UserDetails userDetails = new UserDetails()
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
            .occupationCode(UPDATED_OCCUPATION_CODE)
            .residentialStatus(UPDATED_RESIDENTIAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .placeOfNationality(UPDATED_PLACE_OF_NATIONALITY)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .nameOfEmployer(UPDATED_NAME_OF_EMPLOYER)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .addressType(UPDATED_ADDRESS_TYPE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .uinfin(UPDATED_UINFIN)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return userDetails;
    }

    @BeforeEach
    public void initTest() {
        userDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDetails() throws Exception {
        int databaseSizeBeforeCreate = userDetailsRepository.findAll().size();

        // Create the UserDetails
        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetails)))
            .andExpect(status().isCreated());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        UserDetails testUserDetails = userDetailsList.get(userDetailsList.size() - 1);
        assertThat(testUserDetails.getNric()).isEqualTo(DEFAULT_NRIC);
        assertThat(testUserDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserDetails.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserDetails.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testUserDetails.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testUserDetails.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testUserDetails.getEducationLevel()).isEqualTo(DEFAULT_EDUCATION_LEVEL);
        assertThat(testUserDetails.getResidentialPostalCode()).isEqualTo(DEFAULT_RESIDENTIAL_POSTAL_CODE);
        assertThat(testUserDetails.getResidentialUnitNo()).isEqualTo(DEFAULT_RESIDENTIAL_UNIT_NO);
        assertThat(testUserDetails.getResidentialAddress1()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_1);
        assertThat(testUserDetails.getResidentialAddress2()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_2);
        assertThat(testUserDetails.getResidentialAddress3()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_3);
        assertThat(testUserDetails.getResidentialAddress4()).isEqualTo(DEFAULT_RESIDENTIAL_ADDRESS_4);
        assertThat(testUserDetails.getResidentialSameAsMailing()).isEqualTo(DEFAULT_RESIDENTIAL_SAME_AS_MAILING);
        assertThat(testUserDetails.getMailingPostalCode()).isEqualTo(DEFAULT_MAILING_POSTAL_CODE);
        assertThat(testUserDetails.getMailingUnitNo()).isEqualTo(DEFAULT_MAILING_UNIT_NO);
        assertThat(testUserDetails.getMailingAddress1()).isEqualTo(DEFAULT_MAILING_ADDRESS_1);
        assertThat(testUserDetails.getMailingAddress2()).isEqualTo(DEFAULT_MAILING_ADDRESS_2);
        assertThat(testUserDetails.getMailingAddress3()).isEqualTo(DEFAULT_MAILING_ADDRESS_3);
        assertThat(testUserDetails.getMailingAddress4()).isEqualTo(DEFAULT_MAILING_ADDRESS_4);
        assertThat(testUserDetails.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testUserDetails.getOccupationCode()).isEqualTo(DEFAULT_OCCUPATION_CODE);
        assertThat(testUserDetails.getResidentialStatus()).isEqualTo(DEFAULT_RESIDENTIAL_STATUS);
        assertThat(testUserDetails.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testUserDetails.getPlaceOfNationality()).isEqualTo(DEFAULT_PLACE_OF_NATIONALITY);
        assertThat(testUserDetails.getCountryOfBirth()).isEqualTo(DEFAULT_COUNTRY_OF_BIRTH);
        assertThat(testUserDetails.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testUserDetails.getNameOfEmployer()).isEqualTo(DEFAULT_NAME_OF_EMPLOYER);
        assertThat(testUserDetails.getAnnualIncome()).isEqualTo(DEFAULT_ANNUAL_INCOME);
        assertThat(testUserDetails.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testUserDetails.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testUserDetails.getUinfin()).isEqualTo(DEFAULT_UINFIN);
        assertThat(testUserDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createUserDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDetailsRepository.findAll().size();

        // Create the UserDetails with an existing ID
        userDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetails)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNricIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDetailsRepository.findAll().size();
        // set the field null
        userDetails.setNric(null);

        // Create the UserDetails, which fails.

        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetails)))
            .andExpect(status().isBadRequest());

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDetailsRepository.findAll().size();
        // set the field null
        userDetails.setEmailId(null);

        // Create the UserDetails, which fails.

        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetails)))
            .andExpect(status().isBadRequest());

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        // Get all the userDetailsList
        restUserDetailsMockMvc.perform(get("/api/user-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDetails.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].occupationCode").value(hasItem(DEFAULT_OCCUPATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].residentialStatus").value(hasItem(DEFAULT_RESIDENTIAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].placeOfNationality").value(hasItem(DEFAULT_PLACE_OF_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].countryOfBirth").value(hasItem(DEFAULT_COUNTRY_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].nameOfEmployer").value(hasItem(DEFAULT_NAME_OF_EMPLOYER.toString())))
            .andExpect(jsonPath("$.[*].annualIncome").value(hasItem(DEFAULT_ANNUAL_INCOME.toString())))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].uinfin").value(hasItem(DEFAULT_UINFIN.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        // Get the userDetails
        restUserDetailsMockMvc.perform(get("/api/user-details/{id}", userDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDetails.getId().intValue()))
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
            .andExpect(jsonPath("$.occupationCode").value(DEFAULT_OCCUPATION_CODE.toString()))
            .andExpect(jsonPath("$.residentialStatus").value(DEFAULT_RESIDENTIAL_STATUS.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.placeOfNationality").value(DEFAULT_PLACE_OF_NATIONALITY.toString()))
            .andExpect(jsonPath("$.countryOfBirth").value(DEFAULT_COUNTRY_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.nameOfEmployer").value(DEFAULT_NAME_OF_EMPLOYER.toString()))
            .andExpect(jsonPath("$.annualIncome").value(DEFAULT_ANNUAL_INCOME.toString()))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.uinfin").value(DEFAULT_UINFIN.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserDetails() throws Exception {
        // Get the userDetails
        restUserDetailsMockMvc.perform(get("/api/user-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        int databaseSizeBeforeUpdate = userDetailsRepository.findAll().size();

        // Update the userDetails
        UserDetails updatedUserDetails = userDetailsRepository.findById(userDetails.getId()).get();
        // Disconnect from session so that the updates on updatedUserDetails are not directly saved in db
        em.detach(updatedUserDetails);
        updatedUserDetails
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
            .occupationCode(UPDATED_OCCUPATION_CODE)
            .residentialStatus(UPDATED_RESIDENTIAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .placeOfNationality(UPDATED_PLACE_OF_NATIONALITY)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .nameOfEmployer(UPDATED_NAME_OF_EMPLOYER)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .addressType(UPDATED_ADDRESS_TYPE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .uinfin(UPDATED_UINFIN)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restUserDetailsMockMvc.perform(put("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserDetails)))
            .andExpect(status().isOk());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeUpdate);
        UserDetails testUserDetails = userDetailsList.get(userDetailsList.size() - 1);
        assertThat(testUserDetails.getNric()).isEqualTo(UPDATED_NRIC);
        assertThat(testUserDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserDetails.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testUserDetails.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testUserDetails.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testUserDetails.getEducationLevel()).isEqualTo(UPDATED_EDUCATION_LEVEL);
        assertThat(testUserDetails.getResidentialPostalCode()).isEqualTo(UPDATED_RESIDENTIAL_POSTAL_CODE);
        assertThat(testUserDetails.getResidentialUnitNo()).isEqualTo(UPDATED_RESIDENTIAL_UNIT_NO);
        assertThat(testUserDetails.getResidentialAddress1()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_1);
        assertThat(testUserDetails.getResidentialAddress2()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_2);
        assertThat(testUserDetails.getResidentialAddress3()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_3);
        assertThat(testUserDetails.getResidentialAddress4()).isEqualTo(UPDATED_RESIDENTIAL_ADDRESS_4);
        assertThat(testUserDetails.getResidentialSameAsMailing()).isEqualTo(UPDATED_RESIDENTIAL_SAME_AS_MAILING);
        assertThat(testUserDetails.getMailingPostalCode()).isEqualTo(UPDATED_MAILING_POSTAL_CODE);
        assertThat(testUserDetails.getMailingUnitNo()).isEqualTo(UPDATED_MAILING_UNIT_NO);
        assertThat(testUserDetails.getMailingAddress1()).isEqualTo(UPDATED_MAILING_ADDRESS_1);
        assertThat(testUserDetails.getMailingAddress2()).isEqualTo(UPDATED_MAILING_ADDRESS_2);
        assertThat(testUserDetails.getMailingAddress3()).isEqualTo(UPDATED_MAILING_ADDRESS_3);
        assertThat(testUserDetails.getMailingAddress4()).isEqualTo(UPDATED_MAILING_ADDRESS_4);
        assertThat(testUserDetails.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testUserDetails.getOccupationCode()).isEqualTo(UPDATED_OCCUPATION_CODE);
        assertThat(testUserDetails.getResidentialStatus()).isEqualTo(UPDATED_RESIDENTIAL_STATUS);
        assertThat(testUserDetails.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testUserDetails.getPlaceOfNationality()).isEqualTo(UPDATED_PLACE_OF_NATIONALITY);
        assertThat(testUserDetails.getCountryOfBirth()).isEqualTo(UPDATED_COUNTRY_OF_BIRTH);
        assertThat(testUserDetails.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testUserDetails.getNameOfEmployer()).isEqualTo(UPDATED_NAME_OF_EMPLOYER);
        assertThat(testUserDetails.getAnnualIncome()).isEqualTo(UPDATED_ANNUAL_INCOME);
        assertThat(testUserDetails.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testUserDetails.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testUserDetails.getUinfin()).isEqualTo(UPDATED_UINFIN);
        assertThat(testUserDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDetails() throws Exception {
        int databaseSizeBeforeUpdate = userDetailsRepository.findAll().size();

        // Create the UserDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDetailsMockMvc.perform(put("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetails)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        int databaseSizeBeforeDelete = userDetailsRepository.findAll().size();

        // Delete the userDetails
        restUserDetailsMockMvc.perform(delete("/api/user-details/{id}", userDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetails.class);
        UserDetails userDetails1 = new UserDetails();
        userDetails1.setId(1L);
        UserDetails userDetails2 = new UserDetails();
        userDetails2.setId(userDetails1.getId());
        assertThat(userDetails1).isEqualTo(userDetails2);
        userDetails2.setId(2L);
        assertThat(userDetails1).isNotEqualTo(userDetails2);
        userDetails1.setId(null);
        assertThat(userDetails1).isNotEqualTo(userDetails2);
    }
}
