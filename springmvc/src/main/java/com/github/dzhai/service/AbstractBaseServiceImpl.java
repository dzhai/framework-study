package com.github.dzhai.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.github.dzhai.util.ReflectionUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@SuppressWarnings("unchecked")
public abstract class AbstractBaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {

	protected abstract Object getCurrentDao();

	@Override
	public int countByExample(Object example) {
		return (int) invokeMethod("countByExample", example);
	}

	@Override
	public int deleteByExample(Object example) {
		return (int) invokeMethod("deleteByExample", example);
	}

	@Override
	public int deleteByPrimaryKey(ID id) {
		return (int) invokeMethod("deleteByPrimaryKey", id);
	}

	@Override
	public int insert(T record) {
		return (int) invokeMethod("insert", record);
	}

	@Override
	public int insertSelective(T record) {
		return (int) invokeMethod("insertSelective", record);
	}

	@Override
	public List<T> selectByExample(Object example) {
		return (List<T>) invokeMethod("selectByExample", example);
	}

	@Override
	public T selectByPrimaryKey(ID id) {
		return (T) invokeMethod("selectByPrimaryKey", id);
	}

	@Override
	public int updateByExampleSelective(T record, Object example) {
		return (int) invokeMethod("updateByExampleSelective", record, example);
	}

	@Override
	public int updateByExample(T record, Object example) {
		return (int) invokeMethod("updateByExample", record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return (int) invokeMethod("updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return (int) invokeMethod("updateByPrimaryKey", record);
	}

	@Override
	public List<T> selectByExample(Object example, PageBounds pageBounds) {
		return (List<T>) invokeMethod("selectByExample", example, pageBounds);
	}

	@Override
	public T selectOneByExample(Object example) {
		List<T> list = selectByExample(example);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<T> selectRelationList(Object params) {
		return (List<T>) invokeMethod("selectRelationList", params);
	}

	@Override
	public List<T> selectRelationList(Object params, PageBounds pageBounds) {
		return (List<T>) invokeMethod("selectRelationList", params, pageBounds);
	}

	/**
	 * 检查参数是否为NULL
	 * 
	 */
	protected void checkParameters(Object... clazzs) {
		if (clazzs == null || clazzs.length == 0) {
			return;
		}
		for (Object clazz : clazzs) {
			if (clazz == null) {
				throw new RuntimeException("参数不能为NULL");
			}
		}

	}

	/**
	 * 通过反射调取方法
	 * 
	 */
	protected Object invokeMethod(String methodName, Object... clazzs) {
		checkParameters(clazzs);
		Object dao = getCurrentDao();
		return ReflectionUtil.invokeMethod(dao, methodName, getParameterTypes(clazzs), clazzs);
	}

	protected Class<?>[] getParameterTypes(Object... clazzs) {
		if (clazzs == null || clazzs.length == 0) {
			return null;
		}
		Class<?>[] parameterTypes = new Class[clazzs.length];
		for (int i = 0; i < clazzs.length; i++) {
			if (clazzs[i] instanceof Map) {
				parameterTypes[i] = Map.class;
			}
			if (clazzs[i] instanceof List) {
				parameterTypes[i] = List.class;
			} else {
				parameterTypes[i] = clazzs[i].getClass();
			}

		}
		return parameterTypes;
	}

}
