package com.cg.TestProject1.web.rest;

import com.cg.TestProject1.TestProject1App;
import com.cg.TestProject1.domain.Table2;
import com.cg.TestProject1.repository.Table2Repository;

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
 * Integration tests for the {@link Table2Resource} REST controller.
 */
@SpringBootTest(classes = TestProject1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class Table2ResourceIT {

    private static final String DEFAULT_COLUMN_2 = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_2 = "BBBBBBBBBB";

    @Autowired
    private Table2Repository table2Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTable2MockMvc;

    private Table2 table2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table2 createEntity(EntityManager em) {
        Table2 table2 = new Table2();
        table2.setColumn2(DEFAULT_COLUMN_2);
        return table2;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table2 createUpdatedEntity(EntityManager em) {
        Table2 table2 = new Table2();
        table2.setColumn2(UPDATED_COLUMN_2);
        return table2;
    }

    @BeforeEach
    public void initTest() {
        table2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createTable2() throws Exception {
        int databaseSizeBeforeCreate = table2Repository.findAll().size();
        // Create the Table2
        restTable2MockMvc.perform(post("/api/table-2-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table2)))
            .andExpect(status().isCreated());

        // Validate the Table2 in the database
        List<Table2> table2List = table2Repository.findAll();
        assertThat(table2List).hasSize(databaseSizeBeforeCreate + 1);
        Table2 testTable2 = table2List.get(table2List.size() - 1);
        assertThat(testTable2.getColumn2()).isEqualTo(DEFAULT_COLUMN_2);
    }

    @Test
    @Transactional
    public void createTable2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = table2Repository.findAll().size();

        // Create the Table2 with an existing ID
        table2.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTable2MockMvc.perform(post("/api/table-2-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table2)))
            .andExpect(status().isBadRequest());

        // Validate the Table2 in the database
        List<Table2> table2List = table2Repository.findAll();
        assertThat(table2List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTable2s() throws Exception {
        // Initialize the database
        table2Repository.saveAndFlush(table2);

        // Get all the table2List
        restTable2MockMvc.perform(get("/api/table-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(table2.getId().intValue())))
            .andExpect(jsonPath("$.[*].Column2").value(hasItem(DEFAULT_COLUMN_2)));
    }
    
    @Test
    @Transactional
    public void getTable2() throws Exception {
        // Initialize the database
        table2Repository.saveAndFlush(table2);

        // Get the table2
        restTable2MockMvc.perform(get("/api/table-2-s/{id}", table2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(table2.getId().intValue()))
            .andExpect(jsonPath("$.Column2").value(DEFAULT_COLUMN_2));
    }
    @Test
    @Transactional
    public void getNonExistingTable2() throws Exception {
        // Get the table2
        restTable2MockMvc.perform(get("/api/table-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTable2() throws Exception {
        // Initialize the database
        table2Repository.saveAndFlush(table2);

        int databaseSizeBeforeUpdate = table2Repository.findAll().size();

        // Update the table2
        Table2 updatedTable2 = table2Repository.findById(table2.getId()).get();
        // Disconnect from session so that the updates on updatedTable2 are not directly saved in db
        em.detach(updatedTable2);
        updatedTable2.setColumn2(UPDATED_COLUMN_2);

        restTable2MockMvc.perform(put("/api/table-2-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTable2)))
            .andExpect(status().isOk());

        // Validate the Table2 in the database
        List<Table2> table2List = table2Repository.findAll();
        assertThat(table2List).hasSize(databaseSizeBeforeUpdate);
        Table2 testTable2 = table2List.get(table2List.size() - 1);
        assertThat(testTable2.getColumn2()).isEqualTo(UPDATED_COLUMN_2);
    }

    @Test
    @Transactional
    public void updateNonExistingTable2() throws Exception {
        int databaseSizeBeforeUpdate = table2Repository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTable2MockMvc.perform(put("/api/table-2-s").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table2)))
            .andExpect(status().isBadRequest());

        // Validate the Table2 in the database
        List<Table2> table2List = table2Repository.findAll();
        assertThat(table2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTable2() throws Exception {
        // Initialize the database
        table2Repository.saveAndFlush(table2);

        int databaseSizeBeforeDelete = table2Repository.findAll().size();

        // Delete the table2
        restTable2MockMvc.perform(delete("/api/table-2-s/{id}", table2.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Table2> table2List = table2Repository.findAll();
        assertThat(table2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
