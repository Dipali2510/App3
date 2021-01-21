package com.cg.TestProject1.web.rest;

import com.cg.TestProject1.TestProject1App;
import com.cg.TestProject1.domain.Table1;
import com.cg.TestProject1.repository.Table1Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link Table1Resource} REST controller.
 */
@SpringBootTest(classes = TestProject1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class Table1ResourceIT {

    private static final String DEFAULT_COLUMN_1 = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_1 = "BBBBBBBBBB";

    @Autowired
    private Table1Repository table1Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTable1MockMvc;

    private Table1 table1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table1 createEntity(EntityManager em) {
        Table1 table1 = new Table1();
        table1.setColumn1(DEFAULT_COLUMN_1);
        return table1;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table1 createUpdatedEntity(EntityManager em) {
        Table1 table1 = new Table1();
        table1.setColumn1(UPDATED_COLUMN_1);
        return table1;
    }

    @BeforeEach
    public void initTest() {
        table1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createTable1() throws Exception {
        int databaseSizeBeforeCreate = table1Repository.findAll().size();
        // Create the Table1
        restTable1MockMvc.perform(post("/api/table-1-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table1)))
            .andExpect(status().isCreated());

        // Validate the Table1 in the database
        List<Table1> table1List = table1Repository.findAll();
        assertThat(table1List).hasSize(databaseSizeBeforeCreate + 1);
        Table1 testTable1 = table1List.get(table1List.size() - 1);
        assertThat(testTable1.getColumn1()).isEqualTo(DEFAULT_COLUMN_1);
    }

    @Test
    @Transactional
    public void createTable1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = table1Repository.findAll().size();

        // Create the Table1 with an existing ID
        table1.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTable1MockMvc.perform(post("/api/table-1-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table1)))
            .andExpect(status().isBadRequest());

        // Validate the Table1 in the database
        List<Table1> table1List = table1Repository.findAll();
        assertThat(table1List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTable1s() throws Exception {
        // Initialize the database
        table1Repository.saveAndFlush(table1);

        // Get all the table1List
        restTable1MockMvc.perform(get("/api/table-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(table1.getId().intValue())))
            .andExpect(jsonPath("$.[*].Column1").value(hasItem(DEFAULT_COLUMN_1)));
    }
    
    @Test
    @Transactional
    public void getTable1() throws Exception {
        // Initialize the database
        table1Repository.saveAndFlush(table1);

        // Get the table1
        restTable1MockMvc.perform(get("/api/table-1-s/{id}", table1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(table1.getId().intValue()))
            .andExpect(jsonPath("$.Column1").value(DEFAULT_COLUMN_1));
    }
    @Test
    @Transactional
    public void getNonExistingTable1() throws Exception {
        // Get the table1
        restTable1MockMvc.perform(get("/api/table-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTable1() throws Exception {
        // Initialize the database
        table1Repository.saveAndFlush(table1);

        int databaseSizeBeforeUpdate = table1Repository.findAll().size();

        // Update the table1
        Table1 updatedTable1 = table1Repository.findById(table1.getId()).get();
        // Disconnect from session so that the updates on updatedTable1 are not directly saved in db
        em.detach(updatedTable1);
        updatedTable1.setColumn1(UPDATED_COLUMN_1);

        restTable1MockMvc.perform(put("/api/table-1-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTable1)))
            .andExpect(status().isOk());

        // Validate the Table1 in the database
        List<Table1> table1List = table1Repository.findAll();
        assertThat(table1List).hasSize(databaseSizeBeforeUpdate);
        Table1 testTable1 = table1List.get(table1List.size() - 1);
        assertThat(testTable1.getColumn1()).isEqualTo(UPDATED_COLUMN_1);
    }

    @Test
    @Transactional
    public void updateNonExistingTable1() throws Exception {
        int databaseSizeBeforeUpdate = table1Repository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTable1MockMvc.perform(put("/api/table-1-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table1)))
            .andExpect(status().isBadRequest());

        // Validate the Table1 in the database
        List<Table1> table1List = table1Repository.findAll();
        assertThat(table1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTable1() throws Exception {
        // Initialize the database
        table1Repository.saveAndFlush(table1);

        int databaseSizeBeforeDelete = table1Repository.findAll().size();

        // Delete the table1
        restTable1MockMvc.perform(delete("/api/table-1-s/{id}", table1.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Table1> table1List = table1Repository.findAll();
        assertThat(table1List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
