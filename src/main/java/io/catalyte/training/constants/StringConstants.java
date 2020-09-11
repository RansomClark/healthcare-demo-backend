package io.catalyte.training.constants;

/**
 * holds io.catalyte.training.constants used throughout the project
 */
public class StringConstants {

  public static final String REQUIRED_FIELD = " is a required field";
  public static final String ADMIN = "ADMIN";
  public static final String EMPLOYEE = "EMPLOYEE";
  public static String APPLICATION_JSON = "application/json";
  public static final String GENERATED_ID = "The database generated id";

  //status io.catalyte.training.constants
  public static final String ID_NOT_FOUND = "The id of the path parameter does not exist in the database";
  public static final String EMAIL_CONFLICT = "The email address is already associated with another patient";
  public static final String SKU_CONFLICT = "The sku of the request body's entity has been used already";
  public static final String BAD_REQUEST_ID = "The id of the request body's entity must match the id of the path parameter";
  public static final String BAD_REQUEST_ROLE = "Invalid role.  Valid roles include: ADMIN and EMPLOYEE";
  public static final String BAD_REQUEST_EMAIL = "The email must have an @ symbol and at least 1 or more characters before and after the @ symbol";
  public static final String BAD_REQUEST_PASSWORD =
      "The password must have at least 8 characters";
  public static final String BAD_REQUEST_PRICE = "The product's price must have exactly 2 decimal places";
  public static final String BAD_REQUEST_STATE = "The patient's state must be one of the 50 US states that exist";
  public static final String BAD_REQUEST_ZIPCODE = "The zip code must have the format XXXXX or XXXXX-XXXX";
  public static final String BAD_REQUEST_MINIMUM = "The quantity must have a value greater than or equal to 0";
  public static final String BAD_REQUEST_PATIENT_NOT_FOUND = "The patient does not exist in the database";
  public static final String BAD_REQUEST_PRODUCT_NOT_FOUND = "The product does not exist in the database";
  public static final String BAD_REQUEST_ORDER_NOT_FOUND = "The order does not exist in the database";
  public static final String BAD_REQUEST_USER_NOT_FOUND = "The user does not exist in the database";
  public static final String BAD_REQUEST_ORDER_TOTAL = "The order's orderTotal must have exactly 2 decimal places";
  public static final String SERVICE_UNAVAILABLE = "The database is not running, service is unavailable at the moment";
  public static final String UNEXPECTED_SERVER_ERROR = "An unexpected server error has occurred";
  public static final String NOT_FOUND = "Not Found";
  public static final String BAD_DATA = "Bad Data";
  public static final String SERVER_ERROR = "Server Error";
  public static final String UNEXPECTED_ERROR = "Unexpected Server Error";
  public static final String VALIDATION_ERROR = "Validation Error";
  public static final String INVALID_POSITIVE = "A positive value is required for this field";


  //endpoint io.catalyte.training.constants
  public static final String CONTEXT_USERS = "/users";
  public static final String CONTEXT_PRODUCTS = "/products";
  public static final String CONTEXT_PATIENTS = "/patients";
  public static final String CONTEXT_ORDERS = "/orders";
  public static final String CONTEXT_ITEMS = "/items";
  public static final String ID_ENDPOINT = "/{id}";
  public static final Long FIRST_ID = 1L;
  public static final String ADDRESS_ENDPOINT = "/address";

}
