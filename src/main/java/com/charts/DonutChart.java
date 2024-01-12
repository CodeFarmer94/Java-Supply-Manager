package com.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;

public abstract class DonutChart implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private DonutChartModel donutModel;
	
	
	
	
	  public DonutChartModel getDonutModel() {
	        donutModel = new DonutChartModel();
	        ChartData data = new ChartData();
	        DonutChartOptions options = new DonutChartOptions();
	        options.setMaintainAspectRatio(false);
	        donutModel.setOptions(options);

	        DonutChartDataSet dataSet = new DonutChartDataSet();
	        List<Number> values = new ArrayList<>();
	        values.add(300);
	        values.add(50);
	        values.add(100);
	        dataSet.setData(values);

	        List<String> bgColors = new ArrayList<>();
	        bgColors.add("rgb(255, 99, 132)");
	        bgColors.add("rgb(54, 162, 235)");
	        bgColors.add("rgb(255, 205, 86)");
	        dataSet.setBackgroundColor(bgColors);

	        data.addChartDataSet(dataSet);
	        List<String> labels = new ArrayList<>();
	        labels.add("Red");
	        labels.add("Blue");
	        labels.add("Yellow");
	        data.setLabels(labels);

	        donutModel.setData(data);
	        return donutModel;
	    }
}
