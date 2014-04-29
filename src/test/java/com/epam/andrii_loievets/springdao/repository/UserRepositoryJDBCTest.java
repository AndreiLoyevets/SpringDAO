/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.andrii_loievets.springdao.repository;

import com.epam.andrii_loievets.springdao.repository.user.UserRepository;
import com.epam.andrii_loievets.springdao.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andrii_Loievets
 */
public class UserRepositoryJDBCTest extends DAOTestsTemplate {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User user;

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM users");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of insertUser method, of class UserRepositoryJDBC.
     */
    @Test
    public void insertUser_ValidUser_1RowInserted() {
        assertEquals(1, userRepository.insertUser(user));
    }
    
    /**
     * Test of insertUser method, of class UserRepositoryJDBC.
     */
    @Test
    public void insertUser_DuplicateUser_0RowInserted() {
        userRepository.insertUser(user);
        assertEquals(0, userRepository.insertUser(user));
    }
    
    /**
     * Test of findById method, of class UserRepositoryJDBC.
     */
    @Test
    public void findById_ExistingUser_UserFound() {
        userRepository.insertUser(user);
        User found = userRepository.findById(user.getId());
        
        assertEquals(user.getId(), found.getId());
    }
    
    /**
     * Test of findByEmail method, of class UserRepositoryJDBC.
     */
    @Test
    public void findById_InvalidUser_ReturnsNull() {
        assertNull(userRepository.findById(user.getId()));
    }

    /**
     * Test of findByEmail method, of class UserRepositoryJDBC.
     */
    @Test
    public void findByEmail_ExistingUser_UserFound() {
        userRepository.insertUser(user);
        User found = userRepository.findByEmail(user.getEmail());
        
        assertEquals(user.getEmail(), found.getEmail());
    }
    
    /**
     * Test of findByEmail method, of class UserRepositoryJDBC.
     */
    @Test
    public void findByEmail_InvalidUser_ReturnsNull() {
        assertNull(userRepository.findByEmail(user.getEmail()));
    }
    
    /**
     * Test of updateUser method, of class UserRepositoryJDBC.
     */
    @Test
    public void updateUser_ValidUser_1RowUpdated() {
        userRepository.insertUser(user);
        user.setActivated(true);
        assertEquals(1, userRepository.updateUser(user));
    }
    
    /**
     * Test of deleteUser method, of class UserRepositoryJDBC.
     */
    @Test
    public void deleteUser_ValidUser_1RowDeleted() {
        userRepository.insertUser(user);
        assertEquals(1, userRepository.deleteUser(user));
    }
    
    /**
     * Test of deleteUser method, of class UserRepositoryJDBC.
     */
    @Test
    public void deleteUser_InvalidUser_NoRowsDeleted() {
        userRepository.insertUser(user);
        user.setEmail("bb");
        assertEquals(0, userRepository.deleteUser(user));
    }
    
    /**
     * Test of deleteBatch method, of class UserRepositoryJDBC.
     */
    @Test
    public void deleteBatch_1User_1RowDeleted() {
        userRepository.insertUser(user);
        List<User> users = new ArrayList<User>();
        users.add(user);
        
        assertEquals(1, userRepository.deleteBatch(users));
    }
    
    /**
     * Test of deleteBatch method, of class UserRepositoryJDBC.
     */
    @Test
    public void deleteBatch_InvalidUser_0RowsDeleted() {
        userRepository.insertUser(user);
        List<User> users = new ArrayList<User>();
        user.setEmail("cc");
        users.add(user);
        
        assertEquals(0, userRepository.deleteBatch(users));
    }
    
    /**
     * Test of deleteBatch method, of class UserRepositoryJDBC.
     */
    @Test
    public void deleteBatch_2Users_2RowsDeleted() {
        userRepository.insertUser(user);
        List<User> users = new ArrayList<User>();
        users.add(user);
        
        // create second user
        User user2 = new User();
        user2.setEmail("dd");
        user2.setPassword("*");
        user2.setId(33);
        user2.setName("Vasiliy");
        user2.setSurname("V");
        user2.setPhone("123456");
        
        userRepository.insertUser(user2);
        users.add(user2);
        
        assertEquals(2, userRepository.deleteBatch(users));
    }
}
