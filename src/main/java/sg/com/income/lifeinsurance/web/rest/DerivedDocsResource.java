package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.domain.DerivedDocs;
import sg.com.income.lifeinsurance.repository.DerivedDocsRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sg.com.income.lifeinsurance.domain.DerivedDocs}.
 */
@RestController
@RequestMapping("/api")
public class DerivedDocsResource {

    private final Logger log = LoggerFactory.getLogger(DerivedDocsResource.class);

    private static final String ENTITY_NAME = "limePolicyServiceDerivedDocs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DerivedDocsRepository derivedDocsRepository;

    public DerivedDocsResource(DerivedDocsRepository derivedDocsRepository) {
        this.derivedDocsRepository = derivedDocsRepository;
    }

    /**
     * {@code POST  /derived-docs} : Create a new derivedDocs.
     *
     * @param derivedDocs the derivedDocs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new derivedDocs, or with status {@code 400 (Bad Request)} if the derivedDocs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/derived-docs")
    public ResponseEntity<DerivedDocs> createDerivedDocs(@RequestBody DerivedDocs derivedDocs) throws URISyntaxException {
        log.debug("REST request to save DerivedDocs : {}", derivedDocs);
        if (derivedDocs.getId() != null) {
            throw new BadRequestAlertException("A new derivedDocs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DerivedDocs result = derivedDocsRepository.save(derivedDocs);
        return ResponseEntity.created(new URI("/api/derived-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /derived-docs} : Updates an existing derivedDocs.
     *
     * @param derivedDocs the derivedDocs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated derivedDocs,
     * or with status {@code 400 (Bad Request)} if the derivedDocs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the derivedDocs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/derived-docs")
    public ResponseEntity<DerivedDocs> updateDerivedDocs(@RequestBody DerivedDocs derivedDocs) throws URISyntaxException {
        log.debug("REST request to update DerivedDocs : {}", derivedDocs);
        if (derivedDocs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DerivedDocs result = derivedDocsRepository.save(derivedDocs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, derivedDocs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /derived-docs} : get all the derivedDocs.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of derivedDocs in body.
     */
    @GetMapping("/derived-docs")
    public ResponseEntity<List<DerivedDocs>> getAllDerivedDocs(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of DerivedDocs");
        Page<DerivedDocs> page = derivedDocsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /derived-docs/:id} : get the "id" derivedDocs.
     *
     * @param id the id of the derivedDocs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the derivedDocs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/derived-docs/{id}")
    public ResponseEntity<DerivedDocs> getDerivedDocs(@PathVariable Long id) {
        log.debug("REST request to get DerivedDocs : {}", id);
        Optional<DerivedDocs> derivedDocs = derivedDocsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(derivedDocs);
    }

    /**
     * {@code DELETE  /derived-docs/:id} : delete the "id" derivedDocs.
     *
     * @param id the id of the derivedDocs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/derived-docs/{id}")
    public ResponseEntity<Void> deleteDerivedDocs(@PathVariable Long id) {
        log.debug("REST request to delete DerivedDocs : {}", id);
        derivedDocsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
