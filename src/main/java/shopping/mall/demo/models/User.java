package shopping.mall.demo.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String refreshtoken;
    private MultipartFile profile;
}
