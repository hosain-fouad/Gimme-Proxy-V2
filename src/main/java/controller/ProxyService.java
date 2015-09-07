package controller;

import dao.ProxyDAO;
import model.MyProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProxyService {

    @Autowired
    private ProxyDAO proxyDAO;

    @RequestMapping("/getProxies")
    public List<MyProxyServer> getProxies(){
    	return proxyDAO.getAllProxies();
    }

    @RequestMapping("/getRandomHttpProxy")
    public MyProxyServer getRandomHttpProxy(){
        List<MyProxyServer> proxyServers = proxyDAO.getAllProxies();
        return proxyServers.stream().filter(proxy -> proxy.getProtocol().equalsIgnoreCase("HTTP")).findAny().get();
    }


}
