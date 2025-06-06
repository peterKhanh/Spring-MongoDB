package peter.mongo.util;

import java.time.Year;

public class AccountUtils {
	 public static final String ACCOUNT_EXISTS_CODE = "001";
	    public static final String ACCOUNT_EXISTS_MESSAGE = "Tai khoan da ton tai!";
	    public static final String ACCOUNT_CREATION_SUCCESS = "002";
	    public static final String ACCOUNT_CREATION_MESSAGE = "Tao tai khoan thanh cong!";
	    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
	    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "So tai khoan khong tin tai";
	    public static final String ACCOUNT_FOUND_CODE = "004";
	    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
	    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
	    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Rut tien thanh cong";
	    public static final String INSUFFICIENT_BALANCE_CODE = "006";
	    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
	    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
	    public static final String ACCOUNT_DEBITED_MESSAGE = "Nop tien thanh cong";
	    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
	    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Chuyen khoan thanh cong";



	    public static String generateAccountNumber(){
	        /**
	         * 2023 + randomSixDigits
	         *
	         * 2023112233
	         */
	        Year currentYear = Year.now();
	        int min = 100000;
	        int max = 999999;

	        //generate a random number between min and max
	        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
	        //convert the current and randomNumber to strings, then concatenate them

	        String year = String.valueOf(currentYear);
	        String randomNumber = String.valueOf(randNumber);
	        StringBuilder accountNumber = new StringBuilder();

	        return accountNumber.append(year).append(randomNumber).toString();
	    }
}
