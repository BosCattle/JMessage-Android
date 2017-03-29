package io.realm;


import android.util.JsonReader;
import io.realm.RealmObjectSchema;
import io.realm.internal.ColumnInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>();
        modelClasses.add(tech.jiangtao.support.kit.realm.VCardRealm.class);
        modelClasses.add(tech.jiangtao.support.kit.realm.GroupRealm.class);
        modelClasses.add(tech.jiangtao.support.kit.realm.SessionRealm.class);
        modelClasses.add(tech.jiangtao.support.kit.realm.MessageRealm.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Table createTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm) {
        checkClass(clazz);

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return io.realm.VCardRealmRealmProxy.initTable(sharedRealm);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return io.realm.GroupRealmRealmProxy.initTable(sharedRealm);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return io.realm.SessionRealmRealmProxy.initTable(sharedRealm);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return io.realm.MessageRealmRealmProxy.initTable(sharedRealm);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public RealmObjectSchema createRealmObjectSchema(Class<? extends RealmModel> clazz, RealmSchema realmSchema) {
        checkClass(clazz);

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return io.realm.VCardRealmRealmProxy.createRealmObjectSchema(realmSchema);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return io.realm.GroupRealmRealmProxy.createRealmObjectSchema(realmSchema);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return io.realm.SessionRealmRealmProxy.createRealmObjectSchema(realmSchema);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return io.realm.MessageRealmRealmProxy.createRealmObjectSchema(realmSchema);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm, boolean allowExtraColumns) {
        checkClass(clazz);

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return io.realm.VCardRealmRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return io.realm.GroupRealmRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return io.realm.SessionRealmRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return io.realm.MessageRealmRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return io.realm.VCardRealmRealmProxy.getFieldNames();
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return io.realm.GroupRealmRealmProxy.getFieldNames();
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return io.realm.SessionRealmRealmProxy.getFieldNames();
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return io.realm.MessageRealmRealmProxy.getFieldNames();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public String getTableName(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return io.realm.VCardRealmRealmProxy.getTableName();
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return io.realm.GroupRealmRealmProxy.getTableName();
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return io.realm.SessionRealmRealmProxy.getTableName();
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return io.realm.MessageRealmRealmProxy.getTableName();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
                return clazz.cast(new io.realm.VCardRealmRealmProxy());
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
                return clazz.cast(new io.realm.GroupRealmRealmProxy());
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
                return clazz.cast(new io.realm.SessionRealmRealmProxy());
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
                return clazz.cast(new io.realm.MessageRealmRealmProxy());
            } else {
                throw getMissingProxyClassException(clazz);
            }
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return clazz.cast(io.realm.VCardRealmRealmProxy.copyOrUpdate(realm, (tech.jiangtao.support.kit.realm.VCardRealm) obj, update, cache));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return clazz.cast(io.realm.GroupRealmRealmProxy.copyOrUpdate(realm, (tech.jiangtao.support.kit.realm.GroupRealm) obj, update, cache));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return clazz.cast(io.realm.SessionRealmRealmProxy.copyOrUpdate(realm, (tech.jiangtao.support.kit.realm.SessionRealm) obj, update, cache));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return clazz.cast(io.realm.MessageRealmRealmProxy.copyOrUpdate(realm, (tech.jiangtao.support.kit.realm.MessageRealm) obj, update, cache));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            io.realm.VCardRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.VCardRealm) object, cache);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            io.realm.GroupRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.GroupRealm) object, cache);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            io.realm.SessionRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.SessionRealm) object, cache);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            io.realm.MessageRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.MessageRealm) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
                io.realm.VCardRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.VCardRealm) object, cache);
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
                io.realm.GroupRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.GroupRealm) object, cache);
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
                io.realm.SessionRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.SessionRealm) object, cache);
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
                io.realm.MessageRealmRealmProxy.insert(realm, (tech.jiangtao.support.kit.realm.MessageRealm) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
                    io.realm.VCardRealmRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
                    io.realm.GroupRealmRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
                    io.realm.SessionRealmRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
                    io.realm.MessageRealmRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            io.realm.VCardRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.VCardRealm) obj, cache);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            io.realm.GroupRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.GroupRealm) obj, cache);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            io.realm.SessionRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.SessionRealm) obj, cache);
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            io.realm.MessageRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.MessageRealm) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
                io.realm.VCardRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.VCardRealm) object, cache);
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
                io.realm.GroupRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.GroupRealm) object, cache);
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
                io.realm.SessionRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.SessionRealm) object, cache);
            } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
                io.realm.MessageRealmRealmProxy.insertOrUpdate(realm, (tech.jiangtao.support.kit.realm.MessageRealm) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
                    io.realm.VCardRealmRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
                    io.realm.GroupRealmRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
                    io.realm.SessionRealmRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
                    io.realm.MessageRealmRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return clazz.cast(io.realm.VCardRealmRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return clazz.cast(io.realm.GroupRealmRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return clazz.cast(io.realm.SessionRealmRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return clazz.cast(io.realm.MessageRealmRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return clazz.cast(io.realm.VCardRealmRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return clazz.cast(io.realm.GroupRealmRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return clazz.cast(io.realm.SessionRealmRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return clazz.cast(io.realm.MessageRealmRealmProxy.createUsingJsonStream(realm, reader));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(tech.jiangtao.support.kit.realm.VCardRealm.class)) {
            return clazz.cast(io.realm.VCardRealmRealmProxy.createDetachedCopy((tech.jiangtao.support.kit.realm.VCardRealm) realmObject, 0, maxDepth, cache));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.GroupRealm.class)) {
            return clazz.cast(io.realm.GroupRealmRealmProxy.createDetachedCopy((tech.jiangtao.support.kit.realm.GroupRealm) realmObject, 0, maxDepth, cache));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.SessionRealm.class)) {
            return clazz.cast(io.realm.SessionRealmRealmProxy.createDetachedCopy((tech.jiangtao.support.kit.realm.SessionRealm) realmObject, 0, maxDepth, cache));
        } else if (clazz.equals(tech.jiangtao.support.kit.realm.MessageRealm.class)) {
            return clazz.cast(io.realm.MessageRealmRealmProxy.createDetachedCopy((tech.jiangtao.support.kit.realm.MessageRealm) realmObject, 0, maxDepth, cache));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

}
