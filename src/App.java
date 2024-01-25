import java.sql.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args)  {
       
        Department dp = new Department(1, "TI");

        Seller seller = new Seller(30, "Pedro", "pedro@email.com", new Date(0), 2000d, dp);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(dp);
        System.out.println(seller);

    }
}
