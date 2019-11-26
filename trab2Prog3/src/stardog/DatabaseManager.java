package stardog;

public class DatabaseManager {

    Stardog aStardog = Stardog.builder().create();
    try
    {
        try (AdminConnection aAdminConnection = AdminConnectionConfiguration.toEmbeddedServer()
                .credentials("admin", "admin")
                .connect()) {
            if (aAdminConnection.list().contains("databaseTrab2")) {
                aAdminConnection.drop("databaseTrab2");
            }

            aAdminConnection.disk("databaseTrab2").create();

            try (Connection aConn = ConnectionConfiguration
                    .to("databaseTrab2")
                    .credentials("admin", "admin")
                    .connect())

        } finally {
            // remove the database
            if (aAdminConnection.list().contains("databaseTrab2")) {
                aAdminConnection.drop("databaseTrab2");
            }
        }
    }
    finally
    {
        aStardog.shutdown();
    }
}
