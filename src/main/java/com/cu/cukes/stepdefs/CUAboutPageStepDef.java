package com.cu.cukes.stepdefs;

import com.cu.page.ContosoUniversityAboutPage;
import com.cu.page.common.CUMenus;

public class CUAboutPageStepDef
{
  ContosoUniversityAboutPage contosoUniversityAboutPage;
  CUMenus cuMenus;

  public CUAboutPageStepDef(ContosoUniversityAboutPage contosoUniversityAboutPage, CUMenus cuMenus)
  {
    this.contosoUniversityAboutPage = contosoUniversityAboutPage;
    this.cuMenus = cuMenus;
  }

}
