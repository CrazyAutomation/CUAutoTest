package com.cu.page;

import com.cu.cukes.TestBase;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

import static com.cu.util.DriverUtils.*;
import static com.cu.util.UIUtils.*;


public class ContosoUniversityHomePage extends TestBase {


    private static final String CU_HOME_HEADER = "Contoso University";
    private static final String CU_HOME_WELCOME = "Welcome to Contoso University";
    private static final String CU_HOME_WELCOME_DETAILS = "Contoso University is a sample application that demonstrates how to use Entity Framework 6 in an ASP.NET MVC 5 web application.";
    private static final String CU_HOME_BUILD = "Build it from scratch";
    private static final String CU_HOME_BUILD_DETAILS = "You can build the application by following the steps in the tutorial series on the ASP.NET site.";
    private static final String CU_HOME_DOWNLOAD = "Download it";
    private static final String CU_HOME_DOWNLOAD_DETAILS = "You can download the completed project from the Microsoft Code Gallery.";

    InputStream inputStream;
    {
        try {
            inputStream = Files.newInputStream(new File("src/main/java/com/cu/PageObjects/CUHomePageObjects.yml").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Yaml yaml = new Yaml();
    Map<String, String> data = yaml.load(inputStream);

    public void validateCUHomePageContent() {
        waitUntilPageFullyLoaded();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Contoso University Home page validations: ");
        validateText("Validate Header Text",CU_HOME_HEADER,getWebElement("tag",data.get("headerText").trim()));
        validateText("Validate welcome Text",CU_HOME_WELCOME,getWebElement("xpath",data.get("welcomeCUText").trim()));
        validateText("Validate welcome details Text",CU_HOME_WELCOME_DETAILS,getWebElement("xpath",data.get("welcomeCUDetails").trim()));
        validateText("Validate build Text",CU_HOME_BUILD,getWebElement("xpath",data.get("buildText").trim()));
        validateText("Validate build details Text",CU_HOME_BUILD_DETAILS,getWebElement("xpath",data.get("buildDetails").trim()));
        validateText("Validate download Text",CU_HOME_DOWNLOAD,getWebElement("xpath",data.get("downloadText").trim()));
        validateText("Validate download details Text",CU_HOME_DOWNLOAD_DETAILS,getWebElement("xpath",data.get("downloadDetails").trim()));
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
