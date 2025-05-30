package peter.mongo.serviceIplm;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import peter.mongo.dto.AccountInfo;
import peter.mongo.dto.AccountRequest;
import peter.mongo.dto.ApiResponse;
import peter.mongo.dto.DepositWithdrawDto;
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
				.phoneNumber(accountRequest.getPhoneNumber())
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
	@Override
	public ApiResponse depositAccount(DepositWithdrawDto depositWithdrawDto) {
		// Kiem tra tai khoan Ton
		boolean isAccountExist = accountRepo.existsByAccountNumber(depositWithdrawDto.getAccountNumber());
		if (!isAccountExist) {
			return ApiResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.accountInfo(null)
					.build();
		}
		// Thuc hien nop tien vao tai khoan
		Account accountDeposite = accountRepo.findByAccountNumber(depositWithdrawDto.getAccountNumber());
		accountDeposite.setAccountBalance(accountDeposite.getAccountBalance().add(depositWithdrawDto.getAmount()));
		accountRepo.save(accountDeposite);
		return ApiResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(accountDeposite.getFirstName()+ " " + accountDeposite.getLastName())
						.accountBalance(accountDeposite.getAccountBalance())
						.accountNumber(accountDeposite.getAccountNumber())
						.build())
				.build();
	}
	@Override
	public ApiResponse withdrawAccount(DepositWithdrawDto depositWithdrawDto) {
		// Kiem tra tài khoan
		boolean isAccountExist = accountRepo.existsByAccountNumber(depositWithdrawDto.getAccountNumber());
		if (!isAccountExist) {
			return ApiResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		// Kiem tra so dư
		Account accountWithdraw = accountRepo.findByAccountNumber(depositWithdrawDto.getAccountNumber());
	
        BigInteger availableBalance =accountWithdraw.getAccountBalance().toBigInteger();
        BigInteger debitAmount = depositWithdrawDto.getAmount().toBigInteger();
//        if (availableBalance.intValue() < debitAmount.intValue()) {
        if (availableBalance.compareTo(debitAmount) < 0) {	
        	return ApiResponse.builder()
					.responseCode("009")
					.responseMessage("Tai khoan khong du tien")
					.accountInfo(null)
					.build();
        }else {
    		// Thưc hien rut tien
        	accountWithdraw.setAccountBalance(accountWithdraw.getAccountBalance().subtract(depositWithdrawDto.getAmount()));
        	accountRepo.save(accountWithdraw);
            return ApiResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(depositWithdrawDto.getAccountNumber())
                            .accountName(accountWithdraw.getFirstName() + " " + accountWithdraw.getLastName())
                            .accountBalance(accountWithdraw.getAccountBalance())
                            .build())
                    .build();

        }
		
        
		
		
	}

}
