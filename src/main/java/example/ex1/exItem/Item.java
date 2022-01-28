package example.ex1.exItem;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String price;
    private String priceSign;
    private String brand;
    private String imageLink;
    private String productLink;
    private String website_Link;
    private String itemFeature;  
    private String skinType;


    protected Item(){} //그냥 생성 못하게 막음 기본적으로 protected. jpa기본스펙. 없으면 오류남

    public Item(String name,
                String price,
                String priceSign,
                String brand,
                String imageLink,
                String productLink,
                String website_Link,
                String itemFeature,
                String skinType) {
        this.name = name;
        this.price = price;
        this.priceSign = priceSign;
        this.brand = brand;
        this.imageLink = imageLink;
        this.productLink = productLink;
        this.website_Link = website_Link;
        this.itemFeature = itemFeature;
        this.skinType = skinType;
    }
}
