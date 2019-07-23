package sg.com.income.lifeinsurance.repository;

import sg.com.income.lifeinsurance.domain.DocTypeReference;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocTypeReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocTypeReferenceRepository extends JpaRepository<DocTypeReference, Long> {

}
