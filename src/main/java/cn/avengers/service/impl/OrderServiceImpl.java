package cn.avengers.service.impl;

import cn.avengers.dao.BookDao;
import cn.avengers.dao.OrderDao;
import cn.avengers.dao.OrderItemDao;
import cn.avengers.dao.impl.BookDaoImpl;
import cn.avengers.dao.impl.OrderDaoImpl;
import cn.avengers.dao.impl.OrderItemDaoImpl;
import cn.avengers.pojo.*;
import cn.avengers.service.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();


    @Override
    public String createOrder(Cart cart, Integer userId) {

        System.out.println("OrderServiceImpl 程序在[" + Thread.currentThread().getName() + "]中");

        //订单===唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        //创建一个订单对象
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);

        //保存订单
        orderDao.saveOrder(order);

        int i = 12 / 0 ;

        //遍历购物车中得每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            //获取每一个购物车中的商品项
            CartItem cartItem = entry.getValue();
            //转换为每一个订单项
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(), orderId);

            orderItemDao.saveOrderItem(orderItem);

            //更新库存和销量
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales( book.getSales() + cartItem.getCount());
            book.setStock( book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);
        }

        //清空购物车
        cart.clear();

        return orderId;

    }


}
