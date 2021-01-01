package tools;

public class JQueryAnswer {

    private boolean success;
    private String message;

    public JQueryAnswer(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public void appendMessage(String append) {
        message += append;
    }
}
