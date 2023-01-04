package com.imooc.comtroller;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.exception.UserException;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserQueryCondition condition) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @GetMapping(value = "/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id) {
        throw new UserException(id);
//        User user = new User();
//        user.setUsername("tom");
//        return user;
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
//        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");

        return user;
    }

    @PutMapping(value = "/{id:\\d+}")
    public User updateUser(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
//                        FieldError filedError = (FieldError) error;
//                        String msg = filedError.getField() +" "+ error.getDefaultMessage();
//                        System.out.println(msg);
                        System.out.println(error.getDefaultMessage());
                    }
            );
        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");

        return user;
    }
}
