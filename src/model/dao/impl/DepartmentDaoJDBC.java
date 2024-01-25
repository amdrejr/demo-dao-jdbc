package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    Connection conn = null;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement st = conn.prepareStatement(
            "DELETE FROM department " +
            "WHERE Id = ?"
        )) {
           st.setInt(1, id); 
           st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Department> findAll() {
        try (PreparedStatement st = conn.prepareStatement("SELECT * from Department")) {
            
            List<Department> list = new ArrayList<>();

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = instantiateDepartment(rs);
                    list.add(dep);
                }
                return list;
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {
        try (PreparedStatement st = conn.prepareStatement(
            "SELECT * from department " +
            "WHERE Id = ?"
        )) {
            st.setInt(1, id);

             // try with resources
             try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    // Department dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
                    return instantiateDepartment(rs);
                }
            } 

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void insert(Department obj) {
        try (PreparedStatement st = conn.prepareStatement(
            "INSERT INTO department " +
            "(Name) " +
            "VALUES (?)",
            Statement.RETURN_GENERATED_KEYS
        )) {
            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("inserted! Rows affecteds: " + rowsAffected); 
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if(rs.next()) {
                        int id = rs.getInt(1);
                        obj.setId(id);
                        System.out.println("Department inserted with Id: " + id);
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
    public void update(Department obj) {
       try (PreparedStatement st = conn.prepareStatement(
            "UPDATE department " +
            "SET Name = ? "  +
            "WHERE ID = ?"
       )) {
        st.setString(1, obj.getName());
        st.setInt(2, obj.getId());

        st.executeUpdate();

       } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        
        
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(
            rs.getInt("Id"), 
            rs.getString("Name")
        );
    }

    
}