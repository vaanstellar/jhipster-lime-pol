package sg.com.income.lifeinsurance.repository;

import sg.com.income.lifeinsurance.domain.PolicyStateChart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PolicyStateChart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyStateChartRepository extends JpaRepository<PolicyStateChart, Long> {

}
