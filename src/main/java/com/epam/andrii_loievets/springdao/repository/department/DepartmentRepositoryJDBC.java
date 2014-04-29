package com.epam.andrii_loievets.springdao.repository.department;

import com.epam.andrii_loievets.springdao.domain.Department;
import com.epam.andrii_loievets.springdao.domain.User;
import com.epam.andrii_loievets.springdao.repository.GenericRepositoryJDBC;
import com.epam.andrii_loievets.springdao.repository.user.UserRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Andrii_Loievets
 */
public class DepartmentRepositoryJDBC extends GenericRepositoryJDBC<Department> implements DepartmentRepository {

    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public DepartmentRepositoryJDBC() {
        rowMapper = new RowMapper<Department>() {

            public Department mapRow(ResultSet resultSet, int i) throws SQLException {
                Department department = new Department();
                department.setDepartmentId(resultSet.getInt("id"));
                department.setLocation(resultSet.getString("location"));

                int managerId = resultSet.getInt("manager_id");
                department.setManager(userRepository.findById(managerId));

                return department;
            }
        };
    }

    public int insertDepartment(Department department) {
        String sql = "INSERT INTO departments"
                + "(id, location, manager_id) values "
                + "(?, ?, ?)";
        
        User manager = department.getManager();
        Object [] params;
        
        if (manager == null) {
            params = new Object[]{department.getDepartmentId(),
                department.getLocation()};
        } else {
            params = new Object[]{department.getDepartmentId(),
                department.getLocation(), manager.getId()};
        }

        try {
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    public Department findById(int id) {
        String sql = "SELECT * FROM departments WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Department findByManagerId(int managerId) {
        String sql = "SELECT * FROM departments WHERE manager_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{managerId}, rowMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public int updateDepartment(Department department) {
        String sql = "UPDATE departments SET location = ?, manager_id = ? " +
                "WHERE id = ?";
        
        User manager = department.getManager();
        Object [] params;
        
        if (manager == null) {
            params = new Object[]{department.getLocation(), null, department.getDepartmentId()};
        } else {
            params = new Object[]{department.getLocation(), manager.getId(), department.getDepartmentId()};
        }
        
        try {
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    public int deleteDepartment(Department department) {
        String sql = "DELETE FROM departments WHERE id = ?";

        return jdbcTemplate.update(sql, department.getDepartmentId());
    }
}
