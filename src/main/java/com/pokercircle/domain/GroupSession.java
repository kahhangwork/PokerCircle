package com.pokercircle.domain;
import java.time.LocalDateTime;

// DOMAIN OBJECT
public class GroupSession extends Session {
    // VARIABLES
    private int grpId;


    // CONSTRUCTORS

    // constructor chaining — for creating a new session (no id or timestamps yet)
    public GroupSession(int grpId, SessionType sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, String notes) {
        this(0, grpId, sessionType, sessionStakes, sessionLocation, sessionStartedAt, sessionEndedAt, notes, LocalDateTime.now(), LocalDateTime.now());
    }

    // overload constructor — for reading a session from the DB
    public GroupSession(int id, int grpId, SessionType sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, sessionType, sessionStakes, sessionLocation, sessionStartedAt, sessionEndedAt, notes, createdAt, updatedAt);
        this.grpId = grpId;
    }


    // GETTERS
    public int getGrpId() {
        return this.grpId;
    }


    // SETTERS
    public void setGrpId(int grpId) {
        this.grpId = grpId;
    }



    @Override
    public String toString() {
        return super.toString().replace("Session{", "GroupSession{") +
                ", grpId=" + grpId +
                '}';    
    }
}
