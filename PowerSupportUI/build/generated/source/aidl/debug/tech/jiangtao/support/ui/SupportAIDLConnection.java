/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportUI/src/main/aidl/tech/jiangtao/support/ui/SupportAIDLConnection.aidl
 */
package tech.jiangtao.support.ui;
public interface SupportAIDLConnection extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements tech.jiangtao.support.ui.SupportAIDLConnection
{
private static final java.lang.String DESCRIPTOR = "tech.jiangtao.support.ui.SupportAIDLConnection";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an tech.jiangtao.support.ui.SupportAIDLConnection interface,
 * generating a proxy if needed.
 */
public static tech.jiangtao.support.ui.SupportAIDLConnection asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof tech.jiangtao.support.ui.SupportAIDLConnection))) {
return ((tech.jiangtao.support.ui.SupportAIDLConnection)iin);
}
return new tech.jiangtao.support.ui.SupportAIDLConnection.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getServiceName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getServiceName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements tech.jiangtao.support.ui.SupportAIDLConnection
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String getServiceName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getServiceName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.lang.String getServiceName() throws android.os.RemoteException;
}
