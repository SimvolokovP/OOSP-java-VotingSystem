package model;

import dao.Repository;

public class CIK {
    private int id;
    private String name;

    public CIK(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CIK(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CIK{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
