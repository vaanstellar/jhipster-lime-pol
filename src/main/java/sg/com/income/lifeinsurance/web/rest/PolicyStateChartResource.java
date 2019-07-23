package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.domain.PolicyStateChart;
import sg.com.income.lifeinsurance.repository.PolicyStateChartRepository;
import sg.com.income.lifeinsurance.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link sg.com.income.lifeinsurance.domain.PolicyStateChart}.
 */
@RestController
@RequestMapping("/api")
public class PolicyStateChartResource {

    private final Logger log = LoggerFactory.getLogger(PolicyStateChartResource.class);

    private static final String ENTITY_NAME = "limePolicyServicePolicyStateChart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyStateChartRepository policyStateChartRepository;

    public PolicyStateChartResource(PolicyStateChartRepository policyStateChartRepository) {
        this.policyStateChartRepository = policyStateChartRepository;
    }

    /**
     * {@code POST  /policy-state-charts} : Create a new policyStateChart.
     *
     * @param policyStateChart the policyStateChart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyStateChart, or with status {@code 400 (Bad Request)} if the policyStateChart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policy-state-charts")
    public ResponseEntity<PolicyStateChart> createPolicyStateChart(@Valid @RequestBody PolicyStateChart policyStateChart) throws URISyntaxException {
        log.debug("REST request to save PolicyStateChart : {}", policyStateChart);
        if (policyStateChart.getId() != null) {
            throw new BadRequestAlertException("A new policyStateChart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyStateChart result = policyStateChartRepository.save(policyStateChart);
        return ResponseEntity.created(new URI("/api/policy-state-charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policy-state-charts} : Updates an existing policyStateChart.
     *
     * @param policyStateChart the policyStateChart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyStateChart,
     * or with status {@code 400 (Bad Request)} if the policyStateChart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyStateChart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policy-state-charts")
    public ResponseEntity<PolicyStateChart> updatePolicyStateChart(@Valid @RequestBody PolicyStateChart policyStateChart) throws URISyntaxException {
        log.debug("REST request to update PolicyStateChart : {}", policyStateChart);
        if (policyStateChart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PolicyStateChart result = policyStateChartRepository.save(policyStateChart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, policyStateChart.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /policy-state-charts} : get all the policyStateCharts.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policyStateCharts in body.
     */
    @GetMapping("/policy-state-charts")
    public ResponseEntity<List<PolicyStateChart>> getAllPolicyStateCharts(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false) String filter) {
        if ("policydetails-is-null".equals(filter)) {
            log.debug("REST request to get all PolicyStateCharts where policyDetails is null");
            return new ResponseEntity<>(StreamSupport
                .stream(policyStateChartRepository.findAll().spliterator(), false)
                .filter(policyStateChart -> policyStateChart.getPolicyDetails() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of PolicyStateCharts");
        Page<PolicyStateChart> page = policyStateChartRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policy-state-charts/:id} : get the "id" policyStateChart.
     *
     * @param id the id of the policyStateChart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyStateChart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policy-state-charts/{id}")
    public ResponseEntity<PolicyStateChart> getPolicyStateChart(@PathVariable Long id) {
        log.debug("REST request to get PolicyStateChart : {}", id);
        Optional<PolicyStateChart> policyStateChart = policyStateChartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(policyStateChart);
    }

    /**
     * {@code DELETE  /policy-state-charts/:id} : delete the "id" policyStateChart.
     *
     * @param id the id of the policyStateChart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policy-state-charts/{id}")
    public ResponseEntity<Void> deletePolicyStateChart(@PathVariable Long id) {
        log.debug("REST request to delete PolicyStateChart : {}", id);
        policyStateChartRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
