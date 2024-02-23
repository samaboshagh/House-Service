package org.example.finalprojectphasetwo.Controller;

import lombok.AllArgsConstructor;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService<User> userService;

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        userService.confirmEmail(confirmationToken);
        return new ResponseEntity<>("EMAIL CONFIRMED SUCCESSFULLY", HttpStatus.OK);
    }
}