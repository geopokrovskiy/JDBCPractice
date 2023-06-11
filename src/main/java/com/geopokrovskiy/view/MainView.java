package com.geopokrovskiy.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {

    private final DeveloperView developerView = new DeveloperView();
    private final SkillView skillView = new SkillView();
    private final SpecialityView specialityView = new SpecialityView();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Hello! What would you like to do? Press the button: ");
            System.out.println("1) Work with Developers");
            System.out.println("2) Work with Skills");
            System.out.println("3) Work with Specialities");
            System.out.println("4) Quit");
            int option = 4;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input!");
            }
            if (option == 1) {
                developerView.start();
            } else if (option == 2) {
                skillView.start();
            } else if (option == 3) {
                specialityView.start();
            } else if (option == 4) {
                System.out.println("See you soon!");
                break;
            }
        }
    }
}
