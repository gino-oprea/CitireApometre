package com.gino.citireapometre.DB;

import java.util.List;

public class ConsumListHelper
{
	private List<Consum> listConsum;
	private String date;
	
	public String getData()
	{
		return date;
	}
	public void setData(String date)
	{
		this.date = date;
	}
	public List<Consum> getListConsum()
	{
		return listConsum;
	}
	public void setListConsum(List<Consum> listConsum)
	{
		this.listConsum = listConsum; 	
	}
}
