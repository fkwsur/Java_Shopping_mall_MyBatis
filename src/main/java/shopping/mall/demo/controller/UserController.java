package shopping.mall.demo.controller;

import shopping.mall.demo.models.*;
import shopping.mall.demo.mapper.*;
import shopping.mall.demo.service.*;

// 프레임워크에 탑재된 기능 가져오기
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// 자바 가지고 있는 내장 모듈
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import shopping.mall.demo.exception.GlobalException;

@RestController // restApi를 작성할 수 있는 컨트롤러
@RequestMapping("/api/user") // url을 api로 지정
@CrossOrigin(origins = "*", allowedHeaders = "*") // cors허용
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final Bcrypt bcrypt;
    private final Jwt jwt;
    private final GlobalException globalException;

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> Hello() {
        Map<String, String> map = new HashMap<>();
        map.put("result", "hello world");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // create
    @PostMapping("/signup")
    public ResponseEntity<Object> SignUp(@RequestBody User req) {
        try {
            String hashpassword = bcrypt.HashPassword(req.getPassword());
            req.setPassword(hashpassword);
            String rtoken = jwt.CreateRToken(req.getUsername());
            req.setRefreshtoken(rtoken);
            userMapper.create(req);
            Map<String, String> map = new HashMap<>();
            map.put("result", "success");

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return globalException.handleRuntimeException(e);
        }

    }

    // checkid
    @GetMapping("/finduser")
    public ResponseEntity<User> findOneUser(@RequestBody User req) {
        return new ResponseEntity<>(userMapper.findOne(req), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> SignIn(@RequestBody User req) {
        User user = new User();
        user = userMapper.findOne(req);
        Boolean result = bcrypt.CompareHash(req.getPassword(), user.getPassword());
        Map<String, String> map = new HashMap<>();
        if (result) {
            String token = jwt.CreateToken(user.getUsername());
            String rtoken = jwt.CreateRToken(user.getUsername());
            System.out.println(token);
            int updateToken = userMapper.updateToken(rtoken, user.getUsername());
            if (updateToken == 0) {
                map.put("result", "정의되지 않은 오류입니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

            map.put("xauth", token);
            map.put("rxauth", rtoken);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.put("result", "password is not correct");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 리프레쉬로 엑세스 재발급
    @GetMapping("/issuetoken")
    public ResponseEntity<Object> IssueRtoken(@RequestHeader("rxauth") String rtoken) {
        try {
            System.out.println(rtoken);

            User user = new User();
            user.setRefreshtoken(rtoken);

            String decoded = jwt.VerifyRToken(rtoken);
            System.out.println(decoded);
            user.setUsername(decoded);

            User result = userMapper.findRtoken(user);
            System.out.println(result);

            Map<String, Object> map = new HashMap<>();
            if (result == null) {
                map.put("result", "잘못된 토큰이거나 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            String token = jwt.CreateToken(user.getUsername());
            map.put("xauth", token);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return globalException.handleRuntimeException(e);
        }
    }

    // 자동로그인 아직 하기전

}
