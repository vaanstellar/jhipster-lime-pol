package sg.com.income.lifeinsurance.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Policy payment details is used to hold the payment related information.
 * A subsequent callback from the ESB server is stored in this table and further sent in ebao during submission.
 */
@ApiModel(description = "Policy payment details is used to hold the payment related information. A subsequent callback from the ESB server is stored in this table and further sent in ebao during submission.")
@Entity
@Table(name = "policy_payment_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PolicyPaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "payment_transaction_no", length = 50)
    private String paymentTransactionNo;

    @Column(name = "prn_no")
    private String prnNo;

    @Column(name = "encrypted_prn_no")
    private String encryptedPrnNo;

    @Size(max = 25)
    @Column(name = "total_first_premium", length = 25)
    private String totalFirstPremium;

    @Size(max = 25)
    @Column(name = "payment_method", length = 25)
    private String paymentMethod;

    @Size(max = 25)
    @Column(name = "esb_payment_mode", length = 25)
    private String esbPaymentMode;

    @Size(max = 10)
    @Column(name = "payment_status", length = 10)
    private String paymentStatus;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "modified_time")
    private Instant modifiedTime;

    @OneToOne(mappedBy = "policyPaymentDetails")
    @JsonIgnore
    private PolicyDetails policyDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentTransactionNo() {
        return paymentTransactionNo;
    }

    public PolicyPaymentDetails paymentTransactionNo(String paymentTransactionNo) {
        this.paymentTransactionNo = paymentTransactionNo;
        return this;
    }

    public void setPaymentTransactionNo(String paymentTransactionNo) {
        this.paymentTransactionNo = paymentTransactionNo;
    }

    public String getPrnNo() {
        return prnNo;
    }

    public PolicyPaymentDetails prnNo(String prnNo) {
        this.prnNo = prnNo;
        return this;
    }

    public void setPrnNo(String prnNo) {
        this.prnNo = prnNo;
    }

    public String getEncryptedPrnNo() {
        return encryptedPrnNo;
    }

    public PolicyPaymentDetails encryptedPrnNo(String encryptedPrnNo) {
        this.encryptedPrnNo = encryptedPrnNo;
        return this;
    }

    public void setEncryptedPrnNo(String encryptedPrnNo) {
        this.encryptedPrnNo = encryptedPrnNo;
    }

    public String getTotalFirstPremium() {
        return totalFirstPremium;
    }

    public PolicyPaymentDetails totalFirstPremium(String totalFirstPremium) {
        this.totalFirstPremium = totalFirstPremium;
        return this;
    }

    public void setTotalFirstPremium(String totalFirstPremium) {
        this.totalFirstPremium = totalFirstPremium;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public PolicyPaymentDetails paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getEsbPaymentMode() {
        return esbPaymentMode;
    }

    public PolicyPaymentDetails esbPaymentMode(String esbPaymentMode) {
        this.esbPaymentMode = esbPaymentMode;
        return this;
    }

    public void setEsbPaymentMode(String esbPaymentMode) {
        this.esbPaymentMode = esbPaymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public PolicyPaymentDetails paymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PolicyPaymentDetails createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public PolicyPaymentDetails modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public PolicyPaymentDetails createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public PolicyPaymentDetails modifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public PolicyDetails getPolicyDetails() {
        return policyDetails;
    }

    public PolicyPaymentDetails policyDetails(PolicyDetails policyDetails) {
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
        if (!(o instanceof PolicyPaymentDetails)) {
            return false;
        }
        return id != null && id.equals(((PolicyPaymentDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PolicyPaymentDetails{" +
            "id=" + getId() +
            ", paymentTransactionNo='" + getPaymentTransactionNo() + "'" +
            ", prnNo='" + getPrnNo() + "'" +
            ", encryptedPrnNo='" + getEncryptedPrnNo() + "'" +
            ", totalFirstPremium='" + getTotalFirstPremium() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", esbPaymentMode='" + getEsbPaymentMode() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifiedTime='" + getModifiedTime() + "'" +
            "}";
    }
}
