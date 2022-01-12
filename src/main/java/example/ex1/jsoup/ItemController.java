package example.ex1.jsoup;

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
                        m.getItemName(),
                        m.getItemPrice(),
                        m.getItemImgSrc(),
                        m.getItemFeature(),
                        m.getItemBrand()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/url")
    public CreateItemResponse save(@RequestBody @Valid Item item){
        Long id = itemRepository.save(item);

        return new CreateItemResponse(id);
    }

    @PutMapping("/url/{id}") //수정 api
    public UpdateItemResponse update(@PathVariable Long id,
                       @RequestBody @Valid UpdateItemRequest request){
        itemRepository.update(id, request.getItemName());
        Item findItem = itemRepository.find(id);
        return new UpdateItemResponse(findItem.getId(),
                findItem.getItemName(),
                findItem.getItemPrice(),
                findItem.getItemImgSrc(),
                findItem.getItemFeature(),
                findItem.getItemBrand());
    }

    @Data
    static class CreateItemRequest {
        private String itemName;
        private String itemPrice;
        private String itemImgSrc;
        private String itemFeature;
        private String itemBrand;
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
        private String itemName;
        private String itemPrice;
        private String itemImgSrc;
        private String itemFeature;
        private String itemBrand;

        public ItemDto(String itemName, String itemPrice, String itemImgSrc, String itemFeature, String itemBrand) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.itemImgSrc = itemImgSrc;
            this.itemFeature = itemFeature;
            this.itemBrand = itemBrand;
        }
    }

    @Data
    @AllArgsConstructor
    static class UpdateItemRequest { //
        private Long id;
        private String itemName;
        private String itemPrice;
        private String itemImgSrc;
        private String itemFeature;
        private String itemBrand;
    }

    @Data
    @AllArgsConstructor
    static class UpdateItemResponse {
        private Long id;
        private String itemName;
        private String itemPrice;
        private String itemImgSrc;
        private String itemFeature;
        private String itemBrand;
    }
}
