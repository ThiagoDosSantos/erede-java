package br.com.userede.erede.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

import br.com.userede.erede.HttpUtils;
import br.com.userede.erede.Store;
import br.com.userede.erede.Transaction;
import br.com.userede.erede.TransactionResponse;
import br.com.userede.erede.eRede;
import br.com.userede.erede.service.error.RedeError;
import br.com.userede.erede.service.error.RedeException;

abstract class AbstractTransactionService {

    final Store store;
    final Transaction transaction;

    private final Logger logger;

    AbstractTransactionService(Store store, Transaction transaction, Logger logger) {
        this.store = store;
        this.transaction = transaction;
        this.logger = logger;
    }

    abstract public TransactionResponse execute();

    URIBuilder getUri() throws URISyntaxException {
        return new URIBuilder(store.getEnvironment().getEndpoint("transactions"));
    }    

    TransactionResponse sendRequest(HttpUriRequest request) {
        
        request.addHeader(HttpHeaders.USER_AGENT, String.format(eRede.USER_AGENT, store.getFiliation()));
        request.addHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + store.getAccessToken());

        request.addHeader("Transaction-Response", "brand-return-opened");

        logger.log(Level.FINE, request.getRequestLine().toString());

        try {
            try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build()) {
                HttpResponse httpResponse = closeableHttpClient.execute(request);
                int status = httpResponse.getStatusLine().getStatusCode();

                String response = HttpUtils.parseResponse(httpResponse);
                TransactionResponse transactionResponse = new Gson()
                        .fromJson(response, TransactionResponse.class);
                
                if (status < 200 || status >= 400) {
                    RedeError redeError = new RedeError(transactionResponse.getReturnCode(),
                            transactionResponse.getReturnMessage());

                    throw new RedeException(response, redeError,
                            transactionResponse);
                }

                return transactionResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    
}
