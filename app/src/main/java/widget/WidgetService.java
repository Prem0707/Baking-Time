package widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Prem on 19-12-2017.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // return remoteView Factory here
        return null;
    }
}
