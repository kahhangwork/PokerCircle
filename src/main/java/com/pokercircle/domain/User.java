package com.pokercircle.domain;
import java.time.LocalDateTime;
import java.util.List;

//DOMAIN OBJECT
public class User {
    //VARIABLES
    private int id;
    private String email;
    private String displayName;
    private String profilePicture;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Group> groups; // users can belong to multiple groups, and groups can have multiple users (many-to-many relationship)

    //METHODS

    //CONSTRUCTORS
    //default constructor
    public User () {}

    //constructor chaining
    public User(String email, String displayName, String passwordHash) {
        this(0, email, displayName, "", passwordHash, LocalDateTime.now(), LocalDateTime.now());
    }

    //overload constructor
    public User (int id, String email, String displayName, String profilePicture, String passwordHash, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.profilePicture = profilePicture;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt; 
        this.updatedAt = updatedAt;
    }


    //GETTERS
    public int getId() {
        return this.id;
    }
    public String getEmail() {
        return this.email;
    }
    public String getDisplayName() {
        return this.displayName;
    }
    public String getProfilePicture() {
        return this.profilePicture;
    }
    public String getPasswordHash() {
        return this.passwordHash;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    
    //SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void invite(User user) {
        // To Do: Implement invite logic (e.g., create a pending GroupMember entry, send notification, etc.)
    }

    public void joinGroup(Group group) {
        // To Do: Implement join group logic (e.g., create a GroupMember entry, etc.)
    }


    @Override
    public String toString () {
        return "User: " + this.id + ", " + this.email + ", " + this.displayName + ", " + this.profilePicture + ", " + this.passwordHash + ", " + this.createdAt + ", " + this.updatedAt;
    }
}
