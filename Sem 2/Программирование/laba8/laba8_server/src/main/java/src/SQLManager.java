package src;
import java.sql.*;

public class SQLManager {

//    private final String url = "jdbc:postgresql://localhost/studs";
//    private final String user = "s367101";
//    private final String password = "7ueLsuX4hcFio6B1";
    private final String url = "jdbc:postgresql://localhost:5432/prog_lab_7";
    private final String user = "postgres";
    private final String password = "ifiksyljc";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    public boolean send(PreparedStatement sql) {
        Main.logger.info("Запрос: " + sql);
        try {
            sql.executeUpdate();
            sql.getConnection().close();
        } catch(SQLException ex) {
            return false;
        }
        return true;
    }
    public boolean sendRaw(String sql) {
        Main.logger.info("Запрос: " + sql);
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch(SQLException ex) {
            return false;
        }
        return true;
    }

    public ResultSet get(PreparedStatement sql) {
        Main.logger.info("Запрос: " + sql);
        ResultSet result = null;
        try {
            result = sql.executeQuery();
            sql.getConnection().close();
        } catch (SQLException ex) {}
        return result;
    }
    public ResultSet getRaw(String sql) {
        Main.logger.info("Запрос: " + sql);
        ResultSet result = null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            result = statement.executeQuery(sql);
            connection.close();
        } catch (SQLException ex) {}
        return result;
    }
}
