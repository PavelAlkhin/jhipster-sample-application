package eco.vessel.doc.repository;

import eco.vessel.doc.domain.Folders;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Folders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoldersRepository extends JpaRepository<Folders, Long> {
}
