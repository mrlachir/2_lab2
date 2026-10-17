package presentation;
import dao.IDao;
import metier.IMetier;
import org.junit.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class SpringConfigurationTest {
    private void verifyProfile(String profile, String bean, double expected) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.getEnvironment().setActiveProfiles(profile);
            context.register(Presentation2.class);
            context.refresh();
            assertEquals(1, context.getBeansOfType(IDao.class).size());
            assertTrue(context.containsBean(bean));
            assertEquals(expected, context.getBean(IMetier.class).calcul(), 0.001);
        }
    }
    @Test public void devUsesDao2() { verifyProfile("dev", "dao2", 300.0); }
    @Test public void prodUsesDao() { verifyProfile("prod", "dao", 200.0); }
    @Test public void qualifierSelectsDao2WithBothBeansPresent() {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(PresentationQualifier.class)) {
            assertEquals(2, context.getBeansOfType(IDao.class).size());
            assertThrows(NoUniqueBeanDefinitionException.class, () -> context.getBean(IDao.class));
            assertEquals(300, context.getBean(IMetier.class).calcul(), 0.001);
        }
    }
    @Test public void xmlInjectsDao2ThroughSetter() {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
            assertEquals(2, context.getBeansOfType(IDao.class).size());
            assertEquals(300, context.getBean(IMetier.class).calcul(), 0.001);
        }
    }
    @Test public void noProfileDoesNotSilentlyChooseDao() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Presentation2.class);
            assertThrows(UnsatisfiedDependencyException.class, context::refresh);
        }
    }
}
