package hcloud.platform;

import hcloud.platform.modules.server.controller.ElegantShutdownConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ReflectionUtils;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 */
@EnableScheduling
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
@MapperScan(basePackages = {"hcloud.platform.modules.*.dao"})
public class HcloudPlatformApplication {
    private RedisProperties redisProperties;

    public HcloudPlatformApplication(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HcloudPlatformApplication.class, args);
        run.registerShutdownHook();
    }
    @Bean
    public RedissonClient redisson() throws IOException {
        Config config = null;
        Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
        Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
        Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, this.redisProperties);
        int timeout;
        Method nodesMethod;
        if (null == timeoutValue) {
            timeout = 10000;
        } else if (!(timeoutValue instanceof Integer)) {
            nodesMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
            timeout = ((Long) ReflectionUtils.invokeMethod(nodesMethod, timeoutValue)).intValue();
        } else {
            timeout = (Integer) timeoutValue;
        }

        if (this.redisProperties.getSentinel() != null) {
            nodesMethod = ReflectionUtils.findMethod(RedisProperties.Sentinel.class, "getNodes");
            Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, this.redisProperties.getSentinel());
            String[] nodes;
            if (nodesValue instanceof String) {
                nodes = this.convert(Arrays.asList(((String) nodesValue).split(",")));
            } else {
                nodes = this.convert((List) nodesValue);
            }

            config = new Config();
            (config.useSentinelServers().setMasterName(this.redisProperties.getSentinel().getMaster()).addSentinelAddress(nodes).setDatabase(this.redisProperties.getDatabase()).setConnectTimeout(timeout)).setPassword(this.redisProperties.getPassword());
        } else {
            Method method;
            if (clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, this.redisProperties) != null) {
                Object clusterObject = ReflectionUtils.invokeMethod(clusterMethod, this.redisProperties);
                method = ReflectionUtils.findMethod(clusterObject.getClass(), "getNodes");
                List<String> nodesObject = (List) ReflectionUtils.invokeMethod(method, clusterObject);
                String[] nodes = this.convert(nodesObject);
                config = new Config();
                (config.useClusterServers().addNodeAddress(nodes).setConnectTimeout(timeout)).setPassword(this.redisProperties.getPassword());
            } else {
                config = new Config();
                String prefix = "redis://";
                method = ReflectionUtils.findMethod(RedisProperties.class, "isSsl");
                if (method != null && (Boolean) ReflectionUtils.invokeMethod(method, this.redisProperties)) {
                    prefix = "rediss://";
                }

                (config.useSingleServer().setAddress(prefix + this.redisProperties.getHost() + ":" + this.redisProperties.getPort()).setConnectTimeout(timeout)).setDatabase(this.redisProperties.getDatabase()).setPassword(this.redisProperties.getPassword());
            }
        }

        return Redisson.create(config);
    }

    private String[] convert(List<String> nodesObject) {
        List<String> nodes = new ArrayList(nodesObject.size());
        Iterator var3 = nodesObject.iterator();

        while (true) {
            while (var3.hasNext()) {
                String node = (String) var3.next();
                if (!node.startsWith("redis://") && !node.startsWith("rediss://")) {
                    nodes.add("redis://" + node);
                } else {
                    nodes.add(node);
                }
            }

            return nodes.toArray(new String[nodes.size()]);
        }
    }
    @Bean
    public ElegantShutdownConfig elegantShutdownConfig() {
        return new ElegantShutdownConfig();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers((elegantShutdownConfig()));
        return tomcat;
    }

}
