package blackcv.service;

import blackcv.service.dto.ListReasonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link blackcv.domain.ListReason}.
 */
public interface ListReasonService {

    /**
     * Save a listReason.
     *
     * @param listReasonDTO the entity to save.
     * @return the persisted entity.
     */
    ListReasonDTO save(ListReasonDTO listReasonDTO);

    /**
     * Get all the listReasons.
     *
     * @return the list of entities.
     */
    List<ListReasonDTO> findAll();


    /**
     * Get the "id" listReason.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ListReasonDTO> findOne(Long id);

    /**
     * Delete the "id" listReason.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
