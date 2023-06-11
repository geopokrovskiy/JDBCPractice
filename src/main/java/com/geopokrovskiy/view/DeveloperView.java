package com.geopokrovskiy.view;

import com.geopokrovskiy.controller.DeveloperController;
import com.geopokrovskiy.controller.SkillController;
import com.geopokrovskiy.controller.SpecialityController;
import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;

import java.util.*;

public class DeveloperView {

    private final DeveloperController developerController = new DeveloperController();
    private final SpecialityController specialityController = new SpecialityController();
    private final SkillController skillController = new SkillController();


    public void start() {
        Scanner scanner = new Scanner(System.in);
        List<Developer> developerList = developerController.getAllDevs();
        System.out.println("List of developers: " + developerList);

        while (true) {
            System.out.println("You are in developer menu. What would you like to do?");
            System.out.println("1) Add new developer");
            System.out.println("2) Update a developer's data");
            System.out.println("3) Delete a developer");
            System.out.println("4) To Main Menu");
            int devOption = scanner.nextInt();
            if (devOption == 1) {
                String firstName = "";
                while (firstName.length() < 1) {
                    System.out.println("Enter the first name of the developer: ");
                    firstName = scanner.nextLine();
                }
                String lastName = "";
                while (lastName.length() < 1) {
                    System.out.println("Enter the last name of the developer: ");
                    lastName = scanner.nextLine();
                }
                /*List<Speciality> specList = specialityController.getAllSpecs();
                Speciality devSpeciality = null;
                if (!specList.isEmpty()) {
                    while (devSpeciality == null) {
                        System.out.println("Choose the speciality among the list: ");
                        specList.forEach(speciality -> {
                            System.out.println(speciality.getId() + ") " + speciality.getName());
                        });
                        long id;
                        try {
                            id = scanner.nextLong();
                            devSpeciality = specList.stream().filter(spec -> spec.getId() == id).findFirst().orElse(null);
                        } catch (InputMismatchException e){
                            System.out.println("Incorrect input!");
                        }
                    }
                }*/
                /*List<Skill> skillList = skillController.getAllSkills();
                Set<Skill> chosenSkills = new TreeSet<>(Comparator.comparingLong(Skill::getId));
                if (!skillList.isEmpty()) {
                    while (true) {
                        System.out.println("Choose skills among the list. Press 0 to quit");
                        skillList.forEach(skill -> {
                            System.out.println(skill.getId() + ") " + skill.getName());
                        });
                        long id;
                        try {
                            id = scanner.nextLong();
                            if (id == 0) {
                                break;
                            }
                            Skill devSkill = skillList.stream().filter(skill -> skill.getId() == id).findFirst().orElse(null);
                            if (devSkill != null) {
                                chosenSkills.add(devSkill);
                                System.out.println("The skill has been added! The list of chosen skills: ");
                                chosenSkills.forEach(skill -> {
                                    System.out.println(skill.getId() + ") " + skill.getName());
                                });
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Incorrect input!");
                        }
                    }
                }*/
                if(developerController.addDeveloper(firstName, lastName) != null){
                    System.out.println("The developer has been added!");
                }
                break;
            } else if (devOption == 2) {
                Developer oldDev = null;
                while (oldDev == null) {
                    System.out.println("Which developer would you like to update? Select its ID.");
                    developerList.forEach(developer -> {
                        System.out.println(developer.getId() + ") " + developer.getFirstName() + " " + developer.getLastName());
                    });
                    long id;
                    try {
                        id = scanner.nextLong();
                        oldDev = developerList.stream().filter(developer -> developer.getId() == id).findFirst().orElse(null);
                    } catch (InputMismatchException e){
                        System.out.println("Incorrect input!");
                    }
                }
                while (true) {
                    System.out.println("What would you like to update?");
                    System.out.println("1) First name");
                    System.out.println("2) Last name");
                    System.out.println("3) Skills");
                    System.out.println("4) Speciality");
                    long updateOption;
                    try {
                        updateOption = scanner.nextLong();
                        if (updateOption == 1) {
                            String newFirstName = "";
                            while (newFirstName.length() < 1) {
                                System.out.println("Enter the new first name");
                                newFirstName = scanner.nextLine();
                            }
                            developerController.changeFirstName(newFirstName, oldDev.getId());
                            break;
                        } else if (updateOption == 2) {
                            String newLastName = "";
                            while (newLastName.length() < 1) {
                                System.out.println("Enter the new last name");
                                newLastName = scanner.nextLine();
                            }
                            developerController.changeLastName(newLastName, oldDev.getId());
                            break;
                        } else if (updateOption == 3) {
                            List<Skill> skillList = skillController.getAllSkills();
                            skillList.removeAll(oldDev.getSkills());
                            if(skillList.isEmpty()){
                                break;
                            }
                            Set<Skill> chosenSkills = new TreeSet<>(Comparator.comparingLong(Skill::getId));
                            System.out.println("Choose skills among the list. Press 0 to quit");
                            while (true) {
                                skillList.forEach(skill -> {
                                    System.out.println(skill.getId() + ") " + skill.getName());
                                });
                                long id;
                                try {
                                    id = scanner.nextLong();
                                    if (id == 0) {
                                        break;
                                    }
                                    Skill devSkill = skillList.stream().filter(skill -> skill.getId() == id).findFirst().orElse(null);
                                    if (devSkill != null) {
                                        chosenSkills.add(devSkill);
                                        System.out.println("The skill has been added! The list of chosen skills: ");
                                        chosenSkills.forEach(skill -> {
                                            System.out.println(skill.getId() + ") " + skill.getName());
                                        });
                                        System.out.println();
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Incorrect input!");
                                }
                            }
                            developerController.addSkills(chosenSkills.stream().toList(), oldDev.getId());
                            break;
                        } else if (updateOption == 4) {
                            List<Speciality> specList = specialityController.getAllSpecs();
                            Speciality newSpeciality = null;
                            if (!specList.isEmpty()) {
                                while (newSpeciality == null) {
                                    System.out.println("Choose the new speciality among the list: ");
                                    specList.forEach(speciality -> {
                                        System.out.println(speciality.getId() + ") " + speciality.getName());
                                    });
                                    long id;
                                    try {
                                        id = scanner.nextLong();
                                        newSpeciality = specList.stream().filter(spec -> spec.getId() == id).findFirst().orElse(null);
                                    } catch (InputMismatchException e){
                                        System.out.println("Incorrect input!");
                                    }
                                }
                            }
                            developerController.changeSpec(newSpeciality.getId(), oldDev.getId());
                            break;
                        }
                    } catch (InputMismatchException e){
                        System.out.println("Incorrect input!");
                        break;
                    }
                }
            } else if (devOption == 3) {
                Developer toDelete = null;
                while (toDelete == null) {
                    System.out.println("Which developer would you like to delete? Select his/her ID.");
                    developerList.forEach(developer -> {
                        System.out.println(developer.getId() + ") " + developer.getFirstName() + " " + developer.getLastName());
                    });
                    long id;
                    try {
                        id = scanner.nextLong();
                        toDelete = developerList.stream().filter(developer -> developer.getId() == id).findFirst().orElse(null);
                    } catch (InputMismatchException e){
                        System.out.println("Incorrect input!");
                    }
                }
                 if(!developerController.deleteDeveloper(toDelete.getId())){
                     System.out.println("The developer has already been deleted!");
                 }
                break;
            } else if (devOption == 4) {
                break;
            }
        }
    }
}
