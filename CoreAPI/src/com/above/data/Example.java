package com.above.data;

import java.sql.SQLException;

import com.above.exceptions.InvalidDataColumnException;
import com.above.exceptions.InvalidDataRowException;

/**
 * Example class to show Developers how to use the data management system
 * 
 * @author NathanGrad
 *
 */
public class Example {

	public Example() {
		// Create DataTable with a string array of DataColumn names
		DataTable table = new DataTable(new String[] { "Name", "Role", "Age", "Religion" });
		// Create new row in the DataTable
		DataRow row1 = table.addRow();
		try {
			// Set the cell content for the DataColumns to objects
			row1.setCell("Name", "Nathan");
			row1.setCell("Role", "Lead-Developer");
			row1.setCell("Religion", "Atheist");
			row1.setCell("Age", 17);
		} catch (InvalidDataColumnException e) {
			e.printStackTrace();
		}
		// Create new row in the DataTable
		DataRow row2 = table.addRow();
		try {
			// Set the cell content for the DataColumns to objects
			row2.setCell("Name", "Nash");
			row2.setCell("Age", 15);
			row2.setCell("Religion", "Christian");
			row2.setCell("Role", "Manager");
		} catch (InvalidDataColumnException e) {
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		int counter = 0;
		// For all the DataRows within the DataTable...
		for (@SuppressWarnings("unused")
		DataRow r : table.getRows()) {
			// Get all the column names and loop through them to get the objects
			for (DataColumn c : table.getColumns()) {
				try {
					// Get the row with the 0-based index within the DataTable
					// and get the cell with the column name of the DataColumn
					// that's currently specified within the loop
					builder.append(c.getName() + ": " + table.getRow(counter).getCell(c.getName()) + "\n");
				} catch (InvalidDataColumnException | InvalidDataRowException e) {
					e.printStackTrace();
				}
			}
			builder.append("\n");
			counter++;
		}
		// System.out.println(builder.toString());
		try {
			dbExample();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void dbExample() throws SQLException {
		MySqlManager mysql = new MySqlManager();
		mysql.open();
		DataTable table = mysql.executeQuery("SELECT * FROM PunishmentHistory");
		mysql.close();
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		int counter = 0;
		for (DataRow r : table.getRows()) {
			for (DataColumn c : table.getColumns()) {
				try {
					builder.append(c.getName() + ": " + table.getRow(counter).getCell(c.getName()) + "\n");
				} catch (InvalidDataColumnException | InvalidDataRowException e) {
					e.printStackTrace();
				}
			}
			builder.append("\n");
			counter++;
		}
		System.out.println(builder.toString());
	}
}
