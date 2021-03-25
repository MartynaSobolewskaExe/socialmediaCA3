package socialmedia;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing user's account
 */
public class Account {
    private long id;
    // will increment with each new account. Will make sure that they all have an unique numerical id
    private static long counter;
    private String descriptionField;
    private String handle;
    private static List<String> accountHandles = new ArrayList<>();

    /**
     * Creates an instance of Account class
     * @param descriptionField String containing description field
     * @param handle handle to identify the account
     * @throws IllegalHandleException if the handle already exists in the platform.
     * @throws InvalidHandleException if the new handle is empty, has more than 30 characters, or has white spaces.
     */
    public Account(String descriptionField, String handle) throws IllegalHandleException, InvalidHandleException {
        setId();
        setHandle(handle);
        setDescriptionField(descriptionField);
    }

    /**
     * Creates an instance of Account class with empty description field
     * @param handle handle to identify the account
     * @throws IllegalHandleException if the handle already exists in the platform.
     * @throws InvalidHandleException if the new handle is empty, has more than 30 characters, or has white spaces.
     */
    public Account(String handle) throws IllegalHandleException, InvalidHandleException {
        setId();
        setHandle(handle);
    }

    public long getId() {
        return id;
    }

    public void setId() {
        this.id = counter;
        counter += 1;
    }

    public String getDescriptionField() {
        return descriptionField;
    }

    public void setDescriptionField(String descriptionField) {
        this.descriptionField = descriptionField;
    }

    public String getHandle() {
        return handle;
    }

    /**
     * Sets a handle for account instance.
     * @param handle handle to identify the account.
     * @throws IllegalHandleException if the handle already exists in the platform.
     * @throws InvalidHandleException if the new handle is empty, has more than 30 characters, or has white spaces.
     */
    private void setHandle(String handle) throws IllegalHandleException, InvalidHandleException{
        if (handle != null && handle != ""
                && handle.length() <= 30 &&
                !containsWhitespace(handle)) {
            if (!accountHandles.contains(handle)){
                this.handle = handle;
                accountHandles.add(handle);
            }else
                throw new IllegalHandleException();
        }else
            throw new InvalidHandleException();
    }

    /**
     * Checks a string for whitespaces
     * @param str string checked for whitespaces
     * @return true if given string contains any whitespace, else false
     */
    private boolean containsWhitespace(String str){
        // true if contains onw or more instances of any whitespace character
        return str.matches(".*\\s.*");
    }
}