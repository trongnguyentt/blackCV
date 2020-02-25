package blackcv.repository;

import blackcv.domain.ListReason;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListReason entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListReasonRepository extends JpaRepository<ListReason, Long> {

}
