package eco.vessel.doc.web.rest;

import eco.vessel.doc.domain.Folders;
import eco.vessel.doc.repository.FoldersRepository;
import eco.vessel.doc.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link eco.vessel.doc.domain.Folders}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FoldersResource {

    private final Logger log = LoggerFactory.getLogger(FoldersResource.class);

    private static final String ENTITY_NAME = "folders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoldersRepository foldersRepository;

    public FoldersResource(FoldersRepository foldersRepository) {
        this.foldersRepository = foldersRepository;
    }

    /**
     * {@code POST  /folders} : Create a new folders.
     *
     * @param folders the folders to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new folders, or with status {@code 400 (Bad Request)} if the folders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/folders")
    public ResponseEntity<Folders> createFolders(@RequestBody Folders folders) throws URISyntaxException {
        log.debug("REST request to save Folders : {}", folders);
        if (folders.getId() != null) {
            throw new BadRequestAlertException("A new folders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Folders result = foldersRepository.save(folders);
        return ResponseEntity.created(new URI("/api/folders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /folders} : Updates an existing folders.
     *
     * @param folders the folders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated folders,
     * or with status {@code 400 (Bad Request)} if the folders is not valid,
     * or with status {@code 500 (Internal Server Error)} if the folders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/folders")
    public ResponseEntity<Folders> updateFolders(@RequestBody Folders folders) throws URISyntaxException {
        log.debug("REST request to update Folders : {}", folders);
        if (folders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Folders result = foldersRepository.save(folders);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, folders.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /folders} : get all the folders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of folders in body.
     */
    @GetMapping("/folders")
    public ResponseEntity<List<Folders>> getAllFolders(Pageable pageable) {
        log.debug("REST request to get a page of Folders");
        Page<Folders> page = foldersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /folders/:id} : get the "id" folders.
     *
     * @param id the id of the folders to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the folders, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/folders/{id}")
    public ResponseEntity<Folders> getFolders(@PathVariable Long id) {
        log.debug("REST request to get Folders : {}", id);
        Optional<Folders> folders = foldersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(folders);
    }

    /**
     * {@code DELETE  /folders/:id} : delete the "id" folders.
     *
     * @param id the id of the folders to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/folders/{id}")
    public ResponseEntity<Void> deleteFolders(@PathVariable Long id) {
        log.debug("REST request to delete Folders : {}", id);
        foldersRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
