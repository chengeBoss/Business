package org.bigjava.orderitem.dao;

import java.util.List;

import org.bigjava.function.Paging;
import org.bigjava.orderitem.entity.Orderitem;
import org.bigjava.product.entity.Product;
import org.bigjava.user.entity.User;

public interface OrderItemDao {
	
	/**
	 * 添加订单项 
	 */
	public void addOrderItem(Orderitem orderItem, User user, Product product);
	
	/**
	 * 删除订单项
	 */
	public void deleteOrderItem(Orderitem orderItem);
	
	/**
	 * 通过订单项id查询订单项
	 */
	public Orderitem queryOrderItem_id(int o_id);
	
	/**
	 * 分页条件查询订单项
	 */
	public List<Orderitem> queryAllOrderItem(User user, Paging page);
	
	/**
	 * 条件查询订单项个数
	 */
	public int queryOrderItemNumber(User user);
}
