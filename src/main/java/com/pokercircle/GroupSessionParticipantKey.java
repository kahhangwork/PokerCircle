package com.pokercircle;

// Composite key for grp_session_participant, which has no single auto-increment id.
// This class holds both parts of the primary key: (session_id, member_id).
public class GroupSessionParticipantKey {
    private int sessionId;
    private int memberId;

    public GroupSessionParticipantKey(int sessionId, int memberId) {
        this.sessionId = sessionId;
        this.memberId = memberId;
    }

    public int getSessionId() {
        return this.sessionId;
    }
    public int getMemberId() {
        return this.memberId;
    }
}
