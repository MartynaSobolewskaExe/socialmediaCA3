package socialmedia;

/**
 * A class representing other post's endorsement.
 */
public class Endorsement extends Post{
    private Post endorsedPost;

    public Endorsement(Post endorsedPost, Account account){
        super(account);
        this.message = endorsedPost.message;
        this.endorsedPost = endorsedPost;
        endorsedPost.endorse(this);
        endorsedPost.author.endorse();
    }

    @Override
    public String toString() {
        return "Endorsement{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", author=" + author +
                '}';
    }
}
