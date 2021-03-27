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
	private List<Post> posts = new ArrayList<>();

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
		Account a = getAccountById(id);
		a.removeAccount();
		accounts.remove(a);
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		Account a = getAccountByHandle(handle);
		a.removeAccount();
		// TODO: remove corresponding posts and likes too
		accounts.remove(a);
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		Account a = getAccountByHandle(oldHandle);
		a.changeAccountHandle(newHandle);
	}

	/**
	 * Finds account with a given handle.
	 * @param handle handle to identify the account.
	 * @return account with a given handle.
	 * @throws HandleNotRecognisedException if there is no matching account.
	 */
	public Account getAccountByHandle(String handle) throws HandleNotRecognisedException {
		boolean found = false;
		int foundIndex = -1;
		for (int i = 0; i < accounts.size() && !found; i++) {
			if (accounts.get(i).getHandle() == handle){
				found = true;
				foundIndex = i;
			}
		}
		if (found){
			return accounts.get(foundIndex);
		}
		else {
			throw new HandleNotRecognisedException("Account with handle: " + handle + " not found in the system.");
		}
	}

	/**
	 * Finds account with a given id.
	 * @param id id to identify the account.
	 * @return account with a given id.
	 * @throws AccountIDNotRecognisedException
	 */
	public Account getAccountById(int id) throws AccountIDNotRecognisedException {
		boolean found = false;
		int foundIndex = -1;
		for (int i = 0; i < accounts.size() && !found; i++) {
			if (accounts.get(i).getId() == id){
				found = true;
				foundIndex = i;
			}
		}
		if (found){
			return accounts.get(foundIndex);
		}
		else {
			throw new AccountIDNotRecognisedException("Account with id: " + id + " not found in the system.");
		}
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		Account a = getAccountByHandle(handle);
		a.setDescriptionField(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		Account a = getAccountByHandle(handle);
		return a.toString();
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		Account a = getAccountByHandle(handle);
		Post p = new Post(message, a);
		posts.add(p);
		return p.getId();
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		Post p = getPostById(id);
		Account a = getAccountByHandle(handle);
		if (p instanceof Endorsement){
			throw new NotActionablePostException("Post with id: '" + id + "' is an endorsement " +
					"so it cannot be endorsed.");
		}
		Endorsement endorsement = new Endorsement(p,a);
		posts.add(endorsement);
		return endorsement.getId();
	}


	public Post getPostById(int id) throws PostIDNotRecognisedException{
		for (int i = 0; i < posts.size(); i++) {
			if (posts.get(i).getId() == id){
				return posts.get(i);
			}
		}
		throw new PostIDNotRecognisedException("Post with id: '" + id + "' not found.");
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

	public List<Post> getPosts() {return posts; }
}
