package org.example.cafespring.service;

import org.example.cafespring.model.Favorite;
import org.example.cafespring.repository.FavoriteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteDAO favoriteDAO;

    @Autowired
    public FavoriteService(FavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    public List<Favorite> findAll() {
        return favoriteDAO.findAll();
    }

    public void save(Favorite favorite) {
        favoriteDAO.add(favorite);
    }

    public void delete(int clientId, int dishId) {
        favoriteDAO.delete(clientId, dishId);
    }

    public void update(Favorite favorite) {
        favoriteDAO.update(favorite);
    }

    public Favorite findByIds(int clientId, int dishId) {
        return favoriteDAO.findByIds(clientId, dishId);
    }
}
