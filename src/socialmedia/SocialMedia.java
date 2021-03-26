package socialmedia;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SocialMedia is an implementor of the SocialMediaPlatform interface.
 */
public class SocialMedia implements SocialMediaPlatform {

	private static final Logger LOGGER = Logger.getLogger( SocialMedia.class.getName());
	private List<Account> accounts = new ArrayList<>();

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		Account account = new Account(handle);
		accounts.add(account);
		return account.getId();
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		try {
			Account account = new Account(handle, description);
			accounts.add(account);
			return account.getId();
		}catch (IllegalHandleException ihe){
			LOGGER.log( Level.SEVERE, ihe.toString(), ihe );
			return -1;

		}catch (InvalidHandleException ine){
			LOGGER.log( Level.SEVERE, ine.toString(), ine );
			return -1;
		}
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		boolean found = false;
		int foundIndex = -1;
		for (int i = 0; i < accounts.size() && !found; i++) {
			if (accounts.get(i).getId() == id){
				found = true;
				foundIndex = i;
			}
		}
		if (found){
			accounts.get(foundIndex).removeAccount();
			accounts.remove(foundIndex);
		}
		else {
			throw new AccountIDNotRecognisedException("Account with id: " + id + " not found in the system.");
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		boolean found = false;
		int foundIndex = -1;
		for (int i = 0; i < accounts.size() && !found; i++) {
			if (accounts.get(i).getHandle() == handle){
				found = true;
				foundIndex = i;
			}
		}
		if (found){
			accounts.get(foundIndex).removeAccount();
			accounts.remove(foundIndex);
		}
		else {
			throw new HandleNotRecognisedException("Account with handle: " + handle + " not found in the system.");
		}
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		boolean found = false;
		int foundIndex = -1;
		for (int i = 0; i < accounts.size() && !found; i++) {
			if (accounts.get(i).getHandle() == oldHandle){
				found = true;
				foundIndex = i;
			}
		}
		if (found){
			accounts.get(foundIndex).changeAccountHandle(newHandle);
		}
		else {
			throw new HandleNotRecognisedException("Account with handle: " + oldHandle + " not found in the system.");
		}

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	public List<Account> getAllAccounts(){
		return accounts;
	}

}
