package peter.mongo.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phoneNumber;
	private String stateOfOrigin;
	private String accountNumber;
	private BigDecimal accountBalance;
	private String status;

}
