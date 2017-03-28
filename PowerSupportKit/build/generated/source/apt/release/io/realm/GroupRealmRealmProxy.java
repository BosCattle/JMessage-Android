package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupRealmRealmProxy extends tech.jiangtao.support.kit.realm.GroupRealm
    implements RealmObjectProxy, GroupRealmRealmProxyInterface {

    static final class GroupRealmColumnInfo extends ColumnInfo
        implements Cloneable {

        public long groupNameIndex;

        GroupRealmColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(1);
            this.groupNameIndex = getValidColumnIndex(path, table, "GroupRealm", "groupName");
            indicesMap.put("groupName", this.groupNameIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final GroupRealmColumnInfo otherInfo = (GroupRealmColumnInfo) other;
            this.groupNameIndex = otherInfo.groupNameIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final GroupRealmColumnInfo clone() {
            return (GroupRealmColumnInfo) super.clone();
        }

    }
    private GroupRealmColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("groupName");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    GroupRealmRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (GroupRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(tech.jiangtao.support.kit.realm.GroupRealm.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$groupName() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.groupNameIndex);
    }

    public void realmSet$groupName(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.groupNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.groupNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.groupNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.groupNameIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("GroupRealm")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("GroupRealm");
            realmObjectSchema.add(new Property("groupName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("GroupRealm");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_GroupRealm")) {
            Table table = sharedRealm.getTable("class_GroupRealm");
            table.addColumn(RealmFieldType.STRING, "groupName", Table.NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_GroupRealm");
    }

    public static GroupRealmColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_GroupRealm")) {
            Table table = sharedRealm.getTable("class_GroupRealm");
            final long columnCount = table.getColumnCount();
            if (columnCount != 1) {
                if (columnCount < 1) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 1 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 1 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 1 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final GroupRealmColumnInfo columnInfo = new GroupRealmColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("groupName")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'groupName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("groupName") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'groupName' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.groupNameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'groupName' is required. Either set @Required to field 'groupName' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'GroupRealm' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_GroupRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static tech.jiangtao.support.kit.realm.GroupRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        tech.jiangtao.support.kit.realm.GroupRealm obj = realm.createObjectInternal(tech.jiangtao.support.kit.realm.GroupRealm.class, true, excludeFields);
        if (json.has("groupName")) {
            if (json.isNull("groupName")) {
                ((GroupRealmRealmProxyInterface) obj).realmSet$groupName(null);
            } else {
                ((GroupRealmRealmProxyInterface) obj).realmSet$groupName((String) json.getString("groupName"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static tech.jiangtao.support.kit.realm.GroupRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        tech.jiangtao.support.kit.realm.GroupRealm obj = new tech.jiangtao.support.kit.realm.GroupRealm();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("groupName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GroupRealmRealmProxyInterface) obj).realmSet$groupName(null);
                } else {
                    ((GroupRealmRealmProxyInterface) obj).realmSet$groupName((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static tech.jiangtao.support.kit.realm.GroupRealm copyOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.GroupRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.GroupRealm) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static tech.jiangtao.support.kit.realm.GroupRealm copy(Realm realm, tech.jiangtao.support.kit.realm.GroupRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.GroupRealm) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            tech.jiangtao.support.kit.realm.GroupRealm realmObject = realm.createObjectInternal(tech.jiangtao.support.kit.realm.GroupRealm.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((GroupRealmRealmProxyInterface) realmObject).realmSet$groupName(((GroupRealmRealmProxyInterface) newObject).realmGet$groupName());
            return realmObject;
        }
    }

    public static long insert(Realm realm, tech.jiangtao.support.kit.realm.GroupRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.GroupRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupRealmColumnInfo columnInfo = (GroupRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.GroupRealm.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$groupName = ((GroupRealmRealmProxyInterface)object).realmGet$groupName();
        if (realmGet$groupName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.GroupRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupRealmColumnInfo columnInfo = (GroupRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.GroupRealm.class);
        tech.jiangtao.support.kit.realm.GroupRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.GroupRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$groupName = ((GroupRealmRealmProxyInterface)object).realmGet$groupName();
                if (realmGet$groupName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.GroupRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.GroupRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupRealmColumnInfo columnInfo = (GroupRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.GroupRealm.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$groupName = ((GroupRealmRealmProxyInterface)object).realmGet$groupName();
        if (realmGet$groupName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.groupNameIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.GroupRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupRealmColumnInfo columnInfo = (GroupRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.GroupRealm.class);
        tech.jiangtao.support.kit.realm.GroupRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.GroupRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$groupName = ((GroupRealmRealmProxyInterface)object).realmGet$groupName();
                if (realmGet$groupName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.groupNameIndex, rowIndex, false);
                }
            }
        }
    }

    public static tech.jiangtao.support.kit.realm.GroupRealm createDetachedCopy(tech.jiangtao.support.kit.realm.GroupRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        tech.jiangtao.support.kit.realm.GroupRealm unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (tech.jiangtao.support.kit.realm.GroupRealm)cachedObject.object;
            } else {
                unmanagedObject = (tech.jiangtao.support.kit.realm.GroupRealm)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new tech.jiangtao.support.kit.realm.GroupRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((GroupRealmRealmProxyInterface) unmanagedObject).realmSet$groupName(((GroupRealmRealmProxyInterface) realmObject).realmGet$groupName());
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("GroupRealm = [");
        stringBuilder.append("{groupName:");
        stringBuilder.append(realmGet$groupName() != null ? realmGet$groupName() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupRealmRealmProxy aGroupRealm = (GroupRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aGroupRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aGroupRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aGroupRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
