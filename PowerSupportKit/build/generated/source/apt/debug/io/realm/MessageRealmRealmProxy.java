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

public class MessageRealmRealmProxy extends tech.jiangtao.support.kit.realm.MessageRealm
    implements RealmObjectProxy, MessageRealmRealmProxyInterface {

    static final class MessageRealmColumnInfo extends ColumnInfo
        implements Cloneable {

        public long idIndex;
        public long mainJIDIndex;
        public long withJIDIndex;
        public long textMessageIndex;
        public long timeIndex;
        public long threadIndex;
        public long typeIndex;
        public long messageTypeIndex;
        public long messageStatusIndex;

        MessageRealmColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(9);
            this.idIndex = getValidColumnIndex(path, table, "MessageRealm", "id");
            indicesMap.put("id", this.idIndex);
            this.mainJIDIndex = getValidColumnIndex(path, table, "MessageRealm", "mainJID");
            indicesMap.put("mainJID", this.mainJIDIndex);
            this.withJIDIndex = getValidColumnIndex(path, table, "MessageRealm", "withJID");
            indicesMap.put("withJID", this.withJIDIndex);
            this.textMessageIndex = getValidColumnIndex(path, table, "MessageRealm", "textMessage");
            indicesMap.put("textMessage", this.textMessageIndex);
            this.timeIndex = getValidColumnIndex(path, table, "MessageRealm", "time");
            indicesMap.put("time", this.timeIndex);
            this.threadIndex = getValidColumnIndex(path, table, "MessageRealm", "thread");
            indicesMap.put("thread", this.threadIndex);
            this.typeIndex = getValidColumnIndex(path, table, "MessageRealm", "type");
            indicesMap.put("type", this.typeIndex);
            this.messageTypeIndex = getValidColumnIndex(path, table, "MessageRealm", "messageType");
            indicesMap.put("messageType", this.messageTypeIndex);
            this.messageStatusIndex = getValidColumnIndex(path, table, "MessageRealm", "messageStatus");
            indicesMap.put("messageStatus", this.messageStatusIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final MessageRealmColumnInfo otherInfo = (MessageRealmColumnInfo) other;
            this.idIndex = otherInfo.idIndex;
            this.mainJIDIndex = otherInfo.mainJIDIndex;
            this.withJIDIndex = otherInfo.withJIDIndex;
            this.textMessageIndex = otherInfo.textMessageIndex;
            this.timeIndex = otherInfo.timeIndex;
            this.threadIndex = otherInfo.threadIndex;
            this.typeIndex = otherInfo.typeIndex;
            this.messageTypeIndex = otherInfo.messageTypeIndex;
            this.messageStatusIndex = otherInfo.messageStatusIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final MessageRealmColumnInfo clone() {
            return (MessageRealmColumnInfo) super.clone();
        }

    }
    private MessageRealmColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("mainJID");
        fieldNames.add("withJID");
        fieldNames.add("textMessage");
        fieldNames.add("time");
        fieldNames.add("thread");
        fieldNames.add("type");
        fieldNames.add("messageType");
        fieldNames.add("messageStatus");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    MessageRealmRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (MessageRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(tech.jiangtao.support.kit.realm.MessageRealm.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$id() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.idIndex);
    }

    public void realmSet$id(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @SuppressWarnings("cast")
    public String realmGet$mainJID() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.mainJIDIndex);
    }

    public void realmSet$mainJID(String value) {
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
                row.getTable().setNull(columnInfo.mainJIDIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.mainJIDIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.mainJIDIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.mainJIDIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$withJID() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.withJIDIndex);
    }

    public void realmSet$withJID(String value) {
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
                row.getTable().setNull(columnInfo.withJIDIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.withJIDIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.withJIDIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.withJIDIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$textMessage() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.textMessageIndex);
    }

    public void realmSet$textMessage(String value) {
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
                row.getTable().setNull(columnInfo.textMessageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.textMessageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.textMessageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.textMessageIndex, value);
    }

    @SuppressWarnings("cast")
    public Date realmGet$time() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.timeIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.timeIndex);
    }

    public void realmSet$time(Date value) {
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
                row.getTable().setNull(columnInfo.timeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.timeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.timeIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.timeIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$thread() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.threadIndex);
    }

    public void realmSet$thread(String value) {
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
                row.getTable().setNull(columnInfo.threadIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.threadIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.threadIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.threadIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$type() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.typeIndex);
    }

    public void realmSet$type(String value) {
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
                row.getTable().setNull(columnInfo.typeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.typeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.typeIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$messageType() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.messageTypeIndex);
    }

    public void realmSet$messageType(String value) {
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
                row.getTable().setNull(columnInfo.messageTypeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.messageTypeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.messageTypeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.messageTypeIndex, value);
    }

    @SuppressWarnings("cast")
    public boolean realmGet$messageStatus() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.messageStatusIndex);
    }

    public void realmSet$messageStatus(boolean value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.messageStatusIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.messageStatusIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("MessageRealm")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("MessageRealm");
            realmObjectSchema.add(new Property("id", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("mainJID", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("withJID", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("textMessage", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("time", RealmFieldType.DATE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("thread", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("messageType", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("messageStatus", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("MessageRealm");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_MessageRealm")) {
            Table table = sharedRealm.getTable("class_MessageRealm");
            table.addColumn(RealmFieldType.STRING, "id", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "mainJID", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "withJID", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "textMessage", Table.NULLABLE);
            table.addColumn(RealmFieldType.DATE, "time", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "thread", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "type", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "messageType", Table.NULLABLE);
            table.addColumn(RealmFieldType.BOOLEAN, "messageStatus", Table.NOT_NULLABLE);
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return sharedRealm.getTable("class_MessageRealm");
    }

    public static MessageRealmColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_MessageRealm")) {
            Table table = sharedRealm.getTable("class_MessageRealm");
            final long columnCount = table.getColumnCount();
            if (columnCount != 9) {
                if (columnCount < 9) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 9 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 9 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 9 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final MessageRealmColumnInfo columnInfo = new MessageRealmColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("id") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'id' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.idIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(),"@PrimaryKey field 'id' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'id' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("id"))) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'id' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("mainJID")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'mainJID' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("mainJID") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'mainJID' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.mainJIDIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'mainJID' is required. Either set @Required to field 'mainJID' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("withJID")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'withJID' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("withJID") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'withJID' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.withJIDIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'withJID' is required. Either set @Required to field 'withJID' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("textMessage")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'textMessage' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("textMessage") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'textMessage' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.textMessageIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'textMessage' is required. Either set @Required to field 'textMessage' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("time")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'time' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("time") != RealmFieldType.DATE) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Date' for field 'time' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.timeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'time' is required. Either set @Required to field 'time' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("thread")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'thread' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("thread") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'thread' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.threadIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'thread' is required. Either set @Required to field 'thread' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("type")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'type' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("type") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'type' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.typeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'type' is required. Either set @Required to field 'type' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("messageType")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'messageType' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("messageType") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'messageType' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.messageTypeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'messageType' is required. Either set @Required to field 'messageType' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("messageStatus")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'messageStatus' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("messageStatus") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'messageStatus' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.messageStatusIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'messageStatus' does support null values in the existing Realm file. Use corresponding boxed type for field 'messageStatus' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'MessageRealm' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_MessageRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static tech.jiangtao.support.kit.realm.MessageRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        tech.jiangtao.support.kit.realm.MessageRealm obj = null;
        if (update) {
            Table table = realm.getTable(tech.jiangtao.support.kit.realm.MessageRealm.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = TableOrView.NO_MATCH;
            if (json.isNull("id")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("id"));
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.MessageRealm.class), false, Collections.<String> emptyList());
                    obj = new io.realm.MessageRealmRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.MessageRealmRealmProxy) realm.createObjectInternal(tech.jiangtao.support.kit.realm.MessageRealm.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.MessageRealmRealmProxy) realm.createObjectInternal(tech.jiangtao.support.kit.realm.MessageRealm.class, json.getString("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }
        if (json.has("mainJID")) {
            if (json.isNull("mainJID")) {
                ((MessageRealmRealmProxyInterface) obj).realmSet$mainJID(null);
            } else {
                ((MessageRealmRealmProxyInterface) obj).realmSet$mainJID((String) json.getString("mainJID"));
            }
        }
        if (json.has("withJID")) {
            if (json.isNull("withJID")) {
                ((MessageRealmRealmProxyInterface) obj).realmSet$withJID(null);
            } else {
                ((MessageRealmRealmProxyInterface) obj).realmSet$withJID((String) json.getString("withJID"));
            }
        }
        if (json.has("textMessage")) {
            if (json.isNull("textMessage")) {
                ((MessageRealmRealmProxyInterface) obj).realmSet$textMessage(null);
            } else {
                ((MessageRealmRealmProxyInterface) obj).realmSet$textMessage((String) json.getString("textMessage"));
            }
        }
        if (json.has("time")) {
            if (json.isNull("time")) {
                ((MessageRealmRealmProxyInterface) obj).realmSet$time(null);
            } else {
                Object timestamp = json.get("time");
                if (timestamp instanceof String) {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$time(JsonUtils.stringToDate((String) timestamp));
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$time(new Date(json.getLong("time")));
                }
            }
        }
        if (json.has("thread")) {
            if (json.isNull("thread")) {
                ((MessageRealmRealmProxyInterface) obj).realmSet$thread(null);
            } else {
                ((MessageRealmRealmProxyInterface) obj).realmSet$thread((String) json.getString("thread"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                ((MessageRealmRealmProxyInterface) obj).realmSet$type(null);
            } else {
                ((MessageRealmRealmProxyInterface) obj).realmSet$type((String) json.getString("type"));
            }
        }
        if (json.has("messageType")) {
            if (json.isNull("messageType")) {
                ((MessageRealmRealmProxyInterface) obj).realmSet$messageType(null);
            } else {
                ((MessageRealmRealmProxyInterface) obj).realmSet$messageType((String) json.getString("messageType"));
            }
        }
        if (json.has("messageStatus")) {
            if (json.isNull("messageStatus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'messageStatus' to null.");
            } else {
                ((MessageRealmRealmProxyInterface) obj).realmSet$messageStatus((boolean) json.getBoolean("messageStatus"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static tech.jiangtao.support.kit.realm.MessageRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        tech.jiangtao.support.kit.realm.MessageRealm obj = new tech.jiangtao.support.kit.realm.MessageRealm();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$id(null);
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$id((String) reader.nextString());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("mainJID")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$mainJID(null);
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$mainJID((String) reader.nextString());
                }
            } else if (name.equals("withJID")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$withJID(null);
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$withJID((String) reader.nextString());
                }
            } else if (name.equals("textMessage")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$textMessage(null);
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$textMessage((String) reader.nextString());
                }
            } else if (name.equals("time")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$time(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        ((MessageRealmRealmProxyInterface) obj).realmSet$time(new Date(timestamp));
                    }
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$time(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("thread")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$thread(null);
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$thread((String) reader.nextString());
                }
            } else if (name.equals("type")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$type(null);
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$type((String) reader.nextString());
                }
            } else if (name.equals("messageType")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmRealmProxyInterface) obj).realmSet$messageType(null);
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$messageType((String) reader.nextString());
                }
            } else if (name.equals("messageStatus")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'messageStatus' to null.");
                } else {
                    ((MessageRealmRealmProxyInterface) obj).realmSet$messageStatus((boolean) reader.nextBoolean());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static tech.jiangtao.support.kit.realm.MessageRealm copyOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.MessageRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.MessageRealm) cachedRealmObject;
        } else {
            tech.jiangtao.support.kit.realm.MessageRealm realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(tech.jiangtao.support.kit.realm.MessageRealm.class);
                long pkColumnIndex = table.getPrimaryKey();
                String value = ((MessageRealmRealmProxyInterface) object).realmGet$id();
                long rowIndex = TableOrView.NO_MATCH;
                if (value == null) {
                    rowIndex = table.findFirstNull(pkColumnIndex);
                } else {
                    rowIndex = table.findFirstString(pkColumnIndex, value);
                }
                if (rowIndex != TableOrView.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.MessageRealm.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.MessageRealmRealmProxy();
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

    public static tech.jiangtao.support.kit.realm.MessageRealm copy(Realm realm, tech.jiangtao.support.kit.realm.MessageRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.MessageRealm) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            tech.jiangtao.support.kit.realm.MessageRealm realmObject = realm.createObjectInternal(tech.jiangtao.support.kit.realm.MessageRealm.class, ((MessageRealmRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$mainJID(((MessageRealmRealmProxyInterface) newObject).realmGet$mainJID());
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$withJID(((MessageRealmRealmProxyInterface) newObject).realmGet$withJID());
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$textMessage(((MessageRealmRealmProxyInterface) newObject).realmGet$textMessage());
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$time(((MessageRealmRealmProxyInterface) newObject).realmGet$time());
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$thread(((MessageRealmRealmProxyInterface) newObject).realmGet$thread());
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$type(((MessageRealmRealmProxyInterface) newObject).realmGet$type());
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$messageType(((MessageRealmRealmProxyInterface) newObject).realmGet$messageType());
            ((MessageRealmRealmProxyInterface) realmObject).realmSet$messageStatus(((MessageRealmRealmProxyInterface) newObject).realmGet$messageStatus());
            return realmObject;
        }
    }

    public static long insert(Realm realm, tech.jiangtao.support.kit.realm.MessageRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageRealmColumnInfo columnInfo = (MessageRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((MessageRealmRealmProxyInterface) object).realmGet$id();
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
        String realmGet$mainJID = ((MessageRealmRealmProxyInterface)object).realmGet$mainJID();
        if (realmGet$mainJID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.mainJIDIndex, rowIndex, realmGet$mainJID, false);
        }
        String realmGet$withJID = ((MessageRealmRealmProxyInterface)object).realmGet$withJID();
        if (realmGet$withJID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.withJIDIndex, rowIndex, realmGet$withJID, false);
        }
        String realmGet$textMessage = ((MessageRealmRealmProxyInterface)object).realmGet$textMessage();
        if (realmGet$textMessage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textMessageIndex, rowIndex, realmGet$textMessage, false);
        }
        java.util.Date realmGet$time = ((MessageRealmRealmProxyInterface)object).realmGet$time();
        if (realmGet$time != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
        }
        String realmGet$thread = ((MessageRealmRealmProxyInterface)object).realmGet$thread();
        if (realmGet$thread != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.threadIndex, rowIndex, realmGet$thread, false);
        }
        String realmGet$type = ((MessageRealmRealmProxyInterface)object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        }
        String realmGet$messageType = ((MessageRealmRealmProxyInterface)object).realmGet$messageType();
        if (realmGet$messageType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.messageTypeIndex, rowIndex, realmGet$messageType, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.messageStatusIndex, rowIndex, ((MessageRealmRealmProxyInterface)object).realmGet$messageStatus(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageRealmColumnInfo columnInfo = (MessageRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        tech.jiangtao.support.kit.realm.MessageRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.MessageRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((MessageRealmRealmProxyInterface) object).realmGet$id();
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
                String realmGet$mainJID = ((MessageRealmRealmProxyInterface)object).realmGet$mainJID();
                if (realmGet$mainJID != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.mainJIDIndex, rowIndex, realmGet$mainJID, false);
                }
                String realmGet$withJID = ((MessageRealmRealmProxyInterface)object).realmGet$withJID();
                if (realmGet$withJID != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.withJIDIndex, rowIndex, realmGet$withJID, false);
                }
                String realmGet$textMessage = ((MessageRealmRealmProxyInterface)object).realmGet$textMessage();
                if (realmGet$textMessage != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.textMessageIndex, rowIndex, realmGet$textMessage, false);
                }
                java.util.Date realmGet$time = ((MessageRealmRealmProxyInterface)object).realmGet$time();
                if (realmGet$time != null) {
                    Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
                }
                String realmGet$thread = ((MessageRealmRealmProxyInterface)object).realmGet$thread();
                if (realmGet$thread != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.threadIndex, rowIndex, realmGet$thread, false);
                }
                String realmGet$type = ((MessageRealmRealmProxyInterface)object).realmGet$type();
                if (realmGet$type != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
                }
                String realmGet$messageType = ((MessageRealmRealmProxyInterface)object).realmGet$messageType();
                if (realmGet$messageType != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.messageTypeIndex, rowIndex, realmGet$messageType, false);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.messageStatusIndex, rowIndex, ((MessageRealmRealmProxyInterface)object).realmGet$messageStatus(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.MessageRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageRealmColumnInfo columnInfo = (MessageRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((MessageRealmRealmProxyInterface) object).realmGet$id();
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
        String realmGet$mainJID = ((MessageRealmRealmProxyInterface)object).realmGet$mainJID();
        if (realmGet$mainJID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.mainJIDIndex, rowIndex, realmGet$mainJID, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.mainJIDIndex, rowIndex, false);
        }
        String realmGet$withJID = ((MessageRealmRealmProxyInterface)object).realmGet$withJID();
        if (realmGet$withJID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.withJIDIndex, rowIndex, realmGet$withJID, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.withJIDIndex, rowIndex, false);
        }
        String realmGet$textMessage = ((MessageRealmRealmProxyInterface)object).realmGet$textMessage();
        if (realmGet$textMessage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textMessageIndex, rowIndex, realmGet$textMessage, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.textMessageIndex, rowIndex, false);
        }
        java.util.Date realmGet$time = ((MessageRealmRealmProxyInterface)object).realmGet$time();
        if (realmGet$time != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.timeIndex, rowIndex, false);
        }
        String realmGet$thread = ((MessageRealmRealmProxyInterface)object).realmGet$thread();
        if (realmGet$thread != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.threadIndex, rowIndex, realmGet$thread, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.threadIndex, rowIndex, false);
        }
        String realmGet$type = ((MessageRealmRealmProxyInterface)object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
        }
        String realmGet$messageType = ((MessageRealmRealmProxyInterface)object).realmGet$messageType();
        if (realmGet$messageType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.messageTypeIndex, rowIndex, realmGet$messageType, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.messageTypeIndex, rowIndex, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.messageStatusIndex, rowIndex, ((MessageRealmRealmProxyInterface)object).realmGet$messageStatus(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageRealmColumnInfo columnInfo = (MessageRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.MessageRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        tech.jiangtao.support.kit.realm.MessageRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.MessageRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((MessageRealmRealmProxyInterface) object).realmGet$id();
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
                String realmGet$mainJID = ((MessageRealmRealmProxyInterface)object).realmGet$mainJID();
                if (realmGet$mainJID != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.mainJIDIndex, rowIndex, realmGet$mainJID, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.mainJIDIndex, rowIndex, false);
                }
                String realmGet$withJID = ((MessageRealmRealmProxyInterface)object).realmGet$withJID();
                if (realmGet$withJID != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.withJIDIndex, rowIndex, realmGet$withJID, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.withJIDIndex, rowIndex, false);
                }
                String realmGet$textMessage = ((MessageRealmRealmProxyInterface)object).realmGet$textMessage();
                if (realmGet$textMessage != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.textMessageIndex, rowIndex, realmGet$textMessage, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.textMessageIndex, rowIndex, false);
                }
                java.util.Date realmGet$time = ((MessageRealmRealmProxyInterface)object).realmGet$time();
                if (realmGet$time != null) {
                    Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.timeIndex, rowIndex, false);
                }
                String realmGet$thread = ((MessageRealmRealmProxyInterface)object).realmGet$thread();
                if (realmGet$thread != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.threadIndex, rowIndex, realmGet$thread, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.threadIndex, rowIndex, false);
                }
                String realmGet$type = ((MessageRealmRealmProxyInterface)object).realmGet$type();
                if (realmGet$type != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
                }
                String realmGet$messageType = ((MessageRealmRealmProxyInterface)object).realmGet$messageType();
                if (realmGet$messageType != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.messageTypeIndex, rowIndex, realmGet$messageType, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.messageTypeIndex, rowIndex, false);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.messageStatusIndex, rowIndex, ((MessageRealmRealmProxyInterface)object).realmGet$messageStatus(), false);
            }
        }
    }

    public static tech.jiangtao.support.kit.realm.MessageRealm createDetachedCopy(tech.jiangtao.support.kit.realm.MessageRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        tech.jiangtao.support.kit.realm.MessageRealm unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (tech.jiangtao.support.kit.realm.MessageRealm)cachedObject.object;
            } else {
                unmanagedObject = (tech.jiangtao.support.kit.realm.MessageRealm)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new tech.jiangtao.support.kit.realm.MessageRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$id(((MessageRealmRealmProxyInterface) realmObject).realmGet$id());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$mainJID(((MessageRealmRealmProxyInterface) realmObject).realmGet$mainJID());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$withJID(((MessageRealmRealmProxyInterface) realmObject).realmGet$withJID());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$textMessage(((MessageRealmRealmProxyInterface) realmObject).realmGet$textMessage());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$time(((MessageRealmRealmProxyInterface) realmObject).realmGet$time());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$thread(((MessageRealmRealmProxyInterface) realmObject).realmGet$thread());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$type(((MessageRealmRealmProxyInterface) realmObject).realmGet$type());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$messageType(((MessageRealmRealmProxyInterface) realmObject).realmGet$messageType());
        ((MessageRealmRealmProxyInterface) unmanagedObject).realmSet$messageStatus(((MessageRealmRealmProxyInterface) realmObject).realmGet$messageStatus());
        return unmanagedObject;
    }

    static tech.jiangtao.support.kit.realm.MessageRealm update(Realm realm, tech.jiangtao.support.kit.realm.MessageRealm realmObject, tech.jiangtao.support.kit.realm.MessageRealm newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$mainJID(((MessageRealmRealmProxyInterface) newObject).realmGet$mainJID());
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$withJID(((MessageRealmRealmProxyInterface) newObject).realmGet$withJID());
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$textMessage(((MessageRealmRealmProxyInterface) newObject).realmGet$textMessage());
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$time(((MessageRealmRealmProxyInterface) newObject).realmGet$time());
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$thread(((MessageRealmRealmProxyInterface) newObject).realmGet$thread());
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$type(((MessageRealmRealmProxyInterface) newObject).realmGet$type());
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$messageType(((MessageRealmRealmProxyInterface) newObject).realmGet$messageType());
        ((MessageRealmRealmProxyInterface) realmObject).realmSet$messageStatus(((MessageRealmRealmProxyInterface) newObject).realmGet$messageStatus());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("MessageRealm = [");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id() != null ? realmGet$id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{mainJID:");
        stringBuilder.append(realmGet$mainJID() != null ? realmGet$mainJID() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{withJID:");
        stringBuilder.append(realmGet$withJID() != null ? realmGet$withJID() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{textMessage:");
        stringBuilder.append(realmGet$textMessage() != null ? realmGet$textMessage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        stringBuilder.append(realmGet$time() != null ? realmGet$time() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{thread:");
        stringBuilder.append(realmGet$thread() != null ? realmGet$thread() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type() != null ? realmGet$type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{messageType:");
        stringBuilder.append(realmGet$messageType() != null ? realmGet$messageType() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{messageStatus:");
        stringBuilder.append(realmGet$messageStatus());
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
        MessageRealmRealmProxy aMessageRealm = (MessageRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aMessageRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aMessageRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aMessageRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
