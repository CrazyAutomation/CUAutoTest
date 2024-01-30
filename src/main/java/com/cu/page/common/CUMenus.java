package com.cu.page.common;

import com.cu.cukes.TestBase;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

import static com.cu.util.DriverUtils.*;
import static com.cu.util.UIUtils.*;


public class CUMenus extends TestBase {
    private static final String CU_HOME_TITLE = "Home Page - Contoso University";
    private static final String CU_ABOUT_TITLE = "Student Body Statistics - Contoso University";
    private static final String CU_STUDENTS_TITLE = "Students - Contoso University";
    private static final String CU_COURSES_TITLE = "Courses - Contoso University";
    private static final String CU_INSTRUCTORS_TITLE = "Instructors - Contoso University";
    private static final String CU_DEPARTMENTS_TITLE = "Departments - Contoso University";


    InputStream inputStream;

    {
        try {
            inputStream = Files.newInputStream(new File("src/main/java/com/cu/PageObjects/CUCommonObjects.yml").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Yaml yaml = new Yaml();
    Map<String, String> data = yaml.load(inputStream);




    public void validateCUMenus() {
        waitUntilPageFullyLoaded();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Contoso University Menu options validations: ");
        validateButtonsExistence("Contoso University image existence status", getWebElement("xpath", data.get("cuHomeMnu").trim()));
        validateButtonsExistence("Home menu existence status", getWebElement("xpath", data.get("homeMnu").trim()));
        validateButtonsExistence("About menu existence status", getWebElement("xpath", data.get("aboutMnu").trim()));
        validateButtonsExistence("Students menu existence status", getWebElement("xpath", data.get("studentsMnu").trim()));
        validateButtonsExistence("Courses menu existence status", getWebElement("xpath", data.get("coursesMnu").trim()));
        validateButtonsExistence("Instructors menu existence status", getWebElement("xpath", data.get("instructorsMnu").trim()));
        validateButtonsExistence("Departments menu existence status", getWebElement("xpath", data.get("departmentsMnu").trim()));
        validateButtonsExistence("Footer link existence status", getWebElement("xpath", data.get("footer").trim()));
        System.out.println("------------------------------------------------------------------------------------------------");
    }


    public void clickMenu(String option) {
        waitUntilPageFullyLoaded();
        switch (option) {
            case "Home":
                clickButton(getWebElement("xpath", data.get("homeMnu").trim()));
                break;
            case "About":
                clickButton(getWebElement("xpath", data.get("aboutMnu").trim()));
                break;
            case "Students":
                clickButton(getWebElement("xpath", data.get("studentsMnu").trim()));
                break;
            case "Courses":
                clickButton(getWebElement("xpath", data.get("coursesMnu").trim()));
                break;
            case "Instructors":
                clickButton(getWebElement("xpath", data.get("instructorsMnu").trim()));
                break;
            case "Departments":
                clickButton(getWebElement("xpath", data.get("departmentsMnu").trim()));
                break;
            default:
                System.out.println("There is no such menu '" + option + "' exists!");
        }
        System.out.println(option + " menu option is clicked");
    }

    public void validateTitles(String title) {
        waitUntilPageFullyLoaded();
        switch (title) {
            case "Home":
                validateTitle("CU Home page title", CU_HOME_TITLE);
                break;
            case "About":
                validateTitle("CU About page title", CU_ABOUT_TITLE);
                break;
            case "Students":
                validateTitle("CU Students page title", CU_STUDENTS_TITLE);
                break;
            case "Courses":
                validateTitle("CU Courses page title", CU_COURSES_TITLE);
                break;
            case "Instructors":
                validateTitle("CU Instructors page title", CU_INSTRUCTORS_TITLE);
                break;
            case "Departments":
                validateTitle("CU Departments page title", CU_DEPARTMENTS_TITLE);
                break;
            default:
                System.out.println("There is no such menu '" + title + "' exists!");
        }
    }


}
