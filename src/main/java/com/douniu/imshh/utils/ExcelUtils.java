package com.douniu.imshh.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.douniu.imshh.utils.test.CommonUtils;

public class ExcelUtils {

	/**
	 * Sheet复制
	 * 
	 * @param fromSheet
	 * @param toSheet
	 * @param copyValueFlag
	 */
	public static void copySheet(Workbook wb, Sheet fromSheet, Sheet toSheet, boolean copyValueFlag) {
		// 合并区域处理

		mergerRegion(fromSheet, toSheet);
		int index = 0;
		for (Iterator<Row> rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			Row tmpRow = rowIt.next();
			Row newRow = toSheet.createRow(tmpRow.getRowNum());

			CellStyle style = tmpRow.getRowStyle();
			if (style != null)
				newRow.setRowStyle(tmpRow.getRowStyle());

			newRow.setHeight(tmpRow.getHeight());

			// 针对第一行设置行宽
			if (index == 0) {
				int first = tmpRow.getFirstCellNum();
				int last = tmpRow.getLastCellNum();
				for (int i = first; i < last; i++) {
					int w = fromSheet.getColumnWidth(i);
					toSheet.setColumnWidth(i, w + 1);
				}
				toSheet.setDefaultColumnWidth(fromSheet.getDefaultColumnWidth());
			}

			// 行复制
			copyRow(wb, tmpRow, newRow, copyValueFlag);

			index++;
		}
	}

