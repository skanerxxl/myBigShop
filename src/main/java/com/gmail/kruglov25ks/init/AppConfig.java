package com.gmail.kruglov25ks.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.gmail.kruglov25ks")
@EnableWebMvc
@EnableTransactionManagement
public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {// подключаем статические ресурсы
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

    @Bean
    public DataSource dataSource() {// бин для работы с базой, по сути заменяет персистенс.xml
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");// драйвер
        ds.setUrl("jdbc:mysql://localhost:3306/shop?characterEncoding=UTF-8");// юрл для базы?кодировка сессии соединения с бд

        ds.setUsername("root");// логин
        ds.setPassword("koka1nxxl");// пароль от базы

        return ds;// завернули в объект настройки для доступа к базе
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {// указываем, что провайдером для реализации jpa будет hibernate
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);// выводить базу на консоль
        adapter.setGenerateDdl(true);// генерируем схему
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");// указываем диалект, как в персистенс.xml
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory// для создания этого бина, в параметры передаём другие необходимые бины из конфига
    (DataSource dataSource, JpaVendorAdapter jpaVendeorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();// фабрика, кот-я будет создавать EM один на трэд
        entityManagerFactory.setDataSource(dataSource);// связываем фабрику с базой
        entityManagerFactory.setJpaVendorAdapter(jpaVendeorAdapter);// связываем фабрику с ORM
        entityManagerFactory.setPackagesToScan("ru.rambler.skanerxxl.db.entity");// указываем пакеты, где entyty искать
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {// будет управлять
        // транзакциями для EM, кот-е продюсируются этой фабрикой
        return new JpaTransactionManager(emf);
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {// спринговый бин, служит для перехода на страницу
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();// объект подсказывает спрингу где брать вьюшки
        resolver.setPrefix("/WEB-INF/pages/");// путь к каталогу в проэкте, где лежат вьюшки
        resolver.setSuffix(".jsp");// расширение файлов
        resolver.setViewClass(JstlView.class);// класс кот-й будет проводить корректную обработку
        // страниц в runtime, те используется jstl-движок
        resolver.setOrder(1);// приоритет
        return resolver;
    }

    @Bean
// бин, который парсит мультипарт запросы, CommonsMultipartResolver-обёртка вокруг сторонней
// библиотеки ApacheCommons.io, т.к. родная спринговая-глючная
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
}
