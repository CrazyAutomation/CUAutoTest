@Reg
Feature: Contoso University Departments page
  As a user
  I validate the content of Contoso University Departments page


  Scenario:1 - Validate Contoso University Departments page
    Given I launch contoso university application
    When I click on "Departments" menu option
    Then I validate contoso university "Departments" page
