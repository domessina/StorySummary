package schn.beme.storysummary.presenterhelper.network;

import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;
import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.narrativecomponent.Trait;
import schn.beme.storysummary.synchronization.ActionDoneResponse;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.narrativecomponent.E_NarrativeComponent;
import schn.beme.storysummary.narrativecomponent.NarrativeComponent;
import schn.beme.storysummary.presenterhelper.Helper;

/**
 * Created by Dorito on 13-08-16.
 */
public class OkHttpWebServiceHelper implements Helper.WebService{

    static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    //for to be sure userId rest the same during all process
    static final int USER_ID=MyApplication.userId;

    //---------------PUSHES SECTION--------------------

    @Override
    public ActionDoneResponse pushDiagram(Diagram d, String action) {
        return pushDiagramAndAction(d, action, "/pushDiagram");
    }

    @Override
    public void pushUserChoice(Diagram d, String action) {
        pushDiagramAndAction(d,action, "/pushUserChoice");
    }

    private ActionDoneResponse pushDiagramAndAction(Diagram d, String action, String lastPathSection){
        ObjectMapper mapper;
        String body;

        //serialization
        try {
            mapper=new ObjectMapper();
            body = mapper.writeValueAsString(d);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        //prepare request
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://localhost/v1/api/sync/"+USER_ID+lastPathSection).newBuilder();
        urlBuilder.addQueryParameter("action", action);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON_TYPE,body))
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response;
        String responseBody;
        ActionDoneResponse actionDone=null;
        try {
            //post request
            response = client.newCall(request).execute();
            responseBody=response.body().string();

            //deserialization
            if(lastPathSection.equals("/pushDiagram")){
                actionDone= mapper.readValue(responseBody,ActionDoneResponse.class);}
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return actionDone;
    }

    //----------------END PUSHES------------------





    //-----------PERFORM S UPDATE BY GETTING ALL COMPONENT FROM SERVER----------
    @Override
    public <T> List<T> getAllTByDiagram(int diagramId, E_NarrativeComponent type, Class<T> clazz) {
        //the web service give the chapters already sorted by position
        ObjectMapper mapper;
        String body;

        //prepare request
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://localhost/v1/api/nc/"+type.toString()+"/list").newBuilder();
        urlBuilder.addQueryParameter("diagramId", String.valueOf(diagramId));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response;
        String responseBody;
        List<T> components;
        try {
            //get request
            response = client.newCall(request).execute();
            responseBody=response.body().string();

            //deserialization
            mapper=new ObjectMapper();
            final CollectionType javaType =mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            components= mapper.readValue(responseBody,javaType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return components;
    }
    //--------------END PERFORM S UPDATE----------------



    //--------PERFORM C UPDATE BY PUTTING ANDROID VERSION OF EVERY COMPONENT--------

    @Override
    public <T extends NarrativeComponent> boolean postOrPutT(T component, E_NarrativeComponent type,boolean isPost ) {
        ObjectMapper mapper;
        String body;

        //serialization
        try {
            mapper=new ObjectMapper();
            body = mapper.writeValueAsString(component);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }

        //prepare request
        HttpUrl.Builder urlBuilder = HttpUrl.parse("/v1/api/nc/"+type.toString()).newBuilder();
        String url = urlBuilder.build().toString();

        Request request;
        if(isPost){
            request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSON_TYPE,body))
                    .build();
        }
        else{
            request = new Request.Builder()
                    .url(url)
                    .put(RequestBody.create(JSON_TYPE,body))
                    .build();
        }

        OkHttpClient client = new OkHttpClient();
        Response response;

        try {
            //post request
            response = client.newCall(request).execute();
            if(response.code()!=200)
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //---------END PERFORM C UPDATE-----------------



    @Override
    public Drawable getPicture(int sceneId) {

        return null;
    }
}
