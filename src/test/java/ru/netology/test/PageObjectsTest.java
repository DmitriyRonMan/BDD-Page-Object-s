package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;


public class PageObjectsTest {

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int sum = 1500;
        var balanceForFirstCard = dashboardPage.getCardBalance(firstCard) - sum;
        var balanceForSecondCard = dashboardPage.getCardBalance(secondCard) + sum;
        var moneytransferForCard = dashboardPage.choosCardForTransfer(secondCard);
        dashboardPage = moneytransferForCard.makeMoneyTransfer(Integer.parseInt(String.valueOf(sum)), firstCard);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(balanceForFirstCard, actualBalanceForFirstCard);
        assertEquals(balanceForSecondCard, actualBalanceForSecondCard);

    }


}
