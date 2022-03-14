package vendymachine.form;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import vendymachine.validation.PasswordConfirmation;

import javax.validation.constraints.*;
import java.util.*;

// For the Registration form, duh
@Getter
@Setter
@PasswordConfirmation(password = "password", confirmPassword = "confirmPass",
		message = "Password & Confirm Password must match")
public class RegistrationFormBean {
	
	// Twitch ID grabbed from Twitch API is used as username
	private Long username;
	
	@NotEmpty(message = "Password is required")
	@Length(min = 8, max = 24, message = "Password must be 8 to 24 characters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,24}", message = "Password must contain at least " +
			"one uppercase letter, one lowercase letter, and one number")
	private String password;
	
	@NotEmpty(message = "Confirm Password is required")
	private String confirmPass;
	
	private List<String> errorMessages = new ArrayList<>();
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
