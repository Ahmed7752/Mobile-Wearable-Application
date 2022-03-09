package androidev.main_app;


import java.util.List;

public class PollModel {
    private String title, topic;
    private int pollID, totalLikes;


    public PollModel(int pollID, String title, String topic, int totalLikes) {
        this.title = title;
        this.pollID = pollID;
        this.topic = topic;
        this.totalLikes = totalLikes;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }
}