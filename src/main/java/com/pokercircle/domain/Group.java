package com.pokercircle.domain;
import java.time.LocalDateTime;
import java.util.List;


public class Group {
    private int id;
    private String name;
    private int createdBy;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String privateCode; // code that users can use join the group

    private List<User> members; // groups can have multiple users, and users can belong to multiple groups (many-to-many relationship)


    // METHODS

    // CONSTRUCTORS
    //default constructor - creates an empty object with no values. Used when you wan to create a blank object and fill it in piece by piece with setters. 
    public Group() {}

    //constructor chaining - for when you want to create a new group and don't have an id or timestamps yet. The DB will handle those for us, so we can set them to default values here.
    public Group(String name, int createdBy, String description, String privateCode) {
        this(0, name, createdBy, description, privateCode, null, LocalDateTime.now(), LocalDateTime.now());
    }

    //overload constructor - for when you want to create a new group with all values specified. This is useful for when you're reading a group from the database and want to create a Group object with all the values from the DB, including the id and timestamps.
    public Group(int id, String name, int createdBy, String description, String privateCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.description = description;
        this.privateCode = privateCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // GETTERS
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getCreatedBy() {
        return this.createdBy;
    }
    public String getDescription() {
        return this.description;
    }
    public String getPrivateCode() {
        return this.privateCode;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrivateCode(String privateCode) {
        this.privateCode = privateCode;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                ", privateCode='" + privateCode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';    
    }            
}
