package com.team.deskposable.repository;

import com.team.deskposable.entity.Desk;
import com.team.deskposable.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fx on 28/07/17.
 */
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findItemsByDesk_Id(Long id);
}
