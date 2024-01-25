
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class App {
    public static void main(String[] args)  {
       
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("--- Teste 1: Seller findById ---");
        Seller seller = sellerDao.findById(4);
        System.out.println(seller);

    }
}
