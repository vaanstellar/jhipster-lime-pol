package sg.com.income.lifeinsurance.repository;

import sg.com.income.lifeinsurance.domain.DerivedDocs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DerivedDocs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DerivedDocsRepository extends JpaRepository<DerivedDocs, Long> {

}
