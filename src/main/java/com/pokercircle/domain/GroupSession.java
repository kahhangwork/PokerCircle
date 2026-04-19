package com.pokercircle.domain;
import java.time.LocalDateTime;

// DOMAIN OBJECT
public class GroupSession {
    // VARIABLES
    private int id;
    private int grpId;
    private String sessionType;
    private String sessionStakes;
    private String sessionLocation;     // nullable
    private LocalDateTime sessionStartedAt;
    private LocalDateTime sessionEndedAt;   // nullable — null means session is still in progress
    private String notes;               // nullable
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // CONSTRUCTORS
    // default constructor
    public GroupSession() {}

    // constructor chaining — for creating a new session (no id or timestamps yet)
    public GroupSession(int grpId, String sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, String notes) {
        this(0, grpId, sessionType, sessionStakes, sessionLocation, sessionStartedAt, sessionEndedAt, notes, LocalDateTime.now(), LocalDateTime.now());
    }

    // overload constructor — for reading a session from the DB
    public GroupSession(int id, int grpId, String sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.grpId = grpId;
        this.sessionType = sessionType;
        this.sessionStakes = sessionStakes;
        this.sessionLocation = sessionLocation;
        this.sessionStartedAt = sessionStartedAt;
        this.sessionEndedAt = sessionEndedAt;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // GETTERS
    public int getId() {
        return this.id;
    }
    public int getGrpId() {
        return this.grpId;
    }
    public String getSessionType() {
        return this.sessionType;
    }
    public String getSessionStakes() {
        return this.sessionStakes;
    }
    public String getSessionLocation() {
        return this.sessionLocation;
    }
    public LocalDateTime getSessionStartedAt() {
        return this.sessionStartedAt;
    }
    public LocalDateTime getSessionEndedAt() {
        return this.sessionEndedAt;
    }
    public String getNotes() {
        return this.notes;
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
    public void setGrpId(int grpId) {
        this.grpId = grpId;
    }
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
    public void setSessionStakes(String sessionStakes) {
        this.sessionStakes = sessionStakes;
    }
    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }
    public void setSessionStartedAt(LocalDateTime sessionStartedAt) {
        this.sessionStartedAt = sessionStartedAt;
    }
    public void setSessionEndedAt(LocalDateTime sessionEndedAt) {
        this.sessionEndedAt = sessionEndedAt;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "GroupSession{" +
                "id=" + id +
                ", grpId=" + grpId +
                ", sessionType='" + sessionType + '\'' +
                ", sessionStakes='" + sessionStakes + '\'' +
                ", sessionLocation='" + sessionLocation + '\'' +
                ", sessionStartedAt=" + sessionStartedAt +
                ", sessionEndedAt=" + sessionEndedAt +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
