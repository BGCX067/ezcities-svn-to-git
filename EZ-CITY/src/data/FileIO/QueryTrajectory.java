package data.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import simulation.obj.Agent;
import simulation.obj.Node;
import data.dataManeger.Scene;
import simulation.obj.Trajectory;
import simulation.obj.Trip;

public class QueryTrajectory {

	private Connection conn;
	private Scene sc;
	private String filename;

	public QueryTrajectory() throws Exception {

		conn = DatabaseConn.getConnection();
		System.out.println("connected");

	}

	public void setScene(Scene s) {
		sc = s;
	}

	public void setFilename(String s) {
		filename = s;
	}

	/**
	 * /** jid varchar(16) , cid varchar(20) not null, passtype int not null,
	 * travelmode varchar(8), srvnum varchar(8), // bus transline number
	 * bugregnum int, ??? // still nothing direction bus number int, boardstop
	 * varchar(32), alighstop varchar(32), ridedate varchar(32), ridestarttime
	 * varchar(32), ridedis float not null, fair // the payment transcount // in
	 * 40 mins xs - location ys - location
	 * 
	 */

	/**
	 * 
	 * @return the whole database
	 * @throws Exception
	 * @throws SQLException
	 */
	public ArrayList<Trajectory> getAll() throws Exception, SQLException {

		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stm
				.executeQuery("select * from test.c1 where cid = '2000020000870800' order by ridestart desc");
		return this.getTrajectory(rs);
	}

	/**
	 * 
	 * @return the whole trajectory belongs to certain BUS
	 * @throws Exception
	 * @throws SQLException
	 */
	public ArrayList<Trajectory> getTByBus(String bus) throws SQLException {
		// TODO Auto-generated method stub
		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stm.executeQuery("select * from test.c1 where dir = "
				+ bus + " order by ridestart asc");

		return this.getTrajectory(rs);

	}

	/**
	 * 
	 * @return the whole trajectory belongs to certain transitline and passed
	 *         certain busStop
	 * @throws Exception
	 * @throws SQLException
	 */
	public ArrayList<Trajectory> getTByBuslineAndStop(String bus, String busstop)
			throws Exception, SQLException {

		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stm.executeQuery("select * from test.c1 where svrnum = "
				+ bus + " and onstop = " + busstop + " order by ridestart asc");
		return this.getTrajectory(rs);
	}

	/**
	 * 
	 * @return insert a record into table OnAndOff
	 * @throws Exception
	 * @throws SQLException
	 * @throws IOException
	 */
	public void insertOnAndOff(String stopId, Node node, int[] in, int[] out,
			int date) throws SQLException, IOException {
		// TODO Auto-generated method stub

		conn.setAutoCommit(false);

		Statement stm = conn.createStatement();
		// INSERT INTO tbl_name (a,b,c) VALUES(1,2,3),(4,5,6),(7,8,9);

		// File f = new File("F:\\onandoff.txt");
		// if (f.exists()) {
		// System.out.print("yes");
		// } else {
		// System.out.print("wrong");
		// f.createNewFile();// �?存在则创建
		// }

		String statement = "";
		String writing = "";
		statement += "insert into test.onandoff (busStop,coord_x,coord_y,on0, on1,on2,on3,on4,on5,on6,on7,on8,on9,on10,on11,on12,on13,on14,on15,on16,on17,on18,on19,on20,on21,on22,on23,off0,off1,off2,off3,off4,off5,off6,off7,off8,off9,off10,off11,off12,off13,off14,off15,off16,off17,off18,off19,off20,off21,off22,off23,date) values(";
		writing += stopId + "," + node.getX() + "," + node.getY();
		statement += "'" + stopId + "'," + node.getX() + "," + node.getY()
				+ ",";

		for (int i = 0; i < 24; i++) {
			writing += "," + in[i];
			statement += in[i] + ",";
		}
		for (int i = 0; i < 24; i++) {
			writing += "," + out[i];
			statement += out[i] + ",";
		}

		writing += "\r\n";
		statement += date + ")";

		System.out.println(statement);

		stm.executeUpdate(statement);

		conn.commit();

		// BufferedWriter outr = null;
		// try {
		// outr = new BufferedWriter(new OutputStreamWriter(
		// new FileOutputStream(f, true)));
		// outr.write(writing);
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// outr.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

	}

