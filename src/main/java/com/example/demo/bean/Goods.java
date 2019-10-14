package com.example.demo.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Goods implements Serializable{

	private int gid;
	private String gname;
	private BigDecimal gprice;
	private int bfb;
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public BigDecimal getGprice() {
		return gprice;
	}
	public void setGprice(BigDecimal gprice) {
		this.gprice = gprice;
	}
	public int getBfb() {
		return bfb;
	}
	public void setBfb(int bfb) {
		this.bfb = bfb;
	}
	@Override
	public String toString() {
		return "Goods [gid=" + gid + ", gname=" + gname + ", gprice=" + gprice + ", bfb=" + bfb + "]";
	}
	
}
