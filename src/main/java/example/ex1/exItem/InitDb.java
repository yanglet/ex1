package example.ex1.exItem;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() throws IOException {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final ItemRepository itemRepository;

        public void dbInit() throws IOException {
            String in_url = "https://www.innisfree.com/kr/ko/ProductList/skincare/Product.do?catCd01=UA";
            String si_url = "https://www.sidmool.com/shop/shopbrand.html?xcode=019&mcode=011&type=Y";
            String be_url = "https://www.beplain.co.kr/goods/goods_list.php?cateCd=017004";

            Document in_doc = Jsoup.connect(in_url).get();
            Document si_doc = Jsoup.connect(si_url).get();
            Document be_doc = Jsoup.connect(be_url).get();

            Elements in_nameList = in_doc.getElementsByAttributeValue("class", "name");
            Elements in_priceList = in_doc.getElementsByAttributeValue("class","price");

            Elements si_nameList = si_doc.getElementsByAttributeValue("class", "shoplist_title");
            Elements si_priceList = si_doc.getElementsByAttributeValue("class","shoplist_price");
            Elements si_imgsrc = si_doc.getElementsByAttributeValue("class", "MS_prod_img_s");

            Elements be_nameList = be_doc.getElementsByAttributeValue("class", "item_name");
            Elements be_priceList = be_doc.getElementsByAttributeValue("class","item_price");
            Elements be_imgsrc = be_doc.getElementsByAttributeValue("class", "middle");


            for(int j = 0; j < 20; j++) {
                Item item = new Item(in_nameList.get(j).text(),
                        in_priceList.get(j).text(),
                        "priceSign",
                        "이니스프리",
                        "imageLink",
                        "productLink",
                        "websiteLink",
                        "itemFeature",
                        "skinType");
                itemRepository.save(item);
            }

            for(int j = 0; j < 20; j++) {
                Item item = new Item(si_nameList.get(j).text(),
                        si_priceList.get(j).text(),
                        "priceSign",
                        "시드물",
                        si_imgsrc.get(j).toString(),
                        "productLink",
                        "websiteLink",
                        "itemFeature",
                        "skinType");
                itemRepository.save(item);
            }

            for(int j = 0; j < 16; j++) {
                Item item = new Item(be_nameList.get(j).text(),
                        be_priceList.get(j).text(),
                        "priceSign",
                        "비플레인",
                        be_imgsrc.get(j).toString(),
                        "productLink",
                        "websiteLink",
                        "itemFeature",
                        "skinType");
                itemRepository.save(item);
            }
        }
    }
}
