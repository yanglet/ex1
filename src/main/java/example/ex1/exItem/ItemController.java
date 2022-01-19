package example.ex1.exItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class ItemController {
    private final ItemRepository itemRepository;

    @Autowired //생략가능
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
    @GetMapping("/")
    public String Form(){
        return "basic/form";
    }
    */

    @GetMapping("/url") //조회 api
    public Result readNameList(){
        List<ItemDto> collect = itemRepository.findAll()
                .stream()
                .map(m -> new ItemDto(m.getId(),
                        m.getName(),
                        m.getPrice(),
                        m.getPriceSign(),
                        m.getBrand(),
                        m.getImageLink(),
                        m.getProductLink(),
                        m.getWebsite_Link(),
                        m.getItemFeature(),
                        m.getSkinType()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/url")
    public CreateItemResponse save(@RequestBody Item item){
        Long id = itemRepository.save(item);

        return new CreateItemResponse(id);
    }

    @PutMapping("/url/{id}") //수정 api
    public UpdateItemResponse update(@PathVariable Long id,
                       @RequestBody @Valid UpdateItemRequest request){
        itemRepository.update(id, request.getName());
        Item findItem = itemRepository.find(id);

        return new UpdateItemResponse(findItem.getId(),
                findItem.getName(),
                findItem.getPrice(),
                findItem.getPriceSign(),
                findItem.getBrand(),
                findItem.getImageLink(),
                findItem.getProductLink(),
                findItem.getWebsite_Link(),
                findItem.getItemFeature(),
                findItem.getSkinType());
    }

    @Data
    static class CreateItemRequest {
        private String name;
        private String price;
        private String priceSign;
//        private String currency;
//        private String tagList;
        private String brand;
        private String imageLink;
        private String productLink;
        private String website_Link;
//        private String description;
        private String itemFeature;
        private String skinType;
    }
    @Data
    @AllArgsConstructor
    static class CreateItemResponse {
        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class ItemDto{ //엔티티 대신 데이터만 담아서 주고 받을 dto클래스
        private Long id;
        private String name;
        private String price;
        private String priceSign;
//        private String currency;
//        private String tagList;
        private String brand;
        private String imageLink;
        private String productLink;
        private String website_Link;
//        private String description;
        private String itemFeature;
        private String skinType;


    }

    @Data
    @AllArgsConstructor
    static class UpdateItemRequest { //
        private Long id;
        private String name;
        private String price;
        private String priceSign;
//        private String currency;
//        private String tagList;
        private String brand;
        private String imageLink;
        private String productLink;
        private String website_Link;
//        private String description;
        private String itemFeature;
        private String skinType;

    }

    @Data
    @AllArgsConstructor
    static class UpdateItemResponse {
        private Long id;
        private String name;
        private String price;
        private String priceSign;
//        private String currency;
//        private String tagList;
        private String brand;
        private String imageLink;
        private String productLink;
        private String website_Link;
//        private String description;
        private String itemFeature;
        private String skinType;
    }
}
