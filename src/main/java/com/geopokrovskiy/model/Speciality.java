package com.geopokrovskiy.model;

import java.util.Objects;

public class Speciality {
    private Long id;
    private String name;
    private Status status;

    public Speciality(long id, String name) {
        this.id = id;
        this.name = name;
        this.status = Status.ACTIVE;
    }

    public Speciality(String name) {
        this.name = name;
        this.status = Status.ACTIVE;
    }

    public Speciality() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speciality that = (Speciality) o;
        return id == that.id && Objects.equals(name, that.name) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
