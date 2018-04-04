LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
OPENCV_INSTALL_MODULES:=on
include /root/AndroidStudioProjects/AndroidScannerDemo-master/ScanDemoExample/scanlibrary/src/main/jni/sdk/native/jni/OpenCV.mk

LOCAL_MODULE    := Scanner
LOCAL_SRC_FILES := scan.cpp
LOCAL_LDLIBS    += -lm -llog -landroid
LOCAL_LDFLAGS += -ljnigraphics
include $(BUILD_SHARED_LIBRARY)