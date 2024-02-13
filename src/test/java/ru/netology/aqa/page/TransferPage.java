package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.aqa.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement fromInput = $("[data-test-id=from] input");
//    private final SelenideElement toInput = $("[data-test-id=to] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public TransferPage(DataHelper.Card destinationCard) {
        $(byText("Пополнение карты")).shouldBe(visible);
        $("[data-test-id=to] input").shouldBe(disabled).shouldHave(value(destinationCard.getMaskedNumber()));
    }

    public DashboardPage transferAmount(DataHelper.Card fromCard, int amount) {
        amountInput.setValue(String.valueOf(amount));
        fromInput.setValue(fromCard.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
}
