package vendymachine.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConfirmationImpl.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConfirmation {
	
	String password();
	
	String confirmPassword();
	
	String message() default "{PasswordConfirmation}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
