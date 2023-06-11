package com.geopokrovskiy.repository.jdbc;

import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.DeveloperRepository;
import com.geopokrovskiy.repository.SpecialityRepository;
import com.geopokrovskiy.utils.JdbcUtils;
import com.mysql.cj.protocol.Resultset;


import java.sql.*;
import java.util.*;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {

    private Developer mapResultSetToDeveloper(ResultSet resultSet) throws SQLException{
        Developer developer = new Developer();
        List<Skill> skills = new ArrayList<>();
        while(resultSet.next()){
            developer.setId(resultSet.getLong(1));
            developer.setFirstName(resultSet.getString(2));
            developer.setLastName(resultSet.getString(3));
            Speciality speciality = new Speciality(resultSet.getString(4));
            speciality.setId(resultSet.getLong(5));
            developer.setSpeciality(speciality);
            if(resultSet.getString(7) != null) {
                Skill skill = new Skill(resultSet.getString(7));
                skill.setId(resultSet.getLong(6));
                skills.add(skill);
            }
        }
        developer.setStatus(Status.ACTIVE);
        developer.setSkills(skills);
        return developer;
    }

    @Override
    public Developer addNew(Developer value) {
        String sql = "insert into developers(id_spec, first_name, last_name, active_status_dev) values (1, ?, ?, 1)";
        try (PreparedStatement preparedStatement = JdbcUtils.preparedStatementWithKeys(sql)) {
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Developer getById(Long aLong) {
        String sql = "SELECT DISTINCT developers.id, first_name, last_name, speciality_name, s.id, skls.id, skill_name " +
                "    FROM developers " +
                "    LEFT JOIN speciality s on developers.id_spec = s.id\n" +
                "    LEFT JOIN devs_skills ds ON developers.id = ds.id_dev\n" +
                "    LEFT JOIN skills skls ON ds.id_skill = skls.id\n" +
                "    WHERE developers.active_status_dev=true AND s.active_status_spec=true AND developers.id=?";
        try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSetToDeveloper(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Developer> getAll() {
        String sql = "SELECT developers.id from developers WHERE active_status_dev=true";
        ArrayList<Developer> developers = new ArrayList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Developer developer = this.getById(resultSet.getLong(1));
                developers.add(developer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return developers;
    }

    @Override
    public Developer update(Developer value) {
        String sql = "UPDATE developers SET developers.id_spec=?, developers.first_name=?, developers.last_name=? WHERE " +
                "developers.id =? AND developers.active_status_dev=true";
        try(PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)){
            preparedStatement.setLong(1, value.getSpeciality().getId());
            preparedStatement.setString(2, value.getFirstName());
            preparedStatement.setString(3, value.getLastName());
            preparedStatement.setLong(4, value.getId());
            if(preparedStatement.executeUpdate() > 0) return value;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        Developer developer = this.getById(aLong);
        if(developer != null) {
            developer.setStatus(Status.DELETED);
            String sql = "UPDATE developers SET developers.active_status_dev=false WHERE developers.id=?";
            try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
                preparedStatement.setLong(1, aLong);
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Developer addSkills(Long developerId, List<Skill> skills){
        Developer developer = this.getById(developerId);
        if(developer != null) {
            for (Skill skill : skills) {
                String sql = "INSERT INTO devs_skills(id_dev, id_skill) values(?, ?)";
                try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
                    preparedStatement.setLong(1, developerId);
                    preparedStatement.setLong(2, skill.getId());
                    preparedStatement.execute();
                    List<Skill> skillList = developer.getSkills();
                    if(skillList == null) skillList = new ArrayList<>();
                    skillList.add(skill);
                    developer.setSkills(skillList);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return developer;
        }
        return null;
    }

    public List<Long> getSkillIds(Long developerId){
        String sql = "SELECT id_skill FROM devs_skills where devs_skills.id_dev=?";
        List<Long> skillIds = new ArrayList<>();
        try(PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)){
            preparedStatement.setLong(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                skillIds.add(resultSet.getLong(1));
            }
            return skillIds;
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
