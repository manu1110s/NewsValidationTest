Feature: News Validity Check

  Scenario: Consider first article from theGuardianDotCom website , and verify if article exists in Google search
    Given User is on The Guardian Home Page
    And User Navigates News Section
    And User Considers first Article Headline
    And User Navigates to Google Home Page
    When User search for Article in Google
    Then User sees article in Search results


  Scenario: Consider first article from theGuardianDotCom website , and verify if article is valid in Google search
    Given User is on The Guardian Home Page
    And User Navigates News Section
    And User Considers first Article Headline
    And User Navigates to Google Home Page
    When User search for Article in Google
    Then User checks for Validity of Article

