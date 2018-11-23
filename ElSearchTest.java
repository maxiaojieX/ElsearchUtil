package com.danger.elsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by xiaojie.Ma on 2018/11/23.
 */
public class ElSearchTest {

    public static void main(String[] args) throws  Exception {
        addEmploy(createClient());
    }

    public static void addEmploy(TransportClient  client) throws Exception{
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("name","zhangsan")
                .field("age",27)
                .field("position","technique english")
                .field("country","China")
                .field("join_date","2017-01-01")
                .field("salary","10000")
                .endObject();
        IndexResponse response = client.prepareIndex("company","employee","6")
                .setSource(builder).get();
        System.out.println(response);
    }

    public static TransportClient createClient(){
        Settings settings = Settings.builder()
                .put("cluster.name","esName")
                .put("xpack.security.transport.ssl.enabled", false)
                .put("xpack.security.user", "elastic:changeme")
                .put("client.transport.sniff", true).build();
        TransportClient client = null;
        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("39.105.12.38"), 9200));
            System.out.println("ok");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client.close();
        return client;
    }


}
