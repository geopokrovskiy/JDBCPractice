package com.geopokrovskiy.service;

import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.repository.SkillRepository;
import com.geopokrovskiy.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private SkillRepository skillRepository;

    public SkillService() {
        this.skillRepository = new JdbcSkillRepositoryImpl();
    }

    public Skill addNewSkill(Skill skill) {
        return this.skillRepository.addNew(skill);
    }

    public List<Skill> getAllSkills() {
        return this.skillRepository.getAll();
    }

    public Skill getSkillById(Long id) {
        return this.skillRepository.getById(id);
    }

    public Skill updateSkillName(String name, Long skillId) {
        Skill skill = this.getSkillById(skillId);
        if (skill != null) {
            skill.setName(name);
            return this.skillRepository.update(skill);
        }
        return null;
    }

    public boolean delete(Long id) {
        return this.skillRepository.delete(id);
    }
}
