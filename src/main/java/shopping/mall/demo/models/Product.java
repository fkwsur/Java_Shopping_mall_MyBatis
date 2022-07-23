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

public class Product {
    private String id;
    private String seller;
    private String desc;
    private String itemname;
    private int price;
    private String item_image;
    private MultipartFile image;
}
