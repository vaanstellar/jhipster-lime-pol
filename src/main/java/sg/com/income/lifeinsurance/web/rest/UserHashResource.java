package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.domain.UserHash;
import sg.com.income.lifeinsurance.repository.UserHashRepository;
import sg.com.income.lifeinsurance.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sg.com.income.lifeinsurance.domain.UserHash}.
 */
@RestController
@RequestMapping("/api")
public class UserHashResource {

    private final Logger log = LoggerFactory.getLogger(UserHashResource.class);

    private static final String ENTITY_NAME = "limePolicyServiceUserHash";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserHashRepository userHashRepository;

    public UserHashResource(UserHashRepository userHashRepository) {
        this.userHashRepository = userHashRepository;
    }

    /**
     * {@code POST  /user-hashes} : Create a new userHash.
     *
     * @param userHash the userHash to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userHash, or with status {@code 400 (Bad Request)} if the userHash has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-hashes")
    public ResponseEntity<UserHash> createUserHash(@Valid @RequestBody UserHash userHash) throws URISyntaxException {
        log.debug("REST request to save UserHash : {}", userHash);
        if (userHash.getId() != null) {
            throw new BadRequestAlertException("A new userHash cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserHash result = userHashRepository.save(userHash);
        return ResponseEntity.created(new URI("/api/user-hashes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-hashes} : Updates an existing userHash.
     *
     * @param userHash the userHash to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userHash,
     * or with status {@code 400 (Bad Request)} if the userHash is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userHash couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-hashes")
    public ResponseEntity<UserHash> updateUserHash(@Valid @RequestBody UserHash userHash) throws URISyntaxException {
        log.debug("REST request to update UserHash : {}", userHash);
        if (userHash.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserHash result = userHashRepository.save(userHash);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userHash.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-hashes} : get all the userHashes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userHashes in body.
     */
    @GetMapping("/user-hashes")
    public List<UserHash> getAllUserHashes() {
        log.debug("REST request to get all UserHashes");
        return userHashRepository.findAll();
    }

    /**
     * {@code GET  /user-hashes/:id} : get the "id" userHash.
     *
     * @param id the id of the userHash to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userHash, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-hashes/{id}")
    public ResponseEntity<UserHash> getUserHash(@PathVariable Long id) {
        log.debug("REST request to get UserHash : {}", id);
        Optional<UserHash> userHash = userHashRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userHash);
    }

    /**
     * {@code DELETE  /user-hashes/:id} : delete the "id" userHash.
     *
     * @param id the id of the userHash to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-hashes/{id}")
    public ResponseEntity<Void> deleteUserHash(@PathVariable Long id) {
        log.debug("REST request to delete UserHash : {}", id);
        userHashRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
