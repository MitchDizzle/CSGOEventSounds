package wtf.dizzle.csgostate.eventsounds;

import java.io.File;
import java.util.Map;

import com.brekcel.csgostate.JSON.JsonResponse;
import com.brekcel.csgostate.post.PostHandlerAdapter;

/**
 * TODO Description.
 * @author MG031901
 *
 */
public class StatePostHandler extends PostHandlerAdapter {

    @SuppressWarnings("unused")
    private final Map<String, File> soundMap;

    /**
     *
     */
    public StatePostHandler(Map<String, File> soundMap) {
        this.soundMap = soundMap;
    }


    @Override
    public void receivedJsonResponse(JsonResponse jsonResponse) {
        System.out.print(jsonResponse);
    }
}
