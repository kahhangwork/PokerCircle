package com.pokercircle;
import java.time.LocalDateTime;

// DOMAIN OBJECT
public class GroupSessionParticipant {
    // VARIABLES
    // Note: no single auto-increment id — primary key is (sessionId + memberId) combined
    private int sessionId;
    private int memberId;
    private LocalDateTime joinedAt;     // nullable
    private LocalDateTime leftAt;       // nullable


    // CONSTRUCTORS
    // default constructor
    public GroupSessionParticipant() {}

    // overload constructor — used for both creating and reading from the DB
    public GroupSessionParticipant(int sessionId, int memberId, LocalDateTime joinedAt, LocalDateTime leftAt) {
        this.sessionId = sessionId;
        this.memberId = memberId;
        this.joinedAt = joinedAt;
        this.leftAt = leftAt;
    }


    // GETTERS
    public int getSessionId() {
        return this.sessionId;
    }
    public int getMemberId() {
        return this.memberId;
    }
    public LocalDateTime getJoinedAt() {
        return this.joinedAt;
    }
    public LocalDateTime getLeftAt() {
        return this.leftAt;
    }


    // SETTERS
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }


    @Override
    public String toString() {
        return "GroupSessionParticipant{" +
                "sessionId=" + sessionId +
                ", memberId=" + memberId +
                ", joinedAt=" + joinedAt +
                ", leftAt=" + leftAt +
                '}';
    }
}
