package sg.com.income.lifeinsurance.web.rest;

import sg.com.income.lifeinsurance.domain.UserSuspension;
import sg.com.income.lifeinsurance.repository.UserSuspensionRepository;
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
 * REST controller for managing {@link sg.com.income.lifeinsurance.domain.UserSuspension}.
 */
@RestController
@RequestMapping("/api")
public class UserSuspensionResource {

    private final Logger log = LoggerFactory.getLogger(UserSuspensionResource.class);

    private static final String ENTITY_NAME = "limePolicyServiceUserSuspension";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSuspensionRepository userSuspensionRepository;

    public UserSuspensionResource(UserSuspensionRepository userSuspensionRepository) {
        this.userSuspensionRepository = userSuspensionRepository;
    }

    /**
     * {@code POST  /user-suspensions} : Create a new userSuspension.
     *
     * @param userSuspension the userSuspension to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSuspension, or with status {@code 400 (Bad Request)} if the userSuspension has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-suspensions")
    public ResponseEntity<UserSuspension> createUserSuspension(@Valid @RequestBody UserSuspension userSuspension) throws URISyntaxException {
        log.debug("REST request to save UserSuspension : {}", userSuspension);
        if (userSuspension.getId() != null) {
            throw new BadRequestAlertException("A new userSuspension cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSuspension result = userSuspensionRepository.save(userSuspension);
        return ResponseEntity.created(new URI("/api/user-suspensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-suspensions} : Updates an existing userSuspension.
     *
     * @param userSuspension the userSuspension to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSuspension,
     * or with status {@code 400 (Bad Request)} if the userSuspension is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSuspension couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-suspensions")
    public ResponseEntity<UserSuspension> updateUserSuspension(@Valid @RequestBody UserSuspension userSuspension) throws URISyntaxException {
        log.debug("REST request to update UserSuspension : {}", userSuspension);
        if (userSuspension.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserSuspension result = userSuspensionRepository.save(userSuspension);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userSuspension.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-suspensions} : get all the userSuspensions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSuspensions in body.
     */
    @GetMapping("/user-suspensions")
    public List<UserSuspension> getAllUserSuspensions() {
        log.debug("REST request to get all UserSuspensions");
        return userSuspensionRepository.findAll();
    }

    /**
     * {@code GET  /user-suspensions/:id} : get the "id" userSuspension.
     *
     * @param id the id of the userSuspension to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSuspension, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-suspensions/{id}")
    public ResponseEntity<UserSuspension> getUserSuspension(@PathVariable Long id) {
        log.debug("REST request to get UserSuspension : {}", id);
        Optional<UserSuspension> userSuspension = userSuspensionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userSuspension);
    }

    /**
     * {@code DELETE  /user-suspensions/:id} : delete the "id" userSuspension.
     *
     * @param id the id of the userSuspension to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-suspensions/{id}")
    public ResponseEntity<Void> deleteUserSuspension(@PathVariable Long id) {
        log.debug("REST request to delete UserSuspension : {}", id);
        userSuspensionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
