package com.cg.TestProject1.web.rest;

import com.cg.TestProject1.domain.Table1;
import com.cg.TestProject1.repository.Table1Repository;
import com.cg.TestProject1.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.cg.TestProject1.domain.Table1}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class Table1Resource {

    private final Logger log = LoggerFactory.getLogger(Table1Resource.class);

    private static final String ENTITY_NAME = "table1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Table1Repository table1Repository;

    public Table1Resource(Table1Repository table1Repository) {
        this.table1Repository = table1Repository;
    }

    /**
     * {@code POST  /table-1-s} : Create a new table1.
     *
     * @param table1 the table1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new table1, or with status {@code 400 (Bad Request)} if the table1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/table-1-s")
    public ResponseEntity<Table1> createTable1(@RequestBody Table1 table1) throws URISyntaxException {
        log.debug("REST request to save Table1 : {}", table1);
        if (table1.getId() != null) {
            throw new BadRequestAlertException("A new table1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Table1 result = table1Repository.save(table1);
        return ResponseEntity.created(new URI("/api/table-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /table-1-s} : Updates an existing table1.
     *
     * @param table1 the table1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated table1,
     * or with status {@code 400 (Bad Request)} if the table1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the table1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/table-1-s")
    public ResponseEntity<Table1> updateTable1(@RequestBody Table1 table1) throws URISyntaxException {
        log.debug("REST request to update Table1 : {}", table1);
        if (table1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Table1 result = table1Repository.save(table1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, table1.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /table-1-s} : get all the table1s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of table1s in body.
     */
    @GetMapping("/table-1-s")
    public List<Table1> getAllTable1s() {
        if ("table1_column1-is-null".equals(filter)) {
            log.debug("REST request to get all Table1s where Table1_Column1 is null");
            return StreamSupport
                .stream(table1Repository.findAll().spliterator(), false)
                .filter(table1 -> table1.getTable1_Column1() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Table1s");
        return table1Repository.findAll();
    }

    /**
     * {@code GET  /table-1-s/:id} : get the "id" table1.
     *
     * @param id the id of the table1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the table1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/table-1-s/{id}")
    public ResponseEntity<Table1> getTable1(@PathVariable Long id) {
        log.debug("REST request to get Table1 : {}", id);
        Optional<Table1> table1 = table1Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(table1);
    }

    /**
     * {@code DELETE  /table-1-s/:id} : delete the "id" table1.
     *
     * @param id the id of the table1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/table-1-s/{id}")
    public ResponseEntity<Void> deleteTable1(@PathVariable Long id) {
        log.debug("REST request to delete Table1 : {}", id);
        table1Repository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
