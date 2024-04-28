package com.example.smd_a3;

public class passwordModel {
    int id;
    String username;
    String password;
    String url;
    passwordModel(int id,String name,String pass, String u)
    {
        this.id=id;
        username=name;
        password=pass;
        url=u;
    }
    @Override
    public String toString() {
        return "ID: " + id + ", Username: " + username + ", Password: " + password + ", URL: " + url;
    }


    public passwordModel() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }
}
