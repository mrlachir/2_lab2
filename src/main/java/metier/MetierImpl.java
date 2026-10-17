package metier;
import dao.IDao;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {
    // Profiles select one DAO. Two active implementations require a qualifier.
    @Autowired
    private IDao dao;

    @Override public double calcul() {
        if (dao == null) throw new IllegalStateException("Le DAO n'a pas été injecté.");
        return dao.getValue() * 2;
    }
    // Used by XML configuration and the isolated unit test.
    public void setDao(IDao dao) { this.dao = Objects.requireNonNull(dao, "dao"); }
}
