package com.pokercircle;
import java.time.LocalDateTime;

// DOMAIN OBJECT
public class GroupMember {
    // VARIABLES
    private int id;
    private int grpId;
    private int usrId;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;   // nullable — null means still an active member


    // CONSTRUCTORS
    // default constructor
    public GroupMember() {}

    // constructor chaining — for creating a new membership (not yet in the DB)
    public GroupMember(int grpId, int usrId) {
        this(0, grpId, usrId, LocalDateTime.now(), null);
    }

    // overload constructor — for reading a membership from the DB
    public GroupMember(int id, int grpId, int usrId, LocalDateTime joinedAt, LocalDateTime leftAt) {
        this.id = id;
        this.grpId = grpId;
        this.usrId = usrId;
        this.joinedAt = joinedAt;
        this.leftAt = leftAt;
    }


    // GETTERS
    public int getId() {
        return this.id;
    }
    public int getGrpId() {
        return this.grpId;
    }
    public int getUsrId() {
        return this.usrId;
    }
    public LocalDateTime getJoinedAt() {
        return this.joinedAt;
    }
    public LocalDateTime getLeftAt() {
        return this.leftAt;
    }


    // SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public void setGrpId(int grpId) {
        this.grpId = grpId;
    }
    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }


    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id +
                ", grpId=" + grpId +
                ", usrId=" + usrId +
                ", joinedAt=" + joinedAt +
                ", leftAt=" + leftAt +
                '}';
    }
}
