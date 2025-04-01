package payk96.rpg_shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "payk96.rpg_shop.repository",
        entityManagerFactoryRef = "rpgShopEntityManagerFactory",
        transactionManagerRef = "rpgShopTransactionManager"
)
public class RpgShopDataSourceConfig {

    @Value("${spring.datasource.rpgshop.url}")
    private String url;

    @Value("${spring.datasource.rpgshop.username}")
    private String username;

    @Value("${spring.datasource.rpgshop.password}")
    private String password;

    @Value("${spring.datasource.rpgshop.driver-class-name}")
    private String driverClassName;

    @Bean
    @Primary
    public DataSourceProperties rpgShopDataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(url);
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setDriverClassName(driverClassName);
        return properties;
    }

    @Bean
    @Primary
    public DataSource rpgShopDataSource() {
        return rpgShopDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "rpgShopEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean rpgShopManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(rpgShopDataSource())
                .packages("payk96.rpg_shop.model")
                .persistenceUnit("rpgshop")
                .properties(hibernateProperties())
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager rpgShopTransactionManager(
            EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(rpgShopManagerFactory(builder).getObject()));
    }

    private Map<String, Object> hibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }

}
