package cfg;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import utils.PropertyFetcher;

import java.net.URISyntaxException;
import java.util.Properties;

@ComponentScan({
        "controller",
        "dao",
        "engines",
        "model"
})
@EnableAutoConfiguration
@EnableTransactionManagement
public class Application {

    private static final PropertyFetcher propertyFetcher = new PropertyFetcher("config.properties");

    public static void main(String[] args) {
        propertyFetcher.loadProperties();
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        ((Thread)ctx.getBean("proxyGeneratorEngine")).start();
        ((Thread)ctx.getBean("proxyCleanerEngine")).start();
    }

    @Bean
    public SessionFactory sessionFactory() throws URISyntaxException {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder
            .scanPackages("dao", "model", "controller")
            .addProperties(getHibernateProperties());

        return builder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "false");
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        prop.put("hibernate.connection.username", propertyFetcher.getPropertyValue("database.user"));
        prop.put("hibernate.connection.password", propertyFetcher.getPropertyValue("database.password"));
        prop.put("hibernate.connection.url", propertyFetcher.getPropertyValue("database.url"));
        return prop;
    }

    @Bean
    public DriverManagerDataSource dataSource() throws URISyntaxException {

        DriverManagerDataSource basicDataSource = new DriverManagerDataSource();
        basicDataSource.setUrl(propertyFetcher.getPropertyValue("database.url"));
        basicDataSource.setUsername(propertyFetcher.getPropertyValue("database.user"));
        basicDataSource.setPassword(propertyFetcher.getPropertyValue("database.password"));

        return basicDataSource;
    }

}
