package vendymachine.form;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandFormBean {
	
	private Long cid;
	
	@NotEmpty(message = "Command Name is required")
	@Length(max = 255, message = "Command Name must be between 1 & 255 characters")
	private String command;
	
	@NotEmpty(message = "Command Response is required")
	@Length(max = 500, message = "Command Response must be between 1 & 500 characters")
	private String response;
	
	@NotEmpty(message = "Permission is required")
	private String permission;
	
	@NotNull(message = "Cooldown is required")
	@Range(min = 0, max = 60, message = "Cooldown must be between 0 & 60 minutes")
	private Integer cooldown;
	
	@NotNull(message = "Enabled is required")
	private Boolean enabled;
	
	private Integer user;
	
	private List<String> errorMessages = new ArrayList<>();
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
