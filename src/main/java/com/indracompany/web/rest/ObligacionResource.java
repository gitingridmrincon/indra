package com.indracompany.web.rest;

import com.indracompany.domain.Obligacion;
import com.indracompany.repository.ObligacionRepository;
import com.indracompany.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.indracompany.domain.Obligacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ObligacionResource {

    private final Logger log = LoggerFactory.getLogger(ObligacionResource.class);

    private static final String ENTITY_NAME = "obligacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObligacionRepository obligacionRepository;

    public ObligacionResource(ObligacionRepository obligacionRepository) {
        this.obligacionRepository = obligacionRepository;
    }

    /**
     * {@code POST  /obligacions} : Create a new obligacion.
     *
     * @param obligacion the obligacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new obligacion, or with status {@code 400 (Bad Request)} if the obligacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/obligacions")
    public ResponseEntity<Obligacion> createObligacion(@RequestBody Obligacion obligacion) throws URISyntaxException {
        log.debug("REST request to save Obligacion : {}", obligacion);
        if (obligacion.getId() != null) {
            throw new BadRequestAlertException("A new obligacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Obligacion result = obligacionRepository.save(obligacion);
        return ResponseEntity.created(new URI("/api/obligacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /obligacions} : Updates an existing obligacion.
     *
     * @param obligacion the obligacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obligacion,
     * or with status {@code 400 (Bad Request)} if the obligacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the obligacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/obligacions")
    public ResponseEntity<Obligacion> updateObligacion(@RequestBody Obligacion obligacion) throws URISyntaxException {
        log.debug("REST request to update Obligacion : {}", obligacion);
        if (obligacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Obligacion result = obligacionRepository.save(obligacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, obligacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /obligacions} : get all the obligacions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of obligacions in body.
     */
    @GetMapping("/obligacions")
    public List<Obligacion> getAllObligacions() {
        log.debug("REST request to get all Obligacions");
        return obligacionRepository.findAll();
    }

    /**
     * {@code GET  /obligacions/:id} : get the "id" obligacion.
     *
     * @param id the id of the obligacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the obligacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/obligacions/{id}")
    public ResponseEntity<Obligacion> getObligacion(@PathVariable Long id) {
        log.debug("REST request to get Obligacion : {}", id);
        Optional<Obligacion> obligacion = obligacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(obligacion);
    }

    /**
     * {@code DELETE  /obligacions/:id} : delete the "id" obligacion.
     *
     * @param id the id of the obligacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/obligacions/{id}")
    public ResponseEntity<Void> deleteObligacion(@PathVariable Long id) {
        log.debug("REST request to delete Obligacion : {}", id);
        obligacionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
