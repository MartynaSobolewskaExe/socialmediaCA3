import socialmedia.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		SocialMedia platform = new SocialMedia();

		// CREATE ACCOUNT TESTS

		try {
			platform.createAccount("abc123", "wuevhweihv iewugviw iuwhgviuwh uhrvuiwh iuhviqghi ihrj.");
		}catch (IllegalHandleException ihe){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}catch (InvalidHandleException ihe){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		try {
			platform.createAccount("abc123");
			assert (false) : "IllegalHandleException not thrown and it should";
		}catch (IllegalHandleException ihe){
			System.out.println("IllegalHandleException thrown as it should");
		}catch (InvalidHandleException ihe){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		try {
			platform.createAccount("abc1   23");
			assert (false) : "InvalidHandleException not thrown and it should";
		}catch (IllegalHandleException ihe){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}catch (InvalidHandleException ihe){
			System.out.println("InvalidHandleException thrown as it should");
		}

		// REMOVE ACCOUNT TESTS

		// remove using handle
		try{
			platform.removeAccount("abc123");
			assert (platform.getAllAccounts().size() == 0) : "Account not deleted from the list of accounts.";
		}catch (HandleNotRecognisedException hnre){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}

		try{
			platform.removeAccount("abc123");
			assert (false) : "HandleNotRecognisedException should be thrown";
		}catch (HandleNotRecognisedException hnre){
			System.out.println("HandleNotRecognisedException thrown as it should");
		}
		// remove using id
		try {
			int id = platform.createAccount("xyz123");
			platform.removeAccount(id);
			assert (platform.getAllAccounts().size() == 0) : "Account not deleted from the list of accounts.";
		}catch (IllegalHandleException ihe){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}catch (InvalidHandleException ihe){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}catch (AccountIDNotRecognisedException aidnre){
			assert (false) : "AccountIDNotRecognisedException thrown incorrectly";
		}

		try {
			int id = platform.createAccount("xyz123");
			platform.removeAccount(-9);
			assert (false) : "AccountIDNotRecognisedException should be thrown";
		}catch (IllegalHandleException ihe){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}catch (InvalidHandleException ihe){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}catch (AccountIDNotRecognisedException aidnre){
			System.out.println("AccountIDNotRecognisedException thrown as it should");
		}



//		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
//		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
//		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
//		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
//
//		Integer id;
//		try {
//			id = platform.createAccount("my_handle");
//			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";
//
//			platform.removeAccount(id);
//			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";
//
//		} catch (IllegalHandleException e) {
//			assert (false) : "IllegalHandleException thrown incorrectly";
//		} catch (InvalidHandleException e) {
//			assert (false) : "InvalidHandleException thrown incorrectly";
//		} catch (AccountIDNotRecognisedException e) {
//			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
//		}

	}

}
