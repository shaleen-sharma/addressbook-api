package au.com.wrkr.addressbook;

import au.com.wrkr.addressbook.exception.AddressBookError;
import au.com.wrkr.addressbook.exception.AddressBookNameAlreadyExists;
import au.com.wrkr.addressbook.exception.InvalidPhoneNumberException;
import au.com.wrkr.addressbook.exception.NoAddressBookFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class AddressBookControllerAdvice
        extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(AddressBookControllerAdvice.class);

    @ExceptionHandler(value
            = {InvalidPhoneNumberException.class,
			NoAddressBookFoundException.class,
            ConstraintViolationException.class, AddressBookNameAlreadyExists.class})
    protected ResponseEntity<Object> handleBadRequests(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Invalid request, " + ex.getMessage();
        logger.error(bodyOfResponse, ex);
        return buildResponseEntity(new AddressBookError(HttpStatus.BAD_REQUEST
				, bodyOfResponse));
    }


    @ExceptionHandler(value
            = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeExceptions(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Error Occurred, " + ex.getMessage();
        logger.error(bodyOfResponse, ex);
        return buildResponseEntity(new AddressBookError(HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse));
    }

    private ResponseEntity<Object> buildResponseEntity(AddressBookError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}