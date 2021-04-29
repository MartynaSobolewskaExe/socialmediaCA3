import socialmedia.*;

import java.util.Arrays;
import java.util.List;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 *
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

		// ACCOUNT TESTS

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
			platform.removeAccount(-9);
			assert (false) : "AccountIDNotRecognisedException should be thrown";
		}catch (AccountIDNotRecognisedException aidnre){
			System.out.println("AccountIDNotRecognisedException thrown as it should");
		}

		// change account handle
		try {
			int id = platform.createAccount("xyz123");
			platform.changeAccountHandle("xyz123", "abc123");
			// check if account with new handle exists
			boolean changed = false;
			List<Account> accounts = platform.getAllAccounts();
			for (int i = 0; i < accounts.size() && !changed; i++) {
				if (accounts.get(i).getHandle().equals("abc123")){
					changed = true;
				}
			}
			assert (changed) : "No error thrown but the handle was not changed.";
		} catch (IllegalHandleException ihe){
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException ihe) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (HandleNotRecognisedException hnre) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}

		// change description test
		try {
			int id = platform.createAccount("xyz123", "lorem ipsum");
			platform.updateAccountDescription("xyz123", "abcdef");
			boolean changed = false;
			List<Account> accounts = platform.getAllAccounts();
			for (int i = 0; i < accounts.size() && !changed; i++) {
				if (accounts.get(i).getDescriptionField().equals("abcdef")){
					changed = true;
				}
			}
			assert (changed) : "No error thrown but the description was not changed.";
		}catch (IllegalHandleException ihe){
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException ihe) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}

		//POST TESTS
		//create post tests
		try {
			platform.createPost("xyz123", "Lorem ipsum dolores sraka.");
			//System.out.println(Arrays.toString(platform.getPosts().toArray()));

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		try {
			platform.createPost("xyz1234", "Lorem ipsum dolores sraka.");
			assert (false) : "Should throw HandleNotRecognisedException.";
		} catch (HandleNotRecognisedException e) {
			System.out.println("HandleNotRecognisedException thrown correctly");
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		try {
			platform.createPost("xyz123", " 	  ");
			assert (false) : "Should throw InvalidPostException.";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			System.out.println("InvalidPostException thrown correctly");
		}

		// endorse post tests

		try {
			platform.endorsePost("xyz123", 0);
			System.out.println(Arrays.toString(platform.getPosts().toArray()));
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		try {
			platform.endorsePost("xyz123", 2);
			assert (false) : "Should throw NotActionablePostException";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			System.out.println("NotActionablePostException thrown correctly");
		}

		// COMMENTS TESTS
		//Comment post tests
		try {
			platform.commentPost("xyz123", 0,"qwerty. uiopasd fgh jkklzxc. vvbnm...");
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		try {
			platform.commentPost("xyz123", 2,"qwerty. uiopasd fgh jkklzxc. vvbnm...");
			assert (false) : "Should throw NotActionablePostException";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			System.out.println("NotActionablePostException thrown correctly");
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		}


		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		Integer id;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		}

	}

}
