package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.domain.PolicyPaymentDetails;
import sg.com.income.lifeinsurance.repository.PolicyPaymentDetailsRepository;
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
 * REST controller for managing {@link sg.com.income.lifeinsurance.domain.PolicyPaymentDetails}.
 */
@RestController
@RequestMapping("/api")
public class PolicyPaymentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PolicyPaymentDetailsResource.class);

    private static final String ENTITY_NAME = "limePolicyServicePolicyPaymentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyPaymentDetailsRepository policyPaymentDetailsRepository;

    public PolicyPaymentDetailsResource(PolicyPaymentDetailsRepository policyPaymentDetailsRepository) {
        this.policyPaymentDetailsRepository = policyPaymentDetailsRepository;
    }

    /**
     * {@code POST  /policy-payment-details} : Create a new policyPaymentDetails.
     *
     * @param policyPaymentDetails the policyPaymentDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyPaymentDetails, or with status {@code 400 (Bad Request)} if the policyPaymentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policy-payment-details")
    public ResponseEntity<PolicyPaymentDetails> createPolicyPaymentDetails(@Valid @RequestBody PolicyPaymentDetails policyPaymentDetails) throws URISyntaxException {
        log.debug("REST request to save PolicyPaymentDetails : {}", policyPaymentDetails);
        if (policyPaymentDetails.getId() != null) {
            throw new BadRequestAlertException("A new policyPaymentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyPaymentDetails result = policyPaymentDetailsRepository.save(policyPaymentDetails);
        return ResponseEntity.created(new URI("/api/policy-payment-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policy-payment-details} : Updates an existing policyPaymentDetails.
     *
     * @param policyPaymentDetails the policyPaymentDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyPaymentDetails,
     * or with status {@code 400 (Bad Request)} if the policyPaymentDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyPaymentDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policy-payment-details")
    public ResponseEntity<PolicyPaymentDetails> updatePolicyPaymentDetails(@Valid @RequestBody PolicyPaymentDetails policyPaymentDetails) throws URISyntaxException {
        log.debug("REST request to update PolicyPaymentDetails : {}", policyPaymentDetails);
        if (policyPaymentDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PolicyPaymentDetails result = policyPaymentDetailsRepository.save(policyPaymentDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, policyPaymentDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /policy-payment-details} : get all the policyPaymentDetails.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policyPaymentDetails in body.
     */
    @GetMapping("/policy-payment-details")
    public ResponseEntity<List<PolicyPaymentDetails>> getAllPolicyPaymentDetails(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false) String filter) {
        if ("policydetails-is-null".equals(filter)) {
            log.debug("REST request to get all PolicyPaymentDetailss where policyDetails is null");
            return new ResponseEntity<>(StreamSupport
                .stream(policyPaymentDetailsRepository.findAll().spliterator(), false)
                .filter(policyPaymentDetails -> policyPaymentDetails.getPolicyDetails() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of PolicyPaymentDetails");
        Page<PolicyPaymentDetails> page = policyPaymentDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policy-payment-details/:id} : get the "id" policyPaymentDetails.
     *
     * @param id the id of the policyPaymentDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyPaymentDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policy-payment-details/{id}")
    public ResponseEntity<PolicyPaymentDetails> getPolicyPaymentDetails(@PathVariable Long id) {
        log.debug("REST request to get PolicyPaymentDetails : {}", id);
        Optional<PolicyPaymentDetails> policyPaymentDetails = policyPaymentDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(policyPaymentDetails);
    }

    /**
     * {@code DELETE  /policy-payment-details/:id} : delete the "id" policyPaymentDetails.
     *
     * @param id the id of the policyPaymentDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policy-payment-details/{id}")
    public ResponseEntity<Void> deletePolicyPaymentDetails(@PathVariable Long id) {
        log.debug("REST request to delete PolicyPaymentDetails : {}", id);
        policyPaymentDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
