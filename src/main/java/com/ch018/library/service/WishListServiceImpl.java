/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ch018.library.service;

import com.ch018.library.DAO.WishListDAO;
import com.ch018.library.entity.WishList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class WishListServiceImpl implements WishListService{
    
    @Autowired
    WishListDAO wishlistDAO;
    
    @Override
    public void addWish(WishList wish) {
        wishlistDAO.addWish(wish);
    }

    @Override
    public void deleteWish(WishList wish) {
      wishlistDAO.deleteWish(wish);
    }

    @Override
    public Collection getAllWishes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WishList getWishById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
