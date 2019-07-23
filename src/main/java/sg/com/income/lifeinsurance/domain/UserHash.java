package sg.com.income.lifeinsurance.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * User Hash to store data hashing
 */
@ApiModel(description = "User Hash to store data hashing")
@Entity
@Table(name = "user_hash")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserHash implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "nric", unique = true)
    private String nric;

    @Column(name = "hash_1")
    private String hash1;

    @Column(name = "hash_2")
    private String hash2;

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

    public String getNric() {
        return nric;
    }

    public UserHash nric(String nric) {
        this.nric = nric;
        return this;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getHash1() {
        return hash1;
    }

    public UserHash hash1(String hash1) {
        this.hash1 = hash1;
        return this;
    }

    public void setHash1(String hash1) {
        this.hash1 = hash1;
    }

    public String getHash2() {
        return hash2;
    }

    public UserHash hash2(String hash2) {
        this.hash2 = hash2;
        return this;
    }

    public void setHash2(String hash2) {
        this.hash2 = hash2;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public UserHash createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public UserHash modifiedDate(LocalDate modifiedDate) {
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
        if (!(o instanceof UserHash)) {
            return false;
        }
        return id != null && id.equals(((UserHash) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserHash{" +
            "id=" + getId() +
            ", nric='" + getNric() + "'" +
            ", hash1='" + getHash1() + "'" +
            ", hash2='" + getHash2() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
