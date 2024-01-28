package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository<Customer> userRepository;
    @Autowired
    private UserServiceImpl<Customer> userService;

    @Test
    void testChangePassword() {
        // given
        Customer customer = new Customer();
        customer.setUsername("testUser");
        customer.setPassword("oldPass1");
        userRepository.save(customer);
        // when
        userService.changePassword(customer, "newPass1");
        // then
        Customer updatedUser = userRepository.findByUsername("testUser");
        assertNotNull(updatedUser);
        assertNotEquals("oldPass1", updatedUser.getPassword());
        assertEquals("newPass1", updatedUser.getPassword());
    }

    @Test
    void testChangePasswordWithSamePassword() {
        // given
        Customer user = new Customer();
        user.setUsername("testUserr");
        user.setPassword("oldPass1");
        userRepository.save(user);
        // when and then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class, () -> userService.changePassword(user, "oldPass1")
        );
        assertEquals("SAME PASSWORD ! ", exception.getMessage());
        User unchangedUser = userRepository.findByUsername("testUserr");
        assertNotNull(unchangedUser);
        assertEquals("oldPass1", unchangedUser.getPassword());
    }

    @Test
    void testChangePasswordWithNullUser() {
        // when and then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> userService.changePassword(null, "newPassword")
        );
        assertEquals("USERNAME OR PASSWORD IS NULL !", exception.getMessage());
    }
}