package au.com.wrkr.addressbook.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AddressBookError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String message;

	public AddressBookError(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