	/**
	 * 行复制功能
	 * 
	 * @param fromRow
	 * @param toRow
	 */
	static void copyRow(Workbook wb, Row fromRow, Row toRow, boolean copyValueFlag) {
		for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
			Cell tmpCell = cellIt.next();

			Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(wb, tmpCell, newCell, copyValueFlag);
		}
	}

	/**
	 * 复制原有sheet的合并单元格到新创建的sheet
	 * 
	 * @param sheetCreat
	 *            新创建sheet
	 * @param sheet
	 *            原有的sheet
	 */
	static void mergerRegion(Sheet fromSheet, Sheet toSheet) {
		int sheetMergerCount = fromSheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergerCount; i++) {

			CellRangeAddress cra = fromSheet.getMergedRegion(i);

			toSheet.addMergedRegion(cra);
		}
	}

	/**
	 * 复制单元格
	 * 
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true则连同cell的内容一起复制
	 */
	public static void copyCell(Workbook wb, Cell srcCell, Cell distCell, boolean copyValueFlag) {

		CellStyle newstyle = wb.createCellStyle();
		// copyCellStyle(srcCell.getCellStyle(), newstyle);
		// distCell.setEncoding(srcCell.);
		newstyle.cloneStyleFrom(srcCell.getCellStyle());
		// 样式
		distCell.setCellStyle(newstyle);
		// 评论
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// 不同数据类型处理
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);

		if (copyValueFlag) {
			if (srcCellType == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				} else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			} else if (srcCellType == Cell.CELL_TYPE_STRING) {
				distCell.setCellValue(srcCell.getRichStringCellValue());
			} else if (srcCellType == Cell.CELL_TYPE_BLANK) {
				// nothing21
			} else if (srcCellType == Cell.CELL_TYPE_BOOLEAN) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			} else if (srcCellType == Cell.CELL_TYPE_ERROR) {
				distCell.setCellErrorValue(srcCell.getErrorCellValue());

			} else if (srcCellType == Cell.CELL_TYPE_FORMULA) {
				distCell.setCellFormula(srcCell.getCellFormula());
			} else { // nothing29
			}
		}
	}

	public static Workbook parseTemplater(String templaterPath, SheetData data) {
		Workbook nwb = null;
		Workbook twb = null;

		try {
			InputStream is = new FileInputStream(templaterPath);
			if (templaterPath.endsWith(".xlsx")) {
				twb = new XSSFWorkbook(is);
				nwb = new XSSFWorkbook();
			} else if (templaterPath.endsWith(".xls")) {
				twb = new HSSFWorkbook(is);
				nwb = new HSSFWorkbook();
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet target = nwb.createSheet("新的数据");
		Sheet src = twb.getSheetAt(0);
		return nwb;
	}

	/**
	 * 写入excel数据
	 * 
	 * @param model
	 *            采用的模板 位置在 src/model/下 模板第一个sheet页必须是模板sheet
	 * @param sheetDatas
	 *            模板数据
	 */

	public static void writeData(String model, OutputStream out, SheetData... sheetDatas) {

		Workbook wb = null;
		try {

			InputStream input = new FileInputStream(model);// ExcelUtils.class.getResourceAsStream("/model/"
															// + model);

			if (input == null) {
				throw new RuntimeException(
						"model excel file load error :/model/" + model + " , check model file is exists !");
			}

			if (model.endsWith(".xlsx"))
				wb = new XSSFWorkbook(input);
			else if (model.endsWith(".xls"))
				wb = new HSSFWorkbook(input);
			else
				throw new RuntimeException("model file format is not valid , this : " + model + " , eg:.xlsx or xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

			throw new RuntimeException("model excel file load error :/model/" + model);
		}

		Sheet source = wb.getSheetAt(0);

		// 就一个的话 直接用模板
		int size = sheetDatas.length;
		for (int i = 0; i < size; i++) {

			if (i == 0) {
				wb.setSheetName(0, sheetDatas[0].getName());

			} else {
				Sheet toSheet = wb.createSheet(sheetDatas[i].getName());
				// 复制格式
				copySheet(wb, source, toSheet, true);
			}

		}

		for (int i = 0; i < size; i++) {
			
			// 写数据
			writeData(sheetDatas[i], wb.getSheetAt(i));
			
			rangeAddress(sheetDatas[i], wb.getSheetAt(i));
		}

		try {
			wb.write(out);
			out.flush();
			// wb.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void writeData(Workbook tmp, OutputStream out, SheetData sheetDatas) {
		writeData(sheetDatas, tmp.getSheetAt(0));
		rangeAddress(sheetDatas, tmp.getSheetAt(0));

		try {
			tmp.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void rangeAddress(SheetData sheetData, Sheet sheet){
		for (CellRangeAddress region : sheetData.getRanges()){
			sheet.addMergedRegion(region);
		}
	}
	
	/**
	 * 向sheet页中写入数据
	 * 
	 * @param values
	 *            数据Map
	 * @param sheet
	 *            sheet
	 */
	public static void writeData(SheetData sheetData, Sheet sheet) {
		List<Row> rows = new ArrayList<Row>();
		// 从sheet中找到匹配符 #{}表示单个 , ${}表示集合,从该单元格开始向下追加
		for (Iterator<Row> rowIt = sheet.rowIterator(); rowIt.hasNext();) {
			Row row = rowIt.next();
			rows.add(row);
		}

		for (Row row : rows) {
			// 取cell
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {

				Cell cell = row.getCell(j);

				// 判断cell的内容是否包含 $ 或者#
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue() != null
						&& (cell.getStringCellValue().contains("$") || cell.getStringCellValue().contains("#"))) {
					// 剥离# $
					String[] winds = CommonUtils.getWildcard(cell.getStringCellValue().trim());

					for (String wind : winds) {

						writeData(sheetData, wind, cell, sheet);
					}

				}

			}
		}
		/*
		 * for(Iterator<Row> rowIt = sheet.rowIterator(); rowIt.hasNext();) {
		 * Row row = rowIt.next(); //取cell for(int j = row.getFirstCellNum() ; j
		 * < row.getLastCellNum() ; j++) {
		 * 
		 * Cell cell = row.getCell(j);
		 * 
		 * //判断cell的内容是否包含 $ 或者# if(cell != null && cell.getCellType() ==
		 * Cell.CELL_TYPE_STRING && cell.getStringCellValue() != null &&
		 * (cell.getStringCellValue().contains("$") ||
		 * cell.getStringCellValue().contains("#") )) { //剥离# $ String[] winds =
		 * CommonUtils.getWildcard(cell.getStringCellValue().trim());
		 * 
		 * for(String wind : winds) {
		 * 
		 * writeData(sheetData, wind , cell , sheet); }
		 * 
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 */
	}

	/**
	 * 填充数据
	 * 
	 * @param values
	 * @param keyWind
	 *            #{name}只替换当前 or ${names} 从当前行开始向下替换
	 */
	static void writeData(SheetData sheetData, String keyWind, Cell cell, Sheet sheet) {
		String key = keyWind.substring(2, keyWind.length() - 1);

		if (keyWind.startsWith("#")) {

			// 简单替换

			Object value = sheetData.get(key);
			// 为空则替换为空字符串
			if (value == null)
				value = "";
			
			if (value instanceof Date){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				value = df.format(value);
			}
			String cellValue = cell.getStringCellValue();
			cellValue = cellValue.replace(keyWind, value.toString());

			cell.setCellValue(cellValue);

		} else if (keyWind.startsWith("$")) {

			// 从list中每个实体开始解,行数从当前开始
			int rowindex = cell.getRowIndex();
			int columnindex = cell.getColumnIndex();

			List<? extends Object> listdata = sheetData.getDatas();

			// 不为空的时候开始填充
			if (listdata != null && !listdata.isEmpty()) {
				boolean firstRow = true;
				for (Object o : listdata) {
					// Object cellValue = CommonUtils.getValue(o, key);
					Object cellValue = ReflectionUtil.getFieldValue(o, key);
					
					Row row = null;
					if (firstRow) {
						row = sheet.getRow(rowindex);
						firstRow = false;
					} else {
						if (sheet.getRow(rowindex) == null) {
							row = createRow(sheet, rowindex);
						} else {
							row = sheet.getRow(rowindex);
						}
					}

					// 取出cell
					Cell c = row.getCell(columnindex);
					if (c == null)
						c = row.createCell(columnindex);
					if (cell.getCellStyle() != null) {
						c.setCellStyle(cell.getCellStyle());

					}

					/*
					 * if(cell.getCellType() != -1) { int type =
					 * cell.getCellType(); c.setCellType(type);
					 * 
					 * }
					 */

					if (cellValue != null) {
						if (cellValue instanceof Number || CommonUtils.isNumber(cellValue))
							c.setCellValue(Double.valueOf(cellValue.toString()));
						else if (cellValue instanceof Boolean)
							c.setCellValue((Boolean) cellValue);
						else if (cellValue instanceof Date){
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							c.setCellValue(df.format(cellValue));
						}
						else
							c.setCellValue(cellValue.toString());
					} else {

						// 数据为空 如果当前单元格已经有数据则重置为空
						if (c.getStringCellValue() != null) {
							c.setCellValue("");
						}

					}

					rowindex++;
				}
			} else {
				// list数据为空则将$全部替换空字符串
				String cellValue = "";

				cell.setCellValue(cellValue);

			}

		}

	}

	private static Row createRow(Sheet sheet, Integer rowIndex) {
		Row row = null;
		if (sheet.getRow(rowIndex) != null) {
			int lastRowNo = sheet.getLastRowNum();
			sheet.shiftRows(rowIndex, lastRowNo, 1);
		}
		row = sheet.createRow(rowIndex);
		return row;
	}

}