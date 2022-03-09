package androidev.main_app;


public class VoteModel {
    private String vote;
    private int pollID, userID,voteID,questionID;


    public VoteModel(int pollID, int userID, String vote, int voteID, int questionID ) {
        this.userID = userID;
        this.pollID = pollID;
        this.vote = vote;
        this.voteID = voteID;
        this.questionID = questionID;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getVoteID() {
        return voteID;
    }

    public void setVoteID(int voteID) {
        this.voteID = voteID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
}