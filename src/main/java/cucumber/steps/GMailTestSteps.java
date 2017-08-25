package cucumber.steps;

import app.business_objects.Mail;
import app.business_objects.User;
import app.pages.*;
import core.driver.singleton.WebDriverSingleton;
import core.service.GlobalProperties;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

/**
 * Created by Dina_Abdykasheva on 8/21/2017.
 */
public class GMailTestSteps {

    @Given("^user navigates to GMail start page$")
    public void navigateToStartPage() {
        WebDriverSingleton.getWebDriverInstance().get(GlobalProperties.URL);
    }

    @When("^user enters (.*) and (.*)$")
    public void enterUserLoginAndPassword(String username, String password) {
        AccountPage accountPage = new LoginToGMailPage().loginToGMail(new User(username, password));

    }

    @Then("^GMail account page should be displayed$")
    public void verifyGMailAccountPageIsDisplayed() {
        boolean isAccountIconPresent = new AccountPage().isAccountIconPresent();
        Assert.assertTrue(isAccountIconPresent, "User isn't logged in");
    }

    @When("^user fills in (.*), (.*), (.*) fields of mail and save mail to draft$")
    public void fillInMailFields(String recipient, String subject, String body) {
        Mail mail = new Mail(recipient, subject, body);
        DraftsFolderPage writeMail = new AccountPage().clickWriteMailButton().writeMailAndSaveToDraft(mail);
    }



    @Then("^mail with (.*), (.*), (.*) fields should appear in drafts folder$")
    public void verifyMailSavedInDrafts(String recipient, String subject, String body) {
        Mail mail = new Mail(recipient, subject, body);
        boolean isDraftMailSaved = new DraftsFolderPage().isDraftMailDisplayed(mail);
        Assert.assertTrue(isDraftMailSaved, "mentoring task");
    }

    @When("^user opens draft mail$")
    public void openDraftMail() {
        WriteMailPage openSavedDraft = new DraftsFolderPage().openDraftMail();
    }

    @Then("^recipient, subject and body fields should contain valid values: (.*), (.*) and (.*)$")
    public void verifyRecipientFieldIsValid(String recipient, String subject, String body) {
        String receiver = new WriteMailPage().getReceiver();
        String mailSubject = new WriteMailPage().getSubject();
        String mailBody = new WriteMailPage().getBody();
        Assert.assertEquals(recipient, receiver, "Receiver isn't valid");
        Assert.assertEquals(subject, mailSubject, "Subject isn't valid");
        Assert.assertEquals(body, mailBody, "Body isn't valid");
    }

    @When("^user sends mail$")
    public void sendMail() {
        SentFolderPage sendMail = new WriteMailPage().sendMail().openSentMail();
    }

    @Then("^mail with (.*), (.*), (.*) fields should be sent$")
    public void verifyMailIsSent(String recipient, String subject, String body) {
        Mail mail = new Mail(recipient, subject, body);
        boolean isMailSent = new SentFolderPage().isMailSent(mail);
        Assert.assertTrue(isMailSent, "Mail wasn't sent");
    }

    @Then("^mail with (.*), (.*), (.*) fields should be deleted from drafts$")
    public void verifyMailIsDeletedFromDrafts(String recipient, String subject, String body) {
        Mail mail = new Mail(recipient, subject, body);
        DraftsFolderPage openDraftFolder = new AccountPage().openDrafts();
        boolean isMailDeletedFromDrafts = openDraftFolder.isDraftMailDisplayed(mail);
        Assert.assertFalse(isMailDeletedFromDrafts, "Mail isn't deleted from drafts");
    }

    @When("^user clicks sign out$")
    public void clickSignOut() {
        LoginToGMailPage exitGMail = new AccountPage().exitGMail();
    }

    @Then("^user should be signed out$")
    public void verifyUserIsSignedOut() {
        boolean isUserLoggedOff = new LoginToGMailPage().isUserLoggedOff();
        Assert.assertTrue(isUserLoggedOff, "User wasn't logged off");
    }
}
