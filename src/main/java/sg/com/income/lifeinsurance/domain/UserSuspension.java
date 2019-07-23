package sg.com.income.lifeinsurance.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * user suspension functionality is achieved using this table, CIAM service is the consumer for this API
 */
@ApiModel(description = "user suspension functionality is achieved using this table, CIAM service is the consumer for this API")
@Entity
@Table(name = "user_suspension")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserSuspension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "email_id", unique = true)
    private String emailId;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "suspension_time_in_minutes")
    private Long suspensionTimeInMinutes;

    @Size(max = 25)
    @Column(name = "reason", length = 25)
    private String reason;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public UserSuspension emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public UserSuspension retryCount(Integer retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Long getSuspensionTimeInMinutes() {
        return suspensionTimeInMinutes;
    }

    public UserSuspension suspensionTimeInMinutes(Long suspensionTimeInMinutes) {
        this.suspensionTimeInMinutes = suspensionTimeInMinutes;
        return this;
    }

    public void setSuspensionTimeInMinutes(Long suspensionTimeInMinutes) {
        this.suspensionTimeInMinutes = suspensionTimeInMinutes;
    }

    public String getReason() {
        return reason;
    }

    public UserSuspension reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public UserSuspension createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public UserSuspension modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSuspension)) {
            return false;
        }
        return id != null && id.equals(((UserSuspension) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserSuspension{" +
            "id=" + getId() +
            ", emailId='" + getEmailId() + "'" +
            ", retryCount=" + getRetryCount() +
            ", suspensionTimeInMinutes=" + getSuspensionTimeInMinutes() +
            ", reason='" + getReason() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
