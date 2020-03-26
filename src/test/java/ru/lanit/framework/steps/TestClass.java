package ru.lanit.framework.steps;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.lanit.framework.webdriver.WebDriverManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestClass {
    private final Map<String, String> loginPasswordData = new HashMap<>();

    @BeforeClass
    private void checkAuth() {
        loginPasswordData.put("gromovalex", "1234qwerasdf"); // будем проверять только невалидное. валид потом отдельно проверим
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

    @Test
    public void testGoogle() throws InterruptedException {
        WebDriver webDriver = WebDriverManager.getDriver();
        System.out.println("Открыт браузер " + ((ChromeDriver) webDriver).getSessionId());
        webDriver.get("https://dev.n7lanit.ru/");
        WebElement webElement = webDriver.findElement(By.linkText("Категории"));
//        webElement.click();
//        System.out.println("Нашли и клинкули ссылку 'Категории'");
//        Thread.sleep(1000);
//        webElement = webDriver.findElement(By.linkText("Пользователи"));
//        webElement.click();
//        System.out.println("Нашли и клинкули ссылку 'Пользователи'");
//        Thread.sleep(1000);
//        webElement = webDriver.findElement(By.xpath("//div[@class='navbar-search dropdown']"));
//        webElement.click();
//        Thread.sleep(1000);
//        webElement = webDriver.findElement(By.xpath("//*[@aria-controls='dropdown-menu dropdown-search-results']"));
//        webElement.click();
//        webElement.sendKeys("gromovalex");
//        Thread.sleep(1000);
//        webElement = webDriver.findElement(By.xpath("//*[@class='dropdown-search-user']"));
//        webElement.click();
//        Assert.assertTrue(webDriver.findElement(By.xpath("//abbr[@title='Присоединился 26 марта 2020 г., 11:35']")).isDisplayed(), "Присоединился");
//        System.out.println("Нашли пользователя 'gromovalex'");
//        Thread.sleep(1000);

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
            webElement.findElement(By.xpath("//div[@class='alerts-snackbar in']"));
            if (webElement.isDisplayed()) {
                System.out.println("Неверные параметры. Авторизоваться нет возможности! Проверка пройдена!");
                Thread.sleep(4000); // закрывает кнопку красная надпись, попасть по элементу не может если меньше сделать. так она сама пропадет
                webElementLogin = webDriver.findElement(By.xpath("//button[@class='close' and @aria-label='Закрыть']"));
                webElementLogin.click();
            } else {
                System.out.println("Пользователь авторизован! Проверка пройдена!");
            }
        }

//        WebElement webElement1 = webDriver.findElement(By.xpath("//button[@class='btn navbar-btn btn-default btn-sign-in']"));
//        webElement1.click();
//        Thread.sleep(1000);
//        WebElement webElementLogin1 = webDriver.findElement(By.xpath("//input[@id='id_username']"));
//        webElementLogin1.clear();
//        webElementLogin1.click();
//        webElementLogin1.sendKeys("gromovalex");
//        WebElement webElementPassword1 = webDriver.findElement(By.xpath("//input[@id='id_password']"));
//        webElementPassword1.clear();
//        webElementPassword1.click();
//        webElementPassword1.sendKeys("1234qwerasdf");
//        webElementLogin1.sendKeys(Keys.ENTER);
//        Thread.sleep(1000);
//        Assert.assertTrue(webDriver.findElement(By.xpath("//a[@class='dropdown-toggle' and @data-toggle='dropdown']")).isDisplayed());
//        System.out.println("Пользователь авторизован! Тест пройден успешно");
//        webDriver.quit();

    }
}
