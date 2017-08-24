Feature: GMail
As a GMail user
I want to create mail, save mail as draft and sent mail

Background:
Given user navigates to GMail start page

Scenario Outline: GMail test
When user enters <username> and <password>
Then  GMail account page should be displayed
When user fills in <recipient>, <subject>, <body> fields of mail and save mail to draft
Then mail should appear in drafts folder
And user opens draft mail
Then recipient, subject and body fields should contain valid values: <recipient>, <subject> and <body>
When user sends mail
Then mail should be sent
And mail should be deleted from drafts
When user clicks sign out
Then user should be signed out

Examples:
|username        |password  |recipient               |subject       |body     |
|test.da.10062017|testtest01|dina_abdykasheva@mail.ru|mentoring task|body text|