	/**
	 * 
	 * @return the whole trajectory passed certain busStop
	 * @throws Exception
	 * @throws SQLException
	 * @param type
	 *            1- board 2-alight
	 */
	public ArrayList<Trajectory> getTByBusStop(String busstop, int type)
			throws Exception, SQLException {

		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = null;

		if (type == 1) {
			rs = stm.executeQuery("select * from test.c1 where onstop = "
					+ busstop + " order by ridestart asc");

		} else if (type == 2) {
			rs = stm.executeQuery("select * from test.c1 where offstop = "
					+ busstop + " order by ridestart asc");

		}

		return this.getTrajectory(rs);

	}

	public void outputAgents() {
		// TODO Auto-generated method stub

	}


	// get agent by stop 1- on 2 -off
	public Agent getOnePathByStop(String s, int type) throws SQLException,
			IOException {

		Agent ag = new Agent();

		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs1 = null;
		if (type == 1) {
			rs1 = stm.executeQuery("select * from test.c1 where onstop = " + s);
		} else if (type == 2) {
			rs1 = stm
					.executeQuery("select * from test.c1 where offstop = " + s);
		}
		//
		// String cid = "";
		// while (rs1.next()) {
		// cid = rs1.getString(1);
		// }
		//
		// ResultSet rs2 = stm.executeQuery("select * from test.c1 where cid = "
		// + cid);

		ArrayList<Trajectory> ts = this.getTrajectory(rs1);

		// ag.setAgentId(ts.get(0).getCid());

		for (Trajectory t : ts) {
			if ((t.getAlighstop().equalsIgnoreCase(""))
					|| t.getBoardstop().equalsIgnoreCase("")) {
				break;
			}
			Trip trip = new Trip(sc);

			trip.setTripId(t.getJid());
			t.setSrvnum(t.getSrvnum().trim());
			trip.setTransitlineid(t.getSrvnum()); // must be set
			trip.setBusid(t.getDirection());
			trip.setArrTime(t.getRideStarttime());
			trip.setDis(t.getRidedis());
			trip.setFair(t.getFarpaid());
			int setA = trip.setDepNode(t.getBoardstop());
			if (setA == 0) {
				writefail(t.getJid() + " " + t.getCid() + " " + "no board");

				break;
			}
			int setB = trip.setArrNode(t.getAlighstop());
			if (setB == 0) {

				writefail(t.getJid() + " " + t.getCid() + " " + "no alight");
				break;
			}
		}

		return ag;
	}

