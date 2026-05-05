package com.pokercircle.domain;
import java.time.LocalDateTime;

// DOMAIN OBJECT
public class PersonalSession extends Session {
    // VARIABLES
    private int userId;
    private int buyInCents;
    private int cashOutCents;

    // METHODS

    // CONSTRUCTORS

    // constructor chaining
    public PersonalSession(int userId, String sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, int buyInCents, int cashOutCents, String notes) {
        this(0, userId, sessionType, sessionStakes, sessionLocation, sessionStartedAt, sessionEndedAt, buyInCents, cashOutCents, notes, LocalDateTime.now(), LocalDateTime.now());
    }

    // overload constructor
    public PersonalSession(int id, int userId, String sessionType, String sessionStakes, String sessionLocation, LocalDateTime sessionStartedAt, LocalDateTime sessionEndedAt, int buyInCents, int cashOutCents, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, SessionType.valueOf(sessionType.toUpperCase()), sessionStakes, sessionLocation, sessionStartedAt, sessionEndedAt, notes, createdAt, updatedAt);
        this.userId = userId;
        this.buyInCents = buyInCents;
        this.cashOutCents = cashOutCents;
    }

    // GETTERS
    public int getUserId() {
        return this.userId;
    }
    public int getBuyInCents() {
        return this.buyInCents;
    }
    public int getCashOutCents() {
        return this.cashOutCents;
    }

    // SETTERS
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setBuyInCents(int buyInCents) {
        this.buyInCents = buyInCents;
    }
    public void setCashOutCents(int cashOutCents) {
        this.cashOutCents = cashOutCents;
    }

    @Override
    public String toString() {
        return super.toString().replace("Session{", "PersonalSession{") +
                ", userId=" + userId +
                ", buyInCents=" + buyInCents +
                ", cashOutCents=" + cashOutCents +
                '}';
    }       
}
