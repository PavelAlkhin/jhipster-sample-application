package eco.vessel.doc.repository;

import eco.vessel.doc.domain.Documents;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Documents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {
}
