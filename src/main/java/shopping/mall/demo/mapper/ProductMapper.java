package shopping.mall.demo.mapper;

import shopping.mall.demo.models.Product;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("insert into product(`seller`,`itemname`,`desc`,`item_image`,`price`) values(#{seller},#{itemname},#{desc},#{item_image},#{price})")
    int create(Product product);

    @Update("update product set `itemname`=#{itemname},`desc`=#{desc},`item_image`=#{item_image},`price`=#{price} where `id` = #{id} and `seller`=#{seller}")
    int update(Product product);

    @Delete("delete from product where `id`=#{id} and `seller`=#{seller}")
    int delete(String id, String seller);

    @Select("select * from product")
    List<Product> select();
}
