package sg.com.income.lifeinsurance.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User details is a central repository which holds the latest provided information by the customer.
 * the same information will be pre-populated during creation of new policy.
 */
@ApiModel(description = "User details is a central repository which holds the latest provided information by the customer. the same information will be pre-populated during creation of new policy.")
@Entity
@Table(name = "user_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 9)
    @Column(name = "nric", length = 9, nullable = false, unique = true)
    private String nric;

    @Column(name = "name")
    private String name;

    @Size(max = 1)
    @Column(name = "gender", length = 1)
    private String gender;

    @Size(max = 10)
    @Column(name = "birth_date", length = 10)
    private String birthDate;

    @NotNull
    @Size(max = 320)
    @Column(name = "email_id", length = 320, nullable = false)
    private String emailId;

    @Size(max = 15)
    @Column(name = "phone_no", length = 15)
    private String phoneNo;

    @Size(max = 1)
    @Column(name = "education_level", length = 1)
    private String educationLevel;

    @Size(max = 6)
    @Column(name = "residential_postal_code", length = 6)
    private String residentialPostalCode;

    @Size(max = 7)
    @Column(name = "residential_unit_no", length = 7)
    private String residentialUnitNo;

    @Column(name = "residential_address_1")
    private String residentialAddress1;

    @Column(name = "residential_address_2")
    private String residentialAddress2;

    @Column(name = "residential_address_3")
    private String residentialAddress3;

    @Column(name = "residential_address_4")
    private String residentialAddress4;

    @Size(max = 5)
    @Column(name = "residential_same_as_mailing", length = 5)
    private String residentialSameAsMailing;

    @Size(max = 10)
    @Column(name = "mailing_postal_code", length = 10)
    private String mailingPostalCode;

    @Size(max = 10)
    @Column(name = "mailing_unit_no", length = 10)
    private String mailingUnitNo;

    @Column(name = "mailing_address_1")
    private String mailingAddress1;

    @Column(name = "mailing_address_2")
    private String mailingAddress2;

    @Column(name = "mailing_address_3")
    private String mailingAddress3;

    @Column(name = "mailing_address_4")
    private String mailingAddress4;

    @Column(name = "occupation")
    private String occupation;

    @Size(max = 4)
    @Column(name = "occupation_code", length = 4)
    private String occupationCode;

    @Size(max = 3)
    @Column(name = "residential_status", length = 3)
    private String residentialStatus;

    @Size(max = 10)
    @Column(name = "nationality", length = 10)
    private String nationality;

    @Column(name = "place_of_nationality")
    private String placeOfNationality;

    @Size(max = 3)
    @Column(name = "country_of_birth", length = 3)
    private String countryOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "name_of_employer")
    private String nameOfEmployer;

    @Size(max = 15)
    @Column(name = "annual_income", length = 15)
    private String annualIncome;

    @Size(max = 25)
    @Column(name = "address_type", length = 25)
    private String addressType;

    @Size(max = 1)
    @Column(name = "marital_status", length = 1)
    private String maritalStatus;

    @Size(max = 9)
    @Column(name = "uinfin", length = 9)
    private String uinfin;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @OneToMany(mappedBy = "userDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PolicyDetails> policyDetails = new HashSet<>();

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

    public UserDetails nric(String nric) {
        this.nric = nric;
        return this;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getName() {
        return name;
    }

    public UserDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public UserDetails gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public UserDetails birthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailId() {
        return emailId;
    }

    public UserDetails emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public UserDetails phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public UserDetails educationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
        return this;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getResidentialPostalCode() {
        return residentialPostalCode;
    }

    public UserDetails residentialPostalCode(String residentialPostalCode) {
        this.residentialPostalCode = residentialPostalCode;
        return this;
    }

    public void setResidentialPostalCode(String residentialPostalCode) {
        this.residentialPostalCode = residentialPostalCode;
    }

    public String getResidentialUnitNo() {
        return residentialUnitNo;
    }

    public UserDetails residentialUnitNo(String residentialUnitNo) {
        this.residentialUnitNo = residentialUnitNo;
        return this;
    }

    public void setResidentialUnitNo(String residentialUnitNo) {
        this.residentialUnitNo = residentialUnitNo;
    }

    public String getResidentialAddress1() {
        return residentialAddress1;
    }

    public UserDetails residentialAddress1(String residentialAddress1) {
        this.residentialAddress1 = residentialAddress1;
        return this;
    }

    public void setResidentialAddress1(String residentialAddress1) {
        this.residentialAddress1 = residentialAddress1;
    }

    public String getResidentialAddress2() {
        return residentialAddress2;
    }

    public UserDetails residentialAddress2(String residentialAddress2) {
        this.residentialAddress2 = residentialAddress2;
        return this;
    }

    public void setResidentialAddress2(String residentialAddress2) {
        this.residentialAddress2 = residentialAddress2;
    }

    public String getResidentialAddress3() {
        return residentialAddress3;
    }

    public UserDetails residentialAddress3(String residentialAddress3) {
        this.residentialAddress3 = residentialAddress3;
        return this;
    }

    public void setResidentialAddress3(String residentialAddress3) {
        this.residentialAddress3 = residentialAddress3;
    }

    public String getResidentialAddress4() {
        return residentialAddress4;
    }

    public UserDetails residentialAddress4(String residentialAddress4) {
        this.residentialAddress4 = residentialAddress4;
        return this;
    }

    public void setResidentialAddress4(String residentialAddress4) {
        this.residentialAddress4 = residentialAddress4;
    }

    public String getResidentialSameAsMailing() {
        return residentialSameAsMailing;
    }

    public UserDetails residentialSameAsMailing(String residentialSameAsMailing) {
        this.residentialSameAsMailing = residentialSameAsMailing;
        return this;
    }

    public void setResidentialSameAsMailing(String residentialSameAsMailing) {
        this.residentialSameAsMailing = residentialSameAsMailing;
    }

    public String getMailingPostalCode() {
        return mailingPostalCode;
    }

    public UserDetails mailingPostalCode(String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
        return this;
    }

    public void setMailingPostalCode(String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
    }

    public String getMailingUnitNo() {
        return mailingUnitNo;
    }

    public UserDetails mailingUnitNo(String mailingUnitNo) {
        this.mailingUnitNo = mailingUnitNo;
        return this;
    }

    public void setMailingUnitNo(String mailingUnitNo) {
        this.mailingUnitNo = mailingUnitNo;
    }

    public String getMailingAddress1() {
        return mailingAddress1;
    }

    public UserDetails mailingAddress1(String mailingAddress1) {
        this.mailingAddress1 = mailingAddress1;
        return this;
    }

    public void setMailingAddress1(String mailingAddress1) {
        this.mailingAddress1 = mailingAddress1;
    }

    public String getMailingAddress2() {
        return mailingAddress2;
    }

    public UserDetails mailingAddress2(String mailingAddress2) {
        this.mailingAddress2 = mailingAddress2;
        return this;
    }

    public void setMailingAddress2(String mailingAddress2) {
        this.mailingAddress2 = mailingAddress2;
    }

    public String getMailingAddress3() {
        return mailingAddress3;
    }

    public UserDetails mailingAddress3(String mailingAddress3) {
        this.mailingAddress3 = mailingAddress3;
        return this;
    }

    public void setMailingAddress3(String mailingAddress3) {
        this.mailingAddress3 = mailingAddress3;
    }

    public String getMailingAddress4() {
        return mailingAddress4;
    }

    public UserDetails mailingAddress4(String mailingAddress4) {
        this.mailingAddress4 = mailingAddress4;
        return this;
    }

    public void setMailingAddress4(String mailingAddress4) {
        this.mailingAddress4 = mailingAddress4;
    }

    public String getOccupation() {
        return occupation;
    }

    public UserDetails occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupationCode() {
        return occupationCode;
    }

    public UserDetails occupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
        return this;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }

    public String getResidentialStatus() {
        return residentialStatus;
    }

    public UserDetails residentialStatus(String residentialStatus) {
        this.residentialStatus = residentialStatus;
        return this;
    }

    public void setResidentialStatus(String residentialStatus) {
        this.residentialStatus = residentialStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public UserDetails nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfNationality() {
        return placeOfNationality;
    }

    public UserDetails placeOfNationality(String placeOfNationality) {
        this.placeOfNationality = placeOfNationality;
        return this;
    }

    public void setPlaceOfNationality(String placeOfNationality) {
        this.placeOfNationality = placeOfNationality;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public UserDetails countryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
        return this;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public UserDetails placeOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getNameOfEmployer() {
        return nameOfEmployer;
    }

    public UserDetails nameOfEmployer(String nameOfEmployer) {
        this.nameOfEmployer = nameOfEmployer;
        return this;
    }

    public void setNameOfEmployer(String nameOfEmployer) {
        this.nameOfEmployer = nameOfEmployer;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public UserDetails annualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
        return this;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAddressType() {
        return addressType;
    }

    public UserDetails addressType(String addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public UserDetails maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getUinfin() {
        return uinfin;
    }

    public UserDetails uinfin(String uinfin) {
        this.uinfin = uinfin;
        return this;
    }

    public void setUinfin(String uinfin) {
        this.uinfin = uinfin;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public UserDetails createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public UserDetails modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<PolicyDetails> getPolicyDetails() {
        return policyDetails;
    }

    public UserDetails policyDetails(Set<PolicyDetails> policyDetails) {
        this.policyDetails = policyDetails;
        return this;
    }

    public UserDetails addPolicyDetails(PolicyDetails policyDetails) {
        this.policyDetails.add(policyDetails);
        policyDetails.setUserDetails(this);
        return this;
    }

    public UserDetails removePolicyDetails(PolicyDetails policyDetails) {
        this.policyDetails.remove(policyDetails);
        policyDetails.setUserDetails(null);
        return this;
    }

    public void setPolicyDetails(Set<PolicyDetails> policyDetails) {
        this.policyDetails = policyDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDetails)) {
            return false;
        }
        return id != null && id.equals(((UserDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
            "id=" + getId() +
            ", nric='" + getNric() + "'" +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", educationLevel='" + getEducationLevel() + "'" +
            ", residentialPostalCode='" + getResidentialPostalCode() + "'" +
            ", residentialUnitNo='" + getResidentialUnitNo() + "'" +
            ", residentialAddress1='" + getResidentialAddress1() + "'" +
            ", residentialAddress2='" + getResidentialAddress2() + "'" +
            ", residentialAddress3='" + getResidentialAddress3() + "'" +
            ", residentialAddress4='" + getResidentialAddress4() + "'" +
            ", residentialSameAsMailing='" + getResidentialSameAsMailing() + "'" +
            ", mailingPostalCode='" + getMailingPostalCode() + "'" +
            ", mailingUnitNo='" + getMailingUnitNo() + "'" +
            ", mailingAddress1='" + getMailingAddress1() + "'" +
            ", mailingAddress2='" + getMailingAddress2() + "'" +
            ", mailingAddress3='" + getMailingAddress3() + "'" +
            ", mailingAddress4='" + getMailingAddress4() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", occupationCode='" + getOccupationCode() + "'" +
            ", residentialStatus='" + getResidentialStatus() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", placeOfNationality='" + getPlaceOfNationality() + "'" +
            ", countryOfBirth='" + getCountryOfBirth() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", nameOfEmployer='" + getNameOfEmployer() + "'" +
            ", annualIncome='" + getAnnualIncome() + "'" +
            ", addressType='" + getAddressType() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", uinfin='" + getUinfin() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
