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

public class SessionRealmRealmProxy extends tech.jiangtao.support.kit.realm.SessionRealm
    implements RealmObjectProxy, SessionRealmRealmProxyInterface {

    static final class SessionRealmColumnInfo extends ColumnInfo
        implements Cloneable {

        public long session_idIndex;
        public long user_fromIndex;
        public long user_toIndex;
        public long vcard_idIndex;
        public long message_idIndex;
        public long unReadCountIndex;

        SessionRealmColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(6);
            this.session_idIndex = getValidColumnIndex(path, table, "SessionRealm", "session_id");
            indicesMap.put("session_id", this.session_idIndex);
            this.user_fromIndex = getValidColumnIndex(path, table, "SessionRealm", "user_from");
            indicesMap.put("user_from", this.user_fromIndex);
            this.user_toIndex = getValidColumnIndex(path, table, "SessionRealm", "user_to");
            indicesMap.put("user_to", this.user_toIndex);
            this.vcard_idIndex = getValidColumnIndex(path, table, "SessionRealm", "vcard_id");
            indicesMap.put("vcard_id", this.vcard_idIndex);
            this.message_idIndex = getValidColumnIndex(path, table, "SessionRealm", "message_id");
            indicesMap.put("message_id", this.message_idIndex);
            this.unReadCountIndex = getValidColumnIndex(path, table, "SessionRealm", "unReadCount");
            indicesMap.put("unReadCount", this.unReadCountIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final SessionRealmColumnInfo otherInfo = (SessionRealmColumnInfo) other;
            this.session_idIndex = otherInfo.session_idIndex;
            this.user_fromIndex = otherInfo.user_fromIndex;
            this.user_toIndex = otherInfo.user_toIndex;
            this.vcard_idIndex = otherInfo.vcard_idIndex;
            this.message_idIndex = otherInfo.message_idIndex;
            this.unReadCountIndex = otherInfo.unReadCountIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final SessionRealmColumnInfo clone() {
            return (SessionRealmColumnInfo) super.clone();
        }

    }
    private SessionRealmColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("session_id");
        fieldNames.add("user_from");
        fieldNames.add("user_to");
        fieldNames.add("vcard_id");
        fieldNames.add("message_id");
        fieldNames.add("unReadCount");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    SessionRealmRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (SessionRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(tech.jiangtao.support.kit.realm.SessionRealm.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$session_id() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.session_idIndex);
    }

    public void realmSet$session_id(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'session_id' cannot be changed after object was created.");
    }

    @SuppressWarnings("cast")
    public String realmGet$user_from() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.user_fromIndex);
    }

    public void realmSet$user_from(String value) {
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
                row.getTable().setNull(columnInfo.user_fromIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.user_fromIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.user_fromIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.user_fromIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$user_to() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.user_toIndex);
    }

    public void realmSet$user_to(String value) {
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
                row.getTable().setNull(columnInfo.user_toIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.user_toIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.user_toIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.user_toIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$vcard_id() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.vcard_idIndex);
    }

    public void realmSet$vcard_id(String value) {
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
                row.getTable().setNull(columnInfo.vcard_idIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.vcard_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.vcard_idIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.vcard_idIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$message_id() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.message_idIndex);
    }

    public void realmSet$message_id(String value) {
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
                row.getTable().setNull(columnInfo.message_idIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.message_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.message_idIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.message_idIndex, value);
    }

    @SuppressWarnings("cast")
    public int realmGet$unReadCount() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.unReadCountIndex);
    }

    public void realmSet$unReadCount(int value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.unReadCountIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.unReadCountIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("SessionRealm")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("SessionRealm");
            realmObjectSchema.add(new Property("session_id", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("user_from", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("user_to", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("vcard_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("message_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("unReadCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("SessionRealm");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_SessionRealm")) {
            Table table = sharedRealm.getTable("class_SessionRealm");
            table.addColumn(RealmFieldType.STRING, "session_id", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "user_from", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "user_to", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "vcard_id", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "message_id", Table.NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "unReadCount", Table.NOT_NULLABLE);
            table.addSearchIndex(table.getColumnIndex("session_id"));
            table.setPrimaryKey("session_id");
            return table;
        }
        return sharedRealm.getTable("class_SessionRealm");
    }

    public static SessionRealmColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_SessionRealm")) {
            Table table = sharedRealm.getTable("class_SessionRealm");
            final long columnCount = table.getColumnCount();
            if (columnCount != 6) {
                if (columnCount < 6) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 6 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 6 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 6 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final SessionRealmColumnInfo columnInfo = new SessionRealmColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("session_id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'session_id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("session_id") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'session_id' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.session_idIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(),"@PrimaryKey field 'session_id' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("session_id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'session_id' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("session_id"))) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'session_id' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("user_from")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'user_from' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("user_from") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'user_from' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.user_fromIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'user_from' is required. Either set @Required to field 'user_from' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("user_to")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'user_to' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("user_to") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'user_to' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.user_toIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'user_to' is required. Either set @Required to field 'user_to' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("vcard_id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'vcard_id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("vcard_id") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'vcard_id' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.vcard_idIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'vcard_id' is required. Either set @Required to field 'vcard_id' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("message_id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'message_id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("message_id") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'message_id' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.message_idIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'message_id' is required. Either set @Required to field 'message_id' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("unReadCount")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'unReadCount' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("unReadCount") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'unReadCount' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.unReadCountIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'unReadCount' does support null values in the existing Realm file. Use corresponding boxed type for field 'unReadCount' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'SessionRealm' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_SessionRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static tech.jiangtao.support.kit.realm.SessionRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        tech.jiangtao.support.kit.realm.SessionRealm obj = null;
        if (update) {
            Table table = realm.getTable(tech.jiangtao.support.kit.realm.SessionRealm.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = TableOrView.NO_MATCH;
            if (json.isNull("session_id")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("session_id"));
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.SessionRealm.class), false, Collections.<String> emptyList());
                    obj = new io.realm.SessionRealmRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("session_id")) {
                if (json.isNull("session_id")) {
                    obj = (io.realm.SessionRealmRealmProxy) realm.createObjectInternal(tech.jiangtao.support.kit.realm.SessionRealm.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.SessionRealmRealmProxy) realm.createObjectInternal(tech.jiangtao.support.kit.realm.SessionRealm.class, json.getString("session_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'session_id'.");
            }
        }
        if (json.has("user_from")) {
            if (json.isNull("user_from")) {
                ((SessionRealmRealmProxyInterface) obj).realmSet$user_from(null);
            } else {
                ((SessionRealmRealmProxyInterface) obj).realmSet$user_from((String) json.getString("user_from"));
            }
        }
        if (json.has("user_to")) {
            if (json.isNull("user_to")) {
                ((SessionRealmRealmProxyInterface) obj).realmSet$user_to(null);
            } else {
                ((SessionRealmRealmProxyInterface) obj).realmSet$user_to((String) json.getString("user_to"));
            }
        }
        if (json.has("vcard_id")) {
            if (json.isNull("vcard_id")) {
                ((SessionRealmRealmProxyInterface) obj).realmSet$vcard_id(null);
            } else {
                ((SessionRealmRealmProxyInterface) obj).realmSet$vcard_id((String) json.getString("vcard_id"));
            }
        }
        if (json.has("message_id")) {
            if (json.isNull("message_id")) {
                ((SessionRealmRealmProxyInterface) obj).realmSet$message_id(null);
            } else {
                ((SessionRealmRealmProxyInterface) obj).realmSet$message_id((String) json.getString("message_id"));
            }
        }
        if (json.has("unReadCount")) {
            if (json.isNull("unReadCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'unReadCount' to null.");
            } else {
                ((SessionRealmRealmProxyInterface) obj).realmSet$unReadCount((int) json.getInt("unReadCount"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static tech.jiangtao.support.kit.realm.SessionRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        tech.jiangtao.support.kit.realm.SessionRealm obj = new tech.jiangtao.support.kit.realm.SessionRealm();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("session_id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SessionRealmRealmProxyInterface) obj).realmSet$session_id(null);
                } else {
                    ((SessionRealmRealmProxyInterface) obj).realmSet$session_id((String) reader.nextString());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("user_from")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SessionRealmRealmProxyInterface) obj).realmSet$user_from(null);
                } else {
                    ((SessionRealmRealmProxyInterface) obj).realmSet$user_from((String) reader.nextString());
                }
            } else if (name.equals("user_to")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SessionRealmRealmProxyInterface) obj).realmSet$user_to(null);
                } else {
                    ((SessionRealmRealmProxyInterface) obj).realmSet$user_to((String) reader.nextString());
                }
            } else if (name.equals("vcard_id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SessionRealmRealmProxyInterface) obj).realmSet$vcard_id(null);
                } else {
                    ((SessionRealmRealmProxyInterface) obj).realmSet$vcard_id((String) reader.nextString());
                }
            } else if (name.equals("message_id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SessionRealmRealmProxyInterface) obj).realmSet$message_id(null);
                } else {
                    ((SessionRealmRealmProxyInterface) obj).realmSet$message_id((String) reader.nextString());
                }
            } else if (name.equals("unReadCount")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'unReadCount' to null.");
                } else {
                    ((SessionRealmRealmProxyInterface) obj).realmSet$unReadCount((int) reader.nextInt());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'session_id'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static tech.jiangtao.support.kit.realm.SessionRealm copyOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.SessionRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.SessionRealm) cachedRealmObject;
        } else {
            tech.jiangtao.support.kit.realm.SessionRealm realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(tech.jiangtao.support.kit.realm.SessionRealm.class);
                long pkColumnIndex = table.getPrimaryKey();
                String value = ((SessionRealmRealmProxyInterface) object).realmGet$session_id();
                long rowIndex = TableOrView.NO_MATCH;
                if (value == null) {
                    rowIndex = table.findFirstNull(pkColumnIndex);
                } else {
                    rowIndex = table.findFirstString(pkColumnIndex, value);
                }
                if (rowIndex != TableOrView.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.SessionRealm.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.SessionRealmRealmProxy();
                        cache.put(object, (RealmObjectProxy) realmObject);
                    } finally {
                        objectContext.clear();
                    }
                } else {
                    canUpdate = false;
                }
            }

            if (canUpdate) {
                return update(realm, realmObject, object, cache);
            } else {
                return copy(realm, object, update, cache);
            }
        }
    }

    public static tech.jiangtao.support.kit.realm.SessionRealm copy(Realm realm, tech.jiangtao.support.kit.realm.SessionRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.SessionRealm) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            tech.jiangtao.support.kit.realm.SessionRealm realmObject = realm.createObjectInternal(tech.jiangtao.support.kit.realm.SessionRealm.class, ((SessionRealmRealmProxyInterface) newObject).realmGet$session_id(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((SessionRealmRealmProxyInterface) realmObject).realmSet$user_from(((SessionRealmRealmProxyInterface) newObject).realmGet$user_from());
            ((SessionRealmRealmProxyInterface) realmObject).realmSet$user_to(((SessionRealmRealmProxyInterface) newObject).realmGet$user_to());
            ((SessionRealmRealmProxyInterface) realmObject).realmSet$vcard_id(((SessionRealmRealmProxyInterface) newObject).realmGet$vcard_id());
            ((SessionRealmRealmProxyInterface) realmObject).realmSet$message_id(((SessionRealmRealmProxyInterface) newObject).realmGet$message_id());
            ((SessionRealmRealmProxyInterface) realmObject).realmSet$unReadCount(((SessionRealmRealmProxyInterface) newObject).realmGet$unReadCount());
            return realmObject;
        }
    }

    public static long insert(Realm realm, tech.jiangtao.support.kit.realm.SessionRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        SessionRealmColumnInfo columnInfo = (SessionRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((SessionRealmRealmProxyInterface) object).realmGet$session_id();
        long rowIndex = TableOrView.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$user_from = ((SessionRealmRealmProxyInterface)object).realmGet$user_from();
        if (realmGet$user_from != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_fromIndex, rowIndex, realmGet$user_from, false);
        }
        String realmGet$user_to = ((SessionRealmRealmProxyInterface)object).realmGet$user_to();
        if (realmGet$user_to != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_toIndex, rowIndex, realmGet$user_to, false);
        }
        String realmGet$vcard_id = ((SessionRealmRealmProxyInterface)object).realmGet$vcard_id();
        if (realmGet$vcard_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.vcard_idIndex, rowIndex, realmGet$vcard_id, false);
        }
        String realmGet$message_id = ((SessionRealmRealmProxyInterface)object).realmGet$message_id();
        if (realmGet$message_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.message_idIndex, rowIndex, realmGet$message_id, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.unReadCountIndex, rowIndex, ((SessionRealmRealmProxyInterface)object).realmGet$unReadCount(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        SessionRealmColumnInfo columnInfo = (SessionRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        tech.jiangtao.support.kit.realm.SessionRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.SessionRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((SessionRealmRealmProxyInterface) object).realmGet$session_id();
                long rowIndex = TableOrView.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                String realmGet$user_from = ((SessionRealmRealmProxyInterface)object).realmGet$user_from();
                if (realmGet$user_from != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.user_fromIndex, rowIndex, realmGet$user_from, false);
                }
                String realmGet$user_to = ((SessionRealmRealmProxyInterface)object).realmGet$user_to();
                if (realmGet$user_to != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.user_toIndex, rowIndex, realmGet$user_to, false);
                }
                String realmGet$vcard_id = ((SessionRealmRealmProxyInterface)object).realmGet$vcard_id();
                if (realmGet$vcard_id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.vcard_idIndex, rowIndex, realmGet$vcard_id, false);
                }
                String realmGet$message_id = ((SessionRealmRealmProxyInterface)object).realmGet$message_id();
                if (realmGet$message_id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.message_idIndex, rowIndex, realmGet$message_id, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.unReadCountIndex, rowIndex, ((SessionRealmRealmProxyInterface)object).realmGet$unReadCount(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.SessionRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        SessionRealmColumnInfo columnInfo = (SessionRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((SessionRealmRealmProxyInterface) object).realmGet$session_id();
        long rowIndex = TableOrView.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
        }
        cache.put(object, rowIndex);
        String realmGet$user_from = ((SessionRealmRealmProxyInterface)object).realmGet$user_from();
        if (realmGet$user_from != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_fromIndex, rowIndex, realmGet$user_from, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.user_fromIndex, rowIndex, false);
        }
        String realmGet$user_to = ((SessionRealmRealmProxyInterface)object).realmGet$user_to();
        if (realmGet$user_to != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_toIndex, rowIndex, realmGet$user_to, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.user_toIndex, rowIndex, false);
        }
        String realmGet$vcard_id = ((SessionRealmRealmProxyInterface)object).realmGet$vcard_id();
        if (realmGet$vcard_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.vcard_idIndex, rowIndex, realmGet$vcard_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.vcard_idIndex, rowIndex, false);
        }
        String realmGet$message_id = ((SessionRealmRealmProxyInterface)object).realmGet$message_id();
        if (realmGet$message_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.message_idIndex, rowIndex, realmGet$message_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.message_idIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.unReadCountIndex, rowIndex, ((SessionRealmRealmProxyInterface)object).realmGet$unReadCount(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        SessionRealmColumnInfo columnInfo = (SessionRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.SessionRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        tech.jiangtao.support.kit.realm.SessionRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.SessionRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((SessionRealmRealmProxyInterface) object).realmGet$session_id();
                long rowIndex = TableOrView.NO_MATCH;
                if (primaryKeyValue == null) {
                    rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
                } else {
                    rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(primaryKeyValue, false);
                }
                cache.put(object, rowIndex);
                String realmGet$user_from = ((SessionRealmRealmProxyInterface)object).realmGet$user_from();
                if (realmGet$user_from != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.user_fromIndex, rowIndex, realmGet$user_from, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.user_fromIndex, rowIndex, false);
                }
                String realmGet$user_to = ((SessionRealmRealmProxyInterface)object).realmGet$user_to();
                if (realmGet$user_to != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.user_toIndex, rowIndex, realmGet$user_to, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.user_toIndex, rowIndex, false);
                }
                String realmGet$vcard_id = ((SessionRealmRealmProxyInterface)object).realmGet$vcard_id();
                if (realmGet$vcard_id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.vcard_idIndex, rowIndex, realmGet$vcard_id, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.vcard_idIndex, rowIndex, false);
                }
                String realmGet$message_id = ((SessionRealmRealmProxyInterface)object).realmGet$message_id();
                if (realmGet$message_id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.message_idIndex, rowIndex, realmGet$message_id, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.message_idIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.unReadCountIndex, rowIndex, ((SessionRealmRealmProxyInterface)object).realmGet$unReadCount(), false);
            }
        }
    }

    public static tech.jiangtao.support.kit.realm.SessionRealm createDetachedCopy(tech.jiangtao.support.kit.realm.SessionRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        tech.jiangtao.support.kit.realm.SessionRealm unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (tech.jiangtao.support.kit.realm.SessionRealm)cachedObject.object;
            } else {
                unmanagedObject = (tech.jiangtao.support.kit.realm.SessionRealm)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new tech.jiangtao.support.kit.realm.SessionRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((SessionRealmRealmProxyInterface) unmanagedObject).realmSet$session_id(((SessionRealmRealmProxyInterface) realmObject).realmGet$session_id());
        ((SessionRealmRealmProxyInterface) unmanagedObject).realmSet$user_from(((SessionRealmRealmProxyInterface) realmObject).realmGet$user_from());
        ((SessionRealmRealmProxyInterface) unmanagedObject).realmSet$user_to(((SessionRealmRealmProxyInterface) realmObject).realmGet$user_to());
        ((SessionRealmRealmProxyInterface) unmanagedObject).realmSet$vcard_id(((SessionRealmRealmProxyInterface) realmObject).realmGet$vcard_id());
        ((SessionRealmRealmProxyInterface) unmanagedObject).realmSet$message_id(((SessionRealmRealmProxyInterface) realmObject).realmGet$message_id());
        ((SessionRealmRealmProxyInterface) unmanagedObject).realmSet$unReadCount(((SessionRealmRealmProxyInterface) realmObject).realmGet$unReadCount());
        return unmanagedObject;
    }

    static tech.jiangtao.support.kit.realm.SessionRealm update(Realm realm, tech.jiangtao.support.kit.realm.SessionRealm realmObject, tech.jiangtao.support.kit.realm.SessionRealm newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((SessionRealmRealmProxyInterface) realmObject).realmSet$user_from(((SessionRealmRealmProxyInterface) newObject).realmGet$user_from());
        ((SessionRealmRealmProxyInterface) realmObject).realmSet$user_to(((SessionRealmRealmProxyInterface) newObject).realmGet$user_to());
        ((SessionRealmRealmProxyInterface) realmObject).realmSet$vcard_id(((SessionRealmRealmProxyInterface) newObject).realmGet$vcard_id());
        ((SessionRealmRealmProxyInterface) realmObject).realmSet$message_id(((SessionRealmRealmProxyInterface) newObject).realmGet$message_id());
        ((SessionRealmRealmProxyInterface) realmObject).realmSet$unReadCount(((SessionRealmRealmProxyInterface) newObject).realmGet$unReadCount());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("SessionRealm = [");
        stringBuilder.append("{session_id:");
        stringBuilder.append(realmGet$session_id() != null ? realmGet$session_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_from:");
        stringBuilder.append(realmGet$user_from() != null ? realmGet$user_from() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_to:");
        stringBuilder.append(realmGet$user_to() != null ? realmGet$user_to() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{vcard_id:");
        stringBuilder.append(realmGet$vcard_id() != null ? realmGet$vcard_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{message_id:");
        stringBuilder.append(realmGet$message_id() != null ? realmGet$message_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{unReadCount:");
        stringBuilder.append(realmGet$unReadCount());
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
        SessionRealmRealmProxy aSessionRealm = (SessionRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aSessionRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aSessionRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aSessionRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
