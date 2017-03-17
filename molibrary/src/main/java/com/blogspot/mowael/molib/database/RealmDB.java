package com.blogspot.mowael.molib.database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by moham on 3/16/2017.
 */

public class RealmDB {

    private static RealmDB instance;
    private final Realm realm;

    private RealmDB(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public static RealmDB getInstance(Context context) {
        if (instance == null) {
            instance = new RealmDB(context);
        }
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void save(RealmObject object) {
        realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();
    }

    /**
     * Updates an existing RealmObject that is identified by the same
     * io.realm.annotations.PrimaryKey or creates a new copy if no existing
     * object could be found. This is a deep copy or update
     * i.e., all referenced objects will be either copied or updated.
     *
     * @param object
     */
    public void createOrUpdate(RealmObject object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }


    public <T extends RealmObject> RealmResults<T> getAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    /**
     * @return true if objects was deleted, false otherwise.
     */
    public <T extends RealmObject> boolean deleteAll(Class<T> clazz) {
        return getAll(clazz).deleteAllFromRealm();
    }

    public <T extends RealmObject> void delete(Class<T> clazz, int atIndex) {
        getAll(clazz).get(atIndex).deleteFromRealm();
    }

    public <T extends RealmObject> RealmQuery<T> getQuery(Class<T> clazz) {
        return realm.where(clazz);
    }

}
