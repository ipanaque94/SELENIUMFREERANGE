Feature: Navigation
  To see the subpages
  Without logging in
  I can click the navigation bar links

  Background: I am on the Free Range Testers web without logging in.
    Given I navigate to www.freerangetesters.com


  @Courses
  Scenario Outline: I can access the subpages through the navigation bar
    When I go to "<section>" using the navigation bar
    Then I should be on the "<section>" page

    Examples:
      | section       |
      | Cursos        |
      | Recursos      |
      | Mentorías     |
      | Suscripciones |
      | Blog          |


  @Plans
  Scenario: Courses are presented correctly to potential customers
    When I go to "Cursos" using the navigation bar
    And The user selects Introduction to testing course
    When The user selects Comprar ahora
    Then I can validate the options in the checkout page



#Scenario: Users can select a plan wen signing up
#  When I select Elegir Plan
#  Then I can validate the options in the checkout page


