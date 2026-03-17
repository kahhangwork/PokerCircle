package com.pokercircle;
import java.time.LocalDateTime;

// DOMAIN OBJECT
public class PersonalSession {
    // VARIABLES
    private int id;
    private int userId;
    private String sessionType;
    private String sessionStakes;
    private String sessionLocation;
    private LocalDateTime sessionStartedAt;
    private LocalDateTime sessionEndedAt;
    private int buyInCents;
    private int cashOutCents;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // METHODS

    // CONSTRUCTORS
    // default constructor
    public PersonalSession() {}

    // constructor chaining
    public PersonalSession(int userId, String sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, int buyInCents, int cashOutCents, String notes) {
        this(0, userId, sessionType, sessionStakes, sessionLocation, sessionStartedAt, sessionEndedAt, buyInCents, cashOutCents, notes, LocalDateTime.now(), LocalDateTime.now());
    }

    // overload constructor
    public PersonalSession(int id, int userId, String sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, int buyInCents, int cashOutCents, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.sessionType = sessionType;
        this.sessionStakes = sessionStakes;
        this.sessionLocation = sessionLocation;
        this.sessionStartedAt = sessionStartedAt;
        this.sessionEndedAt = sessionEndedAt;
        this.buyInCents = buyInCents;
        this.cashOutCents = cashOutCents;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // GETTERS
    public int getId() {
        return this.id;
    }
    public int getUserId() {
        return this.userId;
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
    public int getBuyInCents() {
        return this.buyInCents;
    }
    public int getCashOutCents() {
        return this.cashOutCents;
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
    public void setUserId(int userId) {
        this.userId = userId;
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
    public void setBuyInCents(int buyInCents) {
        this.buyInCents = buyInCents;
    }
    public void setCashOutCents(int cashOutCents) {
        this.cashOutCents = cashOutCents;
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
        return "PersonalSession{" +
                "id=" + id +
                ", userId=" + userId +
                ", sessionType='" + sessionType + '\'' +
                ", sessionStakes='" + sessionStakes + '\'' +
                ", sessionLocation='" + sessionLocation + '\'' +
                ", sessionStartedAt=" + sessionStartedAt +
                ", sessionEndedAt=" + sessionEndedAt +
                ", buyInCents=" + buyInCents +
                ", cashOutCents=" + cashOutCents +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }    
}
