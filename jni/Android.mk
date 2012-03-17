LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := decode
LOCAL_SRC_FILES := decode.c 
LOCAL_LDLIBS := -llog -lm -DNODEPS -O4


include $(BUILD_SHARED_LIBRARY)