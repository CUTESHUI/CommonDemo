package com.shui.utils.mongoHelper.bean;

import com.shui.utils.mongoHelper.reflection.ReflectionUtil;
import com.shui.utils.mongoHelper.reflection.SerializableFunction;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

public class SortBuilder {
	List<Order> orderList = new ArrayList<>();

	public SortBuilder() {
		
	}

//	public SortBuilder(String column, Direction direction) {
//		Order order = new Order(direction, column);
//		orderList.add(order);
//	}

	public SortBuilder(List<Order> orderList) {
		this.orderList.addAll(orderList);
	}

	public <E, R> SortBuilder(SerializableFunction<E, R> column, Direction direction) {
		Order order = new Order(direction, ReflectionUtil.getFieldName(column));
		orderList.add(order);
	}

//	public SortBuilder add(String column, Direction direction) {
//		Order order = new Order(direction, column);
//		orderList.add(order);
//		return this;
//	}

	public <E, R> SortBuilder add(SerializableFunction<E, R> column, Direction direction) {
		Order order = new Order(direction, ReflectionUtil.getFieldName(column));
		orderList.add(order);
		return this;
	}

	public Sort toSort() {
		return Sort.by(orderList);
	}
}
