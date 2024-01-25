
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args)  {
       
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("--- Teste 1: Seller findById ---");
        Seller seller = sellerDao.findById(4);
        System.out.println(seller);

        System.out.println("\n--- Teste 1: Seller findByDepartment ---");
        Department dep = new Department(3, null);
        List<Seller> sellers = sellerDao.findByDerparment(dep);
        sellers.forEach(System.out::println);

    }
}
