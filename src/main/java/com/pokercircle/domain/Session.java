package com.pokercircle.domain;

import java.time.LocalDateTime;

public abstract class Session {
    private int id;
    private SessionType sessionType;
    private String sessionStakes;
    private String sessionLocation;     // nullable
    private LocalDateTime sessionStartedAt;
    private LocalDateTime sessionEndedAt;   // nullable — null means session is still in progress
    private String notes;               // nullable
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Session(int id, SessionType sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sessionType = sessionType;
        this.sessionStakes = sessionStakes;
        this.sessionLocation = sessionLocation;
        this.sessionStartedAt = sessionStartedAt;
        this.sessionEndedAt = sessionEndedAt;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public SessionType getSessionType() {
        return sessionType;
    }
    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }
    public String getSessionStakes() {
        return sessionStakes;
    }
    public void setSessionStakes(String sessionStakes) {
        this.sessionStakes = sessionStakes;
    }
    public String getSessionLocation() {
        return sessionLocation;
    }
    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }
    public LocalDateTime getSessionStartedAt() {
        return sessionStartedAt;
    }
    public void setSessionStartedAt(LocalDateTime sessionStartedAt) {
        this.sessionStartedAt = sessionStartedAt;
    }
    public LocalDateTime getSessionEndedAt() {
        return sessionEndedAt;
    }
    public void setSessionEndedAt(LocalDateTime sessionEndedAt) {
        this.sessionEndedAt = sessionEndedAt;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Override
    public String toString() {
        return "Session [id=" + id + ", sessionType=" + sessionType + ", sessionStakes=" + sessionStakes
                + ", sessionLocation=" + sessionLocation + ", sessionStartedAt=" + sessionStartedAt
                + ", sessionEndedAt=" + sessionEndedAt + ", notes=" + notes + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }

    
    


}
