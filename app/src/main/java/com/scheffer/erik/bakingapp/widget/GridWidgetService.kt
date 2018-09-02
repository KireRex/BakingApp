package com.scheffer.erik.bakingapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class GridWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory =
            GridRemoteViewsFactory(this.applicationContext)
}