//This is a simple class which stores one bullion value.
// This class will be used in determining if a user has completed a certain task.
package androidev.main_app;

public class IsComplete {
    private Boolean isComplete;


    public IsComplete(Boolean isComplete){
        this.isComplete = isComplete;

    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }
}

