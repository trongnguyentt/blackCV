package blackcv.web.rest;

import blackcv.service.CVService;
import blackcv.web.rest.errors.BadRequestAlertException;
import blackcv.service.dto.CVDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link blackcv.domain.CV}.
 */
@RestController
@RequestMapping("/api")
public class CVResource {

    private final Logger log = LoggerFactory.getLogger(CVResource.class);

    private static final String ENTITY_NAME = "cV";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVService cVService;

    public CVResource(CVService cVService) {
        this.cVService = cVService;
    }

    /**
     * {@code POST  /cvs} : Create a new cV.
     *
     * @param cVDTO the cVDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVDTO, or with status {@code 400 (Bad Request)} if the cV has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cvs")
    public ResponseEntity<CVDTO> createCV(@RequestBody CVDTO cVDTO) throws URISyntaxException {
        log.debug("REST request to save CV : {}", cVDTO);
        if (cVDTO.getId() != null) {
            throw new BadRequestAlertException("A new cV cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVDTO result = cVService.save(cVDTO);
        return ResponseEntity.created(new URI("/api/cvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cvs} : Updates an existing cV.
     *
     * @param cVDTO the cVDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVDTO,
     * or with status {@code 400 (Bad Request)} if the cVDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cvs")
    public ResponseEntity<CVDTO> updateCV(@RequestBody CVDTO cVDTO) throws URISyntaxException {
        log.debug("REST request to update CV : {}", cVDTO);
        if (cVDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVDTO result = cVService.save(cVDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cvs} : get all the cVS.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVS in body.
     */
    @GetMapping("/cvs")
    public List<CVDTO> getAllCVS() {
        log.debug("REST request to get all CVS");
        return cVService.findAll();
    }

    /**
     * {@code GET  /cvs/:id} : get the "id" cV.
     *
     * @param id the id of the cVDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cvs/{id}")
    public ResponseEntity<CVDTO> getCV(@PathVariable Long id) {
        log.debug("REST request to get CV : {}", id);
        Optional<CVDTO> cVDTO = cVService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVDTO);
    }

    /**
     * {@code DELETE  /cvs/:id} : delete the "id" cV.
     *
     * @param id the id of the cVDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cvs/{id}")
    public ResponseEntity<Void> deleteCV(@PathVariable Long id) {
        log.debug("REST request to delete CV : {}", id);
        cVService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
