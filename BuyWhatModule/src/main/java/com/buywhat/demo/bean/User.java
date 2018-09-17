package com.buywhat.demo.bean;

public class User {
    private Integer id;

    private String name;

    private String password;

    private String headUrl;

    private String msg;



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }

    public User() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User(Integer id, String name, String password, String headUrl) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.headUrl = headUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }
}