package example.ex1.jsoup;

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
            Document in_doc = Jsoup.connect(in_url).get();

            Elements in_nameList = in_doc.getElementsByAttributeValue("class", "name");
            Elements in_priceList = in_doc.getElementsByAttributeValue("class","price");

            for(int i = 0; i < 20; i++){
                Item item = new Item(in_nameList.get(i).text(),
                        in_priceList.get(i).text(),
                        "imgsrc",
                        "설명",
                        "이니스프리");

                itemRepository.save(item);
            }
        }
    }
}
