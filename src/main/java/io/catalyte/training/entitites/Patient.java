package io.catalyte.training.entitites;

import static io.catalyte.training.constants.StringConstants.BAD_REQUEST_EMAIL;
import static io.catalyte.training.constants.StringConstants.BAD_REQUEST_MINIMUM;
import static io.catalyte.training.constants.StringConstants.BAD_REQUEST_ZIPCODE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Entity
@Table(name = "patient")
public class Patient {

  @Id
  @GeneratedValue
  @Required
  private Long id;

  @Required
  @NotBlank
  private String firstName;

  @Required
  @NotBlank
  private String lastName;

  @Required
  @NotNull
  @Pattern(regexp = "^\\d{3}-?\\d{2}-?\\d{4}$", message = "Must a valid social security number in XXX-XX-XXXX format")
  private String ssn;

  @Required
  @NotBlank
  @Pattern(regexp = ".+@.+\\..+", message = BAD_REQUEST_EMAIL)
  private String email;

  @Required
  @NotBlank
  private String city;

  @Required
  @NotBlank
  private String street;

  @Required
  @NotBlank
  private String state;

  @Required
  @NotBlank
  @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = BAD_REQUEST_ZIPCODE)
  private String postal;

  @Required
  @NotNull
  private Number age;

  @Required
  @NotNull
  private Number height;

  @Required
  @NotNull
  private Number weight;

  @Required
  @NotBlank
  private String insurance;

  @Required
  @NotBlank
  private String gender;

  public Patient() {

  }

  public Patient(Long id, String firstName, String lastName, String ssn, String email,
      String city, String street, String state, String postal, Number age, Number height,
      Number weight, String insurance, String gender) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.ssn = ssn;
    this.email = email;
    this.city = city;
    this.street = street;
    this.state = state;
    this.postal = postal;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.insurance = insurance;
    this.gender = gender;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostal() {
    return postal;
  }

  public void setPostal(String postal) {
    this.postal = postal;
  }

  public Number getAge() {
    return age;
  }

  public void setAge(Number age) {
    this.age = age;
  }

  public Number getHeight() {
    return height;
  }

  public void setHeight(Number height) {
    this.height = height;
  }

  public Number getWeight() {
    return weight;
  }

  public void setWeight(Number weight) {
    this.weight = weight;
  }

  public String getInsurance() {
    return insurance;
  }

  public void setInsurance(String insurance) {
    this.insurance = insurance;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Patient)) {
      return false;
    }
    Patient patient = (Patient) o;
    return Objects.equals(getId(), patient.getId()) &&
        Objects.equals(getFirstName(), patient.getFirstName()) &&
        Objects.equals(getLastName(), patient.getLastName()) &&
        Objects.equals(getSsn(), patient.getSsn()) &&
        Objects.equals(getEmail(), patient.getEmail()) &&
        Objects.equals(getCity(), patient.getCity()) &&
        Objects.equals(getStreet(), patient.getStreet()) &&
        Objects.equals(getState(), patient.getState()) &&
        Objects.equals(getPostal(), patient.getPostal()) &&
        Objects.equals(getAge(), patient.getAge()) &&
        Objects.equals(getHeight(), patient.getHeight()) &&
        Objects.equals(getWeight(), patient.getWeight()) &&
        Objects.equals(getInsurance(), patient.getInsurance()) &&
        Objects.equals(getGender(), patient.getGender());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getId(), getFirstName(), getLastName(), getSsn(), getEmail(), getCity(), getStreet(),
            getState(), getPostal(), getAge(), getHeight(), getWeight(), getInsurance(),
            getGender());
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(firstName) &&
        Objects.isNull(lastName) &&
        Objects.isNull(email) &&
        Objects.isNull(city) &&
        Objects.isNull(street) &&
        Objects.isNull(ssn) &&
        Objects.isNull(state) &&
        Objects.isNull(postal) &&
        Objects.isNull(age) &&
        Objects.isNull(height) &&
        Objects.isNull(weight) &&
        Objects.isNull(insurance) &&
        Objects.isNull(gender);
  }
}
