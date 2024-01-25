package model.dao.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try ( PreparedStatement st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "ORDER By Name " ) ) {

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            // try with resources
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = map.get(rs.getInt("DepartmentId"));

                    if (dep == null) {
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("DepartmentId"), dep);
                    }

                    Seller obj = instantiateSeller(rs, dep);
                    list.add(obj);   
                }
                return list;
            } 

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
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
        
        try (PreparedStatement st = conn.prepareStatement(
            "INSERT INTO seller "  + 
            "(Name, Email, BirthDate, BaseSalary, DepartmentId) "  +
            "VALUES (?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        )) {
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("inserted! Rows affecteds: " + rowsAffected); 
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if(rs.next()) {
                        int id = rs.getInt(1);
                        obj.setId(id);
                        System.out.println("Seller inserted with Id: " + id);
                    }
                } 
            } else {
                throw new DbException("Unexpected error! no rows affected..");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        
    }

    @Override
    public void update(Seller obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Seller> findByDerparment(Department department) {
        try ( PreparedStatement st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE DepartmentId = ? " +
                "ORDER By Name " ) ) {

            st.setInt(1, department.getId());
            
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            // try with resources
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = map.get(rs.getInt("DepartmentId"));

                    if (dep == null) {
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("DepartmentId"), dep);
                    }

                    Seller obj = instantiateSeller(rs, dep);
                    list.add(obj);   
                }
                return list;
            } 

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
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
