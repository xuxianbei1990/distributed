package calculation.chapter.practise;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2022/8/19
 * Time: 18:11
 * Version:V1.0
 */
public class RMB {


    public static void main(String[] args) throws IOException {
// selenium
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://www.pbc.gov.cn/zhengcehuobisi/125207/125213/125431/index.html");
        driver.getTitle();
        driver.manage().window().maximize();
        List<WebElement> list = driver.findElements(By.tagName("a"));
        list.forEach(webElement -> {
            if (judge(webElement.getText())) {
                System.out.println(webElement.getText() + ":" + webElement.getAttribute("href"));
            }
        });
        driver.quit();
    }

    private static boolean judge(String text) {
        if (text.length() > "公开市场业务交易公告 [2022]第".length()) {
            if (text.startsWith("公开市场业务交易公告 [2022]第")) {
                return true;
            }
        }
        return false;
    }
}
