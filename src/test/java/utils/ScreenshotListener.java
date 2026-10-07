package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Тест пройден: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Тест упал: " + result.getName());

        Object testInstance = result.getInstance();

        BaseTest baseTest = (BaseTest) testInstance;

        WebDriver driver = baseTest.getDriver();

        if (driver != null) {
            takeScreenshot(driver, result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Тест пропущен: " + result.getName());
    }

    private void takeScreenshot(WebDriver driver, String testName) {
        try {
            //создаем папку для тестов, если ее нет
            File screenshotDir = new File("target/screenshots/");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";

            File destFile = new File(screenshotDir, fileName);
            FileUtils.copyFile(srcFile, destFile);

            System.out.println(" Скриншот сохранён: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Не удалось сделать скриншот: " + e.getMessage());
        }
    }
}