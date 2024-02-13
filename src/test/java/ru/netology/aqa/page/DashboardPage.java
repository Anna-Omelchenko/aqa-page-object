package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.aqa.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private static final String SELECTOR_DEPOSIT_BUTTON = "[data-test-id=action-deposit]";

    private final SelenideElement card0001Element = $(byText(DataHelper.getCard0001().getMaskedNumber()));
    private final SelenideElement card0002Element = $(byText(DataHelper.getCard0002().getMaskedNumber()));
    private final SelenideElement depositToCard0001Button = card0001Element.$(SELECTOR_DEPOSIT_BUTTON);
    private final SelenideElement depositToCard0002Button = card0002Element.$(SELECTOR_DEPOSIT_BUTTON);

    public DashboardPage() {
        $("[data-test-id=dashboard]").shouldBe(visible).shouldHave(text("Личный кабинет"));
        $(byText("Ваши карты")).shouldBe(visible);
    }

    private int getCardBalance(SelenideElement cardElement) {
        String text = cardElement.getText();
        String prefix = "баланс: ", postfix = " р.";
        String balance = text.substring(text.indexOf(prefix) + prefix.length(), text.indexOf(postfix));
        return Integer.parseInt(balance);
    }

    public int getCard0001Balance() {
        return getCardBalance(card0001Element);
    }

    public int getCard0002Balance() {
        return getCardBalance(card0002Element);
    }

    public TransferPage depositToCard0001() {
        depositToCard0001Button.click();
        return new TransferPage(DataHelper.getCard0001());
    }

    public TransferPage depositToCard0002() {
        depositToCard0002Button.click();
        return new TransferPage(DataHelper.getCard0002());
    }

}
