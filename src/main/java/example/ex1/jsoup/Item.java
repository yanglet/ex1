package example.ex1.jsoup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String itemName;
    private String itemPrice;
    private String itemImgSrc;
    private String itemFeature;
    private String itemBrand;

    protected Item(){} //그냥 생성 못하게 막음 기본적으로 protected. jpa기본스펙. 없으면 오류남

    public Item(String itemName,
                String itemPrice,
                String itemImgSrc,
                String itemFeature,
                String itemBrand) {

        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImgSrc = itemImgSrc;
        this.itemFeature = itemFeature;
        this.itemBrand = itemBrand;
    }
}
