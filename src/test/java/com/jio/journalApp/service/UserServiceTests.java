package com.jio.journalApp.service;

import com.jio.journalApp.entity.User;
import com.jio.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindByUserName(){
        assertNotNull(userService.findByUserName("Prasad"));
    }

    @Disabled                       //when u run the whole test cases then it will not run.
    @Test
    public void testFindById(){
       assertNotNull(userService.findById(new ObjectId("6a7f69067f4657536e540721")));
    }

    @Test
    public void testGetAll(){
        assertNotNull(userRepository.findAll());
    }

    @Test
    public void testFindByIds(){
        assertTrue(userService.findById(new ObjectId("6a7f68fa7f4657536e540720")).isPresent());
    }

    @Test
    public void testUserRole(){
        User user= userService.findByUserName("Prasad");
        assertNotNull(user);
        assertFalse(user.getRoles().contains("USER"));
    }

    @Test
    public void testUserName(){
        User user= userService.findByUserName("Prasad");
        assertNotNull(user);
        assertEquals("Prasad",user.getUserName());
    }

    @Test
    public void testInvalidUserName(){
        User user= userService.findByUserName("Prasad");
        assertNotNull(user);
        assertNotEquals("Akash",user.getUserName());
    }

    @Test
    public void testAll(){
        User user= userService.findByUserName("Prasad");
        assertNotNull(user);
        assertAll(
                ()-> assertEquals("Prasad",user.getUserName()),
        ()-> assertNotNull(user.getPassWord()),
                ()->assertNotNull(user.getUserName()),
                ()->assertTrue(user.getRoles().contains("ADMIN"))

        );
    }

    @ParameterizedTest
    @CsvSource({
        "1,1,2",
            "2,2,4",
            "3,3,6"          //1 failed, other 2 passes
    })
    public void testDynamicValue(int a, int b, int result){
        assertEquals(result,a+b);
    }

}
