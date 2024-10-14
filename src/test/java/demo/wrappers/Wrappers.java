package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static void Url(WebDriver driver, String url) {
        driver.get(url);
    }

    public static void search(WebDriver driver, String keys) throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='q']"));
        searchBox.clear();
        searchBox.sendKeys(keys);
        Thread.sleep(4000);
        searchBox.submit();
        
     

    }

    public static void sortByRating(WebDriver driver, String rating) {
        WebElement rate = driver.findElement(
                By.xpath("//div[contains(text(),'" + rating + "') ]/preceding-sibling::div[@class='XqNaEv']"));
        rate.click();
    }

    public static void review(WebDriver driver) {
        List<WebElement> titles = driver.findElements(By.xpath("//a[@class='wjcEIp']"));
        List<WebElement> image = driver.findElements(By.xpath("//a[@class='VJA3rP']//img"));
        List<WebElement> reviewCounts = driver.findElements(By.xpath("//span[@class='Wphh3N']"));
        List<Integer> countList = new ArrayList<>();
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < reviewCounts.size(); i++) {
            String text = reviewCounts.get(i).getText().replaceAll("[,()]", "");
            int count = Integer.parseInt(text);
            countList.add(count);
            index.add(i);

        }
        for (int i = 0; i < index.size() - 1; i++) {
            for (int j = 0; j < index.size() - i - 1; j++) {
                if (countList.get(index.get(j)) < countList.get(index.get(j + 1))) {
                    // Swap the indices
                    int temp = index.get(j);
                    index.set(j, index.get(j + 1));
                    index.set(j + 1, temp);
                }
            }
        }
        for (int i = 0; i < 5 && i < index.size(); i++) {
            int ind = index.get(i);
            System.out.println("Title of Element:" + titles.get(ind).getText());
            System.out.println("Image Link:" + image.get(ind).getAttribute("src"));
            System.out.println("Review count:" + countList.get(ind));

        }
    }
    
}