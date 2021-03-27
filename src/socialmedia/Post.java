package socialmedia;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing user's post.
 */
public class Post {
    private Account account;
    private int numberOfEndorsements = 0;
    private int numberOfComments = 0;
    private String postContent;
    private int commentCount = 0;
    private int endorsementCount = 0;
    private boolean isDeleted = false;
    private boolean isComment = false;
    private ArrayList<Post> postChildrenList = new ArrayList<>();
    private boolean isEndorsement = false;
    private int depth = 0;
    protected int id;
    // will increment with each new post. Will make sure that they all have an unique numerical id
    private static int counter = 0;
    protected String message;
    Account author;
    List<Endorsement> endorsements = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    Post(){
    }
    Post(Account account, int id){
        this.account = account;
        this.id = id;
    }
    Post(Account account, String postContent, int postID) {
        this.account = account;
        this.postContent = postContent;
        this.id = id;
    }

    public Post(String message, Account author) throws InvalidPostException {
        setId();
        setMessage(message);
        this.author = author;
    }

    public void clearAccount(){
        this.account = null;
    }

    /**
     * Constructor used by child classes (does not throw exception)
     * It also does not set the message.
     *
     * @param author author Account
     */
    public Post(Account author) {
        setId();
        this.author = author;
    }

    protected void setId() {
        this.id = counter;
        counter += 1;
    }

    /**
     * Sets post's message
     *
     * @param message message to be set
     * @throws InvalidPostException if message is null, just white characters, empty or longer than 100 characters
     */
    public void setMessage(String message) throws InvalidPostException {
        if (message != null && message.trim() != ""
                && message.length() <= 100) {
            this.message = message;
        } else
            throw new InvalidPostException("Message '" + message + "' is either null, just whitespaces " +
                    "or longer than 100 chars. Post cannot be created.");
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Account getAuthor() {
        return author;
    }

    public List<Endorsement> getEndorsements() {
        return endorsements;
    }

    public List<Comment> getComments() {
        return comments;
    }

    /**
     * increments number of endorsements for the post and adds the endorsement.
     */
    public void endorse(Endorsement endorsement) {
        endorsements.add(endorsement);
        this.numberOfEndorsements += 1;
    }

    /**
     * increments comments count and adds the comment.
     */
    public void comment(Comment comment) {
        comments.add(comment);
        this.numberOfComments += 1;
    }

    public void clearAll() {
        account = null;
        postContent = "<The original content was deleted from the system and is not available anymore.>";
        commentCount = -1;
        endorsementCount = -1;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", author=" + author +
                ", endorsements=" + endorsements +
                ", comments=" + comments +
                '}';
    }


    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    public Account getAccount() {
        return account;
    }
    public int getEndorsementCount() {
        return endorsementCount;
    }
    public int getCommentCount() {
        return commentCount;
    }
    public String getPostContent() {
        return postContent;
    }
    public void setComment(boolean comment) {
        isComment = comment;
    }
    public boolean isComment() {
        return isComment;
    }
    public void addDepth(){
        depth++;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth){
        this.depth = depth;
    }
    public ArrayList<Post> getPostChildrenList() {
        return postChildrenList;
    }
    public void setEndorsement(boolean endorsement) {
        isEndorsement = endorsement;
    }

    public boolean isEndorsement() {
        return isEndorsement;
    }
}