package ru.netology.aqa;

import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {

    @Test
    void testSuccess() {
        open("http://localhost:9999");
        var vasyaCreds = DataHelper.getVasyaCreds();
        var loginPage = new LoginPage();
        var verifyCodePage = loginPage.login(vasyaCreds);
        var dashboardPage = verifyCodePage.verifyCode(DataHelper.getVerificationCodeFor(vasyaCreds));
        // Get initial balances
        int card0001InitialBalance = dashboardPage.getCard0001Balance();
        int card0002InitialBalance = dashboardPage.getCard0002Balance();
        // Transfer 200 RUB from card0001 to card0002
        int amount = 200;
        var transferPage = dashboardPage.depositToCard0002();
        dashboardPage = transferPage.transferAmount(DataHelper.getCard0001(), amount);
        assertEquals(card0001InitialBalance - amount, dashboardPage.getCard0001Balance());
        assertEquals(card0002InitialBalance + amount, dashboardPage.getCard0002Balance());
    }

}
