package com.prominente.android.viaticgo.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompatSideChannelService;

import com.prominente.android.viaticgo.clients.ApiClient;
import com.prominente.android.viaticgo.models.ServiceLine;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.prominente.android.viaticgo.services.action.FOO";
    private static final String ACTION_BAZ = "com.prominente.android.viaticgo.services.action.BAZ";
    public static final String ACTION_SYNC_SERVICE_LINES = "com.prominente.android.viaticgo.services.action.SYNC_SERVICE_LINES";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.prominente.android.viaticgo.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.prominente.android.viaticgo.services.extra.PARAM2";

    private GetallTask task;
    private ArrayList<ServiceLine> serviceLinesList;

    public SyncService() {
        super("SyncService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            } else if (ACTION_SYNC_SERVICE_LINES.equals(action)) {
                handleActionSyncServiceLines();
            }
        }
    }

    private void handleActionSyncServiceLines() {
        task = new GetallTask();
        task.execute();
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class GetallTask extends AsyncTask<Void, Integer, ArrayList<ServiceLine>> {
        public GetallTask() {
            serviceLinesList = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ServiceLine> doInBackground(Void... voids) {
            ArrayList<ServiceLine> list = ApiClient.getInstance(SyncService.this).getall();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<ServiceLine> list) {
            if (list != null)
                serviceLinesList = list;
            //TODO: guardar en base
        }
    }
}
