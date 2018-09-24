package com.lastmile.elastic.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastmile.vo.DriverDetailVO;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * 
 * @author Sajin_K
 *
 */
public class ElasticUtil {
	private BulkRequestBuilder bulkRequestBuilder;
	private Client elasticClient;
	private String indexName;
	private String typeName;
	private int count;
	public static final int BATCH_SIZE = 1000;

	/**
	 * 
	 * @param ip
	 * @param port
	 * @param indexName
	 * @param typeName
	 * @param clusterName
	 */
	public void initialize(String indexName, String typeName, Client elasticClient) {

		this.indexName = indexName;
		this.typeName = typeName;
		this.elasticClient = elasticClient;
	}

	/**
	 * 
	 * @param uniqueId
	 * @param jsonMap
	 */
	public void bulkUpdate(String uniqueId, Map<String, ?> jsonMap)  {
		count++;
		if(null == uniqueId) {
			bulkRequestBuilder.add(elasticClient.prepareIndex(indexName, typeName).setSource(jsonMap));
		} else {
			bulkRequestBuilder.add(elasticClient.prepareIndex(indexName, typeName, uniqueId).setSource(jsonMap));
		}

		if (count % BATCH_SIZE == 0) {
			BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
			if (bulkResponse.hasFailures()) {
			}
			bulkRequestBuilder = elasticClient.prepareBulk();
		}
	}

	/**
	 * 
	 * @throws RTBMException
	 */
	public void flush() {
		if (bulkRequestBuilder.numberOfActions() > 0) {
			BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				
			}
			
		}
	}

	public void insert(DriverDetailVO dataVO, String jobId) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			elasticClient.prepareIndex(indexName, typeName, jobId).setSource(mapper.writeValueAsBytes(dataVO)).get();		
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	 * @param uniqueId
	 * @param jsonMap
	 * @throws RTBMException
	 */
	public void updateDocument(String uniqueId, DriverDetailVO dataVO) {
		ObjectMapper mapper = new ObjectMapper();
		IndexRequest idx = new IndexRequest(indexName, typeName, uniqueId);
		try {
			idx.source(mapper.writeValueAsBytes(dataVO));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IndexResponse indexResponse = elasticClient.index(idx).actionGet();
	}

	/**
	 * 
	 * @param uniqueId
	 * @param typeName
	 * @param jsonMap
	 * @throws RTBMException
	 */
	public void updatePartialDocument(String uniqueId, int time) {

		UpdateRequest updateRequest;
		try {
			updateRequest = new UpdateRequest(indexName, typeName, uniqueId)
			        .doc(jsonBuilder()
			            .startObject()
			                .field("revisedEstimatedTime", time)
			            .endObject());
			elasticClient.update(updateRequest).get();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	public SearchResponse searchResponse(String driverId) {
		SearchResponse searchResponse = elasticClient.prepareSearch().setIndices(indexName).setTypes(typeName).
				setQuery(QueryBuilders.matchQuery("driverId", driverId)).addSort("estimatedArrivalTime", SortOrder.ASC).setSize(100).execute().actionGet();
		return searchResponse;
	}
	
	public SearchResponse searchResponseRemedy(String name, QueryBuilder qb) {
		SearchResponse searchResponse = elasticClient.prepareSearch(name).setTypes(name)
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFetchSource(true).addSort("Incident ID", SortOrder.ASC)
				.setScroll(new TimeValue(120000)).addStoredField("Incident ID").setQuery(qb).setSize(5000).execute()
				.actionGet();
		return searchResponse;
	}
	
	/**
	 * 
	 * @param size
	 * @return
	 */
	public SearchResponse searchResponse(int size) {
		SearchResponse searchResponse = elasticClient.prepareSearch(indexName).setTypes(typeName).setSize(size)
				.execute().actionGet();
		return searchResponse;
	}
	
	public DeleteIndexResponse deleteResponse() {
		DeleteIndexResponse delResponse = null;
		//DeleteResponse delResponse = elasticClient.prepareDelete().setIndex(indexName).setType(typeName).execute().actionGet();
		IndicesExistsResponse exists=elasticClient.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet();
		if(exists.isExists()){
			delResponse = elasticClient.admin().indices().delete(new DeleteIndexRequest(indexName)).actionGet();
		}
		return delResponse;
	}
	
	public CreateIndexResponse createIndex(){
		CreateIndexResponse createResponse = null;
		
		createResponse = elasticClient.admin().indices().create(new CreateIndexRequest(indexName)).actionGet();
		
		return createResponse;
	}
	//For deleting data
	public DeleteResponse deleteData(String index, String type, String id) {
		DeleteResponse response = elasticClient.prepareDelete(index,type,id)
	        .execute()
	        .actionGet();
		return response;
	}
	
	public SearchResponse searchResponseWithScroll(String name, QueryBuilder qb) {
		SearchResponse searchResponse = elasticClient.prepareSearch(name).setQuery(qb).setScroll(new TimeValue(60000)).setSize(100000).execute()
				.actionGet();
		return searchResponse;
	}

	public SearchResponse prepareSearchScroll(SearchResponse searchResponse) {
		return elasticClient.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(120000))
				.execute().actionGet();
	}
}
