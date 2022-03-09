package androidev.main_app;


public class ResultsModel {
    private String pollTitle,postcode,topic,vote;
    private int resultID,userID;


    public ResultsModel(int resultID, String pollTitle, int userID, String vote, String postcode, String topic) {
        this.resultID = resultID;
        this.pollTitle = pollTitle;
        this.userID = userID;
        this.vote = vote;
        this.postcode = postcode;
        this.topic = topic;

    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPollTitle() {
        return pollTitle;
    }

    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}