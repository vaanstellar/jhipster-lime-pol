package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.domain.DocTypeReference;
import sg.com.income.lifeinsurance.repository.DocTypeReferenceRepository;
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
 * REST controller for managing {@link sg.com.income.lifeinsurance.domain.DocTypeReference}.
 */
@RestController
@RequestMapping("/api")
public class DocTypeReferenceResource {

    private final Logger log = LoggerFactory.getLogger(DocTypeReferenceResource.class);

    private static final String ENTITY_NAME = "limePolicyServiceDocTypeReference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocTypeReferenceRepository docTypeReferenceRepository;

    public DocTypeReferenceResource(DocTypeReferenceRepository docTypeReferenceRepository) {
        this.docTypeReferenceRepository = docTypeReferenceRepository;
    }

    /**
     * {@code POST  /doc-type-references} : Create a new docTypeReference.
     *
     * @param docTypeReference the docTypeReference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docTypeReference, or with status {@code 400 (Bad Request)} if the docTypeReference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doc-type-references")
    public ResponseEntity<DocTypeReference> createDocTypeReference(@RequestBody DocTypeReference docTypeReference) throws URISyntaxException {
        log.debug("REST request to save DocTypeReference : {}", docTypeReference);
        if (docTypeReference.getId() != null) {
            throw new BadRequestAlertException("A new docTypeReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocTypeReference result = docTypeReferenceRepository.save(docTypeReference);
        return ResponseEntity.created(new URI("/api/doc-type-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doc-type-references} : Updates an existing docTypeReference.
     *
     * @param docTypeReference the docTypeReference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docTypeReference,
     * or with status {@code 400 (Bad Request)} if the docTypeReference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docTypeReference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doc-type-references")
    public ResponseEntity<DocTypeReference> updateDocTypeReference(@RequestBody DocTypeReference docTypeReference) throws URISyntaxException {
        log.debug("REST request to update DocTypeReference : {}", docTypeReference);
        if (docTypeReference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocTypeReference result = docTypeReferenceRepository.save(docTypeReference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, docTypeReference.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doc-type-references} : get all the docTypeReferences.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docTypeReferences in body.
     */
    @GetMapping("/doc-type-references")
    public ResponseEntity<List<DocTypeReference>> getAllDocTypeReferences(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of DocTypeReferences");
        Page<DocTypeReference> page = docTypeReferenceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doc-type-references/:id} : get the "id" docTypeReference.
     *
     * @param id the id of the docTypeReference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docTypeReference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doc-type-references/{id}")
    public ResponseEntity<DocTypeReference> getDocTypeReference(@PathVariable Long id) {
        log.debug("REST request to get DocTypeReference : {}", id);
        Optional<DocTypeReference> docTypeReference = docTypeReferenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(docTypeReference);
    }

    /**
     * {@code DELETE  /doc-type-references/:id} : delete the "id" docTypeReference.
     *
     * @param id the id of the docTypeReference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doc-type-references/{id}")
    public ResponseEntity<Void> deleteDocTypeReference(@PathVariable Long id) {
        log.debug("REST request to delete DocTypeReference : {}", id);
        docTypeReferenceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
