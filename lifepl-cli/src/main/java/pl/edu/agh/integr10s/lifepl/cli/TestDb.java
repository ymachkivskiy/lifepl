package pl.edu.agh.integr10s.lifepl.cli;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.edu.agh.integr10s.persistence.db.dao.TestDAO;
import pl.edu.agh.integr10s.persistence.db.entities.Test;

public class TestDb {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/appconfig.xml");

        TestDAO td = ctx.getBean(TestDAO.class);

        System.out.println("\n\n\n\nFROM DB START\n\n");
        for (Test test : td.getTests()) {
            System.out.println(test);
        }
        System.out.println("\n\nFROM DB END\n\n");



//        for (int i = 0; i < 100; i++) {
//            Test test = new Test();
//            test.setData("dupa ala matla");
//
//            td.store(test);
//        }


    }
}
