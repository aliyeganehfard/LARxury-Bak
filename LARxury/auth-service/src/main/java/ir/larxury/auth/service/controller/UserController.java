package ir.larxury.auth.service.controller;

import ir.larxury.auth.service.service.UserService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user/")
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping("role/add/shopAdmin")
    public ResponseEntity<GeneralResponse> setShopAdminRole(@RequestParam(name = "userId") String userId) {
        userService.setShopAdminRole(userId);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("find/userId")
    public ResponseEntity<GeneralResponse> findUserId(@RequestParam(name = "username") String username) {
        var userId = userService.findUserIdByUsername(username);
        var res = GeneralResponse.successfulResponse(userId, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("find/userInfo")
    public ResponseEntity<GeneralResponse> findUserInfo(@RequestParam(name = "userId") String userId) {
        var userInfo = userService.findUserInformationById(userId);
        var res = GeneralResponse.successfulResponse(userInfo, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
