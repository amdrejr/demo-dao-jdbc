package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC (Connection conn) {
        this.conn = conn;
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Seller findById(Integer id) {
        // try with resources -> dá .close() automaticamente em seus params ao finalizar o try
        try ( PreparedStatement st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE seller.id = ?" ) ) {

            st.setInt(1, id);
            
            // try with resources
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    // Department dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
                    Department dep = instantiateDepartment(rs);
                    return instantiateSeller(rs, dep);   
                }
            } 

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } 
    }

    @Override
    public void insert(Seller obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Seller obj) {
        // TODO Auto-generated method stub
        
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(
            rs.getInt("DepartmentId"), 
            rs.getString("DepName")
        );
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        return new Seller(
            rs.getInt("Id"), 
            rs.getString("Name"), 
            rs.getString("Email"), 
            rs.getDate("BirthDate"), 
            rs.getDouble("BaseSalary"), 
            dep
        );
    }
    
}
