package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import asg.cliche.Command;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.utils.dummy_package.AnnotatingMethodAssembly;
import pl.edu.agh.integr10s.lifepl.cli.shell.utils.dummy_package.MethodAnnotationValue;

import java.util.Arrays;
import java.util.List;

public final class ConfiguredShells {

    private ConfiguredShells() {
    }

    public static List<SubShell> get() {
        MethodAnnotationValue val = new MethodAnnotationValue("ew", Command.class);
        val.setValue("name" , "someCommand");

        AnnotatingMethodAssembly assembly = new AnnotatingMethodAssembly(val);

//        StringValuesAnnotationAdder.getAnnotatedInstance(MainShell.class, "ew", val),



        try {

            return Arrays.asList(
                    assembly.assemble(MainShell.class).newInstance(),
                    new WorldsShell()
            );


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
