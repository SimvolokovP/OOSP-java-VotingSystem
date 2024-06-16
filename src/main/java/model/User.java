package model;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String snils;
    private boolean isVote;

    public User(int id, String login, String password, String name, String snils, boolean isVote) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.snils = snils;
        this.isVote = isVote;
    }

    public User(String login, String password, String name, String snils, boolean isVote) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.snils = snils;
        this.isVote = isVote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVote() {
        return isVote;
    }

    public void setVote(boolean vote) {
        isVote = vote;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", snils='" + snils + '\'' +
                ", isVote=" + isVote +
                '}';
    }
}
