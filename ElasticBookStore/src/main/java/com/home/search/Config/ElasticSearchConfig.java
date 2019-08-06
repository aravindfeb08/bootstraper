package com.home.search.Config;

/*import java.net.InetAddress;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableAutoConfiguration
public class ElasticSearchConfig {

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    @Autowired
    private ElasticsearchTemplate elasticSearchTemplate;


    /*
     * @Bean public ElasticsearchTemplate elasticsearchTemplate() throws
     * UnknownHostException { String server = clusterNodes.split(":")[0]; Integer
     * port = Integer.parseInt(clusterNodes.split(":")[1]); Settings settings =
     * Settings.settingsBuilder() .put("cluster.name", clusterName).build(); Client
     * client = TransportClient.builder().settings(settings).build()
     * .addTransportAddresses(new
     * InetSocketTransportAddress(InetAddress.getByName(server), port)); return new
     * ElasticsearchTemplate(client); }
     */


}
