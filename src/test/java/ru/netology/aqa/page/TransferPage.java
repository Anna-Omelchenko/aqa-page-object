package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.aqa.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement depositToCardHeader = $(byText("Пополнение карты"));
    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement fromInput = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");

    private DataHelper.Card destinationCard;

    public TransferPage(DataHelper.Card destinationCard) {
        this.destinationCard = destinationCard;
        depositToCardHeader.shouldBe(visible);
        $("[data-test-id=to] input").shouldBe(disabled).shouldHave(value(destinationCard.getMaskedNumber()));
    }

    public DashboardPage transferAmount(DataHelper.Card fromCard, int amount) {
        clearInputElement(amountInput);
        amountInput.setValue(String.valueOf(amount));
        clearInputElement(fromInput);
        fromInput.setValue(fromCard.getNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void transferToSameCard(int amount) {
        amountInput.setValue(String.valueOf(amount));
        fromInput.setValue(destinationCard.getNumber());
        transferButton.click();
    }

    public void shouldDisplayErrorNotification() {
        errorNotification.shouldBe(visible);
        depositToCardHeader.shouldBe(visible);
    }

    private static void clearInputElement(SelenideElement input) {
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
    }
}
