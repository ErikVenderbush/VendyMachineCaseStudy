package vendymachine.validation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationImpl implements ConstraintValidator<PasswordConfirmation, Object> {
	
	private String password;
	private String confirmPassword;
	
	@Override
	public void initialize(PasswordConfirmation constraintAnnotation) {
		password = constraintAnnotation.password();
		confirmPassword = constraintAnnotation.confirmPassword();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		
		try {
			final String passwordValue = BeanUtils.getProperty(value, password);
			final String confirmPasswordValue = BeanUtils.getProperty(value, confirmPassword);
			
			if (passwordValue == null && confirmPasswordValue == null) {
				return true;
			}
			
			if (StringUtils.equals(passwordValue, confirmPasswordValue)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode(password)
				.addConstraintViolation();
		
		return false;
	}
}
