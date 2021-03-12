package com.yjg.appui.page;
import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.keyworddriver.ExcelUtil1;
import com.yjg.appui.util.GetByLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

public class VvLonginPage extends BasePage {
    public static String path = System.getProperty("user.dir");
    /***
     * 全局声明： 1、driver对象是公共类AndroidDriverBase类的对象 2、this对象是父类BasePage类的对象
     * */
    public static Logger logger = Logger.getLogger(LoginPage.class);

    public VvLonginPage(AndroidDriverBase driver) {// 需要默认添加一个构造方法
        super(driver);
        // TODO Auto-generated constructor stub
    }

    /**
     * @description 等待登录页面元素加载
     *            selenium api封装引用对象
     * @param timeOut
     *            等待元素超时的时间
     * */
    public void waitLoginPageLoad(int timeOut) {
        this.pause(1000);
        // 而登录的用户名、密码输入框以及登录按钮都在body frame下的navbar frame下,
        logger.info("开始检查登录页面元素");
        // 账号输入框
        this.waitForElementToLoad(timeOut,
                By.id("com.gatewang.yjg:id/loginAccountEt"));
        // 密码输入框
        this.waitForElementToLoad(timeOut, By.id("com.gatewang.yjg:id/pwdEt"));
        // 点击登录
        this.waitForElementToLoad(timeOut,
                By.id("com.gatewang.yjg:id/quick_login_btn"));
        logger.info("检查登录页面元素完毕");
    }

    /**
     * 点击我的页面，执行登录操作
     *
     * @throws Exception
     *
     * */

    public void login(){
        driver.findElement(By.name("我的")).click();
    }
    public void login(String username, String password,String caseNumber) throws Exception {// HomePage登录后调整到首页
        //手机登陆
        //点击本机号码登陆 android.widget.ImageView

        // 点击登录
        this.click(this.reAndroidBy("name",
                "本机号码一键登录"));

//        // 点击我的
//        System.out.printf("========login================Thead Id :%s%n", Thread
//                .currentThread().getId());
//
//        this.click(GetByLocator.getLocator("loginOrReg"));
//        // 输入账号和密码，点击登录
//        LonginInput(username, password,caseNumber);
    }

    /**
     * 登录后页面检查
     *
     * @throws Exception
     * */
    // mCurrentFocus=Window{138c5893 u0
    // com.gatewang.yjg/com.gatewang.yjg.module.common.YJGMainActivity}

    public boolean loginCheck() throws Exception {
        boolean flag = false;// 默认是没有登录框

        if (driver.isExitCurActivity(".ui.activity.PwdForLoginActivity") == false) {// 检查是否调整到首页
            logger.info("当前activity是:" + driver.getCurActivity());
            flag = true;//存在提示框登录
            return flag;// 没有成功
        }else{
            // TODO: handle exception
//				logger.info("没有登录成功");
            // toastCheck();
            return flag;// 登录成功
        }
    }

