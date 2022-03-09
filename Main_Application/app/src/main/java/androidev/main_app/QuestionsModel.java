package androidev.main_app;


public class QuestionsModel {
    private String title;
    private int pollID, questionID;


    public QuestionsModel(int questionID, int pollID, String title ) {
        this.title = title;
        this.pollID = pollID;
        this.questionID = questionID;



    }

    @Override
    public String toString() {
        return title ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

}