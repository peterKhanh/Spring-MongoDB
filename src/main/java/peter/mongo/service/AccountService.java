package peter.mongo.service;

import peter.mongo.dto.AccountRequest;
import peter.mongo.dto.ApiResponse;

public interface AccountService {
	ApiResponse createAccount(AccountRequest accountRequest) ;
}
