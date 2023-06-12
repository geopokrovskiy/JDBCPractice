package com.geopokrovskiy.controller;

import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.service.DeveloperService;

import java.util.ArrayList;
import java.util.List;

public class DeveloperController {
    private DeveloperService developerService;

    public DeveloperController() {
        this.developerService = new DeveloperService();
    }

    public Developer addDeveloper(String firstName, String lastName){
        Developer developer = new Developer(firstName, lastName, null, null);
        return this.developerService.addNewDeveloper(developer);
    }

    public List<Developer> getAllDevs(){
        return this.developerService.getAllDevs();
    }

    public List<Skill> getDevSkills(Long devId){
        List<Skill> skills = this.developerService.getDevSkills(devId);
        return skills != null ? skills : new ArrayList<>();
    }

    public Developer getDevById(Long id){
        return this.developerService.getDevById(id);
    }

    public Developer changeFirstName(String firstName, Long id){
        return this.developerService.updateDevFirstName(firstName, id);
    }

    public Developer changeLastName(String lastName, Long id){
        return this.developerService.updateDevLastName(lastName, id);
    }

    public Developer changeSpec(Long specId, Long devId){
        return this.developerService.updateDevSpeciality(specId, devId);
    }

    public Developer addSkills(List<Skill> skills, Long devId){
        List<Skill> oldSkills = this.getDevSkills(devId);
        oldSkills.addAll(skills);
        return this.developerService.updateDevSkills(oldSkills, devId);
    }

    public boolean deleteDeveloper(Long id){
        return this.developerService.delete(id);
    }
}
