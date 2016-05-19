package org.tinygroup.officeindexsource.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.templateindex.impl.TemplateDocumentCreator;

/**
 * 抽象excel的索引数据源基类
 * 
 * @author yancheng11334
 * 
 */
public abstract class AbstractExcelIndexSource {

	protected final static int UNINIT = -1;
	/**
	 * excel字段定义，如果有多个采用英文逗号分隔
	 */
	private String fields;

	/**
	 * 数据开始行
	 */
	private int firstRow = UNINIT;

	/**
	 * 数据结束行
	 */
	private int lastRow = UNINIT;

	/**
	 * 数据开始列
	 */
	private int firstCol = UNINIT;

	/**
	 * 数据结束列
	 */
	private int lastCol = UNINIT;

	private TemplateDocumentCreator templateDocumentCreator;

	public String[] getFieldNames() {
		return fields == null ? new String[0] : fields.split(",");
	}

	public String getFieldName(int index) {
		return getFieldNames()[index];
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public int getFirstCol() {
		return firstCol;
	}

	public void setFirstCol(int firstCol) {
		this.firstCol = firstCol;
	}

	public int getLastCol() {
		return lastCol;
	}

	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}

	public TemplateDocumentCreator getTemplateDocumentCreator() {
		return templateDocumentCreator;
	}

	public void setTemplateDocumentCreator(
			TemplateDocumentCreator templateDocumentCreator) {
		this.templateDocumentCreator = templateDocumentCreator;
	}

	/**
	 * 返回批量的索引文档列表
	 * 
	 * @param type
	 * @param path
	 * @param input
	 * @return
	 */
	public List<Document> getDocument(String type, String path,
			InputStream input) {
		List<Document> docs = new ArrayList<Document>();
		Workbook wb = null;
		try {
			wb = createWorkbook(input);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i);
				dealSheet(sheet, type, docs);
			}
		} catch (IOException e) {
			throw new FullTextException(String.format("读取excel文件[%s]发生异常:",
					path), e);
		} catch (Exception e) {
			throw new FullTextException(String.format(
					"加载excel文件[%s]为索引文档时发生异常:", path), e);
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					throw new FullTextException(String.format(
							"释放excel文件资源[%s]发生异常:", path), e);
				}
			}
		}

		return docs;
	}

	/**
	 * 处理分页
	 * 
	 * @param sheet
	 * @param type
	 * @param docs
	 */
	protected void dealSheet(Sheet sheet, String type, List<Document> docs) {
		int firstRow = getFirstRow() == UNINIT ? sheet.getFirstRowNum()
				: getFirstRow();
		int lastRow = getLastRow() == UNINIT ? sheet.getLastRowNum()
				: getLastRow();
		for (int i = firstRow; i <= lastRow; i++) {
			Row row = sheet.getRow(i);
			int firstCol = getFirstCol() == UNINIT ? row.getFirstCellNum()
					: getFirstCol();
			int lastCol = getLastCol() == UNINIT ? row.getLastCellNum()
					: getLastCol();
			Context context = new ContextImpl();
			context.put(FullTextHelper.getStoreType(), type);
			for (int j = firstCol; j < lastCol; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				String name = getFieldName(j - firstCol);
				updateContext(cell,name,context);
			}
			docs.add(getTemplateDocumentCreator().execute(context));
		}
	}

	private void updateContext(Cell cell, String name, Context context) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: {
			context.put(name, cell.getNumericCellValue());
			break;
		}
		case Cell.CELL_TYPE_STRING: {
			context.put(name, cell.getStringCellValue());
			break;
		}
		case Cell.CELL_TYPE_FORMULA: {
			context.put(name, cell.getCellFormula());
			break;
		}
		case Cell.CELL_TYPE_BLANK: {
			context.put(name, "");
			break;
		}
		case Cell.CELL_TYPE_BOOLEAN: {
			context.put(name, cell.getBooleanCellValue());
			break;
		}
		case Cell.CELL_TYPE_ERROR: {
			context.put(name, cell.getErrorCellValue());
			break;
		}
		default: {
			context.put(name, cell.getStringCellValue());
		}
		}
	}

	protected abstract Workbook createWorkbook(InputStream input)
			throws IOException;
}
