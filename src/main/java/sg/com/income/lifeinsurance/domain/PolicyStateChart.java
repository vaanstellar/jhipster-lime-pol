package sg.com.income.lifeinsurance.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * policy status will be tracked here. during attempt for retry, policy svc will review the record for that policy
 * to understand the state of the policy and trigger the step processing.
 */
@ApiModel(description = "policy status will be tracked here. during attempt for retry, policy svc will review the record for that policy to understand the state of the policy and trigger the step processing.")
@Entity
@Table(name = "policy_state_chart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PolicyStateChart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    @Column(name = "current_task", length = 25)
    private String currentTask;

    @Lob
    @Column(name = "current_payload")
    private String currentPayload;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_processing")
    private Boolean isProcessing;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @OneToOne(mappedBy = "policyStateChart")
    @JsonIgnore
    private PolicyDetails policyDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentTask() {
        return currentTask;
    }

    public PolicyStateChart currentTask(String currentTask) {
        this.currentTask = currentTask;
        return this;
    }

    public void setCurrentTask(String currentTask) {
        this.currentTask = currentTask;
    }

    public String getCurrentPayload() {
        return currentPayload;
    }

    public PolicyStateChart currentPayload(String currentPayload) {
        this.currentPayload = currentPayload;
        return this;
    }

    public void setCurrentPayload(String currentPayload) {
        this.currentPayload = currentPayload;
    }

    public Boolean isStatus() {
        return status;
    }

    public PolicyStateChart status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isIsProcessing() {
        return isProcessing;
    }

    public PolicyStateChart isProcessing(Boolean isProcessing) {
        this.isProcessing = isProcessing;
        return this;
    }

    public void setIsProcessing(Boolean isProcessing) {
        this.isProcessing = isProcessing;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PolicyStateChart createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public PolicyStateChart modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public PolicyDetails getPolicyDetails() {
        return policyDetails;
    }

    public PolicyStateChart policyDetails(PolicyDetails policyDetails) {
        this.policyDetails = policyDetails;
        return this;
    }

    public void setPolicyDetails(PolicyDetails policyDetails) {
        this.policyDetails = policyDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyStateChart)) {
            return false;
        }
        return id != null && id.equals(((PolicyStateChart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PolicyStateChart{" +
            "id=" + getId() +
            ", currentTask='" + getCurrentTask() + "'" +
            ", currentPayload='" + getCurrentPayload() + "'" +
            ", status='" + isStatus() + "'" +
            ", isProcessing='" + isIsProcessing() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
