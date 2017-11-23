package com.gmail.kruglov25ks.init;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInit implements WebApplicationInitializer {

    // здесь находится то, что заменяет web.xml   !!!

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {// метод вызывается один раз при старте апп
        // нечто, что управляет сервлетами
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();// создаём контекст для спринг мвц апп
        ctx.register(AppConfig.class);// регистрируем класс конфигураций

        ctx.setServletContext(servletContext);// спринг контексту назначаем сервлет контекст,
        // т.е. спринговый контекст знает о сервлет контексте, мы их связываем и теперь спринговый контекст управляет сервлетами
        ctx.refresh();
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));// в сервлет контекст с
        // помощью метода адд добавляем диспетчер сервлетов и передаём ему ссылку на контекст и под
        // именем dispatcher регистрируем наш сервлет
        servlet.addMapping("/");// закрепляем его в корне апп
        servlet.setLoadOnStartup(1);// устанавливаем приоритет 1
        // замена xml, объеденяем контекст сервлетов и контекст спринга и регистрируем в рантайме диспетчер сервлетов,
        // кот-й принимает на себя все запросы

        // Сервлет-фильтр запросов браузера
        // принудительно устанавливает кодировку запроса в UTF-8
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        // применить кодировку по умолчанию, если в запросе не указана кодировка, либо обеспечить кодирование запроса в
        // нужную кодировку вне зависимости от того, установлена она в запросе или нет ("forceEncoding"="true")
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }
}
