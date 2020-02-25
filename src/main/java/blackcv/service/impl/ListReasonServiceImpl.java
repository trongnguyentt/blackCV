package blackcv.service.impl;

import blackcv.service.ListReasonService;
import blackcv.domain.ListReason;
import blackcv.repository.ListReasonRepository;
import blackcv.service.dto.ListReasonDTO;
import blackcv.service.mapper.ListReasonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListReason}.
 */
@Service
@Transactional
public class ListReasonServiceImpl implements ListReasonService {

    private final Logger log = LoggerFactory.getLogger(ListReasonServiceImpl.class);

    private final ListReasonRepository listReasonRepository;

    private final ListReasonMapper listReasonMapper;

    public ListReasonServiceImpl(ListReasonRepository listReasonRepository, ListReasonMapper listReasonMapper) {
        this.listReasonRepository = listReasonRepository;
        this.listReasonMapper = listReasonMapper;
    }

    /**
     * Save a listReason.
     *
     * @param listReasonDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ListReasonDTO save(ListReasonDTO listReasonDTO) {
        log.debug("Request to save ListReason : {}", listReasonDTO);
        ListReason listReason = listReasonMapper.toEntity(listReasonDTO);
        listReason = listReasonRepository.save(listReason);
        return listReasonMapper.toDto(listReason);
    }

    /**
     * Get all the listReasons.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListReasonDTO> findAll() {
        log.debug("Request to get all ListReasons");
        return listReasonRepository.findAll().stream()
            .map(listReasonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listReason by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ListReasonDTO> findOne(Long id) {
        log.debug("Request to get ListReason : {}", id);
        return listReasonRepository.findById(id)
            .map(listReasonMapper::toDto);
    }

    /**
     * Delete the listReason by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ListReason : {}", id);
        listReasonRepository.deleteById(id);
    }
}
