package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.domain.PolicyDetails;
import sg.com.income.lifeinsurance.repository.PolicyDetailsRepository;
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

/**
 * REST controller for managing {@link sg.com.income.lifeinsurance.domain.PolicyDetails}.
 */
@RestController
@RequestMapping("/api")
public class PolicyDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PolicyDetailsResource.class);

    private static final String ENTITY_NAME = "limePolicyServicePolicyDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyDetailsRepository policyDetailsRepository;

    public PolicyDetailsResource(PolicyDetailsRepository policyDetailsRepository) {
        this.policyDetailsRepository = policyDetailsRepository;
    }

    /**
     * {@code POST  /policy-details} : Create a new policyDetails.
     *
     * @param policyDetails the policyDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyDetails, or with status {@code 400 (Bad Request)} if the policyDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policy-details")
    public ResponseEntity<PolicyDetails> createPolicyDetails(@Valid @RequestBody PolicyDetails policyDetails) throws URISyntaxException {
        log.debug("REST request to save PolicyDetails : {}", policyDetails);
        if (policyDetails.getId() != null) {
            throw new BadRequestAlertException("A new policyDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyDetails result = policyDetailsRepository.save(policyDetails);
        return ResponseEntity.created(new URI("/api/policy-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policy-details} : Updates an existing policyDetails.
     *
     * @param policyDetails the policyDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyDetails,
     * or with status {@code 400 (Bad Request)} if the policyDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policy-details")
    public ResponseEntity<PolicyDetails> updatePolicyDetails(@Valid @RequestBody PolicyDetails policyDetails) throws URISyntaxException {
        log.debug("REST request to update PolicyDetails : {}", policyDetails);
        if (policyDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PolicyDetails result = policyDetailsRepository.save(policyDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, policyDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /policy-details} : get all the policyDetails.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policyDetails in body.
     */
    @GetMapping("/policy-details")
    public ResponseEntity<List<PolicyDetails>> getAllPolicyDetails(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of PolicyDetails");
        Page<PolicyDetails> page = policyDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policy-details/:id} : get the "id" policyDetails.
     *
     * @param id the id of the policyDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policy-details/{id}")
    public ResponseEntity<PolicyDetails> getPolicyDetails(@PathVariable Long id) {
        log.debug("REST request to get PolicyDetails : {}", id);
        Optional<PolicyDetails> policyDetails = policyDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(policyDetails);
    }

    /**
     * {@code DELETE  /policy-details/:id} : delete the "id" policyDetails.
     *
     * @param id the id of the policyDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policy-details/{id}")
    public ResponseEntity<Void> deletePolicyDetails(@PathVariable Long id) {
        log.debug("REST request to delete PolicyDetails : {}", id);
        policyDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
