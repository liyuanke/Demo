package com.lyk.data.http.service;

import com.lyk.data.model.FlowResult;
import com.lyk.data.model.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlowService {
    @GET("/api/action/datastore_search")
    Observable<Result<FlowResult>> getFlow(@Query("resource_id") String resourceId,@Query("offset") int offset, @Query("limit") int limit);
}
