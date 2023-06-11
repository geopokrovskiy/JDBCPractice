package com.geopokrovskiy.view;


import com.geopokrovskiy.controller.SpecialityController;
import com.geopokrovskiy.model.Speciality;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SpecialityView {

    private final SpecialityController specialityController = new SpecialityController();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are in specialities menu. What would you like to do?");
        System.out.println("1) Add new speciality");
        System.out.println("2) Update data of a speciality");
        System.out.println("3) Delete a speciality");
        System.out.println("4) To Main Menu");
        List<Speciality> specList = specialityController.getAllSpecs();
        System.out.println("List of specialities: " + specList);
        while (true) {
            int specOption = 4;
            try {
                specOption = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Incorrect input");
            }
            if (specOption == 1) {
                String specName = "";
                while (specName.length() < 1) {
                    System.out.println("Enter the name of the speciality: ");
                    specName = scanner.nextLine();
                }
                specialityController.addSpeciality(specName);
                break;
            } else if (specOption == 2) {
                Speciality oldSpec = null;
                while (oldSpec == null) {
                    System.out.println("Which speciality would you like to update? Select its ID.");
                    specList.forEach(speciality -> {
                        System.out.println(speciality.getId() + ") " + speciality.getName());
                    });
                    long id;
                    try {
                        id = scanner.nextLong();
                        oldSpec = specList.stream().filter(spec -> spec.getId() == id).findFirst().orElse(null);
                    } catch (InputMismatchException e){
                        System.out.println("Incorrect input!");
                    }
                }
                String newSpec = "";
                while (newSpec.length() < 1) {
                    System.out.println("Enter the new name of the speciality.");
                    newSpec = scanner.nextLine();
                }
                specialityController.changeSpecialityName(newSpec, oldSpec.getId());
                break;
            } else if (specOption == 3) {
                Speciality toDelete = null;
                while (toDelete == null) {
                    System.out.println("Which speciality would you like to delete? Select its ID.");
                    for (Speciality speciality : specList) {
                        System.out.println(speciality.getId() + ") " +speciality.getName());
                    }
                    long id;
                    try {
                        id = scanner.nextLong();
                        toDelete = specList.stream().filter(spec -> spec.getId() == id).findFirst().orElse(null);
                    } catch (InputMismatchException e){
                        System.out.println("Incorrect input!");
                    }
                }
                if(!specialityController.deleteSpeciality(toDelete.getId())){
                    System.out.println("The speciality has already been deleted!");
                };
                break;
            } else if (specOption == 4) {
                break;
            }
        }
    }
}
