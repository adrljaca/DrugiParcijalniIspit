import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {


    public static DataSource createDataSource() {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("JavaAdv");
        ds.setUser("sa");
        ds.setPassword("SQL");
        ds.setEncrypt(false);

        return ds;
    }

    public static void connectToDatabase() {
        DataSource dataSource = createDataSource();
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Uspješno ste spojeni na bazu podataka!");
        } catch (SQLException e) {
            System.err.println("Greška prilikom spajanja na bazu podataka");
            e.printStackTrace();
        }
    }

    public static Connection connect() throws SQLException {
        return createDataSource().getConnection();
    }
}