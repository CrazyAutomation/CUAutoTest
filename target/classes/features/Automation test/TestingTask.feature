@Reg
Feature: Contoso University Students page
  As a user
  I validate the content of Contoso University Students page


  Scenario:1 - Validate Contoso University Students page
    Given I launch contoso university application
    When I click on "Students" menu option
    Then I validate contoso university "Students" page
