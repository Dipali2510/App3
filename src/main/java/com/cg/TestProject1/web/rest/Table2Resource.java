package com.cg.TestProject1.web.rest;

import com.cg.TestProject1.domain.Table2;
import com.cg.TestProject1.repository.Table2Repository;
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
 * REST controller for managing {@link com.cg.TestProject1.domain.Table2}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class Table2Resource {

    private final Logger log = LoggerFactory.getLogger(Table2Resource.class);

    private static final String ENTITY_NAME = "table2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Table2Repository table2Repository;

    public Table2Resource(Table2Repository table2Repository) {
        this.table2Repository = table2Repository;
    }

    /**
     * {@code POST  /table-2-s} : Create a new table2.
     *
     * @param table2 the table2 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new table2, or with status {@code 400 (Bad Request)} if the table2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/table-2-s")
    public ResponseEntity<Table2> createTable2(@RequestBody Table2 table2) throws URISyntaxException {
        log.debug("REST request to save Table2 : {}", table2);
        if (table2.getId() != null) {
            throw new BadRequestAlertException("A new table2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Table2 result = table2Repository.save(table2);
        return ResponseEntity.created(new URI("/api/table-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /table-2-s} : Updates an existing table2.
     *
     * @param table2 the table2 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated table2,
     * or with status {@code 400 (Bad Request)} if the table2 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the table2 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/table-2-s")
    public ResponseEntity<Table2> updateTable2(@RequestBody Table2 table2) throws URISyntaxException {
        log.debug("REST request to update Table2 : {}", table2);
        if (table2.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Table2 result = table2Repository.save(table2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, table2.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /table-2-s} : get all the table2s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of table2s in body.
     */
    @GetMapping("/table-2-s")
    public List<Table2> getAllTable2s() {
        if ("table1_column1-is-null".equals(filter)) {
            log.debug("REST request to get all Table2s where Table1_Column1 is null");
            return StreamSupport
                .stream(table2Repository.findAll().spliterator(), false)
                .filter(table2 -> table2.getTable1_Column1() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Table2s");
        return table2Repository.findAll();
    }

    /**
     * {@code GET  /table-2-s/:id} : get the "id" table2.
     *
     * @param id the id of the table2 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the table2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/table-2-s/{id}")
    public ResponseEntity<Table2> getTable2(@PathVariable Long id) {
        log.debug("REST request to get Table2 : {}", id);
        Optional<Table2> table2 = table2Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(table2);
    }

    /**
     * {@code DELETE  /table-2-s/:id} : delete the "id" table2.
     *
     * @param id the id of the table2 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/table-2-s/{id}")
    public ResponseEntity<Void> deleteTable2(@PathVariable Long id) {
        log.debug("REST request to delete Table2 : {}", id);
        table2Repository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
