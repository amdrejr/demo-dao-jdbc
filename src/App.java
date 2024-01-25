
import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args)  {
       
        SellerDao sellerDao = DaoFactory.createSellerDao();

        // System.out.println("--- Teste 1: Seller findById ---");
        // Seller seller = sellerDao.findById(4);
        // System.out.println(seller);

        // System.out.println("\n--- Teste 2: Seller findByDepartment ---");
        // Department dep = new Department(3, null);
        // List<Seller> sellers = sellerDao.findByDerparment(dep);
        // sellers.forEach(System.out::println);

        // System.out.println("\n--- Teste 3: Seller findAll ---");
        // List<Seller> allSellers = sellerDao.findAll();
        // allSellers .forEach(System.out::println);

        System.out.println("\n--- Teste 4: Seller insert ---");
        Department newDep = new Department(1, null);
        Seller newSeller = new Seller(null, "Joana Andrade", "joana@email", new Date(), 7211.00, newDep);
        sellerDao.insert(newSeller);
        System.out.println("Id Seller: " + newSeller.getId());
    }
}
