package sg.com.income.lifeinsurance.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * documents those are derived out of the requests generated.
 * e.g. ebao request, application form which needs to be uploaded via S3.
 */
@ApiModel(description = "documents those are derived out of the requests generated. e.g. ebao request, application form which needs to be uploaded via S3.")
@Entity
@Table(name = "derived_docs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DerivedDocs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "doc_content")
    private String docContent;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties("derivedDocs")
    private PolicyDetails policyDetails;

    @ManyToOne
    @JsonIgnoreProperties("derivedDocs")
    private DocTypeReference docTypeReference;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocContent() {
        return docContent;
    }

    public DerivedDocs docContent(String docContent) {
        this.docContent = docContent;
        return this;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public DerivedDocs createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public DerivedDocs modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public PolicyDetails getPolicyDetails() {
        return policyDetails;
    }

    public DerivedDocs policyDetails(PolicyDetails policyDetails) {
        this.policyDetails = policyDetails;
        return this;
    }

    public void setPolicyDetails(PolicyDetails policyDetails) {
        this.policyDetails = policyDetails;
    }

    public DocTypeReference getDocTypeReference() {
        return docTypeReference;
    }

    public DerivedDocs docTypeReference(DocTypeReference docTypeReference) {
        this.docTypeReference = docTypeReference;
        return this;
    }

    public void setDocTypeReference(DocTypeReference docTypeReference) {
        this.docTypeReference = docTypeReference;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DerivedDocs)) {
            return false;
        }
        return id != null && id.equals(((DerivedDocs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DerivedDocs{" +
            "id=" + getId() +
            ", docContent='" + getDocContent() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
