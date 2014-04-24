package com.epam.andrii_loievets.springdao.repository.user;

import com.epam.andrii_loievets.springdao.domain.User;
import java.util.List;

/**
 *
 * @author Andrii_Loievets
 */
public interface UserRepository {
    int insertUser(User user);
    User findUser(User user);
    int updateUser(User user);
    int deleteUser(User user);
    int deleteBatch(final List<User> users);
}