	public void endfile() throws IOException {
		File f = new File(filename);
		if (f.exists()) {
			System.out.print("yes");
		} else {
			System.out.print("wrong");
			f.createNewFile();// �?存在则创建
		}

		String record = "END";

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f, true)));
			out.write(record);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void writefail(String w) throws IOException {
		File f = new File(filename);
		if (f.exists()) {
			System.out.print("yes");
		} else {
			System.out.print("wrong");
			f.createNewFile();// �?存在则创建
		}

		String record = w + "\r\n";

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f, true)));
			out.write(record);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	

	// get agent by index
	public Agent getOnePathByAgent(int i, String tablename)
			throws SQLException, IOException {
		Agent ag = new Agent();

		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet rs1 = stm.executeQuery("select * from test." + tablename
				+ " LIMIT " + i + ",1");
		//
		// String cid = "";
		// while (rs1.next()) {
		// cid = rs1.getString(1);
		// }
		//
		// ResultSet rs2 = stm.executeQuery("select * from test.c1 where cid = "
		// + cid);

		ArrayList<Trajectory> ts = this.getTrajectory1(rs1);

		ag.setAgentId(ts.get(0).getCid());

		for (Trajectory t : ts) {
			if ((t.getAlighstop().equalsIgnoreCase(""))
					|| t.getBoardstop().equalsIgnoreCase("")) {
				break;
			}
			Trip trip = new Trip(sc);

			trip.setTripId(t.getJid());
			t.setSrvnum(t.getSrvnum().trim());
			trip.setTransitlineid(t.getSrvnum()); // must be set
			trip.setBusid(t.getDirection());
			trip.setArrTime(t.getRideStarttime());
			trip.setDis(t.getRidedis());
			trip.setFair(t.getFarpaid());
			int setA = trip.setDepNode(t.getBoardstop());
			if (setA == 0) {
				writefail(t.getJid() + " " + t.getCid() + " " + "no board");

				break;
			}
			int setB = trip.setArrNode(t.getAlighstop());
			if (setB == 0) {

				writefail(t.getJid() + " " + t.getCid() + " " + "no alight");
				break;
			}
		//	ag.trips.add(trip);
		}

		return ag;

	}

	// return the number of people
	public int getMaxPeopleId() throws Exception {
		int maxId;
		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stm.executeQuery("select Max(id) from People");
		while (rs.next()) {
			maxId = rs.getInt(1);
			return maxId + 1;
		}
		return 1;
	}

	// used to calculate the number of traffic in one place at a certain time
	public int calVisitTimes(int buildingid, java.sql.Date dateB,
			java.sql.Date dateE) throws Exception {

		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stm
				.executeQuery("select count(s.stopid) from trajectory.stops s where s.buildingid = "
						+ buildingid
						+ "and time > "
						+ dateB
						+ "and time <"
						+ dateE);
		return rs.getInt(1);

	}

	public int QueryByType(String travelmode) throws SQLException {
		// TODO Auto-generated method stub

		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stm
				.executeQuery("select count(*) from test.d7 where travelmodel = "
						+ travelmode);

		return rs.getInt(1);

	}

	public ArrayList<Trajectory> getTrajectory(ResultSet rs)
			throws NumberFormatException, SQLException {

		ArrayList<Trajectory> list = new ArrayList<Trajectory>();
		while (rs.next()) {
			Trajectory t = new Trajectory();
			/**
			 * /** jid varchar(16) , cid varchar(20) not null, passtype int not
			 * null, travelmode varchar(8), srvnum varchar(8), // bus transline
			 * number bugregnum int, ??? // still nothing direction bus number
			 * int, boardstop varchar(32), alighstop varchar(32), ridedate
			 * varchar(32), ridestarttime varchar(32), ridedis float not null,
			 * fair // the payment transcount // in 40 mins xs - location ys -
			 * location
			 * 
			 */
			t.setJid(rs.getString(2).trim());
			t.setCid(rs.getString(3).trim());
			t.setPasstype(rs.getString(4));
			t.setTravelmode(rs.getString(5).trim());

			// String num = rs.getString(5);
			// num = num.trim();
			// num = num.replaceAll("\r", "");
			// // globaldefine = globaldefine.Replace("\n", "");
			// num = num.replaceAll("\t", "");
			t.setSrvnum(rs.getString(6).trim());

			t.setBugregnum(rs.getInt(7));
			t.setDirection(rs.getInt(8));
			t.setBoardstop(rs.getString(9).trim());
			t.setAlighstop(rs.getString(10).trim());
			//
			// String date = rs.getString(10);
			//
			// String di[] = date.split("/");
			// Date javadate = new Date(Integer.parseInt(di[2]),
			// Integer.parseInt(di[1]), Integer.parseInt(di[0]));
			// t.setRidedate(javadate);

			// String time = rs.getString(11);
			// String ti[] = time.split(":");
			//
			// Time javatime = new Time(Integer.parseInt(ti[0]),
			// Integer.parseInt(ti[1]),
			// (int)(Double.parseDouble(ti[2])));//Integer.parseInt(t13[0]
			//
			// t.setRidestarttime(javatime);
			t.setRidestarttime(rs.getInt(11));
			t.setRidedis(rs.getFloat(12));
			// t.setRidetime(rs.getFloat(13));
			t.setFarpaid(rs.getFloat(13));
			t.setTransfernum(rs.getInt(14));

			list.add(t);
		}
		return list;
	}

	public ArrayList<Trajectory> getTrajectory1(ResultSet rs)
			throws NumberFormatException, SQLException {

		ArrayList<Trajectory> list = new ArrayList<Trajectory>();
		while (rs.next()) {
			Trajectory t = new Trajectory();
			/**
			 * /** jid varchar(16) , cid varchar(20) not null, passtype int not
			 * null, travelmode varchar(8), srvnum varchar(8), // bus transline
			 * number bugregnum int, ??? // still nothing direction bus number
			 * int, boardstop varchar(32), alighstop varchar(32), ridedate
			 * varchar(32), ridestarttime varchar(32), ridedis float not null,
			 * fair // the payment transcount // in 40 mins xs - location ys -
			 * location
			 * 
			 */
			t.setJid(rs.getString(1).trim());
			t.setCid(rs.getString(2).trim());
			t.setPasstype(rs.getString(3));
			t.setTravelmode(rs.getString(4).trim());

			t.setSrvnum(rs.getString(5).trim());

			// t.setBugregnum(Integer.parseInt(rs.getString(6).trim()));
			// t.setDirection(Integer.parseInt(rs.getString(7).trim()));
			t.setBoardstop(rs.getString(8).trim());
			t.setAlighstop(rs.getString(9).trim());

			// t.setRidestarttime(Integer.parseInt(rs.getString(10)));
			// t.setRidedis(Float.parseFloat(rs.getString(11)));
			//
			// t.setFarpaid(Float.parseFloat(rs.getString(12)));
			// t.setTransfernum(Integer.parseInt(rs.getString(13)));
			// t.setOnX(Double.parseDouble(rs.getString(14)));
			// t.setOnY(Double.parseDouble(rs.getString(15)));
			list.add(t);
		}
		return list;
	}

	class shapeRecord {
		int shapeid = 0;
		int lineid = 0;
	}

	public ArrayList<String> getStopnameByArea(String areaname)
			throws SQLException {
		// TODO Auto-generated method stub

		ArrayList<String> names = new ArrayList<String>();
		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stm
				.executeQuery("select * from test.stopinareas where areaname = '"
						+ areaname + "'");

		while (rs.next()) {
			String name = rs.getString(1);
			names.add(name);
		}

		return names;

	}

	public void createTablebySelect(String tablename, String select)
			throws SQLException {
		// TODO Auto-generated method stub
		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		String tablecreate = "CREATE TABLE `test`.`" + tablename + "` ("
				+ "`jid` varchar(16) DEFAULT NULL,"
				+ "`cid` varchar(20) NOT NULL," + "`tp` int(16) NOT NULL,"
				+ "`mode` int(16) DEFAULT NULL,"
				+ "`svrnum` varchar(8) DEFAULT NULL,"
				+ "`carnum` int(11) DEFAULT NULL,"
				+ "`dir` int(11) DEFAULT NULL,"
				+ "`onstop` varchar(32) DEFAULT NULL,"
				+ "`offstop` varchar(32) DEFAULT NULL,"
				+ "`ridestart` int(32) DEFAULT NULL,"
				+ "`ridedis` float NOT NULL," + "`fair` float NOT NULL,"
				+ "`transcount` int(11) DEFAULT NULL,"
				+ "`xs` double DEFAULT NULL," + "`ys` double DEFAULT NULL,"
				+ "`xe` double DEFAULT NULL," + "`ye` double DEFAULT NULL)";

		Statement st = conn.createStatement();

		st.execute(tablecreate);
		st.close();

		ResultSet rs2 = stm.executeQuery(select);
		String query = " insert into `test`."
				+ tablename
				+ "(`jid`,`cid`,`tp`,`mode`,`svrnum`, `carnum`,`dir`,`onstop`,`offstop`,`ridestart`,`ridedis`,`fair`,`transcount`,`xs`,`ys`,`xe`,`ye`)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);

		while (rs2.next()) {

			int n = 0;
			preparedStmt.setString(1, rs2.getString(1 + n).trim());
			preparedStmt.setString(2, rs2.getString(2 + n).trim());
			preparedStmt.setInt(3, rs2.getInt(3 + n));
			preparedStmt.setInt(4, rs2.getInt(4 + n));
			preparedStmt.setString(5, rs2.getString(5 + n).trim());
			preparedStmt.setInt(6, rs2.getInt(6 + n));
			preparedStmt.setInt(7, rs2.getInt(7 + n));
			preparedStmt.setString(8, rs2.getString(8 + n).trim());
			preparedStmt.setString(9, rs2.getString(9 + n).trim());
			preparedStmt.setInt(10, rs2.getInt(10 + n));
			preparedStmt.setFloat(11, rs2.getFloat(11 + n));
			preparedStmt.setFloat(12, rs2.getFloat(12 + n));
			preparedStmt.setInt(13, rs2.getInt(13 + n));
			preparedStmt.setDouble(14, rs2.getDouble(14 + n));
			preparedStmt.setDouble(15, rs2.getDouble(15 + n));
			preparedStmt.setDouble(16, rs2.getDouble(16 + n));
			preparedStmt.setDouble(17, rs2.getDouble(17 + n));

			// execute the preparedstatement
			preparedStmt.execute();

		}

	}

	public void queryInterAreas(String[] areas) throws SQLException,
			IOException {
		// TODO Auto-generated method stub
		int n = areas.length;
		int[][] fromTo = new int[n][n];
		Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		for (int i = 46; i < n; i++) {
			for (int j = 42; j < n; j++) {
				ResultSet rs = stm
						.executeQuery("SELECT count(cid) FROM test.c1, test.stopinareas s1, test.stopinareas s2 where (s1.areaname = '"
								+ areas[i]
								+ "' and s1.busid = c1.onstop) and (s2.areaname = '"
								+ areas[j] + "' and s2.busid = c1.offstop);");

				while (rs.next()) {
					int number = rs.getInt(1);
					fromTo[i][j] = number;
					System.out
							.println("i " + i + "j " + j + "number " + number);
				}
			}
		}

		writeMatrix(fromTo, areas);

	}

	private void writeMatrix(int[][] fromTo, String areas[]) throws IOException {
		// TODO Auto-generated method stub

		File f = new File("f:\\data issue\\number.txt");
		if (f.exists()) {
			System.out.print("yes");
		} else {
			System.out.print("wrong");
			f.createNewFile();// �?存在则创建
		}

		String record = "";
		for (int i = 0; i < areas.length; i++) {
			record += areas[i];
			for (int j = 0; j < areas.length; j++) {

				record += " " + fromTo[i][j];
			}
			record += "\r\n";
		}

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f, true)));
			out.write(record);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void simplify() throws SQLException {
		// TODO Auto-generated method stub

		//9000575866160800
		int mark = 10;
	
		for (int i = 0; i < 500000; i++) {
			i++;
			Statement stm = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stm.executeQuery("select * from test.t1 LIMIT "+mark+","+ (mark+1));

			String name = "";
			while (rs.next()) {
				name = rs.getString(3);
				System.out.println(name);
				System.out.println(rs.getInt(1));
			}
			
	//		name = "9000079323700800";

			Statement stm1 = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			ResultSet rs2 = stm1
					.executeQuery("select * from test.t1 where cid = '" + name
							+ "' order by ridestart asc");

			ArrayList<Trajectory> t = this.getTrajectory(rs2);

			int size = t.size();

			if (size == 1) {
				mark++;
				continue;
			}
			
			ArrayList<Trajectory> composed = new ArrayList<Trajectory>();
	
			for (int j = 0; j < size; j++) {
				
				
				if(j == 0){
					
					Trajectory total = new Trajectory();
					total.setJid(t.get(0).getJid());
					total.setCid(t.get(0).getCid());
					total.setBoardstop(t.get(0).getBoardstop());
					total.setPasstype(t.get(0).getPasstype());
					total.setAlighstop(t.get(0).getAlighstop());
					total.setRidestarttime(t.get(0).getRideStarttime());
					total.setFarpaid(0);
					total.setDirection(t.get(0).getDirection());
					total.setTransfernum(1);
					total.setTravelmode(t.get(0).getTravelmode());
					total.setSrvnum(t.get(0).getSrvnum());
					
					composed.add(total);
					
					
					
				}else{
				
				if((t.get(j).getRideStarttime() - t.get(j-1).getRideStarttime())>2400){
					
					Trajectory total = new Trajectory();
					total.setJid(t.get(j).getJid());
					total.setCid(t.get(j).getCid());
					total.setBoardstop(t.get(j).getBoardstop());
					total.setPasstype(t.get(j).getPasstype());
					total.setAlighstop(t.get(j).getAlighstop());
					total.setRidestarttime(t.get(j).getRideStarttime());
					total.setFarpaid(j);
					total.setDirection(t.get(j).getDirection());
					total.setTransfernum(1);
					total.setTravelmode(t.get(j).getTravelmode());
					total.setSrvnum(t.get(j).getSrvnum());
					
					composed.add(total);
				}else{
					composed.get(composed.size()-1).setTransfernum(composed.get(composed.size()-1).getTransfernum()+1);
					composed.get(composed.size()-1).setAlighstop(t.get(j).getAlighstop());
					composed.get(composed.size()-1).setFarpaid(composed.get(composed.size()-1).getFarpaid() + t.get(j).getFarpaid());	
				}
				
				
				}
				
			}
			
			if(composed.size() == size){
				mark++;

				continue;
			}
			
			for (int j = 0; j < size; j++) {

				Statement stmt = conn.createStatement();

				String sql = "DELETE FROM test.t1 WHERE jid = "
						+ t.get(j).getJid();

				// Execute the delete statement
				int deleteCount = stmt.executeUpdate(sql);
				System.out.println("delete one");
			}

			System.out.println(composed.size());
			
			for (Trajectory total : composed) {
				conn.setAutoCommit(false);

				Statement stm3 = conn.createStatement();
				String query = "insert into test.t1"
						+ "(jid,cid,tp,mode,svrnum,carnum,dir,onstop,offstop,ridestart,ridedis,fair,transcount)"
						+ " values ('"
						+ total.getJid()
						+ "','"
						+ total.getCid()
						+ "',"
						+ Integer.parseInt(total.getPasstype())
						+ ","
						+ Integer.parseInt(total.getTravelmode())
						+ ",'"
						+ total.getSrvnum()
						+ "',"
						+ 0
						+ ","
						+ total.getDirection()
						+ ",'"
						+ total.getBoardstop()
						+ "','"
						+ total.getAlighstop()
						+ "',"
						+ total.getRideStarttime()
						+ ","
						+ total.getRidedis()
						+ ","
						+ total.getFarpaid()
						+ ","
						+ total.getTransfernum() + ")";

				String statement1 = "insert into test.onandoff (busStop,coord_x,coord_y,on0, on1,on2,on3,on4,on5,on6,on7,on8,on9,on10,on11,on12,on13,on14,on15,on16,on17,on18,on19,on20,on21,on22,on23,off0,off1,off2,off3,off4,off5,off6,off7,off8,off9,off10,off11,off12,off13,off14,off15,off16,off17,off18,off19,off20,off21,off22,off23,date) values(";

				stm3.executeUpdate(query);

				System.out.println("add one" + total.getJid());

				conn.commit();

			}

		}
	}

}