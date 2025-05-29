package peter.mongo.serviceIplm;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import peter.mongo.dto.AccountInfo;
import peter.mongo.dto.AccountRequest;
import peter.mongo.dto.ApiResponse;
import peter.mongo.models.Account;
import peter.mongo.repository.AccountRepository;
import peter.mongo.service.AccountService;
import peter.mongo.util.AccountUtils;
@Service
public class AccountServiceIplm implements AccountService {
	@Autowired
	private AccountRepository accountRepo;
	@Override
	public ApiResponse createAccount(AccountRequest accountRequest) {
		if (accountRepo.existsByEmail(accountRequest.getEmail())) {
			return ApiResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		Account newAcc = Account.builder()
				.firstName(accountRequest.getFirstName())
				.lastName(accountRequest.getLastName())
				.address(accountRequest.getAddress())
				.email(accountRequest.getEmail())
				.stateOfOrigin(accountRequest.getStateOfOrigin())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.status("ACTIVE")
				.build();
		Account savedUser = accountRepo.insert(newAcc);
				
		return ApiResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(savedUser.getFirstName()+ " " + savedUser.getLastName())
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.build())
				.build();
	}

}
