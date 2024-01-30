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


public class ContosoUniversityStudentsPage extends TestBase {


    private static final String CU_STUDENTS_HEADER = "Students";

    InputStream inputStream;

    {
        try {
            inputStream = Files.newInputStream(new File("src/main/java/com/cu/PageObjects/CUStudentsPageObjects.yml").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Yaml yaml = new Yaml();
    Map<String, String> data = yaml.load(inputStream);

    public void validateCUStudentsPageContent() {
        waitUntilPageFullyLoaded();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Contoso University Students page validations: ");
        validateText("Validate Header Text", CU_STUDENTS_HEADER, getWebElement("tag", data.get("headerText").trim()));
        Assert.assertEquals("Validate Create new link Text", "Create New", getWebElement("xpath", data.get("createNew").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("createNew").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("studentDetailsSearch").trim()).getText());
        Assert.assertTrue("Validate Find by search details", getWebElement("xpath", data.get("studentDetailsSearch").trim()).getText().contains("Find by name:"));
        System.out.println(getWebElement("xpath", data.get("studentsTable").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("editButton").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("detailsButton").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("deleteButton").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("paginationText").trim()).getText());
        System.out.println(getWebElement("xpath", data.get("paginationDetails").trim()).getText());
        validateText("Validate pages Text","1",getWebElement("xpath",data.get("paginationDetails").trim()));
        System.out.println("------------------------------------------------------------------------------------------------");
    }

    //    createNew: ".//*[@href = '/Student/Create']"
//    studentDetailsSearch: ".//*[@action = '/Student']"
//    searchTextBox: "SearchString"
//    searchButton: ".//*[@type = 'submit']"
//    studentsTable: ".//table/tbody"
//    editButton: ".//a[@href='/Student/Edit/1']"
//    detailsButton: ".//a[@href='/Student/Details/1']"
//    DeleteButton: ".//a[@href='/Student/Delete/1']"
//    paginationText: "/html/body/div[2]/text()"
//    paginationDetails: ".//*[@class='pagination-container']/ul[1]/li/a"
//
    public void validateCUStudentsPageButtons() {
        waitUntilPageFullyLoaded();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Contoso University Home page validations: ");
        validateButtonsExistence("See tutorial button status", getWebElement("xpath", data.get("createNew").trim()));
        isElementExists("See tutorial button status", getWebElement("xpath", data.get("createNew").trim()));
        validateButtonsExistence("See tutorial button status", getWebElement("id", data.get("searchTextBox").trim()));
        validateButtonsExistence("See tutorial button status", getWebElement("xpath", data.get("searchButton").trim()));
        validateButtonsExistence("See tutorial button status", getWebElement("xpath", data.get("seeTutorialButton").trim()));
        validateButtonsExistence("See tutorial button status", getWebElement("xpath", data.get("seeTutorialButton").trim()));
//        validateButtonsExistence("Download button status", getWebElement("xpath", data.get("downloadButton").trim()));
        System.out.println("------------------------------------------------------------------------------------------------");
    }


}
