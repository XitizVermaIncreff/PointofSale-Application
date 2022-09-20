package com.increff.pos.Service;

import com.increff.pos.Dao.OrderDao;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Transactional(rollbackFor = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public ZonedDateTime add() throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        orderPojo.setTime(date);
        orderDao.add(orderPojo);
        return date;

    }

    public OrderPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    public List<OrderPojo> getAll() throws ApiException {
        return orderDao.selectAll();
    }

    public OrderPojo getCheck(Integer id) throws ApiException {
        OrderPojo orderPojo = orderDao.select(id);
        if (isNull(orderPojo)) {
            throw new ApiException("Order with given id does not exist, id : " + id);
        }
        return orderPojo;
    }

    public void updateOrderStatusPlaced(Integer id) throws ApiException
    {
        OrderPojo orderPojo=orderDao.select(id);
        if(isNull(orderPojo))
        {
            throw new ApiException("Order with given id does not exist, id : "+ id);
        }
        orderPojo.setOrderPlaced(true);
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        orderPojo.setTime(date);
    }

}
