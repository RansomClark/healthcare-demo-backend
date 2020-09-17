package io.catalyte.training.constants;

/**
 * holds io.catalyte.training.constants used throughout the project
 */
public class StringConstants {


  //status io.catalyte.training.constants
  public static final String ID_NOT_FOUND = "The id of the path parameter does not exist in the database";
  public static final String EMAIL_CONFLICT = "The email address is already associated with another patient";
  public static final String BAD_REQUEST_ID = "The id of the request body's entity must match the id of the path parameter";
  public static final String BAD_REQUEST_EMAIL = "The email must have an @ symbol and at least 1 or more characters before and after the @ symbol";
  public static final String BAD_REQUEST_STATE = "The customer's state must be one of the 50 US states that exist";
  public static final String BAD_REQUEST_ZIPCODE = "The zip code must have the format XXXXX or XXXXX-XXXX";
  public static final String BAD_REQUEST_MINIMUM = "The quantity must have a value greater than or equal to 0";

  public static final String BAD_REQUEST_PATIENT_NOT_FOUND = "The patient does not exist in the database";
  public static final String BAD_REQUEST_ENCOUNTER_NOT_FOUND = "The encounter does not exist in the database";
  public static final String NOT_FOUND = "Not Found";
  public static final String BAD_DATA = "Bad Data";
  public static final String SERVER_ERROR = "Server Error";
  public static final String UNEXPECTED_ERROR = "Unexpected Server Error";
  public static final String VALIDATION_ERROR = "Validation Error";

  //endpoint io.catalyte.training.constants

  public static final String CONTEXT_PATIENTS = "/patients";
  public static final String ID_ENDPOINT = "/{id}";

}
