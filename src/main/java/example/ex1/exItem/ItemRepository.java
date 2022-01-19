package example.ex1.exItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

@Repository
public class ItemRepository {

    private final EntityManager em;

    @Autowired //생략가능
    public ItemRepository(EntityManager em) throws IOException {
        this.em = em;
    }

    public Long save(Item item){
        em.persist(item);
        return item.getId();
    }

    public void update(Long id, String name){
        Item item = em.find(Item.class, id);
        item.setName(name);
    }

    public Item find(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
