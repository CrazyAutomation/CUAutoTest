package com.cu.util;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.cu.cukes.TestBase;

public class Hooks extends TestBase
{

    @Before(order = 1)
    public void before(Scenario scenario)
    {
        System.out.println("Starting - " + scenario.getName());
        if (properties == null)
        {
            setupTestContext();
        }

    }


    @After
    public void after(Scenario scenario) {
        System.out.println(scenario.getName() + " Status - " + scenario.getStatus());
        DriverFactory.getDriver().manage().deleteAllCookies();
    }



}
