package hcloud.platform.modules.server.controller;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ElegantShutdownConfig implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private volatile Connector connector;
    private final int waitTime = 10;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (connector == null) {
            return;
        }
        connector.pause();
        Executor executor = connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor executor1 = (ThreadPoolExecutor) executor;
            executor1.shutdownNow();
            try {
                if (!executor1.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                    System.out.println("请尝试暴力关闭");
                }
            } catch (InterruptedException e) {
                System.out.println("异常了");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
