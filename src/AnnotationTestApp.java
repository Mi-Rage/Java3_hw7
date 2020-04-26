import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AnnotationTestApp {

    public static void main(String[] args) throws Exception {
        Class<?> testClass = TestClass.class;

        start(testClass);
    }

    public static void start(Class<?> testClass) throws Exception {

        Method[] methods = testClass.getDeclaredMethods();
        Object object = testClass.newInstance();
        Method beforeMethod = null;
        Method afterMethod = null;
        List<Method> testStack = new ArrayList<>();

        for (Method each : methods) {
            // Если находим метод с аннотацией @BeforeSuite первый раз - присваиваем его
            // соответствующей переменной.
            // А если уже не первый раз - кидаем исключение.
            if (each.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod == null) {
                    beforeMethod = each;
                } else {
                    throw new RuntimeException("ERROR! @BeforeSuite annotation has more than 1");
                }
            }
            // Аналогично с найденной аннотацией @AfterSuite
            if (each.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod == null) {
                    afterMethod = each;
                } else {
                    throw new RuntimeException("ERROR! @AfterSuite annotation has more than 1");
                }
            }
            // Если находим метод с аннотацией @Test - помещаем его в массив этих методов.
            if (each.isAnnotationPresent(Test.class)) {
                testStack.add(each);
            }
        }

        // Сортируем полученный массив методов с аннотациями @Test по возрастанию их приоритетов
        // Для этого используем переопределенный метод compare() класса Comparator
        testStack.sort(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o1.getAnnotation(Test.class).priority() - o2.getAnnotation(Test.class).priority();
            }
        });

        // Запускаем методы согласно заданию.
        // Если имеется метод с аннотацией @BeforeSuite - он запускается первым
        if (beforeMethod != null) {
            beforeMethod.invoke(object);
        }
        // Затем запускаются все методы с аннотацией @Test, предварительно отсортированные по приоритету
        for (Method each : testStack) {
            each.invoke(object);
        }
        // Если имеется метод с аннотацией @AfterSuite - он запускается последним
        if (afterMethod != null) {
            afterMethod.invoke(object);
        }
    }
}
