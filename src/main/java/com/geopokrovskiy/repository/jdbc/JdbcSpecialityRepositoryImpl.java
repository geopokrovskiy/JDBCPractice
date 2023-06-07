package com.geopokrovskiy.repository.jdbc;


import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.SpecialityRepository;
import com.geopokrovskiy.сonstants.Constants;


import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class JdbcSpecialityRepositoryImpl implements SpecialityRepository, AutoCloseable {

    private Connection conn;

    public JdbcSpecialityRepositoryImpl() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(Constants.DB_URL, Constants.USERNAME, Constants.PASSWORD);
    }
    @Override
    public Speciality addNew(Speciality value) {
        String sql = "insert into speciality(speciality_name, active_status_spec) values (?, 1)";
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
    public Speciality getById(Long aLong) {
        String sql = "select * from speciality where speciality.id=? AND active_status_spec=true";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Speciality speciality = new Speciality();
            speciality.setId(resultSet.getLong(1));
            speciality.setName(resultSet.getString(2));
            speciality.setStatus(Status.ACTIVE);
            return speciality;
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public List<Speciality> getAll() {
        String sql = "select * from speciality where speciality.active_status_spec=true";
        ArrayList<Speciality> specs = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Speciality speciality = new Speciality();
                speciality.setId(resultSet.getLong(1));
                speciality.setName(resultSet.getString(2));
                speciality.setStatus(Status.ACTIVE);
                specs.add(speciality);
            }
        } catch (SQLException e) {
        }
        return specs;
    }

    @Override
    public Speciality update(Speciality value) {
        String sql = "UPDATE speciality SET speciality.speciality_name=? " +
                "WHERE speciality.id =? AND speciality.active_status_spec=true";
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
        Speciality speciality = this.getById(aLong);
        if(speciality != null) {
            speciality.setStatus(Status.DELETED);
            String sql = "UPDATE speciality SET speciality.active_status_spec=false WHERE speciality.id=?";
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
        if(this.conn != null){
            this.conn.close();
        }
    }
}
