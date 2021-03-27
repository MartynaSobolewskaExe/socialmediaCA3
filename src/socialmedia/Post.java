package socialmedia;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing user's post.
 */
public class Post {
    private int numberOfEndorsements = 0;
    private int numberOfComments = 0;
    protected int id;
    // will increment with each new post. Will make sure that they all have an unique numerical id
    private static int counter = 0;
    protected String message;
    Account author;
    List<Endorsement> endorsements = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();


    public Post(String message, Account author) throws InvalidPostException{
        setId();
        setMessage(message);
        this.author = author;
    }

    /**
     * Constructor used by child classes (does not throw exception)
     * It also does not set the message.
     * @param author author Account
     */
    public Post(Account author){
        setId();
        this.author = author;
    }

    protected void setId() {
        this.id = counter;
        counter += 1;
    }

    /**
     * Sets post's message
     * @param message message to be set
     * @throws InvalidPostException if message is null, just white characters, empty or longer than 100 characters
     */
    public void setMessage(String message) throws InvalidPostException{
        if (message != null && message.trim() != ""
                && message.length() <= 100){
            this.message = message;
        }else
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
    public void endorse(Endorsement endorsement){
        endorsements.add(endorsement);
        this.numberOfEndorsements += 1;
    }

    /**
     * increments comments count and adds the comment.
     */
    public void comment(Comment comment){
        comments.add(comment);
        this.numberOfComments += 1;
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
}
