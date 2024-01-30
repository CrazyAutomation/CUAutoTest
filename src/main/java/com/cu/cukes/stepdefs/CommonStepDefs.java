package com.cu.cukes.stepdefs;

import com.cu.page.common.CUCommons;
import com.cu.page.common.CUMenus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.cu.cukes.TestBase.*;

public class CommonStepDefs {
    CUMenus cuMenus;
    CUCommons cuCommons;

    public CommonStepDefs(CUMenus cuMenus, CUCommons cuCommons) {
        this.cuMenus = cuMenus;
        this.cuCommons = cuCommons;
    }

    @Given("^I launch contoso university application$")
    public void iLaunchContosoUniversityApplication() {
        navigateToCU();
    }

    @When("I click on {string} menu option")
    public void iClickOnMenuOption(String option) {
        cuMenus.clickMenu(option);
    }

   @Then("I should see the {string} page title")
    public void iShouldSeeThePageTitle(String page) {
        cuMenus.validateTitles(page);
    }

    @When("I validate contoso university {string} page")
    public void iValidateContosoUniversityPage(String page) {
        cuCommons.validatePageContent(page);
    }

}
