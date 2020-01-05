#include "string.h"
#include "stdlib.h"
#include "comm_prot_jni_tool.h"
#include "comm_prot_jni_def.h"

/*<jstring to char* >*/
char *jstring2chars(JNIEnv *env, jstring jstr) {
    char *cstr = NULL;

#ifndef USE_STRDUP
    jclass cls = (*env)->FindClass(env, CLASS_PATH_String);
    jstring strencode = (*env)->NewStringUTF(env, "utf-8");
    jmethodID mid = (*env)->GetMethodID(env, cls, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray bytes = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid, strencode);
    jsize len = (*env)->GetArrayLength(env, bytes);
    jbyte* b = (*env)->GetByteArrayElements(env, bytes, JNI_FALSE);
    if (len > 0)
    {
        cstr = (char*) malloc(len + 1);
        memcpy(cstr, b, len);
        cstr[len] = '\0';
    }
    (*env)->ReleaseByteArrayElements(env, bytes, b, 0);
#else
    const char *tmp_str = (*env)->GetStringUTFChars(env, jstr, NULL);

    cstr = strdup(tmp_str);
    (*env)->ReleaseStringUTFChars(env, jstr, tmp_str);
#endif
    return cstr;
}
/*<jstring to char* />*/

/*<char* to jstring >*/
jstring chars2jsrting(JNIEnv *env, const char *str) {
    jclass tmp = (*env)->FindClass(env, CLASS_PATH_String);
    jstring g_jclass_String = (jclass) ((*env)->NewGlobalRef(env, tmp));
    jmethodID mid = (*env)->GetMethodID(env, g_jclass_String, "<init>", "([BLjava/lang/String;)V");
    jbyteArray bytes = (*env)->NewByteArray(env, strlen(str));
    (*env)->SetByteArrayRegion(env, bytes, 0, strlen(str), (jbyte *) str);
    jstring encoding = (*env)->NewStringUTF(env, "utf-8");
    return (jstring) (*env)->NewObject(env, g_jclass_String, mid, bytes, encoding);
}
/*<char* to jstring />*/

/*<getJobjectField >*/
jobject
getJobjectField(JNIEnv *env, jobject jobj, const char *fid_name, const char *fid_signature) {
    jclass cls = (*env)->GetObjectClass(env, jobj);
    jfieldID fid = (*env)->GetFieldID(env, cls, fid_name, fid_signature);
    return (*env)->GetObjectField(env, jobj, fid);
}
/*<getJobjectField />*/

/*<getJstringField >*/
jstring getJstringField(JNIEnv *env, jobject jobj, const char *fid_name) {
    return (jstring) getJobjectField(env, jobj, fid_name, "Ljava/lang/String;");
}
/*<getJstringField />*/

/*<getJintField >*/
jint getJintField(JNIEnv *env, jobject jobj, const char *fid_name) {
    jclass cls = (*env)->GetObjectClass(env, jobj);
    jfieldID fid = (*env)->GetFieldID(env, cls, fid_name, "I");
    return (*env)->GetIntField(env, jobj, fid);
}
/*<getJintField />*/

/*<getJlongField >*/
jlong getJlongField(JNIEnv *env, jobject jobj, const char *fid_name) {
    jclass cls = (*env)->GetObjectClass(env, jobj);
    jfieldID fid = (*env)->GetFieldID(env, cls, fid_name, "J");
    return (*env)->GetLongField(env, jobj, fid);
}
/*<getJlongField />*/

/*<getStaticJmethodID >*/
jmethodID
getStaticJmethodID(JNIEnv *env, jclass cls/*const char *class_name*/, const char *func_name,
                   const char *func_signature) {

    return (jmethodID) (*env)->GetStaticMethodID(env, cls, func_name, func_signature);
}
/*<getStaticJmethodID />*/

/*<setJobjectField >*/
void setJobjectField(JNIEnv *env, jobject jobj, const char *fid_name, const char *fid_signature,
                     jobject new_value) {

    jclass cls = (*env)->GetObjectClass(env, jobj);
    jfieldID fid = (*env)->GetFieldID(env, cls, fid_name, fid_signature);
    (*env)->SetObjectField(env, jobj, fid, new_value);
}
/*<setJobjectFieldv />*/

/*<setJstringField >*/
void setJstringField(JNIEnv *env, jobject jobj, const char *fid_name, jstring new_value) {

    setJobjectField(env, jobj, fid_name, "Ljava/lang/String;", new_value);
}
/*<setJstringField />*/

/*<setJintField >*/
void setJintField(JNIEnv *env, jobject jobj, const char *fid_name, jint new_value) {

    jclass cls = (*env)->GetObjectClass(env, jobj);
    jfieldID fid = (*env)->GetFieldID(env, cls, fid_name, "I");
    (*env)->SetIntField(env, jobj, fid, new_value);
}
/*<setJintField />*/

/*<setJlongField >*/
void setJlongField(JNIEnv *env, jobject jobj, const char *fid_name, jlong new_value) {

    jclass cls = (*env)->GetObjectClass(env, jobj);
    jfieldID fid = (*env)->GetFieldID(env, cls, fid_name, "J");
    (*env)->SetLongField(env, jobj, fid, new_value);
}
/*<setJlongField />*/

/*<setJBooleanField >*/
void setJBooleanField(JNIEnv *env, jobject jobj, const char *fid_name, jboolean new_value) {

    jclass cls = (*env)->GetObjectClass(env, jobj);
    jfieldID fid = (*env)->GetFieldID(env, cls, fid_name, "Z");
    (*env)->SetBooleanField(env, jobj, fid, new_value);
}
/*<setJBooleanField />*/

/*<newJobjectInstance >*/
jobject newJobjectInstance(JNIEnv *env, jclass cls/*const char *class_name*/) {

    jmethodID mid = (*env)->GetMethodID(env, cls, "<init>", "()V");
    return (*env)->NewObject(env, cls, mid);
}
/*<newJobjectInstance />*/

/*<newJobjectArrayInstance >*/
jobject newJobjectArrayInstance(JNIEnv *env, jclass cls/*const char *class_name*/, jsize arrLen) {

    return (*env)->NewObjectArray(env, arrLen, cls, NULL);
}
/*<newJobjectArrayInstance />*/