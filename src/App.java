
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args)  {
        // SELLERS
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("--- Teste 1: Seller findById ---");
        Seller seller = sellerDao.findById(4);
        System.out.println(seller);

        System.out.println("\n--- Teste 2: Seller findByDepartment ---");
        Department dep = new Department(3, null);
        List<Seller> sellers = sellerDao.findByDerparment(dep);
        sellers.forEach(System.out::println);

        System.out.println("\n--- Teste 3: Seller findAll ---");
        List<Seller> allSellers = sellerDao.findAll();
        allSellers .forEach(System.out::println);

        System.out.println("\n--- Teste 4: Seller insert ---");
        Department newDep = new Department(1, null);
        Seller newSeller = new Seller(null, "Joana Andrade", "joana@email", new Date(), 7211.00, newDep);
        sellerDao.insert(newSeller);
        System.out.println("Id Seller: " + newSeller.getId());

        System.out.println("\n--- Teste 5: Seller update ---");
        Seller updateSeller = sellerDao.findById(3);
        updateSeller.setBaseSalary( updateSeller.getBaseSalary() + 333 );
        sellerDao.update(updateSeller);

        System.out.println("\n--- Teste 6: Seller delete ---");
        sellerDao.deleteById(5);


        System.out.println("\nDEPARTMENTS");
        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("\n--- Teste 1: Department findAll ---");
        List<Department> deps = depDao.findAll();
        deps.forEach(System.out::println);

        System.out.println("\n--- Teste 2: Department findById ---");
        System.out.println(depDao.findById(2));
    
        System.out.println("\n--- Teste 3: Department insert ---");
        Department insertDep = new Department(null, "Home items");
        depDao.insert(insertDep);

        System.out.println("\n--- Teste 4: Department update ---");
        Department upDep = new Department(1, "Informatic");
        depDao.update(upDep);

        System.out.println("\n--- Teste 5: Department delete ---");
        depDao.deleteById(6);

    }
}
