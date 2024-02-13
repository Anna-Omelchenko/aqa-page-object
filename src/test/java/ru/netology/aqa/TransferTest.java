package ru.netology.aqa;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {

    @Test
    void shouldAllowToTransfer() {
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

    @Test
    void shouldAllowZeroBalance() {
        open("http://localhost:9999");
        var vasyaCreds = DataHelper.getVasyaCreds();
        var loginPage = new LoginPage();
        var verifyCodePage = loginPage.login(vasyaCreds);
        var dashboardPage = verifyCodePage.verifyCode(DataHelper.getVerificationCodeFor(vasyaCreds));
        int card0001InitialBalance = dashboardPage.getCard0001Balance();
        int card0002InitialBalance = dashboardPage.getCard0002Balance();
        var transferPage = dashboardPage.depositToCard0002();
        dashboardPage = transferPage.transferAmount(DataHelper.getCard0001(), card0001InitialBalance);
        assertEquals(0, dashboardPage.getCard0001Balance());
        assertEquals(card0002InitialBalance + card0001InitialBalance, dashboardPage.getCard0002Balance());
        // Make another transfer to roll back balances to initial state
        transferPage = dashboardPage.depositToCard0001();
        dashboardPage = transferPage.transferAmount(DataHelper.getCard0002(), card0001InitialBalance);
        assertEquals(card0001InitialBalance, dashboardPage.getCard0001Balance());
        assertEquals(card0002InitialBalance, dashboardPage.getCard0002Balance());
    }

    @Disabled
    @Test
    void shouldNotAllowToTransferFromSameCard() {
        open("http://localhost:9999");
        var vasyaCreds = DataHelper.getVasyaCreds();
        var loginPage = new LoginPage();
        var verifyCodePage = loginPage.login(vasyaCreds);
        var dashboardPage = verifyCodePage.verifyCode(DataHelper.getVerificationCodeFor(vasyaCreds));
        var transferPage = dashboardPage.depositToCard0001();
        transferPage.transferToSameCard(1000);
        transferPage.shouldDisplayErrorNotification();
    }

    @Disabled
    @Test
    void shouldNotAllowNegativeBalance() {
        open("http://localhost:9999");
        var vasyaCreds = DataHelper.getVasyaCreds();
        var loginPage = new LoginPage();
        var verifyCodePage = loginPage.login(vasyaCreds);
        var dashboardPage = verifyCodePage.verifyCode(DataHelper.getVerificationCodeFor(vasyaCreds));
        int card0001InitialBalance = dashboardPage.getCard0001Balance();
        var transferPage = dashboardPage.depositToCard0002();
        transferPage.transferAmount(DataHelper.getCard0001(), card0001InitialBalance + 1);
        transferPage.shouldDisplayErrorNotification();
    }
}
