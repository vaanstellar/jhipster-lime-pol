package sg.com.income.lifeinsurance.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * policy details is a database table / Entity which holds all the information necessary for the ebao submission.
 * its also holds a copy of user details in order to hold the user details submitted by user during the policy submission
 */
@ApiModel(description = "policy details is a database table / Entity which holds all the information necessary for the ebao submission. its also holds a copy of user details in order to hold the user details submitted by user during the policy submission")
@Entity
@Table(name = "policy_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PolicyDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    @Column(name = "policy_code", length = 25, unique = true)
    private String policyCode;

    @Size(max = 10)
    @Column(name = "plan_code", length = 10)
    private String planCode;

    @Size(max = 25)
    @Column(name = "plan_type", length = 25)
    private String planType;

    @Column(name = "agent_code")
    private String agentCode;

    @Size(max = 25)
    @Column(name = "status", length = 25)
    private String status;

    @Column(name = "rider_names")
    private String riderNames;

    @Size(max = 5)
    @Column(name = "contact_by_call", length = 5)
    private String contactByCall;

    @Size(max = 5)
    @Column(name = "contact_by_sms", length = 5)
    private String contactBySMS;

    @NotNull
    @Size(max = 9)
    @Column(name = "nric", length = 9, nullable = false)
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

    @NotNull
    @Size(max = 15)
    @Column(name = "phone_no", length = 15, nullable = false)
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

    @Size(max = 6)
    @Column(name = "mailing_postal_code", length = 6)
    private String mailingPostalCode;

    @Size(max = 7)
    @Column(name = "mailing_unit_no", length = 7)
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

    @Size(max = 4)
    @Column(name = "occupation_code", length = 4)
    private String occupationCode;

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

    @Size(max = 15)
    @Column(name = "my_info_verified", length = 15)
    private String myInfoVerified;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private PolicyPaymentDetails policyPaymentDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private PolicyStateChart policyStateChart;

    @OneToMany(mappedBy = "policyDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DerivedDocs> derivedDocs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("policyDetails")
    private UserDetails userDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyCode() {
        return policyCode;
    }

    public PolicyDetails policyCode(String policyCode) {
        this.policyCode = policyCode;
        return this;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getPlanCode() {
        return planCode;
    }

    public PolicyDetails planCode(String planCode) {
        this.planCode = planCode;
        return this;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanType() {
        return planType;
    }

    public PolicyDetails planType(String planType) {
        this.planType = planType;
        return this;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public PolicyDetails agentCode(String agentCode) {
        this.agentCode = agentCode;
        return this;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getStatus() {
        return status;
    }

    public PolicyDetails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRiderNames() {
        return riderNames;
    }

    public PolicyDetails riderNames(String riderNames) {
        this.riderNames = riderNames;
        return this;
    }

    public void setRiderNames(String riderNames) {
        this.riderNames = riderNames;
    }

    public String getContactByCall() {
        return contactByCall;
    }

    public PolicyDetails contactByCall(String contactByCall) {
        this.contactByCall = contactByCall;
        return this;
    }

    public void setContactByCall(String contactByCall) {
        this.contactByCall = contactByCall;
    }

    public String getContactBySMS() {
        return contactBySMS;
    }

    public PolicyDetails contactBySMS(String contactBySMS) {
        this.contactBySMS = contactBySMS;
        return this;
    }

    public void setContactBySMS(String contactBySMS) {
        this.contactBySMS = contactBySMS;
    }

    public String getNric() {
        return nric;
    }

    public PolicyDetails nric(String nric) {
        this.nric = nric;
        return this;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getName() {
        return name;
    }

    public PolicyDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public PolicyDetails gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public PolicyDetails birthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailId() {
        return emailId;
    }

    public PolicyDetails emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public PolicyDetails phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public PolicyDetails educationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
        return this;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getResidentialPostalCode() {
        return residentialPostalCode;
    }

    public PolicyDetails residentialPostalCode(String residentialPostalCode) {
        this.residentialPostalCode = residentialPostalCode;
        return this;
    }

    public void setResidentialPostalCode(String residentialPostalCode) {
        this.residentialPostalCode = residentialPostalCode;
    }

    public String getResidentialUnitNo() {
        return residentialUnitNo;
    }

    public PolicyDetails residentialUnitNo(String residentialUnitNo) {
        this.residentialUnitNo = residentialUnitNo;
        return this;
    }

    public void setResidentialUnitNo(String residentialUnitNo) {
        this.residentialUnitNo = residentialUnitNo;
    }

    public String getResidentialAddress1() {
        return residentialAddress1;
    }

    public PolicyDetails residentialAddress1(String residentialAddress1) {
        this.residentialAddress1 = residentialAddress1;
        return this;
    }

    public void setResidentialAddress1(String residentialAddress1) {
        this.residentialAddress1 = residentialAddress1;
    }

    public String getResidentialAddress2() {
        return residentialAddress2;
    }

    public PolicyDetails residentialAddress2(String residentialAddress2) {
        this.residentialAddress2 = residentialAddress2;
        return this;
    }

    public void setResidentialAddress2(String residentialAddress2) {
        this.residentialAddress2 = residentialAddress2;
    }

    public String getResidentialAddress3() {
        return residentialAddress3;
    }

    public PolicyDetails residentialAddress3(String residentialAddress3) {
        this.residentialAddress3 = residentialAddress3;
        return this;
    }

    public void setResidentialAddress3(String residentialAddress3) {
        this.residentialAddress3 = residentialAddress3;
    }

    public String getResidentialAddress4() {
        return residentialAddress4;
    }

    public PolicyDetails residentialAddress4(String residentialAddress4) {
        this.residentialAddress4 = residentialAddress4;
        return this;
    }

    public void setResidentialAddress4(String residentialAddress4) {
        this.residentialAddress4 = residentialAddress4;
    }

    public String getResidentialSameAsMailing() {
        return residentialSameAsMailing;
    }

    public PolicyDetails residentialSameAsMailing(String residentialSameAsMailing) {
        this.residentialSameAsMailing = residentialSameAsMailing;
        return this;
    }

    public void setResidentialSameAsMailing(String residentialSameAsMailing) {
        this.residentialSameAsMailing = residentialSameAsMailing;
    }

    public String getMailingPostalCode() {
        return mailingPostalCode;
    }

    public PolicyDetails mailingPostalCode(String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
        return this;
    }

    public void setMailingPostalCode(String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
    }

    public String getMailingUnitNo() {
        return mailingUnitNo;
    }

    public PolicyDetails mailingUnitNo(String mailingUnitNo) {
        this.mailingUnitNo = mailingUnitNo;
        return this;
    }

    public void setMailingUnitNo(String mailingUnitNo) {
        this.mailingUnitNo = mailingUnitNo;
    }

    public String getMailingAddress1() {
        return mailingAddress1;
    }

    public PolicyDetails mailingAddress1(String mailingAddress1) {
        this.mailingAddress1 = mailingAddress1;
        return this;
    }

    public void setMailingAddress1(String mailingAddress1) {
        this.mailingAddress1 = mailingAddress1;
    }

    public String getMailingAddress2() {
        return mailingAddress2;
    }

    public PolicyDetails mailingAddress2(String mailingAddress2) {
        this.mailingAddress2 = mailingAddress2;
        return this;
    }

    public void setMailingAddress2(String mailingAddress2) {
        this.mailingAddress2 = mailingAddress2;
    }

    public String getMailingAddress3() {
        return mailingAddress3;
    }

    public PolicyDetails mailingAddress3(String mailingAddress3) {
        this.mailingAddress3 = mailingAddress3;
        return this;
    }

    public void setMailingAddress3(String mailingAddress3) {
        this.mailingAddress3 = mailingAddress3;
    }

    public String getMailingAddress4() {
        return mailingAddress4;
    }

    public PolicyDetails mailingAddress4(String mailingAddress4) {
        this.mailingAddress4 = mailingAddress4;
        return this;
    }

    public void setMailingAddress4(String mailingAddress4) {
        this.mailingAddress4 = mailingAddress4;
    }

    public String getOccupation() {
        return occupation;
    }

    public PolicyDetails occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getResidentialStatus() {
        return residentialStatus;
    }

    public PolicyDetails residentialStatus(String residentialStatus) {
        this.residentialStatus = residentialStatus;
        return this;
    }

    public void setResidentialStatus(String residentialStatus) {
        this.residentialStatus = residentialStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public PolicyDetails nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfNationality() {
        return placeOfNationality;
    }

    public PolicyDetails placeOfNationality(String placeOfNationality) {
        this.placeOfNationality = placeOfNationality;
        return this;
    }

    public void setPlaceOfNationality(String placeOfNationality) {
        this.placeOfNationality = placeOfNationality;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public PolicyDetails countryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
        return this;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public PolicyDetails placeOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getOccupationCode() {
        return occupationCode;
    }

    public PolicyDetails occupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
        return this;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }

    public String getNameOfEmployer() {
        return nameOfEmployer;
    }

    public PolicyDetails nameOfEmployer(String nameOfEmployer) {
        this.nameOfEmployer = nameOfEmployer;
        return this;
    }

    public void setNameOfEmployer(String nameOfEmployer) {
        this.nameOfEmployer = nameOfEmployer;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public PolicyDetails annualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
        return this;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAddressType() {
        return addressType;
    }

    public PolicyDetails addressType(String addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public PolicyDetails maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getUinfin() {
        return uinfin;
    }

    public PolicyDetails uinfin(String uinfin) {
        this.uinfin = uinfin;
        return this;
    }

    public void setUinfin(String uinfin) {
        this.uinfin = uinfin;
    }

    public String getMyInfoVerified() {
        return myInfoVerified;
    }

    public PolicyDetails myInfoVerified(String myInfoVerified) {
        this.myInfoVerified = myInfoVerified;
        return this;
    }

    public void setMyInfoVerified(String myInfoVerified) {
        this.myInfoVerified = myInfoVerified;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PolicyDetails createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public PolicyDetails modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public PolicyPaymentDetails getPolicyPaymentDetails() {
        return policyPaymentDetails;
    }

    public PolicyDetails policyPaymentDetails(PolicyPaymentDetails policyPaymentDetails) {
        this.policyPaymentDetails = policyPaymentDetails;
        return this;
    }

    public void setPolicyPaymentDetails(PolicyPaymentDetails policyPaymentDetails) {
        this.policyPaymentDetails = policyPaymentDetails;
    }

    public PolicyStateChart getPolicyStateChart() {
        return policyStateChart;
    }

    public PolicyDetails policyStateChart(PolicyStateChart policyStateChart) {
        this.policyStateChart = policyStateChart;
        return this;
    }

    public void setPolicyStateChart(PolicyStateChart policyStateChart) {
        this.policyStateChart = policyStateChart;
    }

    public Set<DerivedDocs> getDerivedDocs() {
        return derivedDocs;
    }

    public PolicyDetails derivedDocs(Set<DerivedDocs> derivedDocs) {
        this.derivedDocs = derivedDocs;
        return this;
    }

    public PolicyDetails addDerivedDocs(DerivedDocs derivedDocs) {
        this.derivedDocs.add(derivedDocs);
        derivedDocs.setPolicyDetails(this);
        return this;
    }

    public PolicyDetails removeDerivedDocs(DerivedDocs derivedDocs) {
        this.derivedDocs.remove(derivedDocs);
        derivedDocs.setPolicyDetails(null);
        return this;
    }

    public void setDerivedDocs(Set<DerivedDocs> derivedDocs) {
        this.derivedDocs = derivedDocs;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public PolicyDetails userDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
        return this;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyDetails)) {
            return false;
        }
        return id != null && id.equals(((PolicyDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PolicyDetails{" +
            "id=" + getId() +
            ", policyCode='" + getPolicyCode() + "'" +
            ", planCode='" + getPlanCode() + "'" +
            ", planType='" + getPlanType() + "'" +
            ", agentCode='" + getAgentCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", riderNames='" + getRiderNames() + "'" +
            ", contactByCall='" + getContactByCall() + "'" +
            ", contactBySMS='" + getContactBySMS() + "'" +
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
            ", residentialStatus='" + getResidentialStatus() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", placeOfNationality='" + getPlaceOfNationality() + "'" +
            ", countryOfBirth='" + getCountryOfBirth() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", occupationCode='" + getOccupationCode() + "'" +
            ", nameOfEmployer='" + getNameOfEmployer() + "'" +
            ", annualIncome='" + getAnnualIncome() + "'" +
            ", addressType='" + getAddressType() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", uinfin='" + getUinfin() + "'" +
            ", myInfoVerified='" + getMyInfoVerified() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
