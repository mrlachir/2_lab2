package presentation;
import metier.IMetier;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"dao", "metier"})
public class Presentation2 {
    public static void main(String[] args) {
        if (args.length > 1) throw new IllegalArgumentException("Usage: Presentation2 [dev|prod]");
        String profile = args.length == 0 ? "dev" : args[0];
        if (!profile.equals("dev") && !profile.equals("prod"))
            throw new IllegalArgumentException("Profil attendu : dev ou prod");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.getEnvironment().setActiveProfiles(profile);
            context.register(Presentation2.class);
            context.refresh();
            System.out.println("Profil actif : " + profile);
            System.out.println("Résultat = " + context.getBean(IMetier.class).calcul());
        }
    }
}
