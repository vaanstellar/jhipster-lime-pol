package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.LimePolicyServiceApp;
import sg.com.income.lifeinsurance.domain.PolicyStateChart;
import sg.com.income.lifeinsurance.repository.PolicyStateChartRepository;
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
 * Integration tests for the {@Link PolicyStateChartResource} REST controller.
 */
@SpringBootTest(classes = LimePolicyServiceApp.class)
public class PolicyStateChartResourceIT {

    private static final String DEFAULT_CURRENT_TASK = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PAYLOAD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Boolean DEFAULT_IS_PROCESSING = false;
    private static final Boolean UPDATED_IS_PROCESSING = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PolicyStateChartRepository policyStateChartRepository;

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

    private MockMvc restPolicyStateChartMockMvc;

    private PolicyStateChart policyStateChart;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolicyStateChartResource policyStateChartResource = new PolicyStateChartResource(policyStateChartRepository);
        this.restPolicyStateChartMockMvc = MockMvcBuilders.standaloneSetup(policyStateChartResource)
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
    public static PolicyStateChart createEntity(EntityManager em) {
        PolicyStateChart policyStateChart = new PolicyStateChart()
            .currentTask(DEFAULT_CURRENT_TASK)
            .currentPayload(DEFAULT_CURRENT_PAYLOAD)
            .status(DEFAULT_STATUS)
            .isProcessing(DEFAULT_IS_PROCESSING)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return policyStateChart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyStateChart createUpdatedEntity(EntityManager em) {
        PolicyStateChart policyStateChart = new PolicyStateChart()
            .currentTask(UPDATED_CURRENT_TASK)
            .currentPayload(UPDATED_CURRENT_PAYLOAD)
            .status(UPDATED_STATUS)
            .isProcessing(UPDATED_IS_PROCESSING)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return policyStateChart;
    }

    @BeforeEach
    public void initTest() {
        policyStateChart = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyStateChart() throws Exception {
        int databaseSizeBeforeCreate = policyStateChartRepository.findAll().size();

        // Create the PolicyStateChart
        restPolicyStateChartMockMvc.perform(post("/api/policy-state-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyStateChart)))
            .andExpect(status().isCreated());

        // Validate the PolicyStateChart in the database
        List<PolicyStateChart> policyStateChartList = policyStateChartRepository.findAll();
        assertThat(policyStateChartList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyStateChart testPolicyStateChart = policyStateChartList.get(policyStateChartList.size() - 1);
        assertThat(testPolicyStateChart.getCurrentTask()).isEqualTo(DEFAULT_CURRENT_TASK);
        assertThat(testPolicyStateChart.getCurrentPayload()).isEqualTo(DEFAULT_CURRENT_PAYLOAD);
        assertThat(testPolicyStateChart.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolicyStateChart.isIsProcessing()).isEqualTo(DEFAULT_IS_PROCESSING);
        assertThat(testPolicyStateChart.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPolicyStateChart.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPolicyStateChartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyStateChartRepository.findAll().size();

        // Create the PolicyStateChart with an existing ID
        policyStateChart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyStateChartMockMvc.perform(post("/api/policy-state-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyStateChart)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyStateChart in the database
        List<PolicyStateChart> policyStateChartList = policyStateChartRepository.findAll();
        assertThat(policyStateChartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPolicyStateCharts() throws Exception {
        // Initialize the database
        policyStateChartRepository.saveAndFlush(policyStateChart);

        // Get all the policyStateChartList
        restPolicyStateChartMockMvc.perform(get("/api/policy-state-charts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyStateChart.getId().intValue())))
            .andExpect(jsonPath("$.[*].currentTask").value(hasItem(DEFAULT_CURRENT_TASK.toString())))
            .andExpect(jsonPath("$.[*].currentPayload").value(hasItem(DEFAULT_CURRENT_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].isProcessing").value(hasItem(DEFAULT_IS_PROCESSING.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPolicyStateChart() throws Exception {
        // Initialize the database
        policyStateChartRepository.saveAndFlush(policyStateChart);

        // Get the policyStateChart
        restPolicyStateChartMockMvc.perform(get("/api/policy-state-charts/{id}", policyStateChart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyStateChart.getId().intValue()))
            .andExpect(jsonPath("$.currentTask").value(DEFAULT_CURRENT_TASK.toString()))
            .andExpect(jsonPath("$.currentPayload").value(DEFAULT_CURRENT_PAYLOAD.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.isProcessing").value(DEFAULT_IS_PROCESSING.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyStateChart() throws Exception {
        // Get the policyStateChart
        restPolicyStateChartMockMvc.perform(get("/api/policy-state-charts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyStateChart() throws Exception {
        // Initialize the database
        policyStateChartRepository.saveAndFlush(policyStateChart);

        int databaseSizeBeforeUpdate = policyStateChartRepository.findAll().size();

        // Update the policyStateChart
        PolicyStateChart updatedPolicyStateChart = policyStateChartRepository.findById(policyStateChart.getId()).get();
        // Disconnect from session so that the updates on updatedPolicyStateChart are not directly saved in db
        em.detach(updatedPolicyStateChart);
        updatedPolicyStateChart
            .currentTask(UPDATED_CURRENT_TASK)
            .currentPayload(UPDATED_CURRENT_PAYLOAD)
            .status(UPDATED_STATUS)
            .isProcessing(UPDATED_IS_PROCESSING)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPolicyStateChartMockMvc.perform(put("/api/policy-state-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolicyStateChart)))
            .andExpect(status().isOk());

        // Validate the PolicyStateChart in the database
        List<PolicyStateChart> policyStateChartList = policyStateChartRepository.findAll();
        assertThat(policyStateChartList).hasSize(databaseSizeBeforeUpdate);
        PolicyStateChart testPolicyStateChart = policyStateChartList.get(policyStateChartList.size() - 1);
        assertThat(testPolicyStateChart.getCurrentTask()).isEqualTo(UPDATED_CURRENT_TASK);
        assertThat(testPolicyStateChart.getCurrentPayload()).isEqualTo(UPDATED_CURRENT_PAYLOAD);
        assertThat(testPolicyStateChart.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicyStateChart.isIsProcessing()).isEqualTo(UPDATED_IS_PROCESSING);
        assertThat(testPolicyStateChart.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPolicyStateChart.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyStateChart() throws Exception {
        int databaseSizeBeforeUpdate = policyStateChartRepository.findAll().size();

        // Create the PolicyStateChart

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyStateChartMockMvc.perform(put("/api/policy-state-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyStateChart)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyStateChart in the database
        List<PolicyStateChart> policyStateChartList = policyStateChartRepository.findAll();
        assertThat(policyStateChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolicyStateChart() throws Exception {
        // Initialize the database
        policyStateChartRepository.saveAndFlush(policyStateChart);

        int databaseSizeBeforeDelete = policyStateChartRepository.findAll().size();

        // Delete the policyStateChart
        restPolicyStateChartMockMvc.perform(delete("/api/policy-state-charts/{id}", policyStateChart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PolicyStateChart> policyStateChartList = policyStateChartRepository.findAll();
        assertThat(policyStateChartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyStateChart.class);
        PolicyStateChart policyStateChart1 = new PolicyStateChart();
        policyStateChart1.setId(1L);
        PolicyStateChart policyStateChart2 = new PolicyStateChart();
        policyStateChart2.setId(policyStateChart1.getId());
        assertThat(policyStateChart1).isEqualTo(policyStateChart2);
        policyStateChart2.setId(2L);
        assertThat(policyStateChart1).isNotEqualTo(policyStateChart2);
        policyStateChart1.setId(null);
        assertThat(policyStateChart1).isNotEqualTo(policyStateChart2);
    }
}
