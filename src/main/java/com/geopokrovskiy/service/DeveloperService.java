package com.geopokrovskiy.service;

import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperService {
    private JdbcDeveloperRepositoryImpl developerRepository;
    private SkillService skillService;

    private SpecialityService specialityService;
    public DeveloperService() {
        try {
            this.developerRepository = new JdbcDeveloperRepositoryImpl();
            this.skillService = new SkillService();
            this.specialityService = new SpecialityService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Developer addNewDeveloper(Developer developer){
        developer.setSpeciality(this.specialityService.getSpecById(1L));
        return this.developerRepository.addNew(developer);
    }

    public List<Developer> getAllDevs(){
        return this.developerRepository.getAll();
    }

    public Developer getDevById(Long id){
        return this.developerRepository.getById(id);
    }

    public List<Skill> getDevSkills(Long developerId){
        List<Long> skillIds = this.developerRepository.getSkillIds(developerId);
        List<Skill> skills = new ArrayList<>();
        for(Long id : skillIds){
            skills.add(this.skillService.getSkillById(id));
        }
        return skills;
    }

    public Developer updateDevFirstName(String firstName, Long devId){
        Developer developer = this.developerRepository.getById(devId);
        if(developer != null){
            developer.setFirstName(firstName);
            return this.developerRepository.update(developer);
        }
        return null;
    }

    public Developer updateDevLastName(String lastName, Long devId){
        Developer developer = this.developerRepository.getById(devId);
        if(developer != null){
            developer.setLastName(lastName);
            return this.developerRepository.update(developer);
        }
        return null;
    }

    public Developer updateDevSpeciality(Long specialityId, Long devId){
        Developer developer = this.developerRepository.getById(devId);
        Speciality speciality = this.specialityService.getSpecById(specialityId);
        if(developer != null){
            developer.setSpeciality(speciality);
            return this.developerRepository.update(developer);
        }
        return null;
    }

    public Developer updateDevSkills(List<Skill> skills, Long devId){
        Developer developer = this.developerRepository.getById(devId);
        if(developer != null && skills != null){
            return this.developerRepository.addSkills(devId, skills);
        }
        return null;
    }

    public boolean delete(Long id){
        return this.developerRepository.delete(id);
    }

}
