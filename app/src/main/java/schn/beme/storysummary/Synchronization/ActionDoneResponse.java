package schn.beme.storysummary.synchronization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dorito on 02-09-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDoneResponse {
    @JsonProperty("action")
    public String action;

    @JsonProperty("clientId")
    public int clientId;

    @JsonProperty("serverId")
    public int serverId;

    @JsonProperty("choices")
    public String choices;
}
