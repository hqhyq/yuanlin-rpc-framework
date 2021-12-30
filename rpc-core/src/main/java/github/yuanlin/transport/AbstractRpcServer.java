package github.yuanlin.transport;

import github.yuanlin.config.ServiceConfig;
import github.yuanlin.factory.SingletonFactory;
import github.yuanlin.provider.ServiceProvider;
import github.yuanlin.provider.impl.ServiceProviderImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * RPC 服务器抽象类
 *
 * @author yuanlin
 * @date 2021/12/30/16:38
 */
public abstract class AbstractRpcServer implements RpcServer {

    protected String host = InetAddress.getLoopbackAddress().getHostAddress();
    protected int port = 8002;

    public AbstractRpcServer() {
        if (StringUtils.isEmpty(host)) {
            throw new IllegalArgumentException("host can't be null");
        }
        serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }
    /**
     * 存放扫描到的标注 @RpcService 的服务
     */
    protected Set<ServiceConfig> serviceConfigs = new HashSet<>();
    /**
     * 服务提供
     */
    protected ServiceProvider serviceProvider;

    @Override
    public <T> void publishService(String serviceName, T serviceBean) {
        serviceProvider.addService(serviceName, serviceBean, new InetSocketAddress(host, port));
    }
}
