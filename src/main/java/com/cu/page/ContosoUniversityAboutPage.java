package com.cu.page;

import com.cu.cukes.TestBase;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

import static com.cu.util.DriverUtils.*;
import static com.cu.util.SystemUtils.printComment;
import static com.cu.util.UIUtils.*;


public class ContosoUniversityAboutPage extends TestBase {

    private static final String CU_ABOUT_HEADER;

    static {
        CU_ABOUT_HEADER = "Student Body Statistics";
    }

    public ContosoUniversityAboutPage() throws IOException {
        // TODO document why this constructor is empty
    }

    public InputStream inputDataStream() throws IOException {
         return Files.newInputStream(new File("src/main/java/com/cu/PageObjects/CUAboutPageObjects.yml").toPath());
    }

    Yaml yaml = new Yaml();
    Map<String, String> data = yaml.load(inputDataStream());

    public void validateCUAboutPageContent() {
        waitUntilPageFullyLoaded();
        printComment("------------------------------------------------------------------------------------------------");
        printComment("Contoso University About page validations: ");
        validateText("Validate Header Text",CU_ABOUT_HEADER, getWebElement("tag",data.get("headerText").trim()));
        isElementExists("Student Body Statistics", getWebElement("xpath",data.get("statsTable").trim()));
        System.out.println("\n"+getWebElement("xpath", data.get("statsTable").trim()).getText());
        printComment("------------------------------------------------------------------------------------------------");
    }

}
