package com.cu.page;

import com.cu.cukes.TestBase;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

import static com.cu.util.DriverUtils.waitUntilPageFullyLoaded;
import static com.cu.util.UIUtils.*;


public class ContosoUniversityDepartmentsPage extends TestBase {


    private static final String CU_DEPARTMENTS_HEADER = "Departments";

    InputStream inputStream;
    {
        try {
            inputStream = Files.newInputStream(new File("src/main/java/com/cu/PageObjects/CUDepartmentsPageObjects.yml").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Yaml yaml = new Yaml();
    Map<String, String> data = yaml.load(inputStream);

    public void validateCUDepartmentsPageContent() {
        waitUntilPageFullyLoaded();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Contoso University Home page validations: ");
        validateText("Validate Header Text",CU_DEPARTMENTS_HEADER,getWebElement("tag",data.get("headerText").trim()));
        isElementExists("See Create new link ", getWebElement("xpath", data.get("createNew").trim()));
        System.out.println(getWebElement("xpath", data.get("departmentTable").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("editButton").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("detailsButton").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("deleteButton").trim()).getText());
        System.out.println("------------------------------------------------------------------------------------------------");
    }


    public void validateCUHomePageButtons() {
        waitUntilPageFullyLoaded();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Contoso University Home page validations: ");
        validateButtonsExistence("See tutorial button status",getWebElement("xpath",data.get("seeTutorialButton").trim()));
        validateButtonsExistence("Download button status",getWebElement("xpath",data.get("downloadButton").trim()));
        System.out.println("------------------------------------------------------------------------------------------------");
    }



}
