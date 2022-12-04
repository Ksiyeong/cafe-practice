package com.cafe.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Members")
@Validated
public class MemberController {

    @PostMapping
    public ResponseEntity postMember() {
        // TODO: 구현
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
