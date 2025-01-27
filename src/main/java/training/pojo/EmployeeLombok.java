package training.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class EmployeeLombok {
	// private data members of POJO class
	String firstName;
	String lastName;
	int age;
}
