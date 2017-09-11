package service;

import action.ActionException;
import entity.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {
    @Test
    public void testCreateUser() {
        User user1 = new User();
        user1.setFirstName("Jim");
        user1.setLastName("Carrey");
        user1.setEmail("carrey@gmail.com");
        try (UserService userService = new UserService()) {
            user1 = userService.createUser(user1);
        } catch (Exception e) {
            throw new ActionException("Failed to createUser()", e);
        }
        User user2;
        try (UserService userService = new UserService()) {
            user2 = userService.findUserById(user1.getId());
        } catch (Exception e) {
            throw new ActionException("Failed to findUserById()", e);
        }
        assertEquals(user1, user2);
    }
}