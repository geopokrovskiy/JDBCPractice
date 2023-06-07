package com.geopokrovskiy.controller;

import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.service.SkillService;

import java.util.List;

public class SkillController {
    private SkillService skillService;

    public SkillController() {
        this.skillService = new SkillService();
    }

    public Skill addSkill(String skillName){
        Skill newSkill = new Skill(skillName);
        newSkill.setStatus(Status.ACTIVE);
        return this.skillService.addNewSkill(newSkill);
    }

    public List<Skill> getAllSkills(){
        return this.skillService.getAllSkills();
    }

    public Skill getSkillById(Long id){
        return this.skillService.getSkillById(id);
    }

    public Skill changeSkillName(String name, Long skillId){
        return this.skillService.updateSkillName(name, skillId);
    }

    public boolean deleteSkill(Long id){
        return this.skillService.delete(id);
    }
}
