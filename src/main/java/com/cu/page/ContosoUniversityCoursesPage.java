package com.cu.page;

import com.cu.cukes.TestBase;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

import static com.cu.util.DriverUtils.waitUntilPageFullyLoaded;
import static com.cu.util.UIUtils.*;


public class ContosoUniversityCoursesPage extends TestBase {


    private static final String CU_COURSES_HEADER = "Courses";

    InputStream inputStream;
    {
        try {
            inputStream = Files.newInputStream(new File("src/main/java/com/cu/PageObjects/CUCoursesPageObjects.yml").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Yaml yaml = new Yaml();
    Map<String, String> data = yaml.load(inputStream);

    public void validateCUCoursesPageContent() {
        waitUntilPageFullyLoaded();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Contoso University Home page validations: ");
        validateText("Validate Header Text",CU_COURSES_HEADER,getWebElement("tag",data.get("headerText").trim()));
        Assert.assertEquals("Validate Create new link Text", "Create New", getWebElement("xpath", data.get("createNew").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("createNew").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("selectDetailsSearch").trim()).getText());
        Assert.assertTrue("Validate Select department search details", getWebElement("xpath", data.get("selectDetailsSearch").trim()).getText().contains("Select Department:"));
        System.out.println(getWebElement("xpath", data.get("coursesTable").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("editButton").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("detailsButton").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("deleteButton").trim()).getText());
        System.out.println("------------------------------------------------------------------------------------------------");
    }

//    public void validateCUHomePageButtons() {
//        waitUntilPageFullyLoaded();
//        System.out.println("------------------------------------------------------------------------------------------------");
//        System.out.println("Contoso University Home page validations: ");
//        validateButtonsExistence("See tutorial button status",getWebElement("xpath",data.get("seeTutorialButton").trim()));
//        validateButtonsExistence("Download button status",getWebElement("xpath",data.get("downloadButton").trim()));
//        System.out.println("------------------------------------------------------------------------------------------------");
//    }



}
