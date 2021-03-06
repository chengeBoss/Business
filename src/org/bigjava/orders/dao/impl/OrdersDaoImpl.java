package org.bigjava.orders.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.bigjava.addr.entity.Addr;
import org.bigjava.function.Paging;
import org.bigjava.orders.dao.OrdersDao;
import org.bigjava.orders.entity.Orders;
import org.bigjava.product.entity.Product;
import org.bigjava.user.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class OrdersDaoImpl extends HibernateDaoSupport implements OrdersDao {

	// 添加订单
	@Override
	public void addOrders(Orders orders, User user, Addr addr) {
		// TODO Auto-generated method stub
		System.out.println("开始执行addOrders方法");
		
		orders.setUser(user);// 添加与User类的外键
		orders.setAddr(addr);// 添加与Addr类的外键
		
		this.getHibernateTemplate().save(orders);// 添加订单信息
		
	}

	// 删除订单信息(退货)
	@Override
	public void deleteOrders(Orders orders) {
		// TODO Auto-generated method stub
		System.out.println("开始执行deleteOrders方法");
		this.getHibernateTemplate().delete(orders);// 删除订单信息
	}

	// 通过订单id获取订单信息
	@Override
	public Orders queryOrders_id(int o_id) {
		// TODO Auto-generated method stub
		System.out.println("执行queryOrders_id方法");
		return this.getHibernateTemplate().get(Orders.class, o_id);// 通过订单id获取订单信息
	}

	// 分页条件查询订单信息
	@Override
	public List<Orders> queryAllOrders(final Paging page, final User user) {
		// TODO Auto-generated method stub
		System.out.println("开始执行queryAllOrders方法");
		List<Orders> list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {// 通过hibernateTemplate回调sessionFactory方法
				// TODO Auto-generated method stub
				String hql = "from Orders";
				Query query = null;
				if (user.getRoot() == 3) {
					query = session.createQuery(hql);
				} else {
					hql += " where u_id = ?";
					query = session.createQuery(hql).setInteger(0, user.getU_id());// 通过用户id查询订单内容
				}
				query.setFirstResult(page.getStart());// 分页查询从哪一条开始查
				query.setMaxResults(page.getPagesize());// 分页查询查多少条

				return query.list();// 将查询到的值转换为数组类型
			}
		});
		System.out.println("查询到的数据" + list);
		return list;
	}

	// 条件查询订单的数量
	@Override
	public int queryAllOrdersNumber(User user) {
		// TODO Auto-generated method stub
		System.out.println("开始执行queryAllOrdersNumber方法");
		int totalNumber = 0;
		List<Long> list = null;
		String hql = "select count(*) from Orders";
		if (user.getRoot() == 3) {// 管理员权限查询全部订单信息
			list = this.getHibernateTemplate().find(hql);
		} else {
			hql += "where u_id = ?";
			list = this.getHibernateTemplate().find(hql, user.getU_id());// 普通用户和店长的权限通过u_id外键查询对应的Orders订单信息
		}
		if (list.size() != 0) {
			totalNumber = list.get(0).intValue();
		}
		return totalNumber;
	}

	// 通过订单编号查询订单
	@Override
	public Orders queryOrders_orderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		System.out.println("开始执行queryOrders_orderNumber方法");
		List<Orders> listOrders = this.getHibernateTemplate().find("from Orders where orderNumber = ?", orderNumber);
		if (listOrders.size() == 0) {
			System.out.println("没有此订单");
			return null;
		}
		return listOrders.get(0);
	}

}
