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

public class VCardRealmRealmProxy extends tech.jiangtao.support.kit.realm.VCardRealm
    implements RealmObjectProxy, VCardRealmRealmProxyInterface {

    static final class VCardRealmColumnInfo extends ColumnInfo
        implements Cloneable {

        public long jidIndex;
        public long nickNameIndex;
        public long sexIndex;
        public long subjectIndex;
        public long officeIndex;
        public long emailIndex;
        public long phoneNumberIndex;
        public long signatureIndex;
        public long avatarIndex;
        public long firstLetterIndex;
        public long allPinYinIndex;
        public long friendIndex;

        VCardRealmColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(12);
            this.jidIndex = getValidColumnIndex(path, table, "VCardRealm", "jid");
            indicesMap.put("jid", this.jidIndex);
            this.nickNameIndex = getValidColumnIndex(path, table, "VCardRealm", "nickName");
            indicesMap.put("nickName", this.nickNameIndex);
            this.sexIndex = getValidColumnIndex(path, table, "VCardRealm", "sex");
            indicesMap.put("sex", this.sexIndex);
            this.subjectIndex = getValidColumnIndex(path, table, "VCardRealm", "subject");
            indicesMap.put("subject", this.subjectIndex);
            this.officeIndex = getValidColumnIndex(path, table, "VCardRealm", "office");
            indicesMap.put("office", this.officeIndex);
            this.emailIndex = getValidColumnIndex(path, table, "VCardRealm", "email");
            indicesMap.put("email", this.emailIndex);
            this.phoneNumberIndex = getValidColumnIndex(path, table, "VCardRealm", "phoneNumber");
            indicesMap.put("phoneNumber", this.phoneNumberIndex);
            this.signatureIndex = getValidColumnIndex(path, table, "VCardRealm", "signature");
            indicesMap.put("signature", this.signatureIndex);
            this.avatarIndex = getValidColumnIndex(path, table, "VCardRealm", "avatar");
            indicesMap.put("avatar", this.avatarIndex);
            this.firstLetterIndex = getValidColumnIndex(path, table, "VCardRealm", "firstLetter");
            indicesMap.put("firstLetter", this.firstLetterIndex);
            this.allPinYinIndex = getValidColumnIndex(path, table, "VCardRealm", "allPinYin");
            indicesMap.put("allPinYin", this.allPinYinIndex);
            this.friendIndex = getValidColumnIndex(path, table, "VCardRealm", "friend");
            indicesMap.put("friend", this.friendIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final VCardRealmColumnInfo otherInfo = (VCardRealmColumnInfo) other;
            this.jidIndex = otherInfo.jidIndex;
            this.nickNameIndex = otherInfo.nickNameIndex;
            this.sexIndex = otherInfo.sexIndex;
            this.subjectIndex = otherInfo.subjectIndex;
            this.officeIndex = otherInfo.officeIndex;
            this.emailIndex = otherInfo.emailIndex;
            this.phoneNumberIndex = otherInfo.phoneNumberIndex;
            this.signatureIndex = otherInfo.signatureIndex;
            this.avatarIndex = otherInfo.avatarIndex;
            this.firstLetterIndex = otherInfo.firstLetterIndex;
            this.allPinYinIndex = otherInfo.allPinYinIndex;
            this.friendIndex = otherInfo.friendIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final VCardRealmColumnInfo clone() {
            return (VCardRealmColumnInfo) super.clone();
        }

    }
    private VCardRealmColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("jid");
        fieldNames.add("nickName");
        fieldNames.add("sex");
        fieldNames.add("subject");
        fieldNames.add("office");
        fieldNames.add("email");
        fieldNames.add("phoneNumber");
        fieldNames.add("signature");
        fieldNames.add("avatar");
        fieldNames.add("firstLetter");
        fieldNames.add("allPinYin");
        fieldNames.add("friend");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    VCardRealmRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (VCardRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(tech.jiangtao.support.kit.realm.VCardRealm.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$jid() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.jidIndex);
    }

    public void realmSet$jid(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'jid' cannot be changed after object was created.");
    }

    @SuppressWarnings("cast")
    public String realmGet$nickName() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nickNameIndex);
    }

    public void realmSet$nickName(String value) {
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
                row.getTable().setNull(columnInfo.nickNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nickNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nickNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nickNameIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$sex() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sexIndex);
    }

    public void realmSet$sex(String value) {
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
                row.getTable().setNull(columnInfo.sexIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.sexIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sexIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sexIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$subject() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.subjectIndex);
    }

    public void realmSet$subject(String value) {
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
                row.getTable().setNull(columnInfo.subjectIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.subjectIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.subjectIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.subjectIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$office() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.officeIndex);
    }

    public void realmSet$office(String value) {
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
                row.getTable().setNull(columnInfo.officeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.officeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.officeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.officeIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$email() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailIndex);
    }

    public void realmSet$email(String value) {
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
                row.getTable().setNull(columnInfo.emailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$phoneNumber() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phoneNumberIndex);
    }

    public void realmSet$phoneNumber(String value) {
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
                row.getTable().setNull(columnInfo.phoneNumberIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.phoneNumberIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phoneNumberIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phoneNumberIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$signature() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.signatureIndex);
    }

    public void realmSet$signature(String value) {
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
                row.getTable().setNull(columnInfo.signatureIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.signatureIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.signatureIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.signatureIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$avatar() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.avatarIndex);
    }

    public void realmSet$avatar(String value) {
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
                row.getTable().setNull(columnInfo.avatarIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.avatarIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.avatarIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.avatarIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$firstLetter() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.firstLetterIndex);
    }

    public void realmSet$firstLetter(String value) {
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
                row.getTable().setNull(columnInfo.firstLetterIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.firstLetterIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.firstLetterIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.firstLetterIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$allPinYin() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.allPinYinIndex);
    }

    public void realmSet$allPinYin(String value) {
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
                row.getTable().setNull(columnInfo.allPinYinIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.allPinYinIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.allPinYinIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.allPinYinIndex, value);
    }

    @SuppressWarnings("cast")
    public boolean realmGet$friend() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.friendIndex);
    }

    public void realmSet$friend(boolean value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.friendIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.friendIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("VCardRealm")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("VCardRealm");
            realmObjectSchema.add(new Property("jid", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("nickName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("sex", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("subject", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("office", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("phoneNumber", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("signature", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("avatar", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("firstLetter", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("allPinYin", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("friend", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("VCardRealm");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_VCardRealm")) {
            Table table = sharedRealm.getTable("class_VCardRealm");
            table.addColumn(RealmFieldType.STRING, "jid", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "nickName", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "sex", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "subject", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "office", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "email", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "phoneNumber", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "signature", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "avatar", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "firstLetter", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "allPinYin", Table.NULLABLE);
            table.addColumn(RealmFieldType.BOOLEAN, "friend", Table.NOT_NULLABLE);
            table.addSearchIndex(table.getColumnIndex("jid"));
            table.setPrimaryKey("jid");
            return table;
        }
        return sharedRealm.getTable("class_VCardRealm");
    }

    public static VCardRealmColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_VCardRealm")) {
            Table table = sharedRealm.getTable("class_VCardRealm");
            final long columnCount = table.getColumnCount();
            if (columnCount != 12) {
                if (columnCount < 12) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 12 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 12 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 12 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final VCardRealmColumnInfo columnInfo = new VCardRealmColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("jid")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'jid' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("jid") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'jid' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.jidIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(),"@PrimaryKey field 'jid' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("jid")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'jid' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("jid"))) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'jid' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("nickName")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'nickName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("nickName") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'nickName' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.nickNameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'nickName' is required. Either set @Required to field 'nickName' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("sex")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'sex' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("sex") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'sex' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.sexIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'sex' is required. Either set @Required to field 'sex' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("subject")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'subject' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("subject") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'subject' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.subjectIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'subject' is required. Either set @Required to field 'subject' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("office")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'office' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("office") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'office' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.officeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'office' is required. Either set @Required to field 'office' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("email")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'email' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("email") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'email' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.emailIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'email' is required. Either set @Required to field 'email' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("phoneNumber")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'phoneNumber' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("phoneNumber") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'phoneNumber' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.phoneNumberIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'phoneNumber' is required. Either set @Required to field 'phoneNumber' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("signature")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'signature' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("signature") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'signature' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.signatureIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'signature' is required. Either set @Required to field 'signature' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("avatar")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'avatar' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("avatar") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'avatar' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.avatarIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'avatar' is required. Either set @Required to field 'avatar' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("firstLetter")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'firstLetter' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("firstLetter") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'firstLetter' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.firstLetterIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'firstLetter' is required. Either set @Required to field 'firstLetter' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("allPinYin")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'allPinYin' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("allPinYin") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'allPinYin' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.allPinYinIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'allPinYin' is required. Either set @Required to field 'allPinYin' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("friend")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'friend' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("friend") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'friend' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.friendIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'friend' does support null values in the existing Realm file. Use corresponding boxed type for field 'friend' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'VCardRealm' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_VCardRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static tech.jiangtao.support.kit.realm.VCardRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        tech.jiangtao.support.kit.realm.VCardRealm obj = null;
        if (update) {
            Table table = realm.getTable(tech.jiangtao.support.kit.realm.VCardRealm.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = TableOrView.NO_MATCH;
            if (json.isNull("jid")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("jid"));
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.VCardRealm.class), false, Collections.<String> emptyList());
                    obj = new io.realm.VCardRealmRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("jid")) {
                if (json.isNull("jid")) {
                    obj = (io.realm.VCardRealmRealmProxy) realm.createObjectInternal(tech.jiangtao.support.kit.realm.VCardRealm.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.VCardRealmRealmProxy) realm.createObjectInternal(tech.jiangtao.support.kit.realm.VCardRealm.class, json.getString("jid"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'jid'.");
            }
        }
        if (json.has("nickName")) {
            if (json.isNull("nickName")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$nickName(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$nickName((String) json.getString("nickName"));
            }
        }
        if (json.has("sex")) {
            if (json.isNull("sex")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$sex(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$sex((String) json.getString("sex"));
            }
        }
        if (json.has("subject")) {
            if (json.isNull("subject")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$subject(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$subject((String) json.getString("subject"));
            }
        }
        if (json.has("office")) {
            if (json.isNull("office")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$office(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$office((String) json.getString("office"));
            }
        }
        if (json.has("email")) {
            if (json.isNull("email")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$email(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("phoneNumber")) {
            if (json.isNull("phoneNumber")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$phoneNumber(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$phoneNumber((String) json.getString("phoneNumber"));
            }
        }
        if (json.has("signature")) {
            if (json.isNull("signature")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$signature(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$signature((String) json.getString("signature"));
            }
        }
        if (json.has("avatar")) {
            if (json.isNull("avatar")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$avatar(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$avatar((String) json.getString("avatar"));
            }
        }
        if (json.has("firstLetter")) {
            if (json.isNull("firstLetter")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$firstLetter(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$firstLetter((String) json.getString("firstLetter"));
            }
        }
        if (json.has("allPinYin")) {
            if (json.isNull("allPinYin")) {
                ((VCardRealmRealmProxyInterface) obj).realmSet$allPinYin(null);
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$allPinYin((String) json.getString("allPinYin"));
            }
        }
        if (json.has("friend")) {
            if (json.isNull("friend")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'friend' to null.");
            } else {
                ((VCardRealmRealmProxyInterface) obj).realmSet$friend((boolean) json.getBoolean("friend"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static tech.jiangtao.support.kit.realm.VCardRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        tech.jiangtao.support.kit.realm.VCardRealm obj = new tech.jiangtao.support.kit.realm.VCardRealm();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("jid")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$jid(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$jid((String) reader.nextString());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("nickName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$nickName(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$nickName((String) reader.nextString());
                }
            } else if (name.equals("sex")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$sex(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$sex((String) reader.nextString());
                }
            } else if (name.equals("subject")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$subject(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$subject((String) reader.nextString());
                }
            } else if (name.equals("office")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$office(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$office((String) reader.nextString());
                }
            } else if (name.equals("email")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$email(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$email((String) reader.nextString());
                }
            } else if (name.equals("phoneNumber")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$phoneNumber(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$phoneNumber((String) reader.nextString());
                }
            } else if (name.equals("signature")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$signature(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$signature((String) reader.nextString());
                }
            } else if (name.equals("avatar")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$avatar(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$avatar((String) reader.nextString());
                }
            } else if (name.equals("firstLetter")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$firstLetter(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$firstLetter((String) reader.nextString());
                }
            } else if (name.equals("allPinYin")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((VCardRealmRealmProxyInterface) obj).realmSet$allPinYin(null);
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$allPinYin((String) reader.nextString());
                }
            } else if (name.equals("friend")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'friend' to null.");
                } else {
                    ((VCardRealmRealmProxyInterface) obj).realmSet$friend((boolean) reader.nextBoolean());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'jid'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static tech.jiangtao.support.kit.realm.VCardRealm copyOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.VCardRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.VCardRealm) cachedRealmObject;
        } else {
            tech.jiangtao.support.kit.realm.VCardRealm realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(tech.jiangtao.support.kit.realm.VCardRealm.class);
                long pkColumnIndex = table.getPrimaryKey();
                String value = ((VCardRealmRealmProxyInterface) object).realmGet$jid();
                long rowIndex = TableOrView.NO_MATCH;
                if (value == null) {
                    rowIndex = table.findFirstNull(pkColumnIndex);
                } else {
                    rowIndex = table.findFirstString(pkColumnIndex, value);
                }
                if (rowIndex != TableOrView.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.VCardRealm.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.VCardRealmRealmProxy();
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

    public static tech.jiangtao.support.kit.realm.VCardRealm copy(Realm realm, tech.jiangtao.support.kit.realm.VCardRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (tech.jiangtao.support.kit.realm.VCardRealm) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            tech.jiangtao.support.kit.realm.VCardRealm realmObject = realm.createObjectInternal(tech.jiangtao.support.kit.realm.VCardRealm.class, ((VCardRealmRealmProxyInterface) newObject).realmGet$jid(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$nickName(((VCardRealmRealmProxyInterface) newObject).realmGet$nickName());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$sex(((VCardRealmRealmProxyInterface) newObject).realmGet$sex());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$subject(((VCardRealmRealmProxyInterface) newObject).realmGet$subject());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$office(((VCardRealmRealmProxyInterface) newObject).realmGet$office());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$email(((VCardRealmRealmProxyInterface) newObject).realmGet$email());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$phoneNumber(((VCardRealmRealmProxyInterface) newObject).realmGet$phoneNumber());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$signature(((VCardRealmRealmProxyInterface) newObject).realmGet$signature());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$avatar(((VCardRealmRealmProxyInterface) newObject).realmGet$avatar());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$firstLetter(((VCardRealmRealmProxyInterface) newObject).realmGet$firstLetter());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$allPinYin(((VCardRealmRealmProxyInterface) newObject).realmGet$allPinYin());
            ((VCardRealmRealmProxyInterface) realmObject).realmSet$friend(((VCardRealmRealmProxyInterface) newObject).realmGet$friend());
            return realmObject;
        }
    }

    public static long insert(Realm realm, tech.jiangtao.support.kit.realm.VCardRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        VCardRealmColumnInfo columnInfo = (VCardRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((VCardRealmRealmProxyInterface) object).realmGet$jid();
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
        String realmGet$nickName = ((VCardRealmRealmProxyInterface)object).realmGet$nickName();
        if (realmGet$nickName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
        }
        String realmGet$sex = ((VCardRealmRealmProxyInterface)object).realmGet$sex();
        if (realmGet$sex != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sexIndex, rowIndex, realmGet$sex, false);
        }
        String realmGet$subject = ((VCardRealmRealmProxyInterface)object).realmGet$subject();
        if (realmGet$subject != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subjectIndex, rowIndex, realmGet$subject, false);
        }
        String realmGet$office = ((VCardRealmRealmProxyInterface)object).realmGet$office();
        if (realmGet$office != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.officeIndex, rowIndex, realmGet$office, false);
        }
        String realmGet$email = ((VCardRealmRealmProxyInterface)object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        }
        String realmGet$phoneNumber = ((VCardRealmRealmProxyInterface)object).realmGet$phoneNumber();
        if (realmGet$phoneNumber != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberIndex, rowIndex, realmGet$phoneNumber, false);
        }
        String realmGet$signature = ((VCardRealmRealmProxyInterface)object).realmGet$signature();
        if (realmGet$signature != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.signatureIndex, rowIndex, realmGet$signature, false);
        }
        String realmGet$avatar = ((VCardRealmRealmProxyInterface)object).realmGet$avatar();
        if (realmGet$avatar != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.avatarIndex, rowIndex, realmGet$avatar, false);
        }
        String realmGet$firstLetter = ((VCardRealmRealmProxyInterface)object).realmGet$firstLetter();
        if (realmGet$firstLetter != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.firstLetterIndex, rowIndex, realmGet$firstLetter, false);
        }
        String realmGet$allPinYin = ((VCardRealmRealmProxyInterface)object).realmGet$allPinYin();
        if (realmGet$allPinYin != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.allPinYinIndex, rowIndex, realmGet$allPinYin, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.friendIndex, rowIndex, ((VCardRealmRealmProxyInterface)object).realmGet$friend(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        VCardRealmColumnInfo columnInfo = (VCardRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        tech.jiangtao.support.kit.realm.VCardRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.VCardRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((VCardRealmRealmProxyInterface) object).realmGet$jid();
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
                String realmGet$nickName = ((VCardRealmRealmProxyInterface)object).realmGet$nickName();
                if (realmGet$nickName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
                }
                String realmGet$sex = ((VCardRealmRealmProxyInterface)object).realmGet$sex();
                if (realmGet$sex != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sexIndex, rowIndex, realmGet$sex, false);
                }
                String realmGet$subject = ((VCardRealmRealmProxyInterface)object).realmGet$subject();
                if (realmGet$subject != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.subjectIndex, rowIndex, realmGet$subject, false);
                }
                String realmGet$office = ((VCardRealmRealmProxyInterface)object).realmGet$office();
                if (realmGet$office != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.officeIndex, rowIndex, realmGet$office, false);
                }
                String realmGet$email = ((VCardRealmRealmProxyInterface)object).realmGet$email();
                if (realmGet$email != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
                }
                String realmGet$phoneNumber = ((VCardRealmRealmProxyInterface)object).realmGet$phoneNumber();
                if (realmGet$phoneNumber != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberIndex, rowIndex, realmGet$phoneNumber, false);
                }
                String realmGet$signature = ((VCardRealmRealmProxyInterface)object).realmGet$signature();
                if (realmGet$signature != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.signatureIndex, rowIndex, realmGet$signature, false);
                }
                String realmGet$avatar = ((VCardRealmRealmProxyInterface)object).realmGet$avatar();
                if (realmGet$avatar != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.avatarIndex, rowIndex, realmGet$avatar, false);
                }
                String realmGet$firstLetter = ((VCardRealmRealmProxyInterface)object).realmGet$firstLetter();
                if (realmGet$firstLetter != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.firstLetterIndex, rowIndex, realmGet$firstLetter, false);
                }
                String realmGet$allPinYin = ((VCardRealmRealmProxyInterface)object).realmGet$allPinYin();
                if (realmGet$allPinYin != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.allPinYinIndex, rowIndex, realmGet$allPinYin, false);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.friendIndex, rowIndex, ((VCardRealmRealmProxyInterface)object).realmGet$friend(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, tech.jiangtao.support.kit.realm.VCardRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        VCardRealmColumnInfo columnInfo = (VCardRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((VCardRealmRealmProxyInterface) object).realmGet$jid();
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
        String realmGet$nickName = ((VCardRealmRealmProxyInterface)object).realmGet$nickName();
        if (realmGet$nickName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nickNameIndex, rowIndex, false);
        }
        String realmGet$sex = ((VCardRealmRealmProxyInterface)object).realmGet$sex();
        if (realmGet$sex != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sexIndex, rowIndex, realmGet$sex, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sexIndex, rowIndex, false);
        }
        String realmGet$subject = ((VCardRealmRealmProxyInterface)object).realmGet$subject();
        if (realmGet$subject != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subjectIndex, rowIndex, realmGet$subject, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.subjectIndex, rowIndex, false);
        }
        String realmGet$office = ((VCardRealmRealmProxyInterface)object).realmGet$office();
        if (realmGet$office != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.officeIndex, rowIndex, realmGet$office, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.officeIndex, rowIndex, false);
        }
        String realmGet$email = ((VCardRealmRealmProxyInterface)object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
        }
        String realmGet$phoneNumber = ((VCardRealmRealmProxyInterface)object).realmGet$phoneNumber();
        if (realmGet$phoneNumber != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberIndex, rowIndex, realmGet$phoneNumber, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phoneNumberIndex, rowIndex, false);
        }
        String realmGet$signature = ((VCardRealmRealmProxyInterface)object).realmGet$signature();
        if (realmGet$signature != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.signatureIndex, rowIndex, realmGet$signature, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.signatureIndex, rowIndex, false);
        }
        String realmGet$avatar = ((VCardRealmRealmProxyInterface)object).realmGet$avatar();
        if (realmGet$avatar != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.avatarIndex, rowIndex, realmGet$avatar, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.avatarIndex, rowIndex, false);
        }
        String realmGet$firstLetter = ((VCardRealmRealmProxyInterface)object).realmGet$firstLetter();
        if (realmGet$firstLetter != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.firstLetterIndex, rowIndex, realmGet$firstLetter, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.firstLetterIndex, rowIndex, false);
        }
        String realmGet$allPinYin = ((VCardRealmRealmProxyInterface)object).realmGet$allPinYin();
        if (realmGet$allPinYin != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.allPinYinIndex, rowIndex, realmGet$allPinYin, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.allPinYinIndex, rowIndex, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.friendIndex, rowIndex, ((VCardRealmRealmProxyInterface)object).realmGet$friend(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        VCardRealmColumnInfo columnInfo = (VCardRealmColumnInfo) realm.schema.getColumnInfo(tech.jiangtao.support.kit.realm.VCardRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        tech.jiangtao.support.kit.realm.VCardRealm object = null;
        while (objects.hasNext()) {
            object = (tech.jiangtao.support.kit.realm.VCardRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                String primaryKeyValue = ((VCardRealmRealmProxyInterface) object).realmGet$jid();
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
                String realmGet$nickName = ((VCardRealmRealmProxyInterface)object).realmGet$nickName();
                if (realmGet$nickName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.nickNameIndex, rowIndex, false);
                }
                String realmGet$sex = ((VCardRealmRealmProxyInterface)object).realmGet$sex();
                if (realmGet$sex != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sexIndex, rowIndex, realmGet$sex, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.sexIndex, rowIndex, false);
                }
                String realmGet$subject = ((VCardRealmRealmProxyInterface)object).realmGet$subject();
                if (realmGet$subject != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.subjectIndex, rowIndex, realmGet$subject, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.subjectIndex, rowIndex, false);
                }
                String realmGet$office = ((VCardRealmRealmProxyInterface)object).realmGet$office();
                if (realmGet$office != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.officeIndex, rowIndex, realmGet$office, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.officeIndex, rowIndex, false);
                }
                String realmGet$email = ((VCardRealmRealmProxyInterface)object).realmGet$email();
                if (realmGet$email != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
                }
                String realmGet$phoneNumber = ((VCardRealmRealmProxyInterface)object).realmGet$phoneNumber();
                if (realmGet$phoneNumber != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberIndex, rowIndex, realmGet$phoneNumber, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.phoneNumberIndex, rowIndex, false);
                }
                String realmGet$signature = ((VCardRealmRealmProxyInterface)object).realmGet$signature();
                if (realmGet$signature != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.signatureIndex, rowIndex, realmGet$signature, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.signatureIndex, rowIndex, false);
                }
                String realmGet$avatar = ((VCardRealmRealmProxyInterface)object).realmGet$avatar();
                if (realmGet$avatar != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.avatarIndex, rowIndex, realmGet$avatar, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.avatarIndex, rowIndex, false);
                }
                String realmGet$firstLetter = ((VCardRealmRealmProxyInterface)object).realmGet$firstLetter();
                if (realmGet$firstLetter != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.firstLetterIndex, rowIndex, realmGet$firstLetter, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.firstLetterIndex, rowIndex, false);
                }
                String realmGet$allPinYin = ((VCardRealmRealmProxyInterface)object).realmGet$allPinYin();
                if (realmGet$allPinYin != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.allPinYinIndex, rowIndex, realmGet$allPinYin, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.allPinYinIndex, rowIndex, false);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.friendIndex, rowIndex, ((VCardRealmRealmProxyInterface)object).realmGet$friend(), false);
            }
        }
    }

    public static tech.jiangtao.support.kit.realm.VCardRealm createDetachedCopy(tech.jiangtao.support.kit.realm.VCardRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        tech.jiangtao.support.kit.realm.VCardRealm unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (tech.jiangtao.support.kit.realm.VCardRealm)cachedObject.object;
            } else {
                unmanagedObject = (tech.jiangtao.support.kit.realm.VCardRealm)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new tech.jiangtao.support.kit.realm.VCardRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$jid(((VCardRealmRealmProxyInterface) realmObject).realmGet$jid());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$nickName(((VCardRealmRealmProxyInterface) realmObject).realmGet$nickName());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$sex(((VCardRealmRealmProxyInterface) realmObject).realmGet$sex());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$subject(((VCardRealmRealmProxyInterface) realmObject).realmGet$subject());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$office(((VCardRealmRealmProxyInterface) realmObject).realmGet$office());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$email(((VCardRealmRealmProxyInterface) realmObject).realmGet$email());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$phoneNumber(((VCardRealmRealmProxyInterface) realmObject).realmGet$phoneNumber());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$signature(((VCardRealmRealmProxyInterface) realmObject).realmGet$signature());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$avatar(((VCardRealmRealmProxyInterface) realmObject).realmGet$avatar());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$firstLetter(((VCardRealmRealmProxyInterface) realmObject).realmGet$firstLetter());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$allPinYin(((VCardRealmRealmProxyInterface) realmObject).realmGet$allPinYin());
        ((VCardRealmRealmProxyInterface) unmanagedObject).realmSet$friend(((VCardRealmRealmProxyInterface) realmObject).realmGet$friend());
        return unmanagedObject;
    }

    static tech.jiangtao.support.kit.realm.VCardRealm update(Realm realm, tech.jiangtao.support.kit.realm.VCardRealm realmObject, tech.jiangtao.support.kit.realm.VCardRealm newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$nickName(((VCardRealmRealmProxyInterface) newObject).realmGet$nickName());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$sex(((VCardRealmRealmProxyInterface) newObject).realmGet$sex());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$subject(((VCardRealmRealmProxyInterface) newObject).realmGet$subject());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$office(((VCardRealmRealmProxyInterface) newObject).realmGet$office());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$email(((VCardRealmRealmProxyInterface) newObject).realmGet$email());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$phoneNumber(((VCardRealmRealmProxyInterface) newObject).realmGet$phoneNumber());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$signature(((VCardRealmRealmProxyInterface) newObject).realmGet$signature());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$avatar(((VCardRealmRealmProxyInterface) newObject).realmGet$avatar());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$firstLetter(((VCardRealmRealmProxyInterface) newObject).realmGet$firstLetter());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$allPinYin(((VCardRealmRealmProxyInterface) newObject).realmGet$allPinYin());
        ((VCardRealmRealmProxyInterface) realmObject).realmSet$friend(((VCardRealmRealmProxyInterface) newObject).realmGet$friend());
        return realmObject;
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
        VCardRealmRealmProxy aVCardRealm = (VCardRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aVCardRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aVCardRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aVCardRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
