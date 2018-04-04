/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_scanlibrary_ScanActivity */

#ifndef _Included_com_scanlibrary_ScanActivity
#define _Included_com_scanlibrary_ScanActivity
#ifdef __cplusplus
extern "C" {
#endif
#undef com_scanlibrary_ScanActivity_BIND_ABOVE_CLIENT
#define com_scanlibrary_ScanActivity_BIND_ABOVE_CLIENT 8L
#undef com_scanlibrary_ScanActivity_BIND_ADJUST_WITH_ACTIVITY
#define com_scanlibrary_ScanActivity_BIND_ADJUST_WITH_ACTIVITY 128L
#undef com_scanlibrary_ScanActivity_BIND_ALLOW_OOM_MANAGEMENT
#define com_scanlibrary_ScanActivity_BIND_ALLOW_OOM_MANAGEMENT 16L
#undef com_scanlibrary_ScanActivity_BIND_AUTO_CREATE
#define com_scanlibrary_ScanActivity_BIND_AUTO_CREATE 1L
#undef com_scanlibrary_ScanActivity_BIND_DEBUG_UNBIND
#define com_scanlibrary_ScanActivity_BIND_DEBUG_UNBIND 2L
#undef com_scanlibrary_ScanActivity_BIND_IMPORTANT
#define com_scanlibrary_ScanActivity_BIND_IMPORTANT 64L
#undef com_scanlibrary_ScanActivity_BIND_NOT_FOREGROUND
#define com_scanlibrary_ScanActivity_BIND_NOT_FOREGROUND 4L
#undef com_scanlibrary_ScanActivity_BIND_WAIVE_PRIORITY
#define com_scanlibrary_ScanActivity_BIND_WAIVE_PRIORITY 32L
#undef com_scanlibrary_ScanActivity_CONTEXT_IGNORE_SECURITY
#define com_scanlibrary_ScanActivity_CONTEXT_IGNORE_SECURITY 2L
#undef com_scanlibrary_ScanActivity_CONTEXT_INCLUDE_CODE
#define com_scanlibrary_ScanActivity_CONTEXT_INCLUDE_CODE 1L
#undef com_scanlibrary_ScanActivity_CONTEXT_RESTRICTED
#define com_scanlibrary_ScanActivity_CONTEXT_RESTRICTED 4L
#undef com_scanlibrary_ScanActivity_MODE_APPEND
#define com_scanlibrary_ScanActivity_MODE_APPEND 32768L
#undef com_scanlibrary_ScanActivity_MODE_ENABLE_WRITE_AHEAD_LOGGING
#define com_scanlibrary_ScanActivity_MODE_ENABLE_WRITE_AHEAD_LOGGING 8L
#undef com_scanlibrary_ScanActivity_MODE_MULTI_PROCESS
#define com_scanlibrary_ScanActivity_MODE_MULTI_PROCESS 4L
#undef com_scanlibrary_ScanActivity_MODE_PRIVATE
#define com_scanlibrary_ScanActivity_MODE_PRIVATE 0L
#undef com_scanlibrary_ScanActivity_MODE_WORLD_READABLE
#define com_scanlibrary_ScanActivity_MODE_WORLD_READABLE 1L
#undef com_scanlibrary_ScanActivity_MODE_WORLD_WRITEABLE
#define com_scanlibrary_ScanActivity_MODE_WORLD_WRITEABLE 2L
#undef com_scanlibrary_ScanActivity_DEFAULT_KEYS_DIALER
#define com_scanlibrary_ScanActivity_DEFAULT_KEYS_DIALER 1L
#undef com_scanlibrary_ScanActivity_DEFAULT_KEYS_DISABLE
#define com_scanlibrary_ScanActivity_DEFAULT_KEYS_DISABLE 0L
#undef com_scanlibrary_ScanActivity_DEFAULT_KEYS_SEARCH_GLOBAL
#define com_scanlibrary_ScanActivity_DEFAULT_KEYS_SEARCH_GLOBAL 4L
#undef com_scanlibrary_ScanActivity_DEFAULT_KEYS_SEARCH_LOCAL
#define com_scanlibrary_ScanActivity_DEFAULT_KEYS_SEARCH_LOCAL 3L
#undef com_scanlibrary_ScanActivity_DEFAULT_KEYS_SHORTCUT
#define com_scanlibrary_ScanActivity_DEFAULT_KEYS_SHORTCUT 2L
#undef com_scanlibrary_ScanActivity_RESULT_CANCELED
#define com_scanlibrary_ScanActivity_RESULT_CANCELED 0L
#undef com_scanlibrary_ScanActivity_RESULT_FIRST_USER
#define com_scanlibrary_ScanActivity_RESULT_FIRST_USER 1L
#undef com_scanlibrary_ScanActivity_RESULT_OK
#define com_scanlibrary_ScanActivity_RESULT_OK -1L
/*
 * Class:     com_scanlibrary_ScanActivity
 * Method:    getScannedBitmap
 * Signature: (IILandroid/graphics/Bitmap;FFFFFFFF)Landroid/graphics/Bitmap;
 */
JNIEXPORT jobject JNICALL Java_com_scanlibrary_ScanActivity_getScannedBitmap
  (JNIEnv *, jobject, jobject, jfloat, jfloat, jfloat, jfloat, jfloat, jfloat, jfloat, jfloat);


JNIEXPORT jfloatArray JNICALL Java_com_scanlibrary_ScanActivity_getPoints
(JNIEnv *, jobject, jobject);

JNIEXPORT jobject JNICALL Java_com_scanlibrary_ScanActivity_getBWBitmap
(JNIEnv *, jobject, jobject);

JNIEXPORT jobject JNICALL Java_com_scanlibrary_ScanActivity_getMagicColorBitmap
(JNIEnv *, jobject, jobject);

JNIEXPORT jobject JNICALL Java_com_scanlibrary_ScanActivity_getGrayBitmap
(JNIEnv *, jobject, jobject);

JNIEXPORT jobject JNICALL Java_com_scanlibrary_ScanActivity_getBitmap
(JNIEnv *, jobject, jobject);


#ifdef __cplusplus
}
#endif
#endif
