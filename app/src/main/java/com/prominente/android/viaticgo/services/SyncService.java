package com.prominente.android.viaticgo.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;

import com.prominente.android.viaticgo.activities.SurrenderActivity;
import com.prominente.android.viaticgo.clients.ApiClient;
import com.prominente.android.viaticgo.data.SugarRepository;
import com.prominente.android.viaticgo.interfaces.ICurrencyRepository;
import com.prominente.android.viaticgo.interfaces.IExpenseTypeRepository;
import com.prominente.android.viaticgo.interfaces.IServiceLineRepository;
import com.prominente.android.viaticgo.interfaces.ISurrenderRepository;
import com.prominente.android.viaticgo.interfaces.ITripRepository;
import com.prominente.android.viaticgo.models.Currency;
import com.prominente.android.viaticgo.models.ExpenseType;
import com.prominente.android.viaticgo.models.ServiceLine;
import com.prominente.android.viaticgo.models.Surrender;
import com.prominente.android.viaticgo.models.Trip;

import java.util.ArrayList;
import java.util.Iterator;

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
    public static final String ACTION_SYNC_ALL = "com.prominente.android.viaticgo.services.action.SYNC_ALL";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.prominente.android.viaticgo.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.prominente.android.viaticgo.services.extra.PARAM2";

    private SyncServiceLinesTask syncServiceLinesTask;
    private IServiceLineRepository serviceLineRepository;

    private SyncCurrenciesTask syncCurrenciesTask;
    private ICurrencyRepository currencyRepository;

    private SyncExpenseTypesTask syncExpenseTypesTask;
    private IExpenseTypeRepository expenseTypeRepository;

    private SyncTripTask syncTripTask;
    private ITripRepository tripRepository;

    private SyncSurrenders syncSurrenders;
    private ISurrenderRepository surrenderRepository;

    public SyncService() {
        super("SyncService");
        serviceLineRepository = SugarRepository.getInstance();
        currencyRepository = SugarRepository.getInstance();
        expenseTypeRepository = SugarRepository.getInstance();
        tripRepository = SugarRepository.getInstance();
        surrenderRepository = SugarRepository.getInstance();
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
            } else if (ACTION_SYNC_ALL.equals(action)) {
                handleActionSyncAll();
            }
        }
    }

    private void handleActionSyncServiceLines() {
        syncServiceLinesTask = new SyncServiceLinesTask();
        syncServiceLinesTask.execute();
    }

    private void handleActionSyncAll() {
        syncServiceLinesTask = new SyncServiceLinesTask();
        syncServiceLinesTask.execute();
        syncCurrenciesTask = new SyncCurrenciesTask();
        syncCurrenciesTask.execute();
        syncExpenseTypesTask = new SyncExpenseTypesTask();
        syncExpenseTypesTask.execute();
        syncTripTask = new SyncTripTask();
        syncTripTask.execute();
        syncSurrenders = new SyncSurrenders();
        syncSurrenders.execute();
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

    private class SyncServiceLinesTask extends AsyncTask<Void, Integer, ArrayList<ServiceLine>> {
        public SyncServiceLinesTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ServiceLine> doInBackground(Void... voids) {
            ArrayList<ServiceLine> list = ApiClient.getInstance(SyncService.this).fetchAllServiceLines();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<ServiceLine> list) {
            if (list != null) {
                serviceLineRepository.syncServiceLines(SyncService.this, list);
            }
        }
    }

    private class SyncCurrenciesTask extends AsyncTask<Void, Integer, ArrayList<Currency>> {
        public SyncCurrenciesTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Currency> doInBackground(Void... voids) {
            ArrayList<Currency> list = ApiClient.getInstance(SyncService.this).fetchAllCurrencies();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Currency> list) {
            if (list != null) {
                currencyRepository.syncCurrencies(SyncService.this, list);
            }
        }
    }

    private class SyncExpenseTypesTask extends AsyncTask<Void, Integer, ArrayList<ExpenseType>> {
        public SyncExpenseTypesTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ExpenseType> doInBackground(Void... voids) {
            ArrayList<ExpenseType> list = ApiClient.getInstance(SyncService.this).fetchAllExpenseTypes();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<ExpenseType> list) {
            if (list != null) {
                expenseTypeRepository.syncExpenseTypes(SyncService.this, list);
            }
        }
    }

    private class SyncTripTask extends AsyncTask<Void, Integer, ArrayList<Trip>> {
        public SyncTripTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Trip> doInBackground(Void... voids) {
            ArrayList<Trip> list = ApiClient.getInstance(SyncService.this).fetchAllTrips();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Trip> list) {
            if (list != null) {
                tripRepository.syncTrips(SyncService.this, list);
            }
        }
    }

    private class SyncSurrenders extends AsyncTask<Void, Integer, ArrayList<Surrender>> {
        public SyncSurrenders() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Surrender> doInBackground(Void... voids) {
            ArrayList<Surrender> list = surrenderRepository.getAllSurrenders(SyncService.this);
            for (Surrender surrender : list)
            {
                int result = ApiClient.getInstance(SyncService.this).sendSurrender(surrender);
                if (result == 1)
                {
                    surrenderRepository.deleteSurrender(SyncService.this, surrender);
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Surrender> list) {
        }
    }
}