    /**
     * 登录框输入账号密码，并且点击登录
     *
     * @throws Exception
     * */
    public void LonginInput(String username, String password,String caseNumber) throws Exception {
        // 判断是否存在登录框的activity mCurrentFocus=Window{28b1a11 u0
        // com.gatewang.yjg/com.gatewang.yjg.ui.activity.PwdForLoginActivity}
        System.out.printf("========logininput================Thead Id :%s%n",
                Thread.currentThread().getId());

        if (driver.isExitCurActivity(".ui.activity.PwdForLoginActivity") == true) {
            logger.info("还没有任何账号登录，输入信息，登录中。。。");
            // 检查登录框元素是否存在
            waitLoginPageLoad(10);
            // 输入账号
            // this.sendkeys(this.reAndroidBy("id",
            // "com.gatewang.yjg:id/loginAccountEt"), username);
            this.sendkeys("id", "com.gatewang.yjg:id/loginAccountEt", username);// 尝试新的输入方法
            // 输入密码
            this.sendkeys("id", "com.gatewang.yjg:id/pwdEt", password);
            // this.sendkeys(this.reAndroidBy("id",
            // "com.gatewang.yjg:id/pwdEt"), password);
            // 点击登录
            this.click(this.reAndroidBy("id",
                    "com.gatewang.yjg:id/quick_login_btn"));
            driver.sleep(1000);
            if (loginCheck() == true) {// 不存在提示框为false
                logger.info("首页状态是：" + loginCheck());
                ExcelUtil1 excel=new ExcelUtil1("configs/test.xlsx", "Sheet1");
                excel.setCellData(Integer.valueOf(caseNumber), 6, "测试成功");
                System.out.println(caseNumber+"-测试成功");
                Assert.fail("Failed to login  [" + "没有登录成功" + "]");//失败了就直接调用监听类，后面方法都不执行

            } else {
                logger.info("首页状态是" + loginCheck() + "-请重新登录");
                this.takescreen("登录失败");
//                this.toastCheck();// 登录失败检查是否存在异常提示框
                //点击取消
                this.click(this
                        .reAndroidBy("id", "com.gatewang.yjg:id/left_ll"));
                ExcelUtil1 excel=new ExcelUtil1("configs/test.xlsx", "Sheet1");
                excel.setCellData(Integer.valueOf(caseNumber), 6, "测试失败");
                System.out.println(caseNumber+"-测试失败");
                Assert.fail("Failed to login  [" + "没有登录成功" + "]");//失败了就直接调用监听类，后面方法都不执行

            }

        } else {
            logger.info("账号已经登录");
        }
    }

    /**
     * 重新登录
     *
     * @throws Exception
     * */
    public void reToLogin(String username, String password,String caseNumber) throws Exception {
        System.out.printf("========================Thead Id :%s%n", Thread
                .currentThread().getId());
        // 注销登录
        logout();
        // 输入账号和密码，点击登录
        LonginInput(username, password,caseNumber);
        // 再次登录
        login(username, password,caseNumber);
    }

    /**
     * 登出操作
     * */
    public void logout() throws InterruptedException {
        // 第一个页面是从首页开始
        // 点击我的
        System.out.printf("========logout================Thead Id :%s%n",
                Thread.currentThread().getId());

        this.click(GetByLocator.getLocator("loginOrReg"));
        // 判断是否弹出登录提示框
        if (driver.isExitCurActivity(".activity.PwdForLoginActivity") == false) {
            logger.info("已经存在登录中账号");
            // 鼠标向上滑动
            driver.swipe("up", 500);
            // //判断是否存在注销登录按钮
            // isDisplayed=isElementExist判断元素是否存在，是否显示
            if (this.isElementExist(this.reAndroidElement("name", "注销登录")) == true) {
                // 点击注销按钮
                this.click(By.name("注销登录"));
                // this.click(GetByLocator.getLocator("loginOut"));
                // 点击确定按钮退出
                this.click(By.name("确定"));
                logger.info("退出成功");
                // System.out.println("退出成功");
            } else {
                logger.info("未知异常");
            }
        } else {
            logger.info("没有登录");
        }
    }

    /**
     * 首次登录页面操作 封装滑动页面
     *
     * @throws InterruptedException
     * */
    public boolean guidePageSwipe() throws InterruptedException {
        /**
         * 引导页的activity mCurrentFocus=Window{125e822f u0
         * com.gatewang.yjg/com.gatewang.yjg.ui.activity.GuideActivity}
         * */
        // driver.sleep(4000);
        boolean flag = true;// 定义一开始是有的
        if (driver.isExitCurActivity(".activity.GuideActivity") == true) {
            driver.swipe("left", 500);
            this.click(By.className("android.widget.ImageView"));
            return flag;
        } else {
            logger.info("不存在引导页");
            flag = false;
            return flag;
        }

    }

    /** ========需要用到手机验证码============ */
    // 快捷登录
    // 注册账号
    // 忘记密码
    /** ========需要用到验证码============ */

    /***
     * @author Neo.shi
     * @Abstract 异常检测 在BasePage中有
     *
     * */

}

