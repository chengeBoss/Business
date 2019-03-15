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

	// ���Ӷ���
	@Override
	public void addOrders(Orders orders, User user, Addr addr, Product product) {
		// TODO Auto-generated method stub
		System.out.println("��ʼִ��addOrders����");
		
		orders.setUser(user);// ������User������
		orders.setAddr(addr);// ������Addr������
		orders.getSetProduct().add(product);// ��product�� ���id���ӵ�hibernate���м��
		
		this.getHibernateTemplate().save(orders);// ���Ӷ�����Ϣ
		
	}

	// ɾ��������Ϣ(�˻�)
	@Override
	public void deleteOrders(Orders orders) {
		// TODO Auto-generated method stub
		System.out.println("��ʼִ��deleteOrders����");
		this.getHibernateTemplate().delete(orders);// ɾ��������Ϣ
	}

	// ͨ������id��ȡ������Ϣ
	@Override
	public Orders queryOrders_id(int o_id) {
		// TODO Auto-generated method stub
		System.out.println("ִ��queryOrders_id����");
		return this.getHibernateTemplate().get(Orders.class, o_id);// ͨ������id��ȡ������Ϣ
	}

	// ��ҳ������ѯ������Ϣ
	@Override
	public List<Orders> queryAllOrders(final Paging page, final User user) {
		// TODO Auto-generated method stub
		System.out.println("��ʼִ��queryAllOrders����");
		List<Orders> list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {// ͨ��hibernateTemplate�ص�sessionFactory����
				// TODO Auto-generated method stub
				String hql = "from Orders";
				Query query = null;
				if (user.getRoot() == 3) {
					query = session.createQuery(hql);
				} else {
					hql += " where u_id = ?";
					query = session.createQuery(hql).setInteger(0, user.getU_id());// ͨ���û�id��ѯ��������
				}
				query.setFirstResult(page.getStart());// ��ҳ��ѯ����һ����ʼ��
				query.setMaxResults(page.getPagesize());// ��ҳ��ѯ�������

				return query.list();// ����ѯ����ֵת��Ϊ��������
			}
		});
		System.out.println("��ѯ��������" + list);
		return list;
	}

	// ������ѯ����������
	@Override
	public int queryAllOrdersNumber(User user) {
		// TODO Auto-generated method stub
		System.out.println("��ʼִ��queryAllOrdersNumber����");
		int totalNumber = 0;
		List<Long> list = null;
		String hql = "select count(*) from Orders";
		if (user.getRoot() == 3) {// ����ԱȨ�޲�ѯȫ��������Ϣ
			list = this.getHibernateTemplate().find(hql);
		} else {
			hql += "where u_id = ?";
			list = this.getHibernateTemplate().find(hql, user.getU_id());// ��ͨ�û��͵곤��Ȩ��ͨ��u_id�����ѯ��Ӧ��Orders������Ϣ
		}
		if (list.size() != 0) {
			totalNumber = list.get(0).intValue();
		}
		return totalNumber;
	}

}