package com.cu.cukes.stepdefs;
import com.cu.page.common.CUMenus;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.cu.page.ContosoUniversityHomePage;


public class CUHomePageStepDef
{
  ContosoUniversityHomePage contosoUniversityHomePage;
  CUMenus cuMenus;

  public CUHomePageStepDef(ContosoUniversityHomePage contosoUniversityHomePage, CUMenus cuMenus)
  {
    this.contosoUniversityHomePage = contosoUniversityHomePage;
    this.cuMenus = cuMenus;
  }

  @When("I validate contoso university home page")
  public void iValidateContosoUniversityHomePage() {
    cuMenus.validateCUMenus();
    contosoUniversityHomePage.validateCUHomePageContent();
  }

  @Then("I am also validate contoso university home page buttons")
  public void iAmAlsoValidateContosoUniversityHomePageButtons() {
    contosoUniversityHomePage.validateCUHomePageButtons();
  }


  @Then("I should be on contoso university home page")
  public void iShouldBeOnContosoUniversityHomePage() {
    contosoUniversityHomePage.validateCUHomePageContent();
  }

}
