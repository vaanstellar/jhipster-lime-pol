package sg.com.income.lifeinsurance.repository;

import sg.com.income.lifeinsurance.domain.PolicyPaymentDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PolicyPaymentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyPaymentDetailsRepository extends JpaRepository<PolicyPaymentDetails, Long> {

}
