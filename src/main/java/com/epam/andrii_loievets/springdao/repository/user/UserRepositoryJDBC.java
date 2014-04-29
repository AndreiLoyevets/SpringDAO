package com.epam.andrii_loievets.springdao.repository.user;

import com.epam.andrii_loievets.springdao.domain.User;
import com.epam.andrii_loievets.springdao.repository.GenericRepositoryJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Andrii_Loievets
 */
public class UserRepositoryJDBC extends GenericRepositoryJDBC<User> implements UserRepository {

    public UserRepositoryJDBC() {
        
        rowMapper = new RowMapper<User>() {

            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("firstName"));
                user.setSurname(resultSet.getString("surname"));
                user.setPhone(resultSet.getString("phone"));
                int isActivated = resultSet.getInt("isActivated");

                user.setActivated((isActivated == 0) ? false : true);

                return user;
            }
        };
    }

    public int insertUser(User user) {
        String sql = "INSERT INTO users "
                + "(id, email, password, firstName, surname, phone) values "
                + "(?, ?, ?, ?, ?, ?)";

        try {
            return jdbcTemplate.update(sql, new Object[]{user.getId(), user.getEmail(), user.getPassword(),
                user.getName(), user.getSurname(), user.getPhone()});
        } catch (DataAccessException e) {
            return 0;
        }
    }

    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, rowMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public int updateUser(User user) {
        String sql = "UPDATE users SET email = ?, password = ?, firstName = ?,"
                + "surname = ?, phone = ?, isActivated = ?"
                + "WHERE email = ?";
        int activated = (Boolean.compare(user.isActivated(), false) == 0 ? 0 : 1);

        return jdbcTemplate.update(sql, new Object[]{user.getEmail(),
            user.getPassword(), user.getName(), user.getSurname(),
            user.getPhone(), activated, user.getEmail()});
    }

    public int deleteUser(User user) {
        String sql = "DELETE FROM users WHERE email = ?";

        return jdbcTemplate.update(sql, user.getEmail());
    }

    public int deleteBatch(final List<User> users) {
        String sql = "DELETE FROM users WHERE email = ?";
        BatchPreparedStatementSetter preparedStatementSetter
                = new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        User user = users.get(i);
                        ps.setString(1, user.getEmail());
                    }

                    public int getBatchSize() {
                        return users.size();
                    }
                };

        int[] results = jdbcTemplate.batchUpdate(sql, preparedStatementSetter);
        int numDeleted = 0;

        for (int i = 0; i < results.length; ++i) {
            if (results[i] == 1) {
                numDeleted++;
            }
        }

        return numDeleted;
    }
}