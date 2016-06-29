package com.github.dzhai.service;

import java.io.Serializable;
import java.util.List;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * 共用service接口
 *
 * @author dzhai
 */
@SuppressWarnings("all")
public interface IBaseService<T,ID extends Serializable> {
	/**
	 * count by example
	 * 
	 */
	int countByExample(Object example);

	/**
	 * delete by example
	 *
	 */
	int deleteByExample(Object example);

	/**
	 * delete by id
	 *
	 */
	int deleteByPrimaryKey(ID id);

	/**
	 * insert T
	 *
	 */
	int insert(T record);

	/**
	 * insert T 非空属性
	 *
	 */
	int insertSelective(T record);

	/**
	 * select list by example
	 *
	 */
	List<T> selectByExample(Object example);

	/**
	 * select T by id
	 *
	 */
	T selectByPrimaryKey(ID id);

	/**
	 * update T by example 非空属性
	 *
	 */
	int updateByExampleSelective(T record, Object example);

	/**
	 * update T by example
	 *
	 */
	int updateByExample(T record, Object example);

	/**
	 * update T 非空属
	 *
	 */
	int updateByPrimaryKeySelective(T record);

	/**
	 * update T
	 *
	 */
	int updateByPrimaryKey(T record);

	/**
	 * select by example 分页查询
	 *
	 */
	List<T> selectByExample(Object example, PageBounds pageBounds);

	/**
	 * select one T by example
	 *
	 */
	T selectOneByExample(Object example);

	/**
	 * select list by param 建议Map
	 *
	 */
	List<T> selectRelationList(Object params);

	/**
	 * select list by param 建议Map 分页查询
	 *
	 */
	List<T> selectRelationList(Object params, PageBounds pageBounds);
}
