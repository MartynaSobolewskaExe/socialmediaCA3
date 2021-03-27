package socialmedia;

public class Comment extends Post{
    private Post commentedPost;

    public Comment(Account author, Post commentedPost, String commentText) throws InvalidPostException {
        super(author);
        super.setMessage(message);
        this.commentedPost = commentedPost;
        commentedPost.comment(this);
        commentedPost.author.comment();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentedPost=" + commentedPost +
                ", id=" + id +
                ", message='" + message + '\'' +
                ", author=" + author +
                '}';
    }
}
