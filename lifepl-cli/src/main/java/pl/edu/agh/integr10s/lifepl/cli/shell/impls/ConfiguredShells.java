package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import asg.cliche.Command;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.utils.AnnotationValue;
import pl.edu.agh.integr10s.lifepl.cli.shell.utils.StringValuesAnnotationAdder;

import java.util.Arrays;
import java.util.List;

public final class ConfiguredShells {

    private ConfiguredShells() {
    }

    public static List<SubShell> get() {
        AnnotationValue val = new AnnotationValue(Command.class);
        val.setValue("name" , "blablalb");
        try {

            return Arrays.asList(
                    StringValuesAnnotationAdder.getAnnotatedInstance(MainShell.class, "ew", val),
                    new WorldsShell()
            );


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
