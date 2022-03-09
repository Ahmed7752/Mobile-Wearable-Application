package androidev.main_app;


public class SavedPollModel {

    private int pollID, userID;


    public SavedPollModel(int pollID, int userID) {
        this.pollID = pollID;
        this.userID = userID;
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
}