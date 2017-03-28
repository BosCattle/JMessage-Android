LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := PowerSupportSip
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libbctoolbox-armeabi.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libbctoolbox-tester-armeabi.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libgnustl_shared.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/liblinphone-armeabi.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/liblinphonetester-armeabi.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libmediastreamer_base-armeabi.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libmediastreamer_voip-armeabi.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libmssilk.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libmswebrtc.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi/libortp-armeabi.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libbctoolbox-armeabi-v7a.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libbctoolbox-tester-armeabi-v7a.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libgnustl_shared.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/liblinphone-armeabi-v7a.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/liblinphonetester-armeabi-v7a.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libmediastreamer_base-armeabi-v7a.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libmediastreamer_voip-armeabi-v7a.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libmssilk.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libmswebrtc.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/armeabi-v7a/libortp-armeabi-v7a.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libbctoolbox-tester-x86.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libbctoolbox-x86.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libgnustl_shared.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/liblinphone-x86.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/liblinphonetester-x86.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libmediastreamer_base-x86.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libmediastreamer_voip-x86.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libmssilk.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libmswebrtc.so \
	/Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni/x86/libortp-x86.so \

LOCAL_C_INCLUDES += /Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/main/jni
LOCAL_C_INCLUDES += /Users/Vurtex/AndroidStudioProjects/JMessage/PowerSupportSip/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
