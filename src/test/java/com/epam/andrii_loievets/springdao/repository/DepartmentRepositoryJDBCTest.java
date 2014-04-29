/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.andrii_loievets.springdao.repository;

import com.epam.andrii_loievets.springdao.domain.Department;
import com.epam.andrii_loievets.springdao.domain.User;
import com.epam.andrii_loievets.springdao.repository.department.DepartmentRepository;
import com.epam.andrii_loievets.springdao.repository.user.UserRepository;
import com.epam.andrii_loievets.springdao.repository.user.UserRepositoryJDBC;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andrii_Loievets
 */
public class DepartmentRepositoryJDBCTest extends DAOTestsTemplate {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private Department department;

    // needed to manipulate managers
    @Autowired
    private User user;
    @Autowired
    private UserRepository userRepository;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM departments");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of insertDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void insertDepartment_ValidDepartment_1RowInserted() {
        userRepository.insertUser(user);
        assertEquals(1, departmentRepository.insertDepartment(department));
    }

    /**
     * Test of insertDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void insertDepartment_InvalidManager_0RowInserted() {
        assertEquals(0, departmentRepository.insertDepartment(department));
    }

    /**
     * Test of findById method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void findById_ValidId_ValidDepartment() {
        userRepository.insertUser(user);
        departmentRepository.insertDepartment(department);
        Department found = departmentRepository.findById(department.getDepartmentId());

        assertEquals(department.getDepartmentId(), found.getDepartmentId());
    }
    
    /**
     * Test of findById method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void findById_InvalidId_ReturnsNull() {
        assertNull(departmentRepository.findById(department.getDepartmentId() + 1));
    }
    
    /**
     * Test of findByManagerId method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void findByManagerId_InvalidId_ReturnsNull() {
        assertNull(departmentRepository.findByManagerId(-1));
    }
    
    /**
     * Test of findByManagerId method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void findByManagerId_ValidId_DepartmentIdMatches() {
        userRepository.insertUser(user);
        departmentRepository.insertDepartment(department);
        
        Department found =
                departmentRepository.findByManagerId(department.getManager().getId());
        
        assertEquals(department.getDepartmentId(), found.getDepartmentId());
    }
    
    /**
     * Test of updateDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void updateDepartment_NewLocation_1RowUpdated() {
        userRepository.insertUser(user);
        departmentRepository.insertDepartment(department);
        
        department.setLocation("Los Angeles");
        
        assertEquals(1, departmentRepository.updateDepartment(department));
    }
    
    /**
     * Test of updateDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void updateDepartment_NewLocation_LocationChanged() {
        userRepository.insertUser(user);
        departmentRepository.insertDepartment(department);
        
        department.setLocation("Los Angeles");
        
        departmentRepository.updateDepartment(department);
        Department found = departmentRepository.findById(department.getDepartmentId());
        
        assertEquals(found.getLocation(), department.getLocation());
    }
    
    /**
     * Test of updateDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void updateDepartment_InvalidDepartment_0RowsUpdated() {
        assertEquals(0, departmentRepository.updateDepartment(department));
    }
    
    /**
     * Test of updateDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void updateDepartment_InvalidManager_0RowsUpdated() {
        userRepository.insertUser(user);
        departmentRepository.insertDepartment(department);
        
        User wrongManager = new User();
        wrongManager.setId(user.getId() + 1);
        Department dep = new Department();
        
        dep.setDepartmentId(department.getDepartmentId());
        dep.setLocation(department.getLocation());
        dep.setManager(wrongManager);
        
        assertEquals(0, departmentRepository.updateDepartment(dep));
    }
    
    /**
     * Test of deleteDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void deleteDepartment_ValidDepartment_1RowDeleted() {
        userRepository.insertUser(user);
        departmentRepository.insertDepartment(department);
        
        //assertEquals(1, userRepository.deleteUser(user));
        assertEquals(1, departmentRepository.deleteDepartment(department));
    }
    
    /**
     * Test of deleteDepartment method, of class DepartmentRepositoryJDBC.
     */
    @Test
    public void deleteDepartment_InvalidDepartment_0RowsDeleted() {
        assertEquals(0, departmentRepository.deleteDepartment(department));
    }
}