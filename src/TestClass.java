public class TestClass {
    @BeforeSuite
    public void beforeMethod() {
        System.out.println("Execute BeforeSuite method");
    }

    //@BeforeSuite
    public void beforeMethod1() {
        System.out.println("Execute BeforeSuite method");
    }

    @Test(priority = 3)
    public void testMethod1() {
        System.out.println("Execute testMethod #1, priority 3");
    }

    @Test(priority = 6)
    public void testMethod2() {
        System.out.println("Execute testMethod #2, priority 6");
    }

    @Test(priority = 4)
    public void testMethod3() {
        System.out.println("Execute testMethod #3, priority 4");
    }

    @Test(priority = 6)
    public void testMethod4() {
        System.out.println("Execute testMethod #4, priority 6");
    }


    @AfterSuite
    public void afterMethod() {
        System.out.println("Execute AfterSuite method");
    }
}
