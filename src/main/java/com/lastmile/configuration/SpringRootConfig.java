package com.lastmile.configuration;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.lastmile.elastic.util.ElasticUtil;

@PropertySource(value = { "classpath:/db/configs/elasticsearch.properties" })
@Configuration
public class SpringRootConfig {

	@Autowired
	private Environment environment;
	
	@Bean 
	public ElasticUtil elasticUtil() {
		ElasticUtil elasticUtil = new ElasticUtil();
		elasticUtil.initialize("xpo", "activity", client());
		return elasticUtil;
	}
	
	public Client client() {
		Client client = null;
		String host = environment.getRequiredProperty("host");
		int port = Integer.parseInt(environment.getRequiredProperty("port"));
		try {
			if(client==null){
				Settings settings = Settings.builder()
						.put("client.transport.sniff", true)
						.put("cluster.name", "elasticsearch")
						.build();
				//			byte[] ipAddr = new byte[]{10,(byte)155,56,7};
				//InetAddress addr = InetAddress.getByName(host);
				client = new PreBuiltTransportClient(settings).
						addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}
	
}