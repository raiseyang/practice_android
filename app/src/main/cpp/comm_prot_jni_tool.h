#ifndef __COMM_PROT_JNI_TOOL_H__
#define __COMM_PROT_JNI_TOOL_H__

#ifdef __cplusplus
extern "C" {
#endif//__cplusplus
/*<----------body---------->*/
#include "comm_prot_jni_def.h"

/**
  * flags of USE_STRDUP
  */
#define USE_STRDUP

extern char *jstring2chars(JNIEnv *env, jstring jstr);

extern jstring chars2jsrting(JNIEnv *env, const char *str);

extern jobject
getJobjectField(JNIEnv *env, jobject jobj, const char *fid_name, const char *fid_class_name);

extern jstring getJstringField(JNIEnv *env, jobject jobj, const char *fid_name);

extern jint getJintField(JNIEnv *env, jobject jobj, const char *fid_name);

extern jlong getJlongField(JNIEnv *env, jobject jobj, const char *fid_name);

extern jmethodID
getStaticJmethodID(JNIEnv *env, jobject jobj, const char *func_name, const char *func_signature);

extern void
setJobjectField(JNIEnv *env, jobject jobj, const char *fid_name, const char *fid_class_name,
                jobject new_value);

extern void setJstringField(JNIEnv *env, jobject jobj, const char *fid_name, jstring new_value);

extern void setJintField(JNIEnv *env, jobject jobj, const char *fid_name, jint new_value);

extern void setJlongField(JNIEnv *env, jobject jobj, const char *fid_name, jlong new_value);

extern void setJBooleanField(JNIEnv *env, jobject jobj, const char *fid_name, jboolean new_value);

extern jobject newJobjectInstance(JNIEnv *env, jclass cls);
/*<----------body----------/>*/
#ifdef __cplusplus
}
#endif//__cplusplus

#endif//__COMM_PROT_JNI_TOOL_H__