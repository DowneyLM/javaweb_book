package cn.avengers.dao;

import cn.avengers.pojo.Book;

import java.util.List;

public interface BookDao {

    //添加书本
    public int addBook(Book book);
    //根据bookid删除书本
    public int deleteBookById(Integer id);
    //更新书本
    public int updateBook(Book book);
    //根据bookid查询书本返回书本
    public Book queryBookById(Integer id);
    //查询所有书本
    public List<Book> queryBooks();
    //查询书本的总记录数
    Integer queryForPageTotalCount();
    //查询页数指定的书本
    List<Book> queryForPageItems(int begin, int pageSize);
    //查询价格范围的书本的记录数
    Integer queryForPageTotalCountByPrice(int min , int max);
    //查询价格范围和页码指定的书本
    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);

}
