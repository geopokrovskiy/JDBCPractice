package com.geopokrovskiy.repository.jdbc;


import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.SpecialityRepository;
import com.geopokrovskiy.utils.JdbcUtils;


import java.sql.*;
import java.util.*;

public class JdbcSpecialityRepositoryImpl implements SpecialityRepository {



    @Override
    public Speciality addNew(Speciality value) {
        String sql = "insert into speciality(speciality_name, active_status_spec) values (?, 1)";
        try (PreparedStatement preparedStatement = JdbcUtils.preparedStatementWithKeys(sql)) {
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Speciality getById(Long aLong) {
        String sql = "select * from speciality where speciality.id=? AND active_status_spec=true";
        try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Speciality> getAll() {
        String sql = "select * from speciality where speciality.active_status_spec=true";
        ArrayList<Speciality> specs = new ArrayList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Speciality speciality = new Speciality();
                speciality.setId(resultSet.getLong(1));
                speciality.setName(resultSet.getString(2));
                speciality.setStatus(Status.ACTIVE);
                specs.add(speciality);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return specs;
    }

    @Override
    public Speciality update(Speciality value) {
        String sql = "UPDATE speciality SET speciality.speciality_name=? " +
                "WHERE speciality.id =? AND speciality.active_status_spec=true";
        try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
            preparedStatement.setString(1, value.getName());
            preparedStatement.setLong(2, value.getId());
            if (preparedStatement.executeUpdate() > 0) return value;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        Speciality speciality = this.getById(aLong);
        if (speciality != null) {
            speciality.setStatus(Status.DELETED);
            String sql = "UPDATE speciality SET speciality.active_status_spec=false WHERE speciality.id=?";
            try (PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql)) {
                preparedStatement.setLong(1, aLong);
                return preparedStatement.executeUpdate() > 0;
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
