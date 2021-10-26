package kr.ac.daegu.springbootapi.security.controller;

import kr.ac.daegu.springbootapi.config.JwtTokenUtil;
import kr.ac.daegu.springbootapi.member.Member;
import kr.ac.daegu.springbootapi.security.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        final Member member = userDetailsService
                .authenticateByEmailAndPassword(authenticationRequest.getEmail(),
                                                authenticationRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(member.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

@Data
class JwtRequest {
    private String email;
    private String password;
}

@Data
@AllArgsConstructor
class JwtResponse {
    private String token;
}
