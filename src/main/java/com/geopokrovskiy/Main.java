package com.geopokrovskiy;

import com.geopokrovskiy.controller.DeveloperController;
import com.geopokrovskiy.controller.SkillController;
import com.geopokrovskiy.controller.SpecialityController;
import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.repository.DeveloperRepository;
import com.geopokrovskiy.repository.SpecialityRepository;
import com.geopokrovskiy.repository.jdbc.JdbcDeveloperRepositoryImpl;
import com.geopokrovskiy.repository.jdbc.JdbcSpecialityRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DeveloperController developerController = new DeveloperController();
        SpecialityController specialityController = new SpecialityController();
        SkillController skillController = new SkillController();

        /*skillController.addSkill("Java");
        skillController.addSkill("C++");
        skillController.addSkill("Python");*/

       /* specialityController.addSpeciality("Front end");
        specialityController.addSpeciality("Back end");
        specialityController.addSpeciality("DevOps");*/

       // developerController.addDeveloper("Georgy", "Pokrovskiy");

       // developerController.addDeveloper("Alexander", "Kuznetsov");

        //developerController.changeDevFirstName("GEORGY",7L);

       // developerController.changeDevFirstName("Георгий",7L);
      //  specialityController.changeSpecialityName("FrontEnd", 2L);
       /* System.out.println(specialityController.getAllSpecs());
        specialityController.deleteSpeciality(4L);
        System.out.println(specialityController.getAllSpecs());*/

       /* System.out.println(developerController.getAllDevs());
        System.out.println(skillController.getAllSkills());
        Skill java = skillController.getSkillById(1L);
        System.out.println(java);
        List<Skill> skills = new ArrayList<>();
        skills.add(java);
        developerController.addSkills(skills, 7L);*/

        System.out.println(developerController.getDevSkills(7L));

    }
}