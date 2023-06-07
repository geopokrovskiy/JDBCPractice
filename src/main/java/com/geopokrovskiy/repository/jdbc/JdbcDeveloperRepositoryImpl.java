package com.geopokrovskiy.repository.jdbc;

import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.DeveloperRepository;
import com.geopokrovskiy.сonstants.Constants;


import java.sql.*;
import java.util.*;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository, AutoCloseable {

    private Connection conn;

    public JdbcDeveloperRepositoryImpl() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL, Constants.USERNAME, Constants.PASSWORD);
    }

    @Override
    public Developer addNew(Developer value) {
        String sql = "insert into developers(id_spec, first_name, last_name, active_status_dev) values (1, ?, ?, 1)";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, value.getFirstName());
            preparedStatement.setString(2, value.getLastName());

            int row = preparedStatement.executeUpdate();
            if (row <= 0) {
                return null;
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    value.setId(generatedKeys.getLong(1));
                }
            }
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Developer getById(Long aLong) {
        String sql = "select * from developers where developers.id=? AND developers.active_status_dev=true";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Developer developer = new Developer();
            developer.setId(resultSet.getLong(1));
            developer.setSpeciality(new Speciality("default speciality"));
            developer.setFirstName(resultSet.getString(3));
            developer.setLastName(resultSet.getString(4));
            developer.setStatus(Status.ACTIVE);
            developer.setSkills(null);
            return developer;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public List<Developer> getAll() {
        String sql = "select * from developers where developers.active_status_dev=true";
        ArrayList<Developer> developers = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getLong(1));
                developer.setFirstName(resultSet.getString(3));
                developer.setLastName(resultSet.getString(4));
                developer.setStatus(Status.ACTIVE);
                developers.add(developer);
            }
        } catch (SQLException e) {
        }
        return developers;
    }

    @Override
    public Developer update(Developer value) {
        String sql = "UPDATE developers SET developers.id_spec=?, developers.first_name=?, developers.last_name=? WHERE " +
                "developers.id =? AND developers.active_status_dev=true";
        try(PreparedStatement preparedStatement = this.conn.prepareStatement(sql)){
            if(value.getSpeciality().getId() != null){
                preparedStatement.setLong(1, value.getSpeciality().getId());
            }
            preparedStatement.setLong(1, 1);
            preparedStatement.setString(2, value.getFirstName());
            preparedStatement.setString(3, value.getLastName());
            preparedStatement.setLong(4, value.getId());
            if(preparedStatement.executeUpdate() > 0) return value;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        Developer developer = this.getById(aLong);
        if(developer != null) {
            developer.setStatus(Status.DELETED);
            String sql = "UPDATE developers SET developers.active_status_dev=false WHERE developers.id=?";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                preparedStatement.setLong(1, aLong);
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public Developer addSkills(Long developerId, List<Skill> skills){
        Developer developer = this.getById(developerId);
        if(developer != null) {
            for (Skill skill : skills) {
                String sql = "INSERT INTO devs_skills(id_dev, id_skill) values(?, ?)";
                try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                    preparedStatement.setLong(1, developerId);
                    preparedStatement.setLong(2, skill.getId());
                    preparedStatement.execute();
                    List<Skill> skillList = developer.getSkills();
                    if(skillList == null) skillList = new ArrayList<>();
                    skillList.add(skill);
                    developer.setSkills(skills);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            return developer;
        }
        return null;
    }

    public List<Long> getSkillIds(Long developerId){
        String sql = "SELECT id_skill FROM devs_skills where devs_skills.id_dev=?";
        List<Long> skillIds = new ArrayList<>();
        try(PreparedStatement preparedStatement = this.conn.prepareStatement(sql)){
            preparedStatement.setLong(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                skillIds.add(resultSet.getLong(1));
            }
            return skillIds;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (this.conn != null) {
            this.conn.close();
        }
    }
}
