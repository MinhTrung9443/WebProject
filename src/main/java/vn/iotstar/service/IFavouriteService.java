package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Favourite;

public interface IFavouriteService {

	void deleteById(Integer id);

	<S extends Favourite> S save(S entity);

	Optional<Favourite> findByUserId(int userId, int productId);

	List<Favourite> findAllByUserId(int Id);

}
