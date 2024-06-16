package model;

public class Vote {
    private int id;
    private int candidateId;
    private int userId;

    public Vote(int id, int candidateId, int userId) {
        this.id = id;
        this.candidateId = candidateId;
        this.userId = userId;
    }

    public Vote(int candidateId, int userId) {
        this.candidateId = candidateId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", candidateId=" + candidateId +
                ", userId=" + userId +
                '}';
    }
}
