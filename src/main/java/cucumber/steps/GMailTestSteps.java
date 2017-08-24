package cucumber.steps;

import app.business_objects.Mail;
import app.business_objects.User;
import app.pages.*;
import core.driver.singleton.WebDriverSingleton;
import core.service.GlobalProperties;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

/**
 * Created by Dina_Abdykasheva on 8/21/2017.
 */
public class GMailTestSteps {
    public DraftsFolderPage writeMail, openDraftFolder;
    public WriteMailPage openSavedDraft;
    public SentFolderPage sendMail;
    public LoginToGMailPage exitGMail;
    public AccountPage accountPage;
    public Mail mail;

    @Given("^user navigates to GMail start page$")
    public void navigateToStartPage() {
        WebDriverSingleton.getWebDriverInstance().get(GlobalProperties.URL);
    }

    @When("^user enters (.*) and (.*)$")
    public void enterUserLoginAndPassword(String username, String password) {
        accountPage = new LoginToGMailPage().loginToGMail(new User(username, password));

    }

    @Then("^GMail account page should be displayed$")
    public void verifyGMailAccountPageIsDisplayed() {
        boolean isAccountIconPresent = accountPage.isAccountIconPresent();
        Assert.assertTrue(isAccountIconPresent, "User isn't logged in");
    }

    @When("^user fills in (.*), (.*), (.*) fields of mail and save mail to draft$")
    public void fillInMailFields(String recipient, String subject, String body) {
        mail = new Mail(recipient, subject, body);
        writeMail = accountPage.clickWriteMailButton().writeMailAndSaveToDraft(mail);
    }

    @Then("^mail should appear in drafts folder$")
    public void verifyMailSavedInDrafts() {
        boolean isDraftMailSaved = writeMail.isDraftMailDisplayed(mail);
        Assert.assertTrue(isDraftMailSaved, "mentoring task");
    }

    @And("^user opens draft mail$")
    public void openDraftMail() {
        openSavedDraft = new DraftsFolderPage().openDraftMail();
    }

    @Then("^recipient, subject and body fields should contain valid values: (.*), (.*) and (.*)$")
    public void verifyRecipientFieldIsValid(String recipient, String subject, String body) {
        String receiver = openSavedDraft.getReceiver();
        String mailSubject = new WriteMailPage().getSubject();
        String mailBody = new WriteMailPage().getBody();
        Assert.assertEquals(recipient, receiver, "Receiver isn't valid");
        Assert.assertEquals(subject, mailSubject, "Subject isn't valid");
        Assert.assertEquals(body, mailBody, "Body isn't valid");
    }

    @When("^user sends mail$")
    public void sendMail() {
        sendMail = new WriteMailPage().sendMail().openSentMail();
    }

    @Then("^mail should be sent$")
    public void verifyMailIsSent() {
        boolean isMailSent = sendMail.isMailSent(mail);
        Assert.assertTrue(isMailSent, "Mail wasn't sent");
    }

    @And("^mail should be deleted from drafts$")
    public void verifyMailIsDeletedFromDrafts() {
        openDraftFolder = accountPage.openDrafts();
        boolean isMailDeletedFromDrafts = openDraftFolder.isDraftMailDisplayed(mail);
        Assert.assertFalse(isMailDeletedFromDrafts, "Mail isn't deleted from drafts");
    }

    @When("^user clicks sign out$")
    public void clickSignOut() {
        exitGMail = accountPage.exitGMail();
    }

    @Then("^user should be signed out$")
    public void verifyUserIsSignedOut() {
        boolean isUserLoggedOff = exitGMail.isUserLoggedOff();
        Assert.assertTrue(isUserLoggedOff, "User wasn't logged off");
    }
}
