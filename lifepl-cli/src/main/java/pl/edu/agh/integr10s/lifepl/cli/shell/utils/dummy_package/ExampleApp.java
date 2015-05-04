package pl.edu.agh.integr10s.lifepl.cli.shell.utils.dummy_package;

import java.lang.reflect.Method;

/**
 * Created by SG0948908 on 5/4/2015.
 */
public class ExampleApp {

    protected void say(String wat) {
        System.out.println("say" + wat);
    }

    public static void main(String[] args) throws Exception {

        AddingVoidMethodsForBaseMethodAssembly<String> a = new AddingVoidMethodsForBaseMethodAssembly<String>("say", "Hello");

        ExampleApp exampleApp = a.assemble(ExampleApp.class).newInstance();

        Method instrumentedMethod = exampleApp.getClass().getDeclaredMethod("sayHello");

        instrumentedMethod.invoke(exampleApp);

    }
}
