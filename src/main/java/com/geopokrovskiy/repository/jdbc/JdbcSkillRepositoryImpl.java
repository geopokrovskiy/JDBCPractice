package com.geopokrovskiy.repository.jdbc;

import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.SkillRepository;
import com.geopokrovskiy.сonstants.Constants;

import java.sql.*;
import java.util.*;

public class JdbcSkillRepositoryImpl implements SkillRepository, AutoCloseable {

    private Connection conn;

    public JdbcSkillRepositoryImpl() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL, Constants.USERNAME, Constants.PASSWORD);
    }

    @Override
    public Skill addNew(Skill value) {
        String sql = "insert into skills(skill_name, active_status_skill) values (?, 1)";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, value.getName());
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
    public Skill getById(Long id) {
        String sql = "select * from skills where skills.id=? AND active_status_skill=true";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Skill skill = new Skill();
            skill.setId(resultSet.getLong(1));
            skill.setName(resultSet.getString(2));
            skill.setStatus(Status.ACTIVE);
            return skill;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public List<Skill> getAll() {
        String sql = "select * from skills where skills.active_status_skill=true";
        ArrayList<Skill> skills = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setId(resultSet.getLong(1));
                skill.setName(resultSet.getString(2));
                skill.setStatus(Status.ACTIVE);
                skills.add(skill);
            }
        } catch (SQLException e) {
        }
        return skills;
    }

    @Override
    public Skill update(Skill value) {
        String sql = "UPDATE skills SET skills.skill_name=? WHERE skills.id =? AND skills.active_status_skill=true";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setString(1, value.getName());
            preparedStatement.setLong(2, value.getId());
            if (preparedStatement.executeUpdate() > 0) return value;
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        Skill skill = this.getById(aLong);
        if (skill != null) {
            skill.setStatus(Status.DELETED);
            String sql = "UPDATE skills SET skills.active_status_skill=false WHERE skills.id=?";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                preparedStatement.setLong(1, aLong);
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
            }
        }
        return false;
    }


    @Override
    public void close() throws Exception {
        if (this.conn != null) {
            this.conn.close();
        }
    }
}
