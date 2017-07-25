package com.mmall.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 订单实体类
 * 
 * @author ligang.sun
 *
 */
public class Order implements Serializable{

	private static final long serialVersionUID = -3221456837442044219L;
	
	private int id;
	private Long orderNo;
	private int userId;	
	private int shippingId;
	private float payment; // 实际付款金额,单位是元,保留两位小数
	private int paymentType; //支付类型,1-在线支付
	private int postage; //运费,单位是元
	private int status; //订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date paymentTime; //支付时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sendTime; //发货时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime; //交易完成时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date closeTime; //交易关闭时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime; 
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getShippingId() {
		return shippingId;
	}
	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public int getPostage() {
		return postage;
	}
	public void setPostage(int postage) {
		this.postage = postage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	

}
