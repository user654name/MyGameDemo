package com.buywhat.demo.bean.Game;

public class Pokemon2 {
    private Integer id;

    private String name;

    private String type;

    private String att;

    private String url;

    private String level;


    @Override
    public String toString() {
        return "Pokemon2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", att='" + att + '\'' +
                ", url='" + url + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att == null ? null : att.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}