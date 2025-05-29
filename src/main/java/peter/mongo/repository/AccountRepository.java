package peter.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import peter.mongo.models.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
	Boolean existsByEmail(String email);

	Boolean existsByAccountNumber(String accountNumber);

	Account findByAccountNumber(String accountNumber);
}
