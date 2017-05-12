package com.blogspot.mowael.molib.storage.database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by moham on 3/16/2017.
 */

public class RealmDB {


    private static RealmDB instance;
    private final Realm realm;

    private RealmDB(Context context, RealmMigration realmMigration, String dbName, int version) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(dbName)
                .schemaVersion(version)
                .migration(realmMigration)
                .build();
        realm = Realm.getInstance(config);
    }

    public static RealmDB getInstance(Context context, RealmMigration realmMigration, String dbName, int version) {
        if (instance == null) {
            instance = new RealmDB(context, realmMigration, dbName, version);
        }
        return instance;
    }

    private RealmDB(Context context, String dbName, int version) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(dbName)
                .schemaVersion(version)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    public static RealmDB getInstance(Context context, String dbName, int version) {
        if (instance == null) {
            instance = new RealmDB(context, dbName, version);
        }
        return instance;
    }

    public static RealmDB getInstance() {
        if (instance == null) {
            throw new RuntimeException("RealmDB must be initialized in the Application class ");
        }
        return instance;
    }


    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    /**
     * @param realm realm of the current thread
     * @param object must have PrimaryKey
     * @param <T>
     */
    public <T extends RealmObject> void saveOrUpdate(Realm realm, final T object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
    }

    public <T extends RealmObject> void save(Realm realm, final T object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(object);
            }
        });
    }

    public <T extends RealmObject> void deleteAndSave(Realm realm, Class<T> aClass, T object) {
        deleteAll(realm, aClass);
        save(realm, object);
    }

    /**
     * Updates an existing RealmObject that is identified by the same
     * io.realm.annotations.PrimaryKey or creates a new copy if no existing
     * object could be found. This is a deep copy or update
     * i.e., all referenced objects will be either copied or updated.
     *
     * @param object
     */
    public <T extends RealmObject> void createOrUpdate(Realm realm, final T object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
    }


    public <T extends RealmObject> RealmResults<T> getAll(Realm realm, Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    public <T extends RealmObject> RealmQuery<T> RealmQuery(Realm realm, Class<T> clazz) {
        return realm.where(clazz);
    }

    /**
     * @return true if objects was deleted, false otherwise.
     */
    public <T extends RealmObject> void deleteAll(Realm realm, final Class<T> clazz) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getAll(realm, clazz).deleteAllFromRealm();
            }
        });
    }

    public <T extends RealmObject> void delete(Realm realm, final Class<T> clazz, final int atIndex) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getAll(realm, clazz).get(atIndex).deleteFromRealm();
            }
        });
    }
}
