package com.pokercircle.domain;
import java.time.LocalDateTime;

// DOMAIN OBJECT
public class GroupSessionTxn {
    // VARIABLES
    // Note: id is `long` not `int` because the SQL column is BIGINT
    private long id;
    private int sessionId;
    // Note: fromMemberId and toMemberId use Integer (capital I) not int,
    // because they are nullable — one is always NULL depending on txnType
    private Integer fromMemberId;   // nullable — NULL for BUY_IN transactions
    private Integer toMemberId;     // nullable — NULL for CASH_OUT transactions
    private String txnType;         // 'BUY_IN', 'CASH_OUT', or 'TRANSFER'
    private int amountCents;
    private LocalDateTime txnAt;
    private String notes;           // nullable


    // CONSTRUCTORS
    // default constructor
    public GroupSessionTxn() {}

    // constructor chaining — for creating a new transaction (no id yet)
    public GroupSessionTxn(int sessionId, Integer fromMemberId, Integer toMemberId, String txnType, int amountCents, LocalDateTime txnAt, String notes) {
        this(0L, sessionId, fromMemberId, toMemberId, txnType, amountCents, txnAt, notes);
    }

    // overload constructor — for reading a transaction from the DB
    public GroupSessionTxn(long id, int sessionId, Integer fromMemberId, Integer toMemberId, String txnType, int amountCents, LocalDateTime txnAt, String notes) {
        this.id = id;
        this.sessionId = sessionId;
        this.fromMemberId = fromMemberId;
        this.toMemberId = toMemberId;
        this.txnType = txnType;
        this.amountCents = amountCents;
        this.txnAt = txnAt;
        this.notes = notes;
    }


    // GETTERS
    public long getId() {
        return this.id;
    }
    public int getSessionId() {
        return this.sessionId;
    }
    public Integer getFromMemberId() {
        return this.fromMemberId;
    }
    public Integer getToMemberId() {
        return this.toMemberId;
    }
    public String getTxnType() {
        return this.txnType;
    }
    public int getAmountCents() {
        return this.amountCents;
    }
    public LocalDateTime getTxnAt() {
        return this.txnAt;
    }
    public String getNotes() {
        return this.notes;
    }


    // SETTERS
    public void setId(long id) {
        this.id = id;
    }
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
    public void setFromMemberId(Integer fromMemberId) {
        this.fromMemberId = fromMemberId;
    }
    public void setToMemberId(Integer toMemberId) {
        this.toMemberId = toMemberId;
    }
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    public void setAmountCents(int amountCents) {
        this.amountCents = amountCents;
    }
    public void setTxnAt(LocalDateTime txnAt) {
        this.txnAt = txnAt;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }


    @Override
    public String toString() {
        return "GroupSessionTxn{" +
                "id=" + id +
                ", sessionId=" + sessionId +
                ", fromMemberId=" + fromMemberId +
                ", toMemberId=" + toMemberId +
                ", txnType='" + txnType + '\'' +
                ", amountCents=" + amountCents +
                ", txnAt=" + txnAt +
                ", notes='" + notes + '\'' +
                '}';
    }
}
