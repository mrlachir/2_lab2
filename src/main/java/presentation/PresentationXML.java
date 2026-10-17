package presentation;
import metier.IMetier;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationXML {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {
            System.out.println("Résultat (XML) = " + context.getBean("metier", IMetier.class).calcul());
        }
    }
}
