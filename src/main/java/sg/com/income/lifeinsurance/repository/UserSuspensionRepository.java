package sg.com.income.lifeinsurance.repository;

import sg.com.income.lifeinsurance.domain.UserSuspension;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserSuspension entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSuspensionRepository extends JpaRepository<UserSuspension, Long> {

}
