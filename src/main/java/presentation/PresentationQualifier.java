package presentation;
import dao.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/** Both DAOs coexist here, so selection is explicit rather than based on bean order. */
@Configuration
public class PresentationQualifier {
    @Bean("dao") public IDao dao() { return new DaoImpl(); }
    @Bean("dao2") public IDao dao2() { return new DaoImpl2(); }
    @Bean("metier")
    public CalculAvecQualifier metier() { return new CalculAvecQualifier(); }

    public static class CalculAvecQualifier implements metier.IMetier {
        @Autowired
        @Qualifier("dao2")
        private IDao dao;
        @Override public double calcul() { return dao.getValue() * 2; }
    }
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(PresentationQualifier.class)) {
            System.out.println("DAO disponibles : " + context.getBeansOfType(IDao.class).keySet());
            System.out.println("Résultat (@Qualifier dao2) = "
                    + context.getBean(metier.IMetier.class).calcul());
        }
    }
}
