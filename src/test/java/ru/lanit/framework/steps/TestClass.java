package ru.lanit.framework.steps;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.lanit.framework.webdriver.WebDriverManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestClass extends BasePageSteps {
    private final Map<String, String> loginPasswordData = new HashMap<>();

    @BeforeClass
    private void checkAuth() {
        loginPasswordData.put("gromovalex", "1234qwerasdf");
        loginPasswordData.put("sdfkjhyfejrhsdfksdfh", "sdjfhksdjfhksdjfhyrgberjhbdf");
        loginPasswordData.put("admin", "простокакойтопароль");
        loginPasswordData.put("" , "");
        loginPasswordData.put("", "abyvalg");
        loginPasswordData.put("abyrvalg", "");
    }

    @AfterTest
    void clearHashMap() {
        loginPasswordData.clear();
    }

    private boolean isElementPresent(String locator_string){
        try {
            WebDriverManager.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return WebDriverManager.getDriver().findElement(By.xpath(locator_string)).isDisplayed();
        } catch (NoSuchElementException e){
            return false;
        } finally {
            WebDriverManager.getDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        }
    }


    @Test
    public void testGoogle() throws InterruptedException, UnhandledAlertException {
        WebDriver webDriver = WebDriverManager.getDriver();
        System.out.println("Открыт браузер " + ((ChromeDriver) webDriver).getSessionId());
        webDriver.get("https://dev.n7lanit.ru/");
        WebElement webElement = webDriver.findElement(By.linkText("Категории"));
        webElement.click();
        System.out.println("Нашли и клинкули ссылку 'Категории'");
        Thread.sleep(1000);
        webElement = webDriver.findElement(By.linkText("Пользователи"));
        webElement.click();
        System.out.println("Нашли и клинкули ссылку 'Пользователи'");
        Thread.sleep(1000);
        webElement = webDriver.findElement(By.xpath("//div[@class='navbar-search dropdown']"));
        webElement.click();
        Thread.sleep(1000);
        webElement = webDriver.findElement(By.xpath("//*[@aria-controls='dropdown-menu dropdown-search-results']"));
        webElement.click();
        webElement.sendKeys("gromovalex");
        Thread.sleep(1000);
        webElement = webDriver.findElement(By.xpath("//*[@class='dropdown-search-user']"));
        webElement.click();
        Assert.assertTrue(webDriver.findElement(By.xpath("//abbr[@title='Присоединился 26 марта 2020 г., 11:35']")).isDisplayed());
        System.out.println("Нашли пользователя 'gromovalex'");
        Thread.sleep(1000);

//        доп задание

        for (Map.Entry<String, String> entry : loginPasswordData.entrySet()) {
            webElement = webDriver.findElement(By.xpath("//button[@class='btn navbar-btn btn-default btn-sign-in']"));
            webElement.click();
            Thread.sleep(1000);
            String username =  entry.getKey();
            String password = entry.getValue();
            WebElement webElementLogin = webDriver.findElement(By.xpath("//input[@id='id_username']"));
            webElementLogin.clear();
            webElementLogin.click();
            webElementLogin.sendKeys(username.toString());
            WebElement webElementPassword = webDriver.findElement(By.xpath("//input[@id='id_password']"));
            webElementPassword.clear();
            webElementPassword.click();
            webElementPassword.sendKeys(password.toString());
            webElementLogin.sendKeys(Keys.ENTER);
            Thread.sleep(1000);

            if (isElementPresent("//div[@class='alerts-snackbar in']")) {
                System.out.println("Неверные параметры. Авторизоваться нет возможности! Проверка пройдена!"); // там 2 вида вывода, или поля пустые или неверный логин пароль. причем это все выведено через 1 элемент, не разделить их
                Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='alerts-snackbar in']")).isDisplayed());
                Thread.sleep(4000); // закрывает кнопку красная надпись, попасть по элементу не может если меньше сделать. так она сама пропадет
                webElementLogin = webDriver.findElement(By.xpath("//button[@class='close' and @aria-label='Закрыть']"));
                webElementLogin.click();
            } else {
                System.out.println("Пользователь "+username+" авторизован! Проверка пройдена!");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class='user-avatar' and @width='64']")));
                Assert.assertTrue(webDriver.findElement(By.xpath("//img[@class='user-avatar' and @width='64']")).isDisplayed());
                webElement = webDriver.findElement(By.xpath("//img[@class='user-avatar' and @width='64']"));
                webElement.click();
                Thread.sleep(100);
                webElement = webDriver.findElement(By.xpath("//button[@class='btn btn-default btn-block']"));
                try {
                    webElement.click();
                    Thread.sleep(1000);
                    webDriver.switchTo().alert().accept();
                } catch (org.openqa.selenium.UnhandledAlertException e) {
                }
            }
        }

        webDriver.quit();

    }
}
