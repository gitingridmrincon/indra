package com.indracompany.web.rest;

import com.indracompany.domain.Banco;
import com.indracompany.repository.BancoRepository;
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
 * REST controller for managing {@link com.indracompany.domain.Banco}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BancoResource {

    private final Logger log = LoggerFactory.getLogger(BancoResource.class);

    private static final String ENTITY_NAME = "banco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BancoRepository bancoRepository;

    public BancoResource(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    /**
     * {@code POST  /bancos} : Create a new banco.
     *
     * @param banco the banco to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banco, or with status {@code 400 (Bad Request)} if the banco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bancos")
    public ResponseEntity<Banco> createBanco(@RequestBody Banco banco) throws URISyntaxException {
        log.debug("REST request to save Banco : {}", banco);
        if (banco.getId() != null) {
            throw new BadRequestAlertException("A new banco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Banco result = bancoRepository.save(banco);
        return ResponseEntity.created(new URI("/api/bancos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bancos} : Updates an existing banco.
     *
     * @param banco the banco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banco,
     * or with status {@code 400 (Bad Request)} if the banco is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bancos")
    public ResponseEntity<Banco> updateBanco(@RequestBody Banco banco) throws URISyntaxException {
        log.debug("REST request to update Banco : {}", banco);
        if (banco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Banco result = bancoRepository.save(banco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banco.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bancos} : get all the bancos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bancos in body.
     */
    @GetMapping("/bancos")
    public List<Banco> getAllBancos() {
        log.debug("REST request to get all Bancos");
        return bancoRepository.findAll();
    }

    /**
     * {@code GET  /bancos/:id} : get the "id" banco.
     *
     * @param id the id of the banco to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banco, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bancos/{id}")
    public ResponseEntity<Banco> getBanco(@PathVariable Long id) {
        log.debug("REST request to get Banco : {}", id);
        Optional<Banco> banco = bancoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(banco);
    }

    /**
     * {@code DELETE  /bancos/:id} : delete the "id" banco.
     *
     * @param id the id of the banco to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bancos/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable Long id) {
        log.debug("REST request to delete Banco : {}", id);
        bancoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
