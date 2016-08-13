package com.above.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.above.exceptions.InvalidDataColumnException;

/**
 * Manager for any MySQL connections
 * 
 * @author NathanGrad
 *
 */
public class MySqlManager {

	private static String url = "jdbc:mysql://na.db.tigernode.com/mcraft28";
	private static String user = "mcraft28";
	private static String password = "537cd02c94";

	private Connection con;
	private Statement st;
	private ResultSet rs;

	/**
	 * Open up a new connection to the MySQL server
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		con = DriverManager.getConnection(url, user, password);
	}

	/**
	 * Execute a query and return a DataTable with the contents requested
	 * 
	 * @param sql
	 *            The SQL query which you want to execute
	 * @return The DataTable response with the requested row(s)
	 * @throws SQLException
	 */
	public DataTable executeQuery(String sql) throws SQLException {
		st = con.createStatement();
		rs = st.executeQuery(sql);
		DataTable dt = null;
		String[] col = null;
		while (rs.next()) {
			if (rs.isFirst()) {
				List<String> columns = new ArrayList<String>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					columns.add(rs.getMetaData().getColumnName(i));
				}
				col = new String[columns.size()];
				for (int i = 0; i < columns.size(); i++) {
					col[i] = columns.get(i);
				}
				dt = new DataTable(col);
			}
			DataRow row = dt.addRow();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				try {
					row.setCell(col[i - 1], rs.getObject(i));
				} catch (InvalidDataColumnException e) {
					e.printStackTrace();
				}
			}
		}
		return dt;
	}

	/**
	 * Execute an SQL statement without the intent to return a response
	 * 
	 * @param sql
	 *            The SQL statement you want to execute
	 * @throws SQLException
	 */
	public void executeNonQuery(String sql) throws SQLException {
		st = con.createStatement();
		st.executeUpdate(sql);
	}

	/**
	 * Close the SQL connection
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
		}
	}
}