package com.epam.andrii_loievets.springdao;

import com.epam.andrii_loievets.springdao.repository.user.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // load context
        ApplicationContext ac = new ClassPathXmlApplicationContext("persistenceContext.xml");
        
        // get user repository
        UserRepository ur = ac.getBean("userRepository", UserRepository.class);
        
        //System.out.println(ur.insertUser(null));
    }
}
