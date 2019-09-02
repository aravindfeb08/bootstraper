package com.home.search.Config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableAutoConfiguration
public class ElasticSearchConfig {

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

   /* @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        String server = clusterNodes.split(":")[0];
        Integer port = Integer.parseInt(clusterNodes.split(":")[1]);
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName).build();
        Client client = new PreBuiltTransportClient(settings)
                .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(server), port));
        return new ElasticsearchTemplate(client);
    }*/

   /* @Bean
    public Client client() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }*/

/*    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        String server = clusterNodes.split(":")[0];
        Integer port = Integer.parseInt(clusterNodes.split(":")[1]);
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(server), port));
        return new ElasticsearchTemplate(client);
    }*/

}
