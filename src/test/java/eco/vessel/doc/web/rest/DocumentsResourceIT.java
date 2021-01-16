package eco.vessel.doc.web.rest;

import eco.vessel.doc.DocsApplicationJHipsterApp;
import eco.vessel.doc.config.TestSecurityConfiguration;
import eco.vessel.doc.domain.Documents;
import eco.vessel.doc.repository.DocumentsRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eco.vessel.doc.domain.enumeration.TypeDoc;
/**
 * Integration tests for the {@link DocumentsResource} REST controller.
 */
@SpringBootTest(classes = { DocsApplicationJHipsterApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DocumentsResourceIT {

    private static final String DEFAULT_DOCTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOCTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FOLDERGUID = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FOLDERGUID = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_DOCNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCNUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_DOCDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOCDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DATETIME = "AAAAAAAAAA";
    private static final String UPDATED_DATETIME = "BBBBBBBBBB";

    private static final TypeDoc DEFAULT_TYPEDOC = TypeDoc.ShipBillOfLading;
    private static final TypeDoc UPDATED_TYPEDOC = TypeDoc.OtherShipDocuments;

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentsMockMvc;

    private Documents documents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documents createEntity(EntityManager em) {
        Documents documents = new Documents()
            .doctype(DEFAULT_DOCTYPE)
            .filename(DEFAULT_FILENAME)
            .date(DEFAULT_DATE)
            .folderguid(DEFAULT_FOLDERGUID)
            .folder(DEFAULT_FOLDER)
            .docnumber(DEFAULT_DOCNUMBER)
            .docdate(DEFAULT_DOCDATE)
            .description(DEFAULT_DESCRIPTION)
            .datetime(DEFAULT_DATETIME)
            .typedoc(DEFAULT_TYPEDOC);
        return documents;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documents createUpdatedEntity(EntityManager em) {
        Documents documents = new Documents()
            .doctype(UPDATED_DOCTYPE)
            .filename(UPDATED_FILENAME)
            .date(UPDATED_DATE)
            .folderguid(UPDATED_FOLDERGUID)
            .folder(UPDATED_FOLDER)
            .docnumber(UPDATED_DOCNUMBER)
            .docdate(UPDATED_DOCDATE)
            .description(UPDATED_DESCRIPTION)
            .datetime(UPDATED_DATETIME)
            .typedoc(UPDATED_TYPEDOC);
        return documents;
    }

    @BeforeEach
    public void initTest() {
        documents = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocuments() throws Exception {
        int databaseSizeBeforeCreate = documentsRepository.findAll().size();
        // Create the Documents
        restDocumentsMockMvc.perform(post("/api/documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documents)))
            .andExpect(status().isCreated());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeCreate + 1);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getDoctype()).isEqualTo(DEFAULT_DOCTYPE);
        assertThat(testDocuments.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testDocuments.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDocuments.getFolderguid()).isEqualTo(DEFAULT_FOLDERGUID);
        assertThat(testDocuments.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testDocuments.getDocnumber()).isEqualTo(DEFAULT_DOCNUMBER);
        assertThat(testDocuments.getDocdate()).isEqualTo(DEFAULT_DOCDATE);
        assertThat(testDocuments.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocuments.getDatetime()).isEqualTo(DEFAULT_DATETIME);
        assertThat(testDocuments.getTypedoc()).isEqualTo(DEFAULT_TYPEDOC);
    }

    @Test
    @Transactional
    public void createDocumentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentsRepository.findAll().size();

        // Create the Documents with an existing ID
        documents.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentsMockMvc.perform(post("/api/documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documents)))
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList
        restDocumentsMockMvc.perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documents.getId().intValue())))
            .andExpect(jsonPath("$.[*].doctype").value(hasItem(DEFAULT_DOCTYPE)))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].folderguid").value(hasItem(DEFAULT_FOLDERGUID.toString())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].docnumber").value(hasItem(DEFAULT_DOCNUMBER)))
            .andExpect(jsonPath("$.[*].docdate").value(hasItem(DEFAULT_DOCDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(DEFAULT_DATETIME)))
            .andExpect(jsonPath("$.[*].typedoc").value(hasItem(DEFAULT_TYPEDOC.toString())));
    }
    
    @Test
    @Transactional
    public void getDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get the documents
        restDocumentsMockMvc.perform(get("/api/documents/{id}", documents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documents.getId().intValue()))
            .andExpect(jsonPath("$.doctype").value(DEFAULT_DOCTYPE))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.folderguid").value(DEFAULT_FOLDERGUID.toString()))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER))
            .andExpect(jsonPath("$.docnumber").value(DEFAULT_DOCNUMBER))
            .andExpect(jsonPath("$.docdate").value(DEFAULT_DOCDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.datetime").value(DEFAULT_DATETIME))
            .andExpect(jsonPath("$.typedoc").value(DEFAULT_TYPEDOC.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDocuments() throws Exception {
        // Get the documents
        restDocumentsMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // Update the documents
        Documents updatedDocuments = documentsRepository.findById(documents.getId()).get();
        // Disconnect from session so that the updates on updatedDocuments are not directly saved in db
        em.detach(updatedDocuments);
        updatedDocuments
            .doctype(UPDATED_DOCTYPE)
            .filename(UPDATED_FILENAME)
            .date(UPDATED_DATE)
            .folderguid(UPDATED_FOLDERGUID)
            .folder(UPDATED_FOLDER)
            .docnumber(UPDATED_DOCNUMBER)
            .docdate(UPDATED_DOCDATE)
            .description(UPDATED_DESCRIPTION)
            .datetime(UPDATED_DATETIME)
            .typedoc(UPDATED_TYPEDOC);

        restDocumentsMockMvc.perform(put("/api/documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocuments)))
            .andExpect(status().isOk());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getDoctype()).isEqualTo(UPDATED_DOCTYPE);
        assertThat(testDocuments.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testDocuments.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDocuments.getFolderguid()).isEqualTo(UPDATED_FOLDERGUID);
        assertThat(testDocuments.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testDocuments.getDocnumber()).isEqualTo(UPDATED_DOCNUMBER);
        assertThat(testDocuments.getDocdate()).isEqualTo(UPDATED_DOCDATE);
        assertThat(testDocuments.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocuments.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testDocuments.getTypedoc()).isEqualTo(UPDATED_TYPEDOC);
    }

    @Test
    @Transactional
    public void updateNonExistingDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentsMockMvc.perform(put("/api/documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documents)))
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeDelete = documentsRepository.findAll().size();

        // Delete the documents
        restDocumentsMockMvc.perform(delete("/api/documents/{id}", documents.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
