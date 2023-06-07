package com.geopokrovskiy.service;

import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.repository.SkillRepository;
import com.geopokrovskiy.repository.jdbc.JdbcSkillRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class SkillService {
    private SkillRepository skillRepository;

    public SkillService() {
        try {
            this.skillRepository = new JdbcSkillRepositoryImpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Skill addNewSkill(Skill skill){
        return this.skillRepository.addNew(skill);
    }

    public List<Skill> getAllSkills(){
        return this.skillRepository.getAll();
    }

    public Skill getSkillById(Long id){
        return this.skillRepository.getById(id);
    }

    public Skill updateSkillName(String name, Long skillId){
        Skill skill = this.getSkillById(skillId);
        if(skill != null) {
            skill.setName(name);
            return this.skillRepository.update(skill);
        }
        return null;
    }

    public boolean delete(Long id){
        return this.skillRepository.delete(id);
    }
}