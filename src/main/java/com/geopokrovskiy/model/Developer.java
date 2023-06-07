package com.geopokrovskiy.model;

import java.util.List;
import java.util.Objects;

public class Developer{
        private Long id;
        private String firstName;
        private String lastName;
        private List<Skill> skills;
        private Speciality speciality;
        private Status status;

        public Developer(long id, String firstName, String lastName, List<Skill> skills, Speciality speciality) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.skills = skills;
                this.speciality = speciality;
                this.status = Status.ACTIVE;
        }

        public Developer(String firstName, String lastName, List<Skill> skills, Speciality speciality) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.skills = skills;
                this.speciality = speciality;
                this.status = Status.ACTIVE;
        }

        public Developer() {
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public List<Skill> getSkills() {
                return skills;
        }

        public void setSkills(List<Skill> skills) {
                this.skills = skills;
        }

        public Speciality getSpeciality() {
                return speciality;
        }

        public void setSpeciality(Speciality speciality) {
                this.speciality = speciality;
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
                Developer developer = (Developer) o;
                return id == developer.id && Objects.equals(firstName, developer.firstName) && Objects.equals(lastName, developer.lastName) && Objects.equals(skills, developer.skills) && Objects.equals(speciality, developer.speciality) && status == developer.status;
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, firstName, lastName, skills, speciality, status);
        }

        @Override
        public String toString() {
                return "Developer{" +
                        "id=" + id +
                        ", firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", skills=" + skills +
                        ", speciality=" + speciality +
                        ", status=" + status +
                        '}';
        }
}
