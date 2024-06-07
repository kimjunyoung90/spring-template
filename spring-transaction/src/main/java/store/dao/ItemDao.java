package store.dao;

import store.domain.Item;

public interface ItemDao {

	Item findById(Integer itemId);

}
