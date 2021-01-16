package eco.vessel.doc.web.rest;

import eco.vessel.doc.DocsApplicationJHipsterApp;
import eco.vessel.doc.config.TestSecurityConfiguration;
import eco.vessel.doc.domain.Folders;
import eco.vessel.doc.repository.FoldersRepository;

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
 * Integration tests for the {@link FoldersResource} REST controller.
 */
@SpringBootTest(classes = { DocsApplicationJHipsterApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class FoldersResourceIT {

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    @Autowired
    private FoldersRepository foldersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoldersMockMvc;

    private Folders folders;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Folders createEntity(EntityManager em) {
        Folders folders = new Folders()
            .folder(DEFAULT_FOLDER);
        return folders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Folders createUpdatedEntity(EntityManager em) {
        Folders folders = new Folders()
            .folder(UPDATED_FOLDER);
        return folders;
    }

    @BeforeEach
    public void initTest() {
        folders = createEntity(em);
    }

    @Test
    @Transactional
    public void createFolders() throws Exception {
        int databaseSizeBeforeCreate = foldersRepository.findAll().size();
        // Create the Folders
        restFoldersMockMvc.perform(post("/api/folders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folders)))
            .andExpect(status().isCreated());

        // Validate the Folders in the database
        List<Folders> foldersList = foldersRepository.findAll();
        assertThat(foldersList).hasSize(databaseSizeBeforeCreate + 1);
        Folders testFolders = foldersList.get(foldersList.size() - 1);
        assertThat(testFolders.getFolder()).isEqualTo(DEFAULT_FOLDER);
    }

    @Test
    @Transactional
    public void createFoldersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foldersRepository.findAll().size();

        // Create the Folders with an existing ID
        folders.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoldersMockMvc.perform(post("/api/folders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folders)))
            .andExpect(status().isBadRequest());

        // Validate the Folders in the database
        List<Folders> foldersList = foldersRepository.findAll();
        assertThat(foldersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFolders() throws Exception {
        // Initialize the database
        foldersRepository.saveAndFlush(folders);

        // Get all the foldersList
        restFoldersMockMvc.perform(get("/api/folders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(folders.getId().intValue())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)));
    }
    
    @Test
    @Transactional
    public void getFolders() throws Exception {
        // Initialize the database
        foldersRepository.saveAndFlush(folders);

        // Get the folders
        restFoldersMockMvc.perform(get("/api/folders/{id}", folders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(folders.getId().intValue()))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER));
    }
    @Test
    @Transactional
    public void getNonExistingFolders() throws Exception {
        // Get the folders
        restFoldersMockMvc.perform(get("/api/folders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFolders() throws Exception {
        // Initialize the database
        foldersRepository.saveAndFlush(folders);

        int databaseSizeBeforeUpdate = foldersRepository.findAll().size();

        // Update the folders
        Folders updatedFolders = foldersRepository.findById(folders.getId()).get();
        // Disconnect from session so that the updates on updatedFolders are not directly saved in db
        em.detach(updatedFolders);
        updatedFolders
            .folder(UPDATED_FOLDER);

        restFoldersMockMvc.perform(put("/api/folders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFolders)))
            .andExpect(status().isOk());

        // Validate the Folders in the database
        List<Folders> foldersList = foldersRepository.findAll();
        assertThat(foldersList).hasSize(databaseSizeBeforeUpdate);
        Folders testFolders = foldersList.get(foldersList.size() - 1);
        assertThat(testFolders.getFolder()).isEqualTo(UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void updateNonExistingFolders() throws Exception {
        int databaseSizeBeforeUpdate = foldersRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoldersMockMvc.perform(put("/api/folders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folders)))
            .andExpect(status().isBadRequest());

        // Validate the Folders in the database
        List<Folders> foldersList = foldersRepository.findAll();
        assertThat(foldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFolders() throws Exception {
        // Initialize the database
        foldersRepository.saveAndFlush(folders);

        int databaseSizeBeforeDelete = foldersRepository.findAll().size();

        // Delete the folders
        restFoldersMockMvc.perform(delete("/api/folders/{id}", folders.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Folders> foldersList = foldersRepository.findAll();
        assertThat(foldersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
