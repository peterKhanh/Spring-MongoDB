package peter.mongo.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Account {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phoneNumber;
	private String stateOfOrigin;
	private String accountNumber;
	private BigDecimal accountBalance;
	private String status;
	private Date createAt = new Date();
}
