package com.taunton.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.Book;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Remark;
import com.taunton.javabean.Trade;
import com.taunton.web.CriteriaBook;
import com.taunton.web.CriteriaHandle;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.CriteriaTrade;
import com.taunton.web.Page;

public interface HandleDAO {

	/**
	 * 新增一条处理记录
	 * @param handle
	 * @throws Exception
	 */
	public void addHandle(Handle handle) throws Exception;
	/**
	 *根据处理编号id删除一条处理记录
	 * @param handleid
	 * @throws Exception
	 */
	public void deleteHandle(String handleid) throws Exception;
	/**
	 * 根据订单处理记录的功能，状态，及订单id更新订单处理记录的有效状态
	 * @param isValid
	 * @param operate
	 * @param status
	 * @param tradeid
	 * @throws Exception
	 */
	public void updateIsValidWithOperateAndStatusAndTradeIdTradeId(Integer isValid,Integer operate,Integer status,Integer isValid_,String tradeid,String updateTime) throws Exception;
	
	/**
	 * 根据处理编号id更新该条记录状态
	 * @param status
	 * @param updateTime
	 * @param handleid
	 * @throws Exception
	 */
	public void updateHandleStatusWithHandleId(Integer status,String updateTime,String handleid) throws Exception;
	/**
	 * 根据处理编号id更新处理记录更新时间
	 * @param updateTime
	 * @param handleid
	 * @throws Exception
	 */
	public void updateUpdateTimeWithHandleId(String updateTime,String handleid) throws Exception;
	/**
	 * 根据处理编号id获取一条处理记录
	 * @param handleid
	 * @return
	 * @throws Exception
	 */
	public Handle getHandleWithHandleId(String handleid) throws Exception;
	/**
	 * 根据处理记录功能、状态、是否有效及前台用户id获取处理记录数量
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public Long getNumberWithOperateAndStatusAndIsValidAndUserId(Integer operate,Integer status,Integer isValid,String userid) throws Exception;
	/**
	 * 根据处理记录功能、状态、是否有效及订单id获取处理记录数量
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param tradeid
	 * @return
	 * @throws Exception
	 */
	public Long getNumberWithOperateAndStatusAndIsValidAndTradeId(Integer operate,Integer status,Integer isValid,String tradeid) throws Exception;
	/**
	 * 根据订单处理记录功能、状态、是否有效及订单id获取对应的订单处理记录集合
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param tradeid
	 * @return
	 * @throws Exception
	 */
	public Set<Handle> getHandleListWithOperateAndStatusAndIsValidAndTradeId(Integer operate,Integer status,Integer isValid,String tradeid) throws Exception;
	/**
	 * 根据订单处理记录功能、状态、是否有效及订单处理记录所属后台用户id获取相应的订单处理记录集合
	 * @param operate 订单处理记录功能（1.发货，2.退款）为null时查询所有功能
	 * @param status 订单处理记录状态（1.已完成，2.未完成） 为null时查询所有状态
	 * @param isValid　订单处理记录有效状态
	 * @param backStageUserid 
	 * @return
	 * @throws Exception
	 */
	public Set<Handle> getHandleListWithOperateAndStatusAndIsValidAndBackStageUserId(Integer operate,Integer status,Integer isValid,String backStageUserid) throws Exception;
	/**
	 * 根据订单处理记录功能、状态、是否有效及订单id获取最近的一次订单处理记录
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param tradeid
	 * @return
	 * @throws Exception
	 */
	public Handle getRecentlyHandleWithOperateAndStatusAndIsValidAndTradeId(Integer operate,Integer status,Integer isValid,String tradeid) throws Exception;
	/**
	 * 根据订单处理记录功能获取的订单处理记录最小未完成比率
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	public double getMinRatio(Integer operate) throws Exception;
	/**
	 * 根据订单处理记录功能获取的订单处理记录最近最小创建时间（最小未完成比率是其前提）
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	public double getMinCloseTime(Integer operate) throws Exception;
	/**
	 *  自动分单分配到的最佳后台用户
	 * @param operate 为订单处理记录的什么功能进行自动分单（1.发货，2.退款）
	 * @return
	 * @throws Exception
	 */
	public Set<Handle> getAssignedBackStageUserId4Trade(Integer operate) throws Exception;
	
	/**
	 * 根据订单处理记录的状态及功能获取订单处理记录page
	 * @param ch
	 * @return
	 * @throws SQLException
	 */
	public Page<Handle> getPageWithStatusAndOperate(CriteriaHandle ch) throws SQLException ;
	/**
	 * 根据订单处理记录的状态及功能获取订单处理记录总条数
	 * @param ct
	 * @return
	 * @throws SQLException
	 */
	public long getTotalHandleNumberWithStatusAndOperate(CriteriaHandle ch) throws SQLException ;
	/**
	 * 根据订单处理记录的状态及功能获取订单处理记录的集合
	 * @param ct
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<Handle> getPageListWithStatusAndOperate(CriteriaHandle ch, int pageSize) throws SQLException ;

}
