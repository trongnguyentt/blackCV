package blackcv.service.mapper;

import blackcv.domain.*;
import blackcv.service.dto.ListReasonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListReason} and its DTO {@link ListReasonDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListReasonMapper extends EntityMapper<ListReasonDTO, ListReason> {



    default ListReason fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListReason listReason = new ListReason();
        listReason.setId(id);
        return listReason;
    }
}
