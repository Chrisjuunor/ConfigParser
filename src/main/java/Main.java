public class Main {

    public static void main(String[] args) {
        String env = args.length > 0 ? args[0] : "production";
        System.setProperty("env", env);

        System.out.println("Appication starting in " + env + " environment");

        ConfigParser config = new ConfigParser("config");

        String dbName = config.get("dbname");
        String appName = config.get("application.name");

        System.out.println(appName);
        System.out.println(dbName);
    }

}

