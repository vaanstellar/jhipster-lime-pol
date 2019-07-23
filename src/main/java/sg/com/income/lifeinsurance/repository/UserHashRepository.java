package sg.com.income.lifeinsurance.repository;

import sg.com.income.lifeinsurance.domain.UserHash;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserHash entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserHashRepository extends JpaRepository<UserHash, Long> {

}
