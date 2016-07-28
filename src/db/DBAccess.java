package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import graph.form.GraphBean;
import graph.form.GraphForm;
import login.form.LoginForm;
import menu.form.MenuForm;
import menu.form.ProductBean;
import order.form.OrderBean;
import order.form.OrderForm;

public class DBAccess {


	/**********************************ログインページ*************************************/

	/**
	 * ログイン情報の検索をするメソッド
	 * 検索情報があればメニュー画面へ遷移し、なければエラーを表示する
	 *
	 * @param frm 入力されたログイン情報
	 * @param con DBコネクション
	 * @return 入力データと一致するログインデータがあればtrueを返す。無ければfalse
	 * @throws Exception 引数がNULL,または値が不正な場合
	 */
	public boolean isExistLoginInfo (LoginForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT LOGIN, PASSWD FROM LOGIN WHERE LOGIN = ? AND PASSWD = ?");
			stmt.setString(1, frm.getLogin());
			stmt.setString(2, frm.getPasswd());
			rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * 入力されたログイン登録情報をLOGINテーブルへ登録する
	 *
	 * @param frm 入力されたログイン情報
	 * @param con
	 * @throws Exception
	 */
	public void insertLoginInfo (LoginForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO LOGIN (LOGIN, PASSWD) VALUES (?, ?)");
			stmt.setString(1, frm.getLogin());
			stmt.setString(2, frm.getPasswd());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}


	/**
	 * LOGINテーブルへアクセスし、ログインID（LOGIN）の重複チェックをするメソッド
	 *
	 * @param frm 入力されたログイン情報
	 * @param con DBコネクション
	 * @return 重複データがあればtrue、無ければfalseを返す
	 * @throws Exception
	 */
	public boolean isExistLoginID (LoginForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT LOGIN, PASSWD FROM LOGIN WHERE LOGIN = ?");
			stmt.setString(1, frm.getLogin());
			rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ログインIDを取得するメソッド
	 * ログインアカウント毎に使用している商品リストや顧客リストの読み込み時に必要
	 *
	 * @param frm 入力されたログイン情報
	 * @param con DBコネクション
	 * @return
	 * @throws Exception
	 */
	public int serchLoginID (LoginForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			stmt = con.prepareStatement("SELECT ID FROM LOGIN WHERE LOGIN = ? AND PASSWD = ?");
			stmt.setString(1, frm.getLogin());
			stmt.setString(2, frm.getPasswd());
			rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			return id;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**********************************メニュー編集ページ*************************************/


	/**
	 * productテーブルから食べ物に商品区分された（PRODUCT__TYPE = '1'）データのみを抽出するメソッド
	 *
	 * @param id 商品ID
	 * @param con DBコネクション
	 * @return
	 * @throws Exception
	 */
	public List<ProductBean> serchFoodProductInfo (int id, Connection con) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ProductBean> list = new ArrayList<ProductBean>();
		try {
			stmt = con.prepareStatement("SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_TYPE, PRICE "
					+ "FROM PRODUCT INNER JOIN LOGIN "
					+ "ON PRODUCT.ID = LOGIN.ID "
					+ "WHERE PRODUCT.ID = ? AND PRODUCT_TYPE = '1'");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductBean bn = new ProductBean();
				bn.setProductID(rs.getInt(1));
				bn.setProductname(rs.getString(2));
				bn.setProducttype(rs.getInt(3));
				bn.setPrice(rs.getInt(4));
				list.add(bn);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 * テーブルから飲み物に商品区分された（PRODUCT__TYPE = '2'）データのみを抽出するメソッド
	 *
	 * @param id 商品ID
	 * @param con DBコネクション
	 * @return
	 * @throws Exception
	 */
	public List<ProductBean> serchDrinkProductInfo (int id, Connection con) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ProductBean> list = new ArrayList<ProductBean>();
		try {
			stmt = con.prepareStatement("SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_TYPE, PRICE "
					+ "FROM PRODUCT INNER JOIN LOGIN "
					+ "ON PRODUCT.ID = LOGIN.ID "
					+ "WHERE PRODUCT.ID = ? AND PRODUCT_TYPE = '2'");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductBean bn = new ProductBean();
				bn.setProductID(rs.getInt(1));
				bn.setProductname(rs.getString(2));
				bn.setProducttype(rs.getInt(3));
				bn.setPrice(rs.getInt(4));
				list.add(bn);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 * 追加された商品データをproductテーブルへ登録するメソッド
	 *
	 * @param frm 入力された商品データ（商品名、商品区分、価格）
	 * @param id ログインID
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void insertNewProductInfo (MenuForm frm, int id, Connection con) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_TYPE, PRICE, ID) "
					+ "VALUES (?, ?, ?, ?)");
			stmt.setString(1, frm.getProductname());
			stmt.setInt(2, frm.getProducttype());
			stmt.setString(3, frm.getPrice());
			stmt.setInt(4, id);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * productテーブルの選択された商品データを修正するメソッド
	 * product_IDと一致する商品データを修正
	 *
	 * @param frm 選択されたproductID、商品名、商品区分、価格データ
	 * @param id ログインID
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void updProductInfo (MenuForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE PRODUCT SET PRODUCT_NAME = ?, PRODUCT_TYPE = ?, PRICE = ? "
					+ "WHERE PRODUCT_ID = ?");
			stmt.setString(1, frm.getProductname());
			stmt.setInt(2, frm.getProducttype());
			stmt.setString(3, frm.getPrice());
			stmt.setInt(4, frm.getProductID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ORDER_Iテーブルから選択された商品データを削除するメソッド
	 * product_IDと一致する商品データを削除する
	 *
	 * @param frm 選択されたproductID
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void deleteProductInfoOrder_I (MenuForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("DELETE FROM ORDER_I WHERE PRODUCT_ID = ?");
			stmt.setInt(1, frm.getProductID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * productテーブルから選択された商品データを削除するメソッド
	 * product_IDと一致する商品データを削除する
	 *
	 * @param frm 選択されたproductID
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void deleteProductInfoProduct (MenuForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("DELETE FROM PRODUCT WHERE PRODUCT_ID = ?");
			stmt.setInt(1, frm.getProductID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**********************************注文ページ*************************************/


	/**
	 * ORDER_HテーブルからログインされたアカウントのIDと一致し、且つ、未会計（会計日時がNULL）の顧客リスト探す
	 *
	 * @param con DBコネクション
	 * @param id ログインしたアカウントのID
	 * @return 顧客の名前とORDER_IDを取得し、LISTへセットし、返す
	 * @throws Exception
	 */
	public List<OrderBean> serchOrderGuestList (Connection con, int id) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<OrderBean> list = new ArrayList<OrderBean>();
		try {
			stmt = con.prepareStatement("SELECT GUEST_NAME, ORDER_ID, AMOUNT "
					+ "FROM ORDER_H INNER JOIN LOGIN "
					+ "ON LOGIN.ID = ORDER_H.ID "
					+ "WHERE LOGIN.ID = ? AND IN_DATE is NULL;");
			stmt.setObject(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				OrderBean bn = new OrderBean();
				bn.setGuestname(rs.getString(1));
				bn.setOrderID(rs.getInt(2));
				bn.setTotalfee(rs.getInt(3));
				list.add(bn);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 * 入力された顧客名をORDER_Hテーブルに保存するメソッド
	 * 注文リストに表示させる
	 *
	 * @param frm 顧客名（guestName）
	 * @param con DBコネクション
	 * @param id ログインID
	 * @throws Exception
	 */
	public void insertNewGuest (OrderForm frm, Connection con, int id) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO ORDER_H (GUEST_NAME, ID) "
					+ "VALUES (?, ?)");
			stmt.setString(1, frm.getGuestname());
			stmt.setObject(2, id);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ORDER_Iテーブルから削除指定された顧客の注文データを削除するメソッド
	 *
	 * @param con DBコネクション
	 * @param frm 選択されたORDER_ID
	 * @throws Exception
	 */
	public void deleteGuestRequestProduct (Connection con, OrderForm frm) throws Exception {

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("DELETE FROM ORDER_I WHERE ORDER_ID = ?");
			stmt.setInt(1, frm.getOrderID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ORDER_Hテーブルから選択された顧客データを削除するメソッド
	 *
	 * @param con DBコネクション
	 * @param frm 選択されたORDER_ID
	 * @throws Exception
	 */
	public void deleteGuest (Connection con, OrderForm frm) throws Exception {

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("DELETE FROM ORDER_H WHERE ORDER_ID = ?");
			stmt.setInt(1, frm.getOrderID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ORDER_HテーブルのGUEST_NAMEを入力されたデータに書き換えるメソッド
	 *
	 * @param frm 入力された顧客名、ORDER_ID
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void updGuest (OrderForm frm, Connection con) throws Exception {

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE ORDER_H SET GUEST_NAME = ? WHERE ORDER_ID = ?");
			stmt.setString(1, frm.getGuestname());
			stmt.setInt(2, frm.getOrderID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ORDER_H、ORDER_I、PRODUCT、LOGINテーブルをジョイン
	 * 合計金額、注文数量、注文商品名を抽出し、リストへセットするメソッド
	 * IN_DATEに値が入っていないもの（会計が済んでいない）データのみを取得する
	 *
	 * @param con
	 * @param orderIDList ORDER_IDのリスト（顧客毎の注文リストを読み込む為）
	 * @return
	 * @throws Exception
	 */
	public List<OrderBean> serchOrderInfo (Connection con, List<Integer> orderIDList) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<OrderBean> list = new ArrayList<OrderBean>();
		try {
			stmt = con.prepareStatement("SELECT GUEST_NAME, AMOUNT, QUANTITY, PRODUCT_NAME, ORDER_I_ID "
					+ "FROM ORDER_H "
					+ "INNER JOIN ORDER_I ON ORDER_H.ORDER_ID = ORDER_I.ORDER_ID "
					+ "INNER JOIN PRODUCT ON ORDER_I.PRODUCT_ID = PRODUCT.PRODUCT_ID "
					+ "INNER JOIN LOGIN ON LOGIN.ID = ORDER_H.ID "
					+ "WHERE ORDER_H.ORDER_ID = ? AND IN_DATE is NULL");
			for (int ID : orderIDList) {
				stmt.setObject(1, ID);
				rs = stmt.executeQuery();
				while (rs.next()) {
					OrderBean bn = new OrderBean();
					bn.setGuestname(rs.getString(1));
					bn.setTotalfee(rs.getInt(2));
					bn.setOrderquantity(rs.getInt(3));
					bn.setOrderproductname(rs.getString(4));
					bn.setOrderI_ID(rs.getInt(5));
					list.add(bn);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 * 注文されたデータの注文ID、商品ID、注文数量をORDER_Iテーブルへ登録する
	 *
	 * @param OrderList 注文商品ID、注文数量（PRODUCT_ID、QUANTITY）
	 * @param frm 注文ID（ORDER_ID）
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void insertRequestOrder_I (List<OrderBean> OrderList, OrderForm frm, Connection con) throws Exception {

		PreparedStatement stmt = null;
		try {
			for (OrderBean orderData : OrderList) {
				stmt = con.prepareStatement("INSERT INTO ORDER_I (ORDER_ID, PRODUCT_ID, QUANTITY) "
						+ "VALUES (?, ?, ?)");
				stmt.setInt(1, frm.getOrderID());
				stmt.setInt(2, orderData.getOrderproductID());
				stmt.setInt(3, orderData.getOrderquantity());
				stmt.executeUpdate();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ORDER_Iテーブルに登録されている注文データ（商品ID、注文ID、注文数量）と
	 * PRODUCTテーブルの商品価格データ（PRODUCT_IDが一致するもの）から合計金額を計算し、抽出するメソッド
	 *
	 * @param frm 注文ID（ORDER_ID）
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public int serchTotalFee (OrderForm frm, Connection con) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		int totalfee = 0;
		try {
			stmt = con.prepareStatement("SELECT SUM(PRICE * QUANTITY) "
					+ "FROM ORDER_I "
					+ "INNER JOIN PRODUCT "
					+ "ON ORDER_I.PRODUCT_ID = PRODUCT.PRODUCT_ID "
					+ "WHERE ORDER_ID = ?");
			stmt.setInt(1, frm.getOrderID());
			rs = stmt.executeQuery();
			if (rs.next()) {
				totalfee = rs.getInt(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return totalfee;
	}


	/**
	 *
	 * 上記メソッドで抽出した合計金額データをORDER_Hへ保存するメソッド
	 * ORDER_IDが一致するレコードへ合計金額を登録する
	 *
	 * @param frm 注文ID（ORDER_ID）
	 * @param totalfee 合計金額
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void updRequestOrder_H (OrderForm frm, int totalfee, Connection con) throws Exception {

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE ORDER_H SET AMOUNT = ? WHERE ORDER_ID = ?");
			stmt.setInt(1, totalfee);
			stmt.setInt(2, frm.getOrderID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * ORDER_IテーブルのQUANTITYの値を
	 * 指定された注文個数に更新するメソッド
	 * ORDER_I_ID（注文明細ID）と一致するデータを改ざんする
	 *
	 * @param frm 修正注文数量、注文明細ID
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void updateOrderQuantity (OrderForm frm, Connection con) throws Exception {

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE ORDER_I SET QUANTITY = ? WHERE ORDER_I_ID = ?");
			stmt.setInt(1, frm.getOrderquantity());
			stmt.setInt(2, frm.getOrderI_ID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * 選択された注文データを削除するメソッド
	 * ORDER_I_ID（注文明細ID）と一致するレコードを削除する
	 *
	 * @param frm ORDER_I_ID（注文明細ID）
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void deleteOrder (OrderForm frm, Connection con) throws Exception {

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("DELETE FROM ORDER_I WHERE ORDER_I_ID = ?");
			stmt.setInt(1, frm.getOrderI_ID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**
	 * 選択された未会計のレコードを抽出するメソッド
	 *
	 * @param con DBコネクション
	 * @param frm 注文ID（ORDER_ID）
	 * @return
	 * @throws Exception
	 */
	public List<OrderBean> serchPaymentInfo (Connection con, OrderForm frm) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<OrderBean> list = new ArrayList<OrderBean>();
		try {
			stmt = con.prepareStatement("SELECT GUEST_NAME, AMOUNT, QUANTITY, PRODUCT_NAME, ORDER_I_ID, PRICE "
					+ "FROM ORDER_H "
					+ "INNER JOIN ORDER_I ON ORDER_H.ORDER_ID = ORDER_I.ORDER_ID "
					+ "INNER JOIN PRODUCT ON ORDER_I.PRODUCT_ID = PRODUCT.PRODUCT_ID "
					+ "INNER JOIN LOGIN ON LOGIN.ID = ORDER_H.ID "
					+ "WHERE ORDER_H.ORDER_ID = ? AND IN_DATE is NULL");
			stmt.setObject(1, frm.getOrderID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				OrderBean bn = new OrderBean();
				bn.setGuestname(rs.getString(1));
				bn.setTotalfee(rs.getInt(2));
				bn.setOrderquantity(rs.getInt(3));
				bn.setOrderproductname(rs.getString(4));
				bn.setOrderI_ID(rs.getInt(5));
				bn.setPrice(rs.getInt(6));
				list.add(bn);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 * ORDER_Hテーブルへ会計日時（現在日時）を登録するメソッド
	 * 会計日時をUPDATEし、会計処理を完了する
	 *
	 * @param frm 注文ID(ORDER_ID)
	 * @param con DBコネクション
	 * @throws Exception
	 */
	public void insertPayment (OrderForm frm, Connection con) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE ORDER_H SET IN_DATE = NOW() WHERE ORDER_ID = ?");
			stmt.setInt(1, frm.getOrderID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}


	/**********************************グラフページ*************************************/


	/**
	 * PRODUCT、ORDER_I、ORDER_Hテーブルから
	 * 注文商品名、商品毎の総売り上げ、注文総数、商品IDを抽出するメソッド
	 *
	 * 注文個数が多い順に10レコード取得
	 *
	 * 商品区分は「1」食べ物に分類されている商品のみ取得
	 *
	 * @param con DBコネクション
	 * @param id ログインID
	 * @return
	 * @throws Exception
	 */
	public List<GraphBean> serchFoodGraphInfo (Connection con, int id) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<GraphBean> list = new ArrayList<GraphBean>();
		int i = 1;
		int check = 0;
		boolean flg = true;
		try {
			stmt = con.prepareStatement("SELECT PRODUCT.PRODUCT_ID, PRODUCT_NAME, SUM(QUANTITY) * PRICE, SUM(QUANTITY) "
					+ "FROM ORDER_I "
					+ "INNER JOIN ORDER_H "
					+ "ON ORDER_I.ORDER_ID = ORDER_H.ORDER_ID "
					+ "INNER JOIN PRODUCT "
					+ "ON ORDER_I.PRODUCT_ID = PRODUCT.PRODUCT_ID "
					+ "WHERE ORDER_H.ID = ? AND PRODUCT.PRODUCT_TYPE = '1'"
					+ "GROUP BY PRODUCT.PRODUCT_ID "
					+ "ORDER BY SUM(QUANTITY) DESC");
			stmt.setObject(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				GraphBean bn = new GraphBean();
				if (flg) {
					bn.setRank(i);
					bn.setProductID(rs.getInt(1));
					bn.setProductname(rs.getString(2));
					bn.setTotalSales(rs.getInt(3));
					bn.setTotalQuantities(rs.getInt(4));
					list.add(bn);
					flg = false;
				} else {
					// 同順位処理
					if (rs.getInt(4) == check) {
						bn.setRank(i);
						bn.setProductID(rs.getInt(1));
						bn.setProductname(rs.getString(2));
						bn.setTotalSales(rs.getInt(3));
						bn.setTotalQuantities(rs.getInt(4));
						list.add(bn);
					} else {
						i++;
						bn.setRank(i);
						bn.setProductID(rs.getInt(1));
						bn.setProductname(rs.getString(2));
						bn.setTotalSales(rs.getInt(3));
						bn.setTotalQuantities(rs.getInt(4));
						list.add(bn);
					}
				}
				check = rs.getInt(4);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 *  PRODUCT、ORDER_I、ORDER_Hテーブルから
	 * 注文商品名、商品毎の総売り上げ、注文総数、商品IDを抽出するメソッド
	 *
	 * 注文個数が多い順に10レコード取得
	 *
	 * 商品区分は「2」飲み物に分類されている商品のみ取得
	 *
	 * @param con DBコネクション
	 * @param id ログインID
	 * @return
	 * @throws Exception
	 */
	public List<GraphBean> serchDrinkGraphInfo (Connection con, int id) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<GraphBean> list = new ArrayList<GraphBean>();
		int i = 1;
		int check = 0;
		boolean flg = true;
		try {
			stmt = con.prepareStatement("SELECT PRODUCT.PRODUCT_ID, PRODUCT_NAME, SUM(QUANTITY) * PRICE, SUM(QUANTITY) "
					+ "FROM ORDER_I "
					+ "INNER JOIN ORDER_H "
					+ "ON ORDER_I.ORDER_ID = ORDER_H.ORDER_ID "
					+ "INNER JOIN PRODUCT "
					+ "ON ORDER_I.PRODUCT_ID = PRODUCT.PRODUCT_ID "
					+ "WHERE ORDER_H.ID = ? AND PRODUCT.PRODUCT_TYPE = '2'"
					+ "GROUP BY PRODUCT.PRODUCT_ID "
					+ "ORDER BY SUM(QUANTITY) DESC");
			stmt.setObject(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				GraphBean bn = new GraphBean();
				if (flg) {
					bn.setRank(i);
					bn.setProductID(rs.getInt(1));
					bn.setProductname(rs.getString(2));
					bn.setTotalSales(rs.getInt(3));
					bn.setTotalQuantities(rs.getInt(4));
					list.add(bn);
					flg = false;
				} else {
					// 同順処理
					if (rs.getInt(4) == check) {
						bn.setRank(i);
						bn.setProductID(rs.getInt(1));
						bn.setProductname(rs.getString(2));
						bn.setTotalSales(rs.getInt(3));
						bn.setTotalQuantities(rs.getInt(4));
						list.add(bn);
					} else {
						i++;
						bn.setRank(i);
						bn.setProductID(rs.getInt(1));
						bn.setProductname(rs.getString(2));
						bn.setTotalSales(rs.getInt(3));
						bn.setTotalQuantities(rs.getInt(4));
						list.add(bn);
					}
				}
				check = rs.getInt(4);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 * ORDER_Hテーブルから
	 * 会計日時、月別の総売り上げ、来客組数を取得するメソッド
	 *
	 * 売上詳細表に使用するデータ
	 *
	 * @param con DBコネクション
	 * @param id ログインID
	 * @return
	 * @throws Exception
	 */
	public List<GraphBean> serchSalesMonthly (Connection con, int id) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<GraphBean> list = new ArrayList<GraphBean>();
		try {
			stmt = con.prepareStatement("SELECT IN_DATE, SUM(AMOUNT), COUNT(*) "
					+ "FROM ORDER_H "
					+ "WHERE ID = ? "
					+ "AND IN_DATE is NOT NULL "
					+ "GROUP BY DATE_FORMAT(IN_DATE, '%Y%m')");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				GraphBean bn = new GraphBean();
				String[] date = rs.getString(1).split("-");
				bn.setYear(date[0]);

/**********************************************************/
				// 月と日データを
				// "00"の形の場合"0"に直す
				if (date[1].indexOf("0") == 0) {
					bn.setMonth(date[1].substring(1, 2));
				} else {
					bn.setMonth(date[1]);
				}
				if (date[2].indexOf("0") == 0) {
					bn.setDay(date[2].substring(1, 2));
				} else {
					bn.setDay(date[2]);
				}
/**********************************************************/

				bn.setTotalSales(rs.getInt(2));
				bn.setVisitor(rs.getInt(3));
				list.add(bn);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}


	/**
	 * ORDER_Hテーブルから会計日時と日別の合計金額を取得するメソッド
	 *
	 * 売上グラフに使用するデータ
	 *
	 * @param con DBコネクション
	 * @param frm 選択された年と月のデータ
	 * @param id ログインID
	 * @return
	 * @throws Exception
	 */
	public List<GraphBean> serchSalesGraphInfo (Connection con, GraphForm frm, int id) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<GraphBean> list = new ArrayList<GraphBean>();
		try {
			stmt = con.prepareStatement("SELECT IN_DATE, SUM(AMOUNT) "
					+ "FROM ORDER_H "
					+ "WHERE ID = ? "
					+ "AND IN_DATE is NOT NULL "
					+ "AND IN_DATE LIKE ? "
					+ "GROUP BY IN_DATE");
			stmt.setInt(1, id);
			stmt.setString(2, "%" + frm.getYear() + "-" + String.format("%02d", Integer.parseInt(frm.getMonth()))  + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				GraphBean bn = new GraphBean();
				String[] date = rs.getString(1).split("-");
				bn.setYear(date[0]);

/**********************************************************/
				// 月と日データを
				// "00"の形の場合"0"に直す
				if (date[1].indexOf("0") == 0) {
					bn.setMonth(date[1].substring(1, 2));
				} else {
					bn.setMonth(date[1]);
				}
				if (date[2].indexOf("0") == 0) {
					bn.setDay(date[2].substring(1, 2));
				} else {
					bn.setDay(date[2]);
				}
/**********************************************************/

				bn.setTotalSales(rs.getInt(2));
				list.add(bn);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}



}
