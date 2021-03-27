package socialmedia;

import javax.security.auth.login.AccountNotFoundException;
import java.io.*;
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
	private ArrayList<Post> posts = new ArrayList<>();
	private StringBuilder childrenPostContent = new StringBuilder();
	private Account DELETED_USER = new Account();


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
		Account a = getAccountByHandle(handle);
		Post p = getPostById(id);
		if (p instanceof Endorsement){
			throw new NotActionablePostException("Post with id: '" + id + "' is an endorsement " +
					"so it cannot be commented.");
		}
		Comment comment = new Comment(a,p,message);
		posts.add(comment);
		return comment.getId();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		Post targetPost = new Post();
		for(Post post: posts){
			if(post.getId() == id){
				targetPost = post;
				break;
			}
		}
		targetPost.setPostContent("<The initial content was deleted from the system, therefore it is not available anymore.>");
		targetPost.setDeleted(true);
		for(Post Endorsement: targetPost.getEndorsements()){
			for(Post post: posts){
				if(post.getId() == Endorsement.getId()){
					posts.remove(post);
					break;
				}
			}
			Endorsement.clearAll();
		}
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		boolean postIDRecognised = false;
		Post targetPost = new Post();
		String individualPost;
		try {
			for (Post post : posts) {
				if (post.getId() == id) {
					postIDRecognised = true;
					targetPost = post;
					break;
				}
			}
			if (!postIDRecognised) {
				throw new PostIDNotRecognisedException("Post Id not recognised exception: Please try again entering a valid Id.");
			}
			individualPost = "Id: " + targetPost.getId() + "\nAccount: " + targetPost.getAccount().getHandle()
					+ "\nNo. endorsements: " + targetPost.getEndorsementCount() + " | No. comments: " + targetPost.getCommentCount() + "\n" + targetPost.getPostContent() + " \n";
			return individualPost;
		} catch(PostIDNotRecognisedException e){
			String message = "Post Id not recognised exception: Please try again entering a valid Id.";
			return message;
		}
	}
	public void clearStringBuilder(){
		childrenPostContent.setLength(0);
	}
	public void FormatStringBuilder(Post post) throws PostIDNotRecognisedException {
		try{
			if(post != null){
				String individualPost;
				if(post.isComment()){
					childrenPostContent.append(("   ").repeat(Math.max(0,post.getDepth()) - 1)).append("| >");
					individualPost = showIndividualPost(post.getId()).replace("\n","\n" + ("   ").repeat(Math.max(0,post.getDepth())));
				}else{
					individualPost = showIndividualPost(post.getId());
				}
				childrenPostContent.append(individualPost).append("|\n");
				for(Post child: post.getPostChildrenList()){
					FormatStringBuilder(child);
				}
			}}
		catch(PostIDNotRecognisedException e){
			System.out.println("PROBLEM WITH showingIndividualPosts()");
		}
	}


	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		int count = 0;
		for(Post post: posts){
			if(post.isComment() || post.isEndorsement()){
				continue;
			}
			count++;
		}
		return count;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		int count = 0;
		for(Post post: posts){
			if(!post.isEndorsement()){
				continue;
			}
			count++;
		}
		return count;
	}

	@Override
	public int getTotalCommentPosts() {
		int count = 0;
		for(Post post: posts){
			if(!post.isComment()){
				continue;
			}
			count++;
		}
		return count;
	}

	@Override
	public int getMostEndorsedPost() {
		Post MostEndorsed = new Post();
		for(Post post : posts){
			if(post.isEndorsement()){
				continue;
			}
			if(MostEndorsed.getAccount() == null){
				MostEndorsed = post;
			}
			else if(MostEndorsed.getEndorsementCount() < post.getEndorsementCount()){
				MostEndorsed = post;
			}

		}
		return MostEndorsed.getId();

	}

	@Override
	public int getMostEndorsedAccount() {
		Account MostEndorsed = new Account();
		for(Account user : accounts){
			if(MostEndorsed.getHandle() == null){
				MostEndorsed = user;
			}
			else if(MostEndorsed.getUserEndorsements() < user.getUserEndorsements()){
				MostEndorsed = user;
			}
		}
		return MostEndorsed.getId();

	}

	@Override
	public void erasePlatform() {
		for(Account account: accounts){
			account.getPosts().clear();
		}
		accounts.clear();
		for(Post posts: posts){
			posts.getPostChildrenList().clear();
			posts.getEndorsements().clear();
			posts.clearAccount();
		}
		posts.clear();
		DELETED_USER.getPosts().clear();

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		try{
			FileOutputStream fileStore = new FileOutputStream(filename + ".ser");
			ObjectOutputStream objectStore = new ObjectOutputStream(fileStore);
			objectStore.writeObject(accounts);
			objectStore.writeObject(posts);
			objectStore.writeObject(DELETED_USER);
			objectStore.close();
			fileStore.close(); }
		catch(IOException e){
			System.out.println("Unfortunately, not able to find the file. Please try again.");
		}
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename + ".ser"));
			accounts = (ArrayList<Account>) ois.readObject();
			posts = (ArrayList<Post>) ois.readObject();
			DELETED_USER = (Account) ois.readObject();
			ois.close();}
		catch(IOException | ClassNotFoundException e) {
			System.out.println("Unfortunately, the class or the file were not found. Please try again.");
		}

	}

	public List<Account> getAllAccounts(){
		return accounts;
	}

	public List<Post> getPosts() {return posts; }
}
