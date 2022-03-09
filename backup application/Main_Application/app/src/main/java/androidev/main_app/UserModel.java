package androidev.main_app;

public class UserModel {
    private String username,email, pw;
    private int id;

    public UserModel(int id, String username, String email,String password) {

        this.username = username;
        this.email = email;
        this.pw = password;
        this.id = id;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
