package org.bigjava.user.dao;

import java.util.List;

import org.bigjava.function.Paging;
import org.bigjava.user.entity.User;

/**
 * �û�����
 * @author Administrator
 *
 */
public interface UserDao {
	
	/**
	 * ע���û�
	 */
	public void registerUser(User user); 
	
	/**
	 * �޸�
	 */
	public void updateUser(User user, User users);
	
	/**
	 * ͨ��id��ѯ�û�
	 */
	public User demend(int id);
	
	/**
	 * ͨ��idɾ���û�
	 */
	public void deleteUser(User user);
	
	/**
	 * ����Աͨ��ģ����ҳ��ѯ�û�
	 */
	public List<User> limitDemend(String username, Paging page);
	
	/**
	 * ����Աͨ��ģ����ѯ��ҳ��
	 */
	public int demendPages(String username);
	
	/**
	 * У���û����Ƿ��Ѵ���
	 */
	public boolean checkUsername(String username);
	
	

}