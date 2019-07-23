package sg.com.income.lifeinsurance.repository;

import sg.com.income.lifeinsurance.domain.PolicyDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PolicyDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyDetailsRepository extends JpaRepository<PolicyDetails, Long> {

}
