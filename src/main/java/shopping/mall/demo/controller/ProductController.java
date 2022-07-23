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
@RequestMapping("/api/product") // url을 api로 지정
@CrossOrigin(origins = "*", allowedHeaders = "*") // cors허용
@RequiredArgsConstructor
public class ProductController {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final Bcrypt bcrypt;
    private final Jwt jwt;
    private final GlobalException globalException;
    private final Upload multer;

    @GetMapping("/hello")
    public ResponseEntity<Object> Hello(@RequestHeader("xauth") String token) {
        String decoded = jwt.VerifyToken(token);
        System.out.println(decoded);
        Map<String, String> map = new HashMap<>();
        map.put("result", decoded);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // create
    @PostMapping("/createproduct")
    public ResponseEntity<Object> CreateProduct(@ModelAttribute Product req, @RequestHeader("xauth") String token) {
        try {
            String decoded = jwt.VerifyToken(token);
            // 토큰으로 유저 검사
            User user = new User();
            user.setUsername(decoded);
            User getAuth = userMapper.findOne(user);
            Map<String, String> map = new HashMap<>();
            if (getAuth == null) {
                map.put("result", "잘못된 토큰이거나 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            req.setSeller(decoded);

            req.setItem_image(multer.uploadFile(req.getImage()));
            int result = productMapper.create(req);
            if (result == 0) {
                map.put("result", "정의되지 않은 오류입니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            map.put("result", "success");

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return globalException.handleRuntimeException(e);
        }
    }

    // update
    @PostMapping("/updateproduct")
    public ResponseEntity<Object> UpdateProduct(@ModelAttribute Product req, @RequestHeader("xauth") String token) {
        try {
            String decoded = jwt.VerifyToken(token);
            System.out.println(decoded);
            // 토큰으로 유저 검사
            User user = new User();
            user.setUsername(decoded);
            User getAuth = userMapper.findOne(user);
            System.out.println(getAuth);
            Map<String, String> map = new HashMap<>();
            if (getAuth == null) {
                map.put("result", "잘못된 토큰이거나 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            req.setSeller(decoded);
            req.setItem_image(multer.uploadFile(req.getImage()));
            int result = productMapper.update(req);
            if (result == 0) {
                map.put("result", "권한이 없거나 없는 게시물입니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            map.put("result", "success");

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return globalException.handleRuntimeException(e);
        }
    }

    // delete
    @PostMapping("/deleteproduct")
    public ResponseEntity<Object> DeleteProduct(@RequestParam String id, @RequestHeader("xauth") String token) {
        try {

            String decoded = jwt.VerifyToken(token);
            int result = productMapper.delete(id, decoded);
            System.out.println(result);
            Map<String, String> map = new HashMap<>();
            if (result == 0) {
                map.put("result", "권한이 없거나 없는 게시물입니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            map.put("result", "success");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return globalException.handleRuntimeException(e);
        }
    }

    // selete
    @GetMapping("/selectproduct")
    public ResponseEntity<Object> SelectProduct(@RequestHeader("xauth") String token) {
        try {
            String decoded = jwt.VerifyToken(token);
            System.out.println(decoded);
            // 토큰으로 유저 검사
            User user = new User();
            user.setUsername(decoded);
            User getAuth = userMapper.findOne(user);
            System.out.println(getAuth);
            if (getAuth == null) {
                Map<String, String> map = new HashMap<>();
                map.put("result", "잘못된 토큰이거나 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            List<Product> product = productMapper.select();
            Map<String, Object> map = new HashMap<>();
            map.put("result", product);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return globalException.handleRuntimeException(e);
        }
    }

    // buyitem
    // @PostMapping("/buyitem")
    // public ResponseEntity<Object> BuyProduct(@RequestParam Product
    // req,@RequestHeader("xauth") String token) {
    // try {
    // String decoded = jwt.VerifyToken(token);
    // System.out.println(decoded);
    // // 토큰으로 유저 검사
    // User user = new User();
    // user.setUsername(decoded);
    // User getAuth = userMapper.findOne(user);
    // System.out.println(getAuth);
    // if (getAuth == null) {
    // Map<String, String> map = new HashMap<>();
    // map.put("result", "잘못된 토큰이거나 권한이 없습니다.");
    // return new ResponseEntity<>(map, HttpStatus.OK);
    // }

    // System.out.println(multer.uploadFile(req.getImage()));
    // req.setItem_image(multer.uploadFile(req.getImage()));
    // productMapper.create(req);
    // Map<String, String> map = new HashMap<>();
    // map.put("result", "success");

    // return new ResponseEntity<>(map, HttpStatus.OK);
    // } catch (Exception e) {
    // return globalException.handleRuntimeException(e);
    // }
    // }

}
