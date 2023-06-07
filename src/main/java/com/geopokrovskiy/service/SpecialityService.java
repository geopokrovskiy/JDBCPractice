package com.geopokrovskiy.service;


import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.repository.SpecialityRepository;
import com.geopokrovskiy.repository.jdbc.JdbcSpecialityRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SpecialityService {
    private SpecialityRepository specialityRepository;

    public SpecialityService() {
        try {
            this.specialityRepository = new JdbcSpecialityRepositoryImpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Speciality addNewSpec(Speciality speciality){
        return this.specialityRepository.addNew(speciality);
    }

    public List<Speciality> getAllSpecs(){
        return this.specialityRepository.getAll();
    }

    public Speciality getSpecById(Long id){
        return this.specialityRepository.getById(id);
    }

    public Speciality updateSpecName(String name, Long specId){
        Speciality speciality = this.getSpecById(specId);
        if(speciality != null) {
            speciality.setName(name);
            return this.specialityRepository.update(speciality);
        }
        return null;
    }

    public boolean delete(Long id){
        return this.specialityRepository.delete(id);
    }
}
