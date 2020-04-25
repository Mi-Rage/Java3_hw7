import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.*;
import java.util.Arrays;

public class CheckDZ {

    public static void main(String[] args) throws Exception {
        check();
    }

    public static void check() throws Exception {
        File file = new File("C:\\Users\\igrom\\OneDrive\\Документы\\JAVA\\Java3\\DZ");
        String[] fileList = file.list();
        if (fileList.length == 0) {
            throw new NullPointerException("А домашек то нету!!1");
        }

        for (String o : fileList) {
            String[] mass = o.split("\\.");
            if (!mass[1].equalsIgnoreCase("class")) {
                throw new RuntimeException(o, new Exception());
            }
            System.out.println("Имя файла " + mass[0]);

            Class<?> loadClass = URLClassLoader.newInstance(new URL[]{file.toURL()}).loadClass(mass[0]);
            // Получаем массив с всеми методами в этом классе
            Method[] methods = loadClass.getDeclaredMethods();
            // Мы знаем что в данном случае не создавалось никаких конструкторов класса
            Constructor<?> constructor = loadClass.getConstructor();
            // Создадим нечто из класса с домашкой
            Object instance = constructor.newInstance();

            // Гляем что за методы сделали ученики. Нас интересуют только те, что возвращают что либо
            for (Method method : methods) {
                method.setAccessible(true);
                // Проверим возвращает ли метод значение
                if (!method.getReturnType().equals(void.class)) {
                    // Выведем в консоль возвращаемый тип, имя и типы параметров метода
                    System.out.println(method.getReturnType() + " " + method.getName() + " "
                            + Arrays.toString(method.getParameterTypes()));
                    // Мы же знаем, какие должны быть имена методов, по этому проверим их выполнение
                    switch (method.getName()) {
                        // Выведем совпал ли ожидаемый результат с результатом метода
                        case ("isNegative") :
                            if (method.invoke(instance, -1).equals(true)
                                    && method.invoke(instance, 1).equals(false)) {
                                System.out.println(method.getName() + " passed!");
                            } else {
                                System.out.println(method.getName() + " failed!");
                            }
                            break;
                        case ("isLeapYear") :
                            if (method.invoke(instance, 2020).equals(true)
                                    && method.invoke(instance, 2019).equals(false)) {
                                System.out.println(method.getName() + " passed!");
                            } else {
                                System.out.println(method.getName() + " failed!");
                            }
                            break;
                        case ("checkTwoNumbers") :
                            if (method.invoke(instance, 10,5).equals(true)
                                    && method.invoke(instance, 15,15).equals(false)) {
                                System.out.println(method.getName() + " passed!");
                            } else {
                                System.out.println(method.getName() + " failed!");
                            }
                            break;
                        case ("calculate") :
                            if (method.invoke(instance, 1,2,3,4).equals(2.75f) ||
                                    method.invoke(instance, 1,2,3,4).equals(2)) {
                                System.out.println(method.getName() + " passed!");
                            } else {
                                System.out.println(method.getName() + " failed!");
                            }
                            break;
                    }
                }

            }
        }
    }
}
