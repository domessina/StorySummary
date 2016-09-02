package schn.beme.storysummary.presenterhelper.network;

import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import schn.beme.storysummary.MyApplication;
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

    @Override
    public NarrativeComponent getNComponent(int id, E_NarrativeComponent type) {

        return null ;
    }

    @Override
    public boolean postNComponent(NarrativeComponent nc, E_NarrativeComponent type) {

        return false;
    }

    @Override
    public boolean putNComponent(NarrativeComponent nc, E_NarrativeComponent type) {

        return false;
    }

    @Override
    public boolean deleteNComponent(NarrativeComponent nc, E_NarrativeComponent type) {

        return false;
    }

    @Override
    public Drawable getPicture(int sceneId) {

        return null;
    }
}
