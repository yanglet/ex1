package example.ex1;

import example.ex1.exItem.Item;
import example.ex1.exItem.ItemRepository;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Ex1ApplicationTests {

	@Autowired
	ItemRepository itemRepository;


	@Transactional
	@Rollback(false)
	public void testItem(){
		Item item = new Item("스킨", "10000", "imgsrc", "설명", "이니스프리");

		Long saveId = itemRepository.save(item);

		Item findItem = itemRepository.find(saveId);

		Assertions.assertThat(findItem).isEqualTo(item);
	}

	@Transactional
	@Rollback(false)
	public void 이니스프리크롤링() throws IOException {
		List<Item> items = new ArrayList<>();
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
			items.add(item);
		}
		List<Item> findItems = itemRepository.findAll();

		Assertions.assertThat(findItems).isEqualTo(items);
	}

	@Transactional
	@Rollback(false)
	public void 시드물크롤링() throws IOException {
		List<Item> items = new ArrayList<>();
		String se_url = "https://www.sidmool.com/shop/shopbrand.html?xcode=019&mcode=011&type=Y";
		Document se_doc = Jsoup.connect(se_url).get();

		Elements se_nameList = se_doc.getElementsByAttributeValue("class", "shoplist_title");
		Elements se_priceList = se_doc.getElementsByAttributeValue("class","shoplist_price");
//		Elements se_rePointList = se_doc.getElementsByAttributeValue("class","reviewPoint");
		Elements se_imgsrc = se_doc.getElementsByAttributeValue("class", "MS_prod_img_s");

		for(int i = 0; i < 20; i++){
			Item item = new Item(se_nameList.get(i).text(),
					se_priceList.get(i).text(),
					se_imgsrc.get(i).toString(),
					"설명",
					"시드물");

			itemRepository.save(item);
			items.add(item);
		}
		List<Item> findItems = itemRepository.findAll();

		Assertions.assertThat(findItems).isEqualTo(items);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void 비플레인크롤링() throws IOException {
		List<Item> items = new ArrayList<>();
		String be_url = "https://www.beplain.co.kr/goods/goods_list.php?cateCd=017";
		Document be_doc = Jsoup.connect(be_url).get();

		Elements be_nameList = be_doc.getElementsByAttributeValue("class", "item_name");
		Elements be_priceList = be_doc.getElementsByAttributeValue("class","item_price");
//		Elements be_rePointList = be_doc.getElementsByAttributeValue("class","reviewPoint");
		Elements be_imgsrc = be_doc.getElementsByAttributeValue("class", "middle");

		for(int i = 0; i < 20; i++){
			Item item = new Item(be_nameList.get(i).text(),
					be_priceList.get(i).text(),
					be_imgsrc.get(i).toString(),
					"설명",
					"비플레인");

			itemRepository.save(item);
			items.add(item);
		}
		List<Item> findItems = itemRepository.findAll();

		Assertions.assertThat(findItems).isEqualTo(items);
	}
}
