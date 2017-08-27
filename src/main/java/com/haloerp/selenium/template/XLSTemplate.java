package com.haloerp.selenium.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haloerp.selenium.Action;
import com.haloerp.selenium.ActionFactory;
import com.haloerp.selenium.TestcaseTemplate;

import jxl.Workbook;
import jxl.biff.drawing.ComboBox;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * 
 * @author Halo_Chen
 *
 */
public class XLSTemplate implements TestcaseTemplate {

	public void generate(String filename) {

		try {
			File file = new File(filename);
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet help = workbook.createSheet("Help", 0);

			int actionCount = writeHelp(help);
			workbook.addNameArea("commandlist", help, 1, 1, 1, actionCount);

			// WritableSheet customCommand =
			// workbook.createSheet("CustomAction", 0);
			// writeCustomCommand(customCommand);

			WritableSheet s2 = workbook.createSheet("s2", 0);
			writeSenrio(s2);

			WritableSheet s1 = workbook.createSheet("s1", 0);
			writeSenrio(s1);

			WritableSheet testcases = workbook.createSheet("Testcases", 0);
			writeTestcase(testcases, s1, s2);

			WritableSheet config = workbook.createSheet("Config", 0);
			writeConfig(config);

			workbook.write();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void writeConfig(WritableSheet sheet) throws WriteException {
		sheet.setColumnView(0, 8);
		sheet.setColumnView(1, 8);

		WritableCellFormat bggray = new WritableCellFormat();
		// bggray.setAlignment(Alignment.CENTRE);
		bggray.setBackground(jxl.format.Colour.GRAY_25);

		Label title0 = new Label(0, 0, "Browser", bggray);
		Label title1 = new Label(0, 1, "Report", bggray);

		sheet.addCell(title0);
		sheet.addCell(title1);

		Label value0 = new Label(1, 0, "IE");
		Label value1 = new Label(1, 1, "Html");

		sheet.addCell(value0);
		sheet.addCell(value1);
	}

	private void writeTestcase(WritableSheet sheet, WritableSheet s1, WritableSheet s2) throws WriteException {
		WritableCellFormat bggray = new WritableCellFormat();
		bggray.setAlignment(Alignment.CENTRE);
		bggray.setBackground(jxl.format.Colour.GRAY_25);

		sheet.setColumnView(0, 4);
		sheet.setColumnView(1, 9);
		sheet.setColumnView(2, 12);
		sheet.setColumnView(3, 40);
		sheet.setColumnView(4, 8);

		Label head0 = new Label(0, 0, "NO.", bggray);
		Label head1 = new Label(1, 0, "Test Name", bggray);
		Label head2 = new Label(2, 0, "Scenrio Sheet", bggray);
		Label head3 = new Label(3, 0, "Description", bggray);
		Label head4 = new Label(4, 0, "Status", bggray);

		sheet.addCell(head0);
		sheet.addCell(head1);
		sheet.addCell(head2);
		sheet.addCell(head3);
		sheet.addCell(head4);

		jxl.write.Number col0 = new jxl.write.Number(0, 1, 1);
		Label col1 = new Label(1, 1, "TC01");
		WritableHyperlink col2 = new WritableHyperlink(2, 1, "s1", s1, 0, 0);

		Label col3 = new Label(3, 1, "Create new request and validate approvers");
		Label col4 = new Label(4, 1, "untest");

		sheet.addCell(col0);
		sheet.addCell(col1);
		sheet.addHyperlink(col2);
		sheet.addCell(col3);
		sheet.addCell(col4);

		col0 = new jxl.write.Number(0, 2, 2);
		col1 = new Label(1, 2, "TC02");
		col2 = new WritableHyperlink(2, 2, "s2", s2, 0, 0);
		col3 = new Label(3, 2, "Query Request");
		col4 = new Label(4, 2, "untest");

		sheet.addCell(col0);
		sheet.addCell(col1);
		sheet.addHyperlink(col2);
		sheet.addCell(col3);
		sheet.addCell(col4);

	}

	private void writeSenrio(WritableSheet sheet) throws RowsExceededException, WriteException {
		WritableCellFormat bggray = new WritableCellFormat();
		bggray.setAlignment(Alignment.CENTRE);
		bggray.setBackground(jxl.format.Colour.GRAY_25);

		sheet.setColumnView(0, 4);
		sheet.setColumnView(1, 26);
		sheet.setColumnView(2, 30);
		sheet.setColumnView(3, 30);
		sheet.setColumnView(4, 8);
		sheet.setColumnView(5, 30);
		sheet.setColumnView(6, 16);

		Label head0 = new Label(0, 0, "NO.", bggray);
		Label head1 = new Label(1, 0, "Command", bggray);
		Label head2 = new Label(2, 0, "Target", bggray);
		Label head3 = new Label(3, 0, "Value", bggray);
		Label head4 = new Label(4, 0, "Status", bggray);
		Label head5 = new Label(5, 0, "Log Message", bggray);
		Label head6 = new Label(6, 0, "ScreenshotLink", bggray);

		sheet.addCell(head0);
		sheet.addCell(head1);
		sheet.addCell(head2);
		sheet.addCell(head3);
		sheet.addCell(head4);
		sheet.addCell(head5);
		sheet.addCell(head6);

		jxl.write.Number col0 = new jxl.write.Number(0, 1, 10);
		Label col1 = new Label(1, 1, "OpenUrl");
		Label col2 = new Label(2, 1, "");
		Label col3 = new Label(3, 1, "http://localhost");
		Label col4 = new Label(4, 1, "untest");

		sheet.addCell(col0);
		sheet.addCell(col1);
		sheet.addCell(col2);
		sheet.addCell(col3);
		sheet.addCell(col4);
		
		ComboBox comboBox = new ComboBox();

		for (int row = 2; row < 31; row++) {
			jxl.write.Number no = new jxl.write.Number(0, row, (row) * 10);
			sheet.addCell(no);

			WritableCellFeatures wcf = new WritableCellFeatures();
			//wcf.setDataValidationRange("commandlist");
			wcf.setComboBox(comboBox);
			 
			Label cmd = new Label(1, row, "");
			cmd.setCellFeatures(wcf);
			sheet.addCell(cmd);
		}
	}

	private int writeHelp(WritableSheet sheet) throws RowsExceededException, WriteException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		// Action
		// WritableCellFormat wc = new WritableCellFormat();
		// wc.setAlignment(Alignment.CENTRE); // center
		// wc.setBorder(Border.ALL, BorderLineStyle.THIN); // border
		// wc.setBackground(jxl.format.Colour.GRAY_80); // background
		// Label title = new Label(0, 0, "Action Reference:", wc);
		// sheet.addCell(title);

		WritableCellFormat bggray = new WritableCellFormat();
		bggray.setAlignment(Alignment.CENTRE);
		bggray.setBackground(jxl.format.Colour.GRAY_25);

		sheet.setColumnView(0, 8);
		sheet.setColumnView(1, 26);
		sheet.setColumnView(2, 26);
		sheet.setColumnView(3, 26);
		sheet.setColumnView(4, 30);

		Label head0 = new Label(0, 0, "Category", bggray);
		Label head1 = new Label(1, 0, "Command List", bggray);
		Label head2 = new Label(2, 0, "Example Target", bggray);
		Label head3 = new Label(3, 0, "Exampe Value", bggray);
		Label head4 = new Label(4, 0, "Description", bggray);

		sheet.addCell(head0);
		sheet.addCell(head1);
		sheet.addCell(head2);
		sheet.addCell(head3);
		sheet.addCell(head4);

		Label actionText = null;

		Map<String, List<String>> packages = new HashMap<String, List<String>>();
		List<String> packageNames = new ArrayList<String>();
		for (Class<? extends Action> action : ActionFactory.getActionAll().values()) {
			String packageName = action.getPackage().getName();
			// Field helpfield = action.getField("help");
			// String helptext = helpfield.get(action).toString();
			List<String> l = packages.get(packageName);
			if (l == null) {
				l = new ArrayList<String>();
				packages.put(packageName, l);
				packageNames.add(packageName);
			}
			l.add(action.getSimpleName());
		}

		packageNames.sort(null);

		WritableCellFormat wc2 = new WritableCellFormat();
		wc2.setBackground(jxl.format.Colour.GRAY_25);

		int row = 0;
		for (String p : packageNames) {
			List<String> a = packages.get(p);

			p = p.substring(p.lastIndexOf(".") + 1);

			a.sort(null);
			for (String ac : a) {
				actionText = new Label(0, ++row, p);
				sheet.addCell(actionText);

				actionText = new Label(1, row, ac);
				sheet.addCell(actionText);
			}
		}

		return row;
	}

	private void writeExample(WritableSheet help) throws RowsExceededException, WriteException {
		// Text
		Label labelC = new Label(0, 0, "Action Reference:");
		help.addCell(labelC);

		// Formatting
		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, true);
		WritableCellFormat wcfF = new WritableCellFormat(wf);
		Label labelCF = new Label(0, 1, "Format", wcfF);
		help.addCell(labelCF);

		// Color Formatting
		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
		WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		Label labelCFC = new Label(0, 2, "This is a Label Cell", wcfFC);
		help.addCell(labelCFC);

		// number
		jxl.write.Number labelN = new jxl.write.Number(0, 3, 3.1415926);
		help.addCell(labelN);

		// formatting number
		NumberFormat nf = new NumberFormat("#.##");
		WritableCellFormat wcfN = new WritableCellFormat(nf);
		jxl.write.Number labelNF = new jxl.write.Number(0, 4, 3.1415926, wcfN);
		help.addCell(labelNF);

		// boolean
		jxl.write.Boolean labelB = new jxl.write.Boolean(0, 5, false);
		help.addCell(labelB);

		// DateTime
		DateTime labelDT = new DateTime(0, 6, new java.util.Date());
		help.addCell(labelDT);

		// formatting DateTime
		DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
		WritableCellFormat wcfDF = new WritableCellFormat(df);
		DateTime labelDTF = new DateTime(0, 7, new Date(), wcfDF);
		help.addCell(labelDTF);

		// Formula
		Formula formula = new Formula(0, 8, "SUM(A4:A5)");
		help.addCell(formula);

		// Image
		// WritableImage wrimage = new WritableImage(0, 9, 10, 10, new
		// File("D:\\img_3729.jpg"));
		// help.addImage(wrimage);
		Label labelT = new Label(0, 9, "image");
		help.addCell(labelT);

		// merge cell
		help.mergeCells(0, 10, 2, 12);
		Label label = new Label(0, 10, "mergeCells");
		help.addCell(label);

		// cell format
		WritableCellFormat wc = new WritableCellFormat();
		wc.setAlignment(Alignment.CENTRE); // center
		wc.setBorder(Border.ALL, BorderLineStyle.THIN); // border
		wc.setBackground(jxl.format.Colour.RED); // background
		label = new Label(0, 13, "Font", wc);
		help.addCell(label);

		// cell font
		WritableFont wfont = new WritableFont(WritableFont.TIMES, 20);
		WritableCellFormat font = new WritableCellFormat(wfont);
		label = new Label(0, 14, "Times", font);
		help.addCell(label);
	}

}
