package com.shui.utils.mongoHelper.core;


import com.shui.utils.mongoHelper.reflection.ReflectionUtil;
import com.shui.utils.mongoHelper.reflection.SerializableFunction;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 查询语句生成器
 */
public abstract class CriteriaWrapper {
	boolean andLink = true;

	Criteria criteria;
	List<Criteria> list = new ArrayList<>();

	/**
	 * 将Wrapper转化为Criteria
	 */
	public Criteria build() {
		criteria = new Criteria();
		if (list.size() > 0) {
			if (andLink) {
				criteria.andOperator(listToArray(list));
			} else {
				criteria.orOperator(listToArray(list));
			}
		}
		return criteria;
	}

	private Criteria[] listToArray(List<Criteria> list) {
		return list.toArray(new Criteria[list.size()]);
	}

	/**
	 * 等于
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper eq(SerializableFunction<E, R> column, Object params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).is(params));
		return this;
	}

	/**
	 * 不等于
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper ne(SerializableFunction<E, R> column, Object params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).ne(params));
		return this;
	}

	/**
	 * 小于
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper lt(SerializableFunction<E, R> column, Object params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).lt(params));
		return this;
	}

	/**
	 * 小于或等于
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper lte(SerializableFunction<E, R> column, Object params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).lte(params));
		return this;
	}

	/**
	 * 大于
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper gt(SerializableFunction<E, R> column, Object params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).gt(params));
		return this;
	}

	/**
	 * 大于或等于
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper gte(SerializableFunction<E, R> column, Object params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).gte(params));
		return this;
	}

	/**
	 * 包含
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper contain(SerializableFunction<E, R> column, Object params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).all(params));
		return this;
	}

	/**
	 * 包含,以或连接
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper containOr(SerializableFunction<E, R> column, Collection<?> params) {
		CriteriaOrWrapper criteriaOrWrapper = new CriteriaOrWrapper();
		for (Object object : params) {
			criteriaOrWrapper.contain(column, object);
		}

		list.add(criteriaOrWrapper.build());
		return this;
	}

	/**
	 * 包含,以或连接
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper containOr(SerializableFunction<E, R> column, Object[] params) {
		return containOr(column, Arrays.asList(params));
	}

	/**
	 * 包含,以且连接
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper containAnd(SerializableFunction<E, R> column, Collection<?> params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).all(params));
		return this;
	}

	/**
	 * 包含,以且连接
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper containAnd(SerializableFunction<E, R> column, Object[] params) {
		return containAnd(column, Arrays.asList(params));
	}

	/**
	 * 相似于
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper like(SerializableFunction<E, R> column, String params) {
		Pattern pattern = Pattern.compile("^.*" + params + ".*$", Pattern.CASE_INSENSITIVE);
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).regex(pattern));
		return this;
	}

	/**
	 * 在其中
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper in(SerializableFunction<E, R> column, Collection<?> params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).in(params));
		return this;
	}

	/**
	 * 在其中
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper in(SerializableFunction<E, R> column, Object[] params) {
		return in(column, Arrays.asList(params));
	}

	/**
	 * 不在其中
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper nin(SerializableFunction<E, R> column, Collection<?> params) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).nin(params));
		return this;
	}

	/**
	 * 不在其中
	 * 
	 * @param column 字段
	 * @param params 参数
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper nin(SerializableFunction<E, R> column, Object[] params) {
		return nin(column, Arrays.asList(params));
	}

	/**
	 * 为空
     *
	 * @param column 字段
	 * @return CriteriaWrapper
	 */
	public <E, R> CriteriaWrapper isNull(SerializableFunction<E, R> column) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).is(null));
		return this;
	}

	/**
	 * 不为空
	 *
	 * @param column 字段
	 */
	public <E, R> CriteriaWrapper isNotNull(SerializableFunction<E, R> column) {
		list.add(Criteria.where(ReflectionUtil.getFieldName(column)).ne(null));
		return this;
	}
	/**
	 * 数组查询
     *
	 * @param arr 数组名
	 * @param column 字段名
	 * @param param 字段值
	 */
	public <E, R> CriteriaWrapper findArray(String arr, SerializableFunction<E, R> column, String param){
		list.add(Criteria.where(arr).elemMatch(Criteria.where(ReflectionUtil.getFieldName(column)).is(param)));
		return this;
	}
	/**
	 * 数组模糊查询
     *
	 * @param arr 数组名
	 * @param column 字段名
	 * @param param 字段值
	 */
	public <E, R> CriteriaWrapper findArrayLike(String arr, SerializableFunction<E, R> column, String param){
		Pattern pattern = Pattern.compile("^.*" + param + ".*$", Pattern.CASE_INSENSITIVE);
		list.add(Criteria.where(arr).elemMatch(Criteria.where(ReflectionUtil.getFieldName(column)).regex(pattern)));
		return this;
	}
}
