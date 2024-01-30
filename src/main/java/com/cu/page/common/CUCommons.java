package com.cu.page.common;

import com.cu.cukes.TestBase;
import com.cu.page.*;

import java.io.IOException;

import static com.cu.util.DriverUtils.*;


public class CUCommons extends TestBase {

    ContosoUniversityHomePage contosoUniversityHomePage = new ContosoUniversityHomePage();
    ContosoUniversityAboutPage contosoUniversityAboutPage = new ContosoUniversityAboutPage();
    ContosoUniversityStudentsPage contosoUniversityStudentsPage = new ContosoUniversityStudentsPage();
    ContosoUniversityCoursesPage contosoUniversityCoursesPage = new ContosoUniversityCoursesPage();
    ContosoUniversityInstructorsPage contosoUniversityInstructorsPage = new ContosoUniversityInstructorsPage();
    ContosoUniversityDepartmentsPage contosoUniversityDepartmentsPage = new ContosoUniversityDepartmentsPage();

    public CUCommons() throws IOException {
    }

    public void validatePageContent(String page) {
        waitUntilPageFullyLoaded();
        switch (page) {
            case "Home":
                contosoUniversityHomePage.validateCUHomePageContent();
                break;
            case "About":
                contosoUniversityAboutPage.validateCUAboutPageContent();
                break;
            case "Students":
                contosoUniversityStudentsPage.validateCUStudentsPageContent();
                break;
            case "Courses":
                contosoUniversityCoursesPage.validateCUCoursesPageContent();
                break;
            case "Instructors":
                contosoUniversityInstructorsPage.validateCUInstructorsPageContent();
                break;
            case "Departments":
                contosoUniversityDepartmentsPage.validateCUDepartmentsPageContent();
                break;
            default:
                System.out.println("There is no such menu '" + page + "' exists!");
        }
    }


}
