package peter.mongo.service;

import peter.mongo.dto.AccountRequest;
import peter.mongo.dto.ApiResponse;
import peter.mongo.dto.DepositWithdrawDto;

public interface AccountService {
	ApiResponse createAccount(AccountRequest accountRequest) ;
	ApiResponse depositAccount(DepositWithdrawDto depositWithdrawDto) ;
	ApiResponse withdrawAccount(DepositWithdrawDto depositWithdrawDto) ;
	
}
