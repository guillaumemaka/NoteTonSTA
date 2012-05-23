package com.supinfo.notetonsta.dao;


import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.MarkCreationException;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;

public interface MarkDao {
	public Mark find(Key markKey);
	public List<Mark> findAll();
	public Mark addMark(Mark mark) throws MarkCreationException;
	public void removeMark(Mark mark);
	public Object getStatsForIntervention(Intervention intervention);
	//public PieChartDataSet getDataSetChartForIntervention(Intervention intervention) throws ChartDataException;
}
