package blackcv.web.rest;

import blackcv.service.ListReasonService;
import blackcv.web.rest.errors.BadRequestAlertException;
import blackcv.service.dto.ListReasonDTO;

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
 * REST controller for managing {@link blackcv.domain.ListReason}.
 */
@RestController
@RequestMapping("/api")
public class ListReasonResource {

    private final Logger log = LoggerFactory.getLogger(ListReasonResource.class);

    private final ListReasonService listReasonService;

    public ListReasonResource(ListReasonService listReasonService) {
        this.listReasonService = listReasonService;
    }

    /**
     * {@code GET  /list-reasons} : get all the listReasons.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listReasons in body.
     */
    @GetMapping("/list-reasons")
    public List<ListReasonDTO> getAllListReasons() {
        log.debug("REST request to get all ListReasons");
        return listReasonService.findAll();
    }

    /**
     * {@code GET  /list-reasons/:id} : get the "id" listReason.
     *
     * @param id the id of the listReasonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listReasonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-reasons/{id}")
    public ResponseEntity<ListReasonDTO> getListReason(@PathVariable Long id) {
        log.debug("REST request to get ListReason : {}", id);
        Optional<ListReasonDTO> listReasonDTO = listReasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listReasonDTO);
    }
}
