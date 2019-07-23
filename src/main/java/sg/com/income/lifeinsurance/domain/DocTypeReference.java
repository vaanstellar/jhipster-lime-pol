package sg.com.income.lifeinsurance.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * document type reference is used to provide the reference.
 */
@ApiModel(description = "document type reference is used to provide the reference.")
@Entity
@Table(name = "doc_type_reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocTypeReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "doc_storage")
    private String docStorage;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @OneToMany(mappedBy = "docTypeReference")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DerivedDocs> derivedDocs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public DocTypeReference docType(String docType) {
        this.docType = docType;
        return this;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocStorage() {
        return docStorage;
    }

    public DocTypeReference docStorage(String docStorage) {
        this.docStorage = docStorage;
        return this;
    }

    public void setDocStorage(String docStorage) {
        this.docStorage = docStorage;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public DocTypeReference createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public DocTypeReference modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<DerivedDocs> getDerivedDocs() {
        return derivedDocs;
    }

    public DocTypeReference derivedDocs(Set<DerivedDocs> derivedDocs) {
        this.derivedDocs = derivedDocs;
        return this;
    }

    public DocTypeReference addDerivedDocs(DerivedDocs derivedDocs) {
        this.derivedDocs.add(derivedDocs);
        derivedDocs.setDocTypeReference(this);
        return this;
    }

    public DocTypeReference removeDerivedDocs(DerivedDocs derivedDocs) {
        this.derivedDocs.remove(derivedDocs);
        derivedDocs.setDocTypeReference(null);
        return this;
    }

    public void setDerivedDocs(Set<DerivedDocs> derivedDocs) {
        this.derivedDocs = derivedDocs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocTypeReference)) {
            return false;
        }
        return id != null && id.equals(((DocTypeReference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocTypeReference{" +
            "id=" + getId() +
            ", docType='" + getDocType() + "'" +
            ", docStorage='" + getDocStorage() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
