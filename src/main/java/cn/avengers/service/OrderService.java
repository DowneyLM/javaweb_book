package cn.avengers.service;

import cn.avengers.pojo.Cart;

public interface OrderService {

    public String createOrder(Cart cart, Integer userId);

}
